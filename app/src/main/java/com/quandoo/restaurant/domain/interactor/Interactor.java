package com.quandoo.restaurant.domain.interactor;

import io.reactivex.Single;

/**
 * Created by Behzad on 12/28/2017.
 */

public interface Interactor<T> {
    Single<T> execute();
}
