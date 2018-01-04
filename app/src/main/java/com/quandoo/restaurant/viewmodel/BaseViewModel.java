package com.quandoo.restaurant.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.quandoo.restaurant.helper.SchedulersFacade;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Behzad on 12/28/2017.
 */

public abstract class BaseViewModel extends ViewModel {

    protected final CompositeDisposable mDisposables = new CompositeDisposable();
    protected final MutableLiveData<NavWrapper> mNavigationStatus = new MutableLiveData<>();
    protected final MutableLiveData<Boolean> mLoadingStatus = new MutableLiveData<>();
    protected SchedulersFacade mSchedulersFacade;

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposables.dispose();
        mDisposables.clear();
    }

    public CompositeDisposable getDisposables() {
        return mDisposables;
    }

    public MutableLiveData<NavWrapper> getNavigationStatus() {
        return mNavigationStatus;
    }

    public MutableLiveData<Boolean> getLoadingStatus() {
        return mLoadingStatus;
    }
}
