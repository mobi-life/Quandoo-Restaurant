package com.quandoo.restaurant.ui.views.fragment;

import com.quandoo.restaurant.di.component.ActivityComponent;
import com.quandoo.restaurant.di.component.AppComponent;
import com.quandoo.restaurant.domain.contract.Screen;

/**
 * Created by Behzad on 12/30/2017.
 */

public interface ActivityContract {
    void showProgress();

    void hideProgress();

    void showError(String message);

    void changeScreen(Screen screen, Object data);

    void setCurrentFragment(BaseFragment fragment);

    void setTitle(String title);

    void showRetry();

    ActivityComponent getActivityComponent();

    AppComponent getApplicationComponent();
}
