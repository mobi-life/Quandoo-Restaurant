package com.quandoo.restaurant.ui.views.fragment;

import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quandoo.restaurant.R;
import com.quandoo.restaurant.domain.exceptions.MultipleTableReservationException;
import com.quandoo.restaurant.domain.model.CustomerModel;
import com.quandoo.restaurant.domain.model.TableModel;
import com.quandoo.restaurant.ui.views.adapters.TablesAdapter;
import com.quandoo.restaurant.ui.views.dialogs.SimpleConfirmationDialog;
import com.quandoo.restaurant.ui.views.dialogs.SimpleMessageDialog;
import com.quandoo.restaurant.viewmodel.NavWrapper;
import com.quandoo.restaurant.viewmodel.TablesViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Behzad on 12/30/2017.
 */

public class TablesFragment extends BaseFragment {

    public static final String NAME = "tables_fragment";
    public static final String FIELD_DATA = "data";
    @Inject
    TablesViewModel mTablesViewModel;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private CustomerModel mCustomer;
    private TablesAdapter mAdapter = new TablesAdapter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.layout_tables, container, true);
        ButterKnife.bind(this, layout);
        initViews();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter.getItemCount() <= 0) {
            retry();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);

        Bundle args = getArguments();
        if (args != null && args.containsKey(FIELD_DATA)) {
            mCustomer = (CustomerModel) args.getSerializable(FIELD_DATA);
        }

        observeLoadingStatus(mTablesViewModel.getLoadingStatus());
        observeNavigationStatus(mTablesViewModel.getNavigationStatus());
        observeTablesStatus();
        observeReservationStatus();

    }

    @Override
    protected void observeNavigationStatus(MutableLiveData<NavWrapper> status) {
        super.observeNavigationStatus(status);
        status.observe(getActivity(), navWrapper -> {
            switch (navWrapper.screen) {
                case ReservationConfirm:
                    askForReservation((TableModel) navWrapper.data);
                    break;
                case CancelReservationConfirm:
                    askForCancelation((TableModel) navWrapper.data);
                    break;
                case ReservationCancellationAlert:
                    showCancelationImpossibleAlert((TableModel) navWrapper.data);
                    break;
            }
        });
    }

    private void askForCancelation(final TableModel table) {
        SimpleConfirmationDialog confitmation = SimpleConfirmationDialog.getInstance(
                getString(R.string.dialog_cancel_reservation_title),
                getString(R.string.dialog_cancel_reservation_message,
                        table.getTableNumber()))
                .setOnPositiveClicked((dialogInterface, i) -> {
                    mTablesViewModel.cancelReservation(table);
                });
        showDialog(confitmation);
    }

    private void showCancelationImpossibleAlert(final TableModel table) {
        SimpleMessageDialog alert = SimpleMessageDialog
                .getInstance(getString(R.string.dialog_reservation_alert_title),
                        getString(R.string.dialog_reservation_alert_message,
                                table.getTableNumber()));
        showDialog(alert);
    }

    private void askForReservation(final TableModel table) {
        SimpleConfirmationDialog confitmation = SimpleConfirmationDialog.getInstance(
                getString(R.string.dialog_reservation_title),
                getString(R.string.dialog_reservation_message,
                        table.getTableNumber(), mCustomer.getName(), mCustomer.getFamily()))
                .setOnPositiveClicked((dialogInterface, i) -> {
                    mTablesViewModel.saveReservationFor(table, mCustomer);
                });
        showDialog(confitmation);
    }

    private void observeTablesStatus() {
        mTablesViewModel.getTablesStatus()
                .observe(getActivity(), response -> {
                    if (response.error != null) {
                        mActivity.showError(response.error.getMessage());
                        mActivity.showRetry();
                    } else if (response.data == null || response.data.isEmpty()) {
                        mActivity.showError(getString(R.string.empty_result));
                        mActivity.showRetry();
                    } else {
                        mAdapter.setTables(response.data);
                    }
                });
    }

    @Override
    public void retry() {
        mTablesViewModel.loadTables();
    }

    private void observeReservationStatus() {
        mTablesViewModel.getReservationStatus()
                .observe(getActivity(), response -> {
                    if (response.error != null) {
                        if (response.error instanceof MultipleTableReservationException) {
                            mActivity.showError(getString(R.string.error_multiple_table_reservation));
                        } else {
                            mActivity.showError(response.error.getMessage());
                        }
                    } else {
                        mAdapter.updateTableState(response.data);
                        mCustomer.setHasReservation(!response.data.isCancellation());
                    }
                });

    }

    private void initViews() {
        mActivity.setTitle(getString(R.string.fragment_tables_title));
        int cols = getResources().getInteger(R.integer.int_tables_col_count);
        mRecyclerView.setLayoutManager(new GridLayoutManager(
                getContext(),
                cols,
                GridLayoutManager.VERTICAL,
                false
        ));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((view, item) ->
                mTablesViewModel.onTableSelected(item));
    }

}
