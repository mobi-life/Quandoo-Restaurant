package com.quandoo.restaurant.ui.views.adapters;

import android.view.View;

/**
 * Created by Behzad on 12/30/2017.
 */

public interface OnItemClickListener<T> {
    void onItemClicked(View view, T item);
}