package com.cero.utilities.runtime;


import com.cero.utilities.Logger;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.*;
import java.util.List;



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
        List<Field> ourFields = List.of(ourClass.getFields());

        for (Field field : ourFields) {
            RuntimeField mcField = field.getAnnotation(RuntimeField.class);
            if (mcField == null) continue;
            loadField(field, mcField);
        }
    }

    public void loadFields(List<String> identifiers) {
        Class<?> ourClass = this.getClass();
        List<Field> ourFields = List.of(ourClass.getFields());
        int foundIdentifiers = 0;

        Logger.info("Loading fields: " + identifiers);

        for (Field field : ourFields) {
            RuntimeField mcField = field.getDeclaredAnnotation(RuntimeField.class);

            if (mcField != null) {
                Logger.info("Got field annotation: (offset = " + mcField.offset() + ", identifier = " + mcField.identifier() + ")");
            }

            if (mcField == null || !identifiers.contains(mcField.identifier())) continue;
            foundIdentifiers++;

            loadField(field, mcField);

            if (foundIdentifiers == identifiers.size()) break;
        }
    }

    private void loadField(@NotNull Field field, @NotNull RuntimeField fieldInfo) {
        field.setAccessible(true);
        try {
            Object value = getFieldAt(fieldInfo.offset());

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
