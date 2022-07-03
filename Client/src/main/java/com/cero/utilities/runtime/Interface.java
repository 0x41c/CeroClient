package com.cero.utilities.runtime;


import com.cero.utilities.Logger;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.*;
import java.util.List;
import java.util.Map;


public class Interface implements InvocationHandler {

    public Interface(@NotNull Class<?> type, Object instance) {
        this.instance = instance;
        this.classData = type;
        this.fields = List.of(type.getFields());
        this.methods = List.of(type.getMethods()) ;
    }

    public Object instance;
    public Class<?> classData;
    public List<Field> fields;
    public List<Method> methods;

    // TODO: Deprecate public use of getFieldAt and setFieldAt.

    public <T> T getFieldAt(int offset) {
        T ret = null;
        Field field = fields.get(offset);
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
        Field field = fields.get(offset);
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
        for (Field field : fields) {
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
                loadField(field, offset + offsetPadding);
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
            List<Field> ourFields = List.of(ourClass.getDeclaredFields());
            int offset = 0;

            for (Field field : ourFields) {
                RuntimeField fieldInfo = field.getDeclaredAnnotation(RuntimeField.class);
                String identifier = fieldInfo.identifier();

                if (identifiers.containsKey(identifier)) {
                    Object value = identifiers.get(identifier);

                    if (value instanceof Map<?, ?> || value.getClass() == Boolean.class) {

                        if (value.getClass() == Boolean.class) {
                            if (!(boolean)value) continue; // Why would someone do this? idk...
                            loadField(field, offset + offsetPadding);
                            continue;
                        }

                        loadField(field, offset + offsetPadding);
                        try {
                            Object fieldValue = field.get(this);
                            if (!(fieldValue instanceof Interface))
                                Logger.error("Passed non-interface type as an identifier map: " + identifier);

                            assert fieldValue instanceof Interface;

                            ((Interface)fieldValue).loadFields((Map<String, ?>) value);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                            Logger.error("Couldn't get our own field? (" + field.getName() + ")");
                        }

                    } else {
                        Logger.error("Illegal value passed to loadFields as an identifier value: "
                                + value + " (" + value.getClass() + ")");
                    }
                    validatedIdentifiers++;
                }


                offset++;
            }

            if (validatedIdentifiers == identifiers.size()) break;

            ourClass = ourClass.getSuperclass();
            offsetPadding += ourFields.size() - 1;
        }
    }

    private void loadField(@NotNull Field field, int offset) {
        field.setAccessible(true);
        try {
            Object value = getFieldAt(offset);

            if (Interface.class.isAssignableFrom(field.getType()) && value != null) { // We need to instantiate this
                Class<?>[] defaultArgs = new Class[2];
                defaultArgs[0] = Class.class;
                defaultArgs[1] = Object.class;
                Constructor<?> defaultConstructor = ((Class<Interface>)field.getType()).getDeclaredConstructor(defaultArgs);
                value = defaultConstructor.newInstance(value.getClass(), value);
            }

            field.set(this, value);
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

    public void setFields(List<String> identifiers) {
        Class<?> ourClass = this.getClass();
        List<Field> ourFields = List.of(ourClass.getFields());

        for (Field field: ourFields) {
            RuntimeField mcField = ourClass.getAnnotation(RuntimeField.class);
            if (mcField == null) continue;
            if (identifiers.contains(mcField.identifier())) {
                Field remoteField = fields.get(mcField.offset());

                // For the memes
                field.setAccessible(true);
                remoteField.setAccessible(true);

                try {
                    Object value = field.get(this);

                    // This means we're proxying
                    // TODO: Recursive identifier list
                    if (value instanceof Interface) {}

                    remoteField.set(instance, value);
                } catch (IllegalAccessException exception) {
                    exception.printStackTrace();
                    Logger.error("Error setting field with identifier: \""
                            + mcField.identifier() + "\" (" + exception.getMessage() + ")");
                }
            }
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
