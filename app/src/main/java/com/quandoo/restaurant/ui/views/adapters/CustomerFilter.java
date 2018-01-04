package com.quandoo.restaurant.ui.views.adapters;

import android.text.TextUtils;
import android.widget.Filter;

import com.quandoo.restaurant.domain.model.CustomerModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Behzad on 1/4/2018.
 */

public class CustomerFilter extends Filter {

    private List<CustomerModel> mCurrentList;
    private CustomersAdapter mAdapter;

    public CustomerFilter(List<CustomerModel> currentList, CustomersAdapter adapter) {
        this.mCurrentList = currentList;
        this.mAdapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults = new FilterResults();
        if (constraint != null && !TextUtils.isEmpty(constraint)) {
            constraint = constraint.toString().toLowerCase();
            List<CustomerModel> foundList = new ArrayList<>();
            for (CustomerModel customer : mCurrentList) {
                if (customer
                        .getName()
                        .concat(" ")
                        .concat(customer.getFamily())
                        .toLowerCase()
                        .contains(constraint.toString().toLowerCase())) {
                    foundList.add(customer);
                }
            }
            filterResults.count = foundList.size();
            filterResults.values = foundList;
        } else {
            filterResults.count = mCurrentList.size();
            filterResults.values = mCurrentList;
        }
        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        mAdapter.setCustomers((List<CustomerModel>) filterResults.values);
    }
}
