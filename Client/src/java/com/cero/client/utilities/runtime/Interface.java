package com.cero.client.utilities.runtime;


import com.cero.client.sdk.client.Minecraft;
import com.cero.client.utilities.Logger;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Interface {

    protected Interface(@NotNull final Class<?> type, final Object instance) {

        if (!Interface.typeCache.containsKey(getClass())) Interface.typeCache.put(getClass(), type);

        this.instance = instance;
        classData = type;
        remoteFields = List.of(type.getFields());
        remoteMethods = List.of(type.getMethods());

        ourClass = getClass();

        Class<?> currentClass = ourClass;
        while (null != currentClass && Interface.class != currentClass) {
            ourFields.addAll(List.of(currentClass.getDeclaredFields()));
            currentClass = currentClass.getSuperclass();
        }

        assert null != ourClass;
        ourMethods = List.of(ourClass.getDeclaredMethods()); // TODO: Implement methods
    }

    private static final HashMap<Class<?>, Class<?>> typeCache = new HashMap<>();

    public Object instance;
    private final Class<?> classData;
    private final List<Field> remoteFields;
    private final List<Method> remoteMethods;

    private final Class<?> ourClass;
    private final ArrayList<Field> ourFields = new ArrayList<>();
    private final List<Method> ourMethods;
    private final HashMap<Integer, Object> fieldCache = new HashMap<>();


    public static <T> T newInstance(final Object ... initargs) {
        final Class<?> ourClass = MethodHandles.lookup().getClass();
        T ourValue = null;
        if (!Interface.typeCache.containsKey(ourClass))
            Logger.error("Couldn't get internal class type as it has not been instantiated yet.");
        try {
            final ArrayList<Class<?>> classList = new ArrayList<>();
            for (final Object arg : initargs) {
                classList.add(arg.getClass());
            }

            final Class<?> internalClass = Interface.typeCache.get(ourClass);
            final Constructor<?> constructor = internalClass.getDeclaredConstructor((Class<?>[])classList.toArray());
            final Object internalVal = constructor.newInstance(initargs);

            final Constructor<?> ourConstructor = ourClass.getDeclaredConstructors()[0];
            ourValue = (T) ourConstructor.newInstance(internalClass, internalVal);
        } catch (final NoSuchMethodException e) {
            e.printStackTrace();
            Logger.error("Couldn't get constructor for " + ourClass.getName() + ", incorrect arguments");
        } catch (final InvocationTargetException e) {
            e.printStackTrace();
            Logger.error("Constructor threw exception: " + e.getMessage());
        } catch (final InstantiationException e) {
            e.printStackTrace();
            Logger.error("Tried calling constructor of abstract class: " + e.getMessage());
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
            Logger.error("Constructor is marked as private or is an inaccessible access level: " + e.getMessage());
        }

        assert null != ourValue;
        return ourValue;
    }

    public static Interface createInterface(@NotNull final Class<?> type, final Object instance) {
        return new Interface(type, instance);
    }


    // TODO: Deprecate public use of getFieldAt and setFieldAt.

    private <T> T getFieldAt(final int offset) {
        T ret = null;
        final Field field = remoteFields.get(offset);
        field.setAccessible(true);
        try {
            ret = (T) field.get(instance);
        } catch (final IllegalAccessException exception) {
            Logger.info("Couldn't get field at offset: " + offset + " " + exception.getMessage());
        }
        assert null != ret;
        return ret;
    }

    private <T> void setFieldAt(final int offset, final T value) {
        final Field field = remoteFields.get(offset);
        final int fieldModifiers = field.getModifiers();

        if (Modifier.FINAL == (fieldModifiers & Modifier.FINAL)) {
            Logger.warning("Attempted to mutate final field " + ourFields.get(offset).getName() + " (" + offset + ")");
        }

        field.setAccessible(true);
        try {
            field.set(instance, value);
        } catch (final IllegalAccessException exception) {
            exception.printStackTrace();
            Logger.error("Couldn't set field at offset: " + offset + " " + exception.getMessage());
        }
    }

    public void dumpFields() {
        int i = 0;
        Logger.info("Dumping class " + classData.getName());
        Logger.info("------------");
        for (final Field field : remoteFields) {
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
            final Field mcField = remoteFields.get(i);
            if (i > ourFields.size() - 1) {
                Logger.info("[" + i + "] OOB field \"" + mcField.getName() + "\":\"" + mcField.getType() + "\"");
                continue;
            }
            final Field ourField = ourFields.get(i);
            final String ourFieldStringType = ourField.getType().getName();
            final String mcFieldStringType = mcField.getType().getName();

            final boolean mismatch = !mcFieldStringType.contains("minecraft") && !mcFieldStringType.contains("mojang")
                    ? !ourFieldStringType.equals(mcFieldStringType)
                    : mcField.getType().isAssignableFrom(ourField.getType());

            if (mismatch)
                Logger.warning("[" + i + "] \"" + mcField.getName() + "\":\"" + ourField.getName() + "\" "
                                + "\"" + mcField.getType().getName() + "\":\"" + ourField.getType().getName() + "\"");
            else Logger.info("[" + i + "] \"" + mcField.getName() + "\":\"" + ourField.getName() + "\" "
                    + "\"" + mcField.getType().getName() + "\":\"" + ourField.getType().getName() + "\"");

        }
    }

    public final void loadAllFields() {
        for (int offset = 0; offset < ourFields.size(); offset++) {
            final Field field = ourFields.get(offset);
            loadField(field, offset);
            initializeFieldIfNecessary(field);
        }
    }

    public void loadFields(final List<Integer> offsets) {
        for (final int offset : offsets) {
            final Field field = ourFields.get(offset);
            if (null != field) {
                loadField(field, offset);
                initializeFieldIfNecessary(field);
            }
        }
    }

    private void initializeFieldIfNecessary(final Field field) {
        Object value = null;
        try {
            value = field.get(this);
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        }
        if (value instanceof Interface && !(value instanceof Minecraft)) {
            ((Interface) value).loadAllFields();
        }
    }

    private void loadField(@NotNull final Field field, final int offset) {
        field.setAccessible(true);

        try {
            Object value = getFieldAt(offset);
            final Object ourValue = field.get(this);

            // Instantiation
            if (Interface.class.isAssignableFrom(field.getType()) && null != value) {

                if (null != ourValue) {
                    assert ourValue instanceof Interface;
                    ((Interface) ourValue).instance = value;
                    fieldCache.put(offset, ourValue);
                    return;
                }

                final Constructor<?> defaultConstructor = field.getType().getDeclaredConstructors()[0];
                assert null != defaultConstructor;
                value = defaultConstructor.newInstance(value.getClass(), value);
            }

            field.set(this, value);
            fieldCache.put(offset, value);
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
            Logger.error("Couldn't set our own field? (" + field.getName() + ")");
        } catch (final InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
            Logger.warning("Couldn't call " + field.getName() + " constructor: " + e);
        } catch (final IllegalArgumentException e) {
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

        for (final Field field : ourFields) {
            final Object fieldCacheVal = fieldCache.get(index);

            field.setAccessible(true);

            try {
                final Object value = field.get(this);

                if (value instanceof Interface && !(value instanceof Minecraft))
                    ((Interface)value).applyChanges();
                else if (fieldCacheVal != value) {
                    if (null != fieldCacheVal && null != value) {
                        if (value.equals(fieldCacheVal)) {
                            index++;
                            continue;
                        }
                    }
                    setFieldAt(index, value);
                }

            } catch (final IllegalAccessException e) {
                e.printStackTrace();
                Logger.error("Couldn't get our own field? (" + field.getName() + ")");
            }

            index++;
        }
    }

    // TODO: Properly implement object swapping.

    @Override
    public String toString() {
        return "Interface{" +
                "instance=" + instance +
                ", classData=" + classData +
                ", remoteFields=" + remoteFields +
                ", remoteMethods=" + remoteMethods +
                ", ourClass=" + ourClass +
                ", ourFields=" + ourFields +
                ", ourMethods=" + ourMethods +
                ", fieldCache=" + fieldCache +
                '}';
    }
}
