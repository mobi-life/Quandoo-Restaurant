package com.quandoo.restaurant.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Behzad on 12/25/2017.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
    /*
     *   Scope definition for making singletons local to Activity lifecycle
     */
}
