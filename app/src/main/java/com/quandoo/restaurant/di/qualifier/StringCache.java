package com.quandoo.restaurant.di.qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by Behzad on 1/1/2018.
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface StringCache {
}
