package com.quandoo.restaurant.ui.views.fragment;

import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quandoo.restaurant.R;
import com.quandoo.restaurant.domain.model.CustomerModel;
import com.quandoo.restaurant.ui.views.adapters.CustomersAdapter;
import com.quandoo.restaurant.ui.views.dialogs.SimpleConfirmationDialog;
import com.quandoo.restaurant.viewmodel.CustomersViewModel;
import com.quandoo.restaurant.viewmodel.NavWrapper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Behzad on 12/30/2017.
 */

public class CustomersFragment
        extends BaseFragment
        implements SearchView.OnQueryTextListener {

    public static final String NAME = "customers_fragment";
    private static final String KEY_SEARCH = "search";

    @Inject
    CustomersViewModel mCustomersViewModel;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.searchView)
    SearchView mSearchView;

    private CustomersAdapter mAdapter = new CustomersAdapter();
    private String mQuery = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.layout_cutomers, container, true);
        ButterKnife.bind(this, layout);
        initViews();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);


        observeLoadingStatus(mCustomersViewModel.getLoadingStatus());
        observeNavigationStatus(mCustomersViewModel.getNavigationStatus());
        observeCustomersStatus();
        observeCustomerUpdateStatus();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter.getItemCount() <= 0) {
            Timber.i("There is no items found, loading customers is going to be called");
            retry();
        }
    }

    private void observeCustomersStatus() {
        mCustomersViewModel.getCustomersStatus()
                .observe(getActivity(), response -> {
                    if (response.error != null) {
                        Timber.e(response.error, "Customers' status returned an error");
                        mActivity.showError(response.error.getMessage());
                        mActivity.showRetry();
                    } else if (response.data == null || response.data.isEmpty()) {
                        Timber.w("Empty data is returned, a list of customers is expected.");
                        mActivity.showError(getString(R.string.empty_result));
                        mActivity.showRetry();
                    } else {
                        Timber.i("A list of customers is successfully returned");
                        mAdapter.setCustomers(response.data);
                        mSearchView.setVisibility(View.VISIBLE);
                        mSearchView.onActionViewCollapsed();
                    }
                    mRefreshLayout.setRefreshing(false);
                });
    }

    private void observeCustomerUpdateStatus() {
        mCustomersViewModel.getCustomerUpdateStatus()
                .observe(getActivity(), customer -> {
                    mAdapter.updateCustomer(customer);
                });
    }

    @Override
    protected void observeNavigationStatus(MutableLiveData<NavWrapper> status) {
        super.observeNavigationStatus(status);
        status.observe(getActivity(), navWrapper -> {
            saveStates();
            switch (navWrapper.screen) {
                case CancelReservationConfirm:
                    askForCancellation((CustomerModel) navWrapper.data);
                    break;
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mAdapter.getFilter().filter(query);
        return true;
    }

    @Override
    public void retry() {
        mCustomersViewModel.loadCustomers();
        Timber.i("Load customers is called!");
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mAdapter.getFilter().filter(newText);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_SEARCH, mSearchView.getQuery().toString());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mQuery = savedInstanceState.getString(KEY_SEARCH);
        }
    }

    @Override
    public boolean onBackPressed() {
        if (!mSearchView.isIconified()) {
            mSearchView.onActionViewCollapsed();
            Timber.i("Back press is handled in customers' fragment!");
            return true;
        }
        return super.onBackPressed();
    }

    private void saveStates() {
        mQuery = mSearchView.getQuery().toString();
    }

    private void askForCancellation(final CustomerModel customer) {
        SimpleConfirmationDialog confitmation = SimpleConfirmationDialog.getInstance(
                getString(R.string.dialog_cancel_reservation_title),
                getString(R.string.dialog_cancel_reservation_message2,
                        customer.getName(), customer.getFamily()))
                .setOnPositiveClicked((dialogInterface, i) -> {
                    mCustomersViewModel.cancelReservation(customer);
                });
        showDialog(confitmation);
        Timber.i("Reservation's cancellation dialog is showed.");
    }

    private void initViews() {
        mActivity.setTitle(getString(R.string.fragment_customers_title));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((view, item) ->
                mCustomersViewModel.onCustomerSelected(item));
        mRefreshLayout.setOnRefreshListener(() -> retry());
        mSearchView.setOnClickListener(view -> {
            if (mSearchView.isIconified()) {
                mSearchView.onActionViewExpanded();
            }
            Timber.i("Searchview is clicked!");
        });
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setVisibility(mAdapter.getCustomers() != null ? View.VISIBLE : View.GONE);
        if (mQuery != null && !TextUtils.isEmpty(mQuery)) {
            mSearchView.onActionViewExpanded();
            mSearchView.setQuery(mQuery, false);
            Timber.i("The query \"%s\" is set for SearchView in initializing stage, which means a configuration change happened", mQuery);
        }
    }
}
