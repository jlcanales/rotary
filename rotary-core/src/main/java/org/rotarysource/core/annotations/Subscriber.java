package org.rotarysource.core.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by javadev on 7/2/17.
 */
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Subscriber {

    /**
     * EPL Statement for this Item
     */
    String eplStatement() default "";

    /**
     * Flag to indicate if statement stay active after registering
     */
    boolean active() default true;
}
