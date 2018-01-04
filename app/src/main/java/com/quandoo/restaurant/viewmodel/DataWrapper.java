package com.quandoo.restaurant.viewmodel;

/**
 * Created by Behzad on 12/28/2017.
 */

public class DataWrapper<T> {
    public T data;
    public Throwable error;

    public DataWrapper(T data, Throwable error) {
        this.data = data;
        this.error = error;
    }
}
