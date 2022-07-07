package com.cero.utilities.runtime;


import com.cero.sdk.client.Minecraft;
import com.cero.utilities.Logger;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Interface {

    public Interface(@NotNull Class<?> type, Object instance) {

        if (!typeCache.containsKey(this.getClass())) typeCache.put(this.getClass(), type);

        this.instance = instance;
        this.classData = type;
        this.remoteFields = List.of(type.getFields());
        this.remoteMethods = List.of(type.getMethods());

        this.ourClass = this.getClass();

        Class<?> currentClass = ourClass;
        while (currentClass != null && currentClass != Interface.class) {
            ourFields.addAll(List.of(currentClass.getDeclaredFields()));
            currentClass = currentClass.getSuperclass();
        }

        this.ourMethods = List.of(ourClass.getDeclaredMethods()); // TODO: Implement methods
    }

    static public HashMap<Class<?>, Class<?>> typeCache = new HashMap<>();

    public Object instance;
    public Class<?> classData;
    public List<Field> remoteFields;
    public List<Method> remoteMethods;

    public Class<?> ourClass;
    public ArrayList<Field> ourFields = new ArrayList<>();
    public List<Method> ourMethods;
    public HashMap<Integer, Object> fieldCache = new HashMap<>();


    static public <T> T newInstance(Object ...arguments) {
        Class<?> ourClass = MethodHandles.lookup().getClass();
        T ourValue = null;
        if (!typeCache.containsKey(ourClass))
            Logger.error("Couldn't get internal class type as it has not been instantiated yet.");
        try {
            ArrayList<Class<?>> classList = new ArrayList<>();
            for (Object arg : arguments) {
                classList.add(arg.getClass());
            }

            Class<?> internalClass = typeCache.get(ourClass);
            Constructor<?> constructor = internalClass.getDeclaredConstructor((Class<?>[])classList.toArray());
            Object internalVal = constructor.newInstance(arguments);

            Constructor<?> ourConstructor = ourClass.getDeclaredConstructors()[0];
            ourValue = (T) ourConstructor.newInstance(internalClass, internalVal);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Logger.error("Couldn't get constructor for " + ourClass.getName() + ", incorrect arguments");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Logger.error("Constructor threw exception: " + e.getMessage());
        } catch (InstantiationException e) {
            e.printStackTrace();
            Logger.error("Tried calling constructor of abstract class: " + e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Logger.error("Constructor is marked as private or is an inaccessible access level: " + e.getMessage());
        }

        assert ourValue != null;
        return ourValue;
    }


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

    public void verifiyFieldTypes() {
        Logger.info("Verifying types.");
        Logger.info("------------");
        if (remoteFields.size() != ourFields.size())
            Logger.warning("Mismatch in field sizes. (us: " + ourFields.size() + ", them: " + remoteFields.size() + ")");
        for (int i = 0; i < remoteFields.size(); i++) {
            Field mcField = remoteFields.get(i);
            if (i > ourFields.size() - 1) {
                Logger.info("[" + i + "] OOB field \"" + mcField.getName() + "\":\"" + mcField.getType() + "\"");
                continue;
            }
            Field ourField = ourFields.get(i);
            String ourFieldStringType = ourField.getType().getName();
            String mcFieldStringType = mcField.getType().getName();

            boolean mismatch = !mcFieldStringType.contains("minecraft") && !mcFieldStringType.contains("mojang")
                    ? !ourFieldStringType.equals(mcFieldStringType)
                    : mcField.getType().isAssignableFrom(ourField.getType());

            if (mismatch)
                Logger.warning("[" + i + "] \"" + mcField.getName() + "\":\"" + ourField.getName() + "\" "
                                + "\"" + mcField.getType().getName() + "\":\"" + ourField.getType().getName() + "\"");
            else Logger.info("[" + i + "] \"" + mcField.getName() + "\":\"" + ourField.getName() + "\" "
                    + "\"" + mcField.getType().getName() + "\":\"" + ourField.getType().getName() + "\"");

        }
    }

    public void loadAllFields() {
        for (int offset = 0; offset < ourFields.size(); offset++) {
            Field field = ourFields.get(offset);
            loadField(field, offset);
            initializeFieldIfNecessary(field);
        }
    }

    public void loadFields(List<Integer> offsets) {
        for (int offset : offsets) {
            Field field = ourFields.get(offset);
            if (field != null) {
                loadField(field, offset);
                initializeFieldIfNecessary(field);
            }
        }
    }

    private void initializeFieldIfNecessary(Field field) {
        Object value = null;
        try {
            value = field.get(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (value instanceof Interface && !(value instanceof Minecraft)) {
            ((Interface) value).loadAllFields();
        }
    }

    private void loadField(@NotNull Field field, int offset) {
        field.setAccessible(true);

        try {
            Object value = getFieldAt(offset);
            Object ourValue = field.get(this);

            // Instantiation
            if (Interface.class.isAssignableFrom(field.getType()) && value != null) {

                if (ourValue != null) {
                    assert ourValue instanceof Interface;
                    ((Interface) ourValue).instance = value;
                    fieldCache.put(offset, ourValue);
                    return;
                }

                Constructor<?> defaultConstructor = field.getType().getDeclaredConstructors()[0];
                assert defaultConstructor != null;
                value = defaultConstructor.newInstance(value.getClass(), value);
            }

            field.set(this, value);
            fieldCache.put(offset, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Logger.error("Couldn't set our own field? (" + field.getName() + ")");
        } catch (InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
            Logger.warning("Couldn't call " + field.getName() + " constructor: " + e);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Logger.warning("Error occurred in " + ourClass.getName() + " (" + instance.getClass().getName() + ")");
            Logger.warning("Got wrong type while updating field: " + field.getName());
            Logger.warning("Our field type: " + field.getType().getName());
            Logger.warning("Remote field type: " + remoteFields.get(offset).getType());
            Logger.warning("Offset: " + offset);
        }
    }


    public void applyChanges() {
        int index = 0;

        for (Field field : ourFields) {
            Object fieldCacheVal = fieldCache.get(index);

            field.setAccessible(true);

            try {
                Object value = field.get(this);

                if (value instanceof Interface && !(value instanceof Minecraft))
                    ((Interface)value).applyChanges();
                else if (fieldCacheVal != value) {
                    if (fieldCacheVal != null && value != null) {
                        if (value.equals(fieldCacheVal)) {
                            index++;
                            continue;
                        }
                    }
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
}
