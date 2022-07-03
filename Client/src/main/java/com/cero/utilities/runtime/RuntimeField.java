package com.cero.utilities.runtime;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Field info used for retrieving the property from the underlying
 * object and assigning it.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RuntimeField {
    // The offset of the field from the base of the class.
    public int offset();
    public String identifier();
}
