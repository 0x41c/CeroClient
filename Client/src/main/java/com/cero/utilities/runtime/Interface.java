package com.cero.utilities.runtime;


import com.cero.sdk.client.Minecraft;
import com.cero.utilities.Logger;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.*;
import java.util.*;


public class Interface implements InvocationHandler {

    public Interface(@NotNull Class<?> type, Object instance) {
        this.instance = instance;
        this.classData = type;
        this.remoteFields = List.of(type.getFields());
        this.remoteMethods = List.of(type.getMethods());

        Class<?> interfaceType = this.getClass().getClasses()[0];
        assert interfaceType != null;

        this.interfaceType = interfaceType;

        List<Field> interfaceFields = List.of(this.interfaceType.getDeclaredFields());

        assert interfaceFields.size() == this.remoteFields.size();

        for (Field field : interfaceFields) {
            field.setAccessible(true);
            try {
                Object value = field.get(null);
                assert value instanceof String;
                fieldIdentifiers.add((String)value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                Logger.info("Couldn't access interface field: " + field.getName() + " (" + e.getMessage() + ")");
            }
        }

        this.ourClass = this.getClass();
        this.ourFields = List.of(ourClass.getDeclaredFields());
        this.ourMethods = List.of(ourClass.getDeclaredMethods());
    }

    public Object instance;
    public Class<?> classData;
    public List<Field> remoteFields;
    public List<Method> remoteMethods;

    public Class<?> ourClass;
    public List<Field> ourFields;
    public List<Method> ourMethods;
    public Class<?> interfaceType;

    public ArrayList<String> fieldIdentifiers = new ArrayList<>();
    public HashMap<String, Object> fieldCache = new HashMap<>();

    private List<Class<?>> wrapperTypes = List.of(
            Boolean.class,
            Character.class,
            Byte.class,
            Short.class,
            Integer.class,
            Long.class,
            Float.class,
            Double.class,
            Void.class
    );

    // TODO: Deprecate public use of getFieldAt and setFieldAt.

    public <T> T getFieldAt(int offset) {
        T ret = null;
        Field field = remoteFields.get(offset);
        field.setAccessible(true);
        try {
            ret = (T) field.get(instance);
        } catch (IllegalAccessException exception) {
            Logger.info("Couldn't get field at offset: " + offset + " " + exception.getMessage());
        }
        assert ret != null;
        return ret;
    }

    public <T> void setFieldAt(int offset, T value) {
        Field field = remoteFields.get(offset);
        int fieldModifiers = field.getModifiers();

        if ((fieldModifiers & Modifier.FINAL) == Modifier.FINAL) {
            Logger.warning("Attempted to mutate final field " + ourFields.get(offset).getName() + " (" + offset + ")");
        }

        field.setAccessible(true);
        try {
            field.set(instance, value);
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
            Logger.error("Couldn't set field at offset: " + offset + " " + exception.getMessage());
        }
    }

    public void dumpFields() {
        int i = 0;
        Logger.info("Dumping class " + classData.getName());
        Logger.info("------------");
        for (Field field : remoteFields) {
            Logger.info("[" + i + "] Field \"" + field.getName() + "\": " + field.getType()); i++;
        }
        Logger.info("------------");
    }

    public void loadAllFields() {
        Class<?> ourClass = this.getClass();

        int offsetPadding = 0;

        while (ourClass != null && ourClass != Interface.class) {
            List<Field> ourFields = List.of(ourClass.getDeclaredFields());
            int offset = 0;

            for (Field field : ourFields) {
                loadField(field, offset + offsetPadding, fieldIdentifiers.get(offset));
                offset++;
            }

            ourClass = ourClass.getSuperclass();
            offsetPadding += ourFields.size() - 1;
        }
    }

    public void loadFields(Map<String, ?> identifiers) {
        Class<?> ourClass = this.getClass();
        int offsetPadding = 0;
        int validatedIdentifiers = 0;

        while (ourClass != null && ourClass != Interface.class) {
            int offset = 0;

            for (Field field : ourFields) {
                String identifier = fieldIdentifiers.get(offset);

                if (identifiers.containsKey(identifier)) {
                    Object value = identifiers.get(identifier);

                    if (value instanceof Map<?, ?>) {
                        loadField(field, offset + offsetPadding, identifier);
                        try {
                            Object fieldValue = field.get(this);
                            if (!(fieldValue instanceof Interface))
                                Logger.error("Passed non-interface type as an identifier map: " + identifier);
                            assert fieldValue instanceof Interface;
                            ((Interface)fieldValue).loadFields((Map<String, ?>)value);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                            Logger.error("Couldn't get our own field? (" + field.getName() + ")");
                        }
                    } else if (value.getClass() == Boolean.class) {
                        if (!(boolean)value) continue;
                        loadField(field, offset + offsetPadding, identifier);
                    } else {
                        Logger.error("Illegal value passed to loadFields as an identifier value: "
                                + value + " (" + value.getClass() + ")");
                    }
                    validatedIdentifiers++;
                    if (validatedIdentifiers == identifiers.size()) continue;
                }
                offset++;
            }

            if (validatedIdentifiers == identifiers.size()) break;

            ourClass = ourClass.getSuperclass();
            offsetPadding += ourFields.size() - 1;
        }
    }

    private void loadField(@NotNull Field field, int offset, @NotNull String identifier) {
        field.setAccessible(true);

        try {
            Object value = getFieldAt(offset);
            Object ourValue = field.get(this);

            // Instantiation
            if (Interface.class.isAssignableFrom(field.getType()) && value != null) {

                if (ourValue != null) {
                    assert ourValue instanceof Interface;
                    ((Interface) ourValue).instance = value;
                    fieldCache.put(identifier, ourValue);
                    return;
                }

                Logger.info("Instantiating field " + field.getName());
                Class<?>[] defaultArgs = new Class[2];
                defaultArgs[0] = Class.class;
                defaultArgs[1] = Object.class;
                Constructor<?> defaultConstructor = field.getType().getDeclaredConstructor(defaultArgs);
                value = defaultConstructor.newInstance(value.getClass(), value);
            }

            field.set(this, value);
            fieldCache.put(identifier, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Logger.error("Couldn't set our own field? (" + field.getName() + ")");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Logger.error("Couldn't get default interface constructor: " + e.getMessage());
        } catch (InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
            Logger.error("Couldn't call constructor: " + e.getMessage());
        }
    }


    public void applyChanges() {
        int index = 0;

        for (Field field : ourFields) {

            String identifier = fieldIdentifiers.get(index);
            Object fieldCacheVal = fieldCache.get(identifier);

            field.setAccessible(true);

            try {
                Object value = field.get(this);

                if (value instanceof Interface && !(value instanceof Minecraft))
                    ((Interface)value).applyChanges();
                else if (fieldCacheVal != value) {
                    if (fieldCacheVal != null && value != null)
                        if (value.equals(fieldCacheVal)) {
                            index++;
                            continue;
                        }

                    Logger.info("Setting field: " + field.getName());
                    Logger.info("Object value: " + value);
                    Logger.info(" Cache value: " + fieldCacheVal);
                    setFieldAt(index, value);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
                Logger.error("Couldn't get our own field? (" + field.getName() + ")");
            }

            index++;
        }
    }

    // TODO: Properly implement object swapping.
    // WARNING: Don't use this without finishing its implementation, using it would be a sin.
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        if (!methods.contains(method)) return method.invoke(proxy, method);
//        int methodIndex = methods.indexOf(method);

        return null;
    }
}
