package com.quandoo.restaurant.ui.views.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.quandoo.restaurant.QuandooRestaurantApp;
import com.quandoo.restaurant.R;
import com.quandoo.restaurant.di.component.ActivityComponent;
import com.quandoo.restaurant.di.component.AppComponent;
import com.quandoo.restaurant.di.component.DaggerActivityComponent;
import com.quandoo.restaurant.domain.contract.Screen;
import com.quandoo.restaurant.ui.views.dialogs.SimpleMessageDialog;
import com.quandoo.restaurant.ui.views.fragment.ActivityContract;
import com.quandoo.restaurant.ui.views.fragment.BaseFragment;
import com.quandoo.restaurant.ui.views.fragment.CustomersFragment;
import com.quandoo.restaurant.ui.views.fragment.TablesFragment;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity
        extends AppCompatActivity
        implements ActivityContract {

    @BindView(R.id.progress)
    View mProgress;
    @BindView(R.id.retry)
    Button mRetry;
    @BindView(R.id.fragment_container)
    FrameLayout mContainer;
    private ActivityComponent mComponent;
    private BaseFragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        getActivityComponent().inject(this);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            addFragment(new CustomersFragment(), CustomersFragment.NAME);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putBoolean("change", true);
    }

    @Override
    public void onBackPressed() {
        if (mCurrentFragment != null
                && mCurrentFragment.onBackPressed()) {
            return;
        }
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            mContainer.removeAllViews();
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    @Override
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        SimpleMessageDialog error = SimpleMessageDialog
                .getInstance(getString(R.string.error), message);
        error.show(getSupportFragmentManager(), "error");
    }

    @Override
    public void changeScreen(Screen screen, Object data) {
        switch (screen) {
            case Tables:
                showTablesScreen((Serializable) data);
                break;
        }
    }

    @Override
    public void setCurrentFragment(BaseFragment fragment) {
        mCurrentFragment = fragment;
    }

    @Override
    public void setTitle(String title) {
        getSupportActionBar()
                .setTitle(title);
    }

    @Override
    public void showRetry() {
        mRetry.setVisibility(View.VISIBLE);
        mRetry.setOnClickListener(view -> {
            mRetry.setVisibility(View.GONE);
            mCurrentFragment.retry();
        });
    }

    @Override
    public ActivityComponent getActivityComponent() {
        if (mComponent == null) {
            mComponent = DaggerActivityComponent.builder()
                    .appComponent(QuandooRestaurantApp.getComponent())
                    .build();
        }
        return mComponent;
    }

    @Override
    public AppComponent getApplicationComponent() {
        return QuandooRestaurantApp.getComponent();
    }

    private void addFragment(BaseFragment fragment, String tag) {
        mContainer.removeAllViews();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, tag)
                .addToBackStack(tag)
                .commit();
        mCurrentFragment = fragment;
    }

    private void showTablesScreen(Serializable data) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TablesFragment.FIELD_DATA, data);
        TablesFragment fragment = new TablesFragment();
        fragment.setArguments(bundle);
        addFragment(fragment, TablesFragment.NAME);
    }
}
