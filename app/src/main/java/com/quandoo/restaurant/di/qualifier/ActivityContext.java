package com.quandoo.restaurant.di.qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by Behzad on 12/27/2017.
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityContext {
}
