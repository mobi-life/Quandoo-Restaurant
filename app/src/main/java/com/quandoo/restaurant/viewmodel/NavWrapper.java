package com.quandoo.restaurant.viewmodel;

import com.quandoo.restaurant.domain.contract.Screen;

/**
 * Created by Behzad on 12/28/2017.
 */

public class NavWrapper<T> {
    public Screen screen;
    public T data;

    public NavWrapper(Screen screen, T data) {
        this.screen = screen;
        this.data = data;
    }
}
