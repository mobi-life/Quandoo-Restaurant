package com.quandoo.restaurant.ui.views.adapters;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.quandoo.restaurant.R;
import com.quandoo.restaurant.domain.model.CustomerModel;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Behzad on 12/30/2017.
 */

public class CustomersAdapter
        extends RecyclerView.Adapter<CustomersAdapter.CustomersViewHolder>
        implements Filterable {

    private List<CustomerModel> mCustomers;
    private CustomerFilter mFilter;
    private OnItemClickListener<CustomerModel> mListener;
    private View.OnClickListener mOnCustomerItemViewClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClicked(view, (CustomerModel) view.getTag());
            }
            Timber.i("Customer item is clicked!");
        }
    };

    @Override
    public CustomersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_customer_item, parent, false);
        return new CustomersViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomersViewHolder holder, int position) {
        CustomerModel customer = mCustomers.get(position);
        holder.bind(customer);
    }

    @Override
    public int getItemCount() {
        if (mCustomers != null) {
            return mCustomers.size();
        }
        return 0;
    }

    public List<CustomerModel> getCustomers() {
        return mCustomers;
    }

    public void setCustomers(List<CustomerModel> customers) {
        if (this.mCustomers != customers) {
            this.mCustomers = customers;
            notifyDataSetChanged();
        }
        Timber.i("Customers list is updated");
    }

    public void setOnItemClickListener(OnItemClickListener<CustomerModel> listener) {
        this.mListener = listener;
    }

    public void updateCustomer(CustomerModel customer) {
        notifyItemChanged(mCustomers.indexOf(customer));
        Timber.i("Updated customer : %s %s",
                customer.getName()
                , customer.getFamily());
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new CustomerFilter(mCustomers, this);
        }
        return mFilter;
    }

    public class CustomersViewHolder extends RecyclerView.ViewHolder {

        public CardView itemView;
        @BindView(R.id.customerName)
        public TextView customerName;
        @BindView(R.id.avatar)
        public ImageView avatar;

        public CustomersViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = (CardView) itemView;
            this.itemView.setOnClickListener(mOnCustomerItemViewClick);
        }

        public void bind(CustomerModel customer) {
            itemView.setTag(customer);
            customerName.setText(customer.getName().concat(" ").concat(customer.getFamily()));
            if (customer.hasReservation()) {
                itemView.setCardBackgroundColor(Color.CYAN);
            } else {
                itemView.setCardBackgroundColor(Color.TRANSPARENT);
            }

            avatar.setImageResource(getRandomAvatar());
        }

        private int getRandomAvatar() {
            Random r = new Random();
            int index = r.nextInt((6 - 1) + 1) + 1;
            int resId = R.drawable.vector_avatar1;
            switch (index) {
                case 2:
                    resId = R.drawable.vector_avatar2;
                    break;
                case 3:
                    resId = R.drawable.vector_avatar3;
                    break;
                case 4:
                    resId = R.drawable.vector_avatar4;
                    break;
                case 5:
                    resId = R.drawable.vector_avatar5;
                    break;
                case 6:
                    resId = R.drawable.vector_avatar6;
                    break;
            }

            return resId;
        }
    }
}
