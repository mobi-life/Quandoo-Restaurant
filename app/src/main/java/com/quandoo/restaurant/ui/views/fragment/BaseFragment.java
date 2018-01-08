package com.quandoo.restaurant.ui.views.fragment;

import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quandoo.restaurant.di.component.DaggerFragmentComponent;
import com.quandoo.restaurant.di.component.FragmentComponent;
import com.quandoo.restaurant.di.module.FragmentModule;
import com.quandoo.restaurant.domain.contract.ActivityContract;
import com.quandoo.restaurant.ui.views.dialogs.BaseDialog;
import com.quandoo.restaurant.viewmodel.NavWrapper;

import timber.log.Timber;

/**
 * Created by Behzad on 12/30/2017.
 */

public class BaseFragment extends Fragment {

    protected ActivityContract mActivity;
    protected FragmentComponent mComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (ActivityContract) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity.setCurrentFragment(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void showDialog(BaseDialog dialog) {
        dialog.show(((AppCompatActivity) getActivity()).getSupportFragmentManager(),
                dialog.toString());
    }

    protected FragmentComponent getComponent() {
        if (mComponent == null) {
            mComponent = DaggerFragmentComponent.builder()
                    .appComponent(mActivity.getApplicationComponent())
                    .fragmentModule(new FragmentModule())
                    .build();
        }
        return mComponent;
    }

    protected void observeLoadingStatus(MutableLiveData<Boolean> status) {
        status.observe(getActivity(), state -> {
            if (state) {
                mActivity.showProgress();
                Timber.i("Show progress is called");
            } else {
                mActivity.hideProgress();
                Timber.i("Hide progress is called");
            }
        });
    }

    protected void observeNavigationStatus(MutableLiveData<NavWrapper> status) {
        status.observe(getActivity(), state -> {
            mActivity.changeScreen(state.screen, state.data);
            Timber.i("Screen change is called for %s", state.screen.name());
        });
    }

    public boolean onBackPressed() {
        return false;
    }

    public void retry() {
    }
}
