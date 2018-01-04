package com.quandoo.restaurant.ui.views.adapters;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quandoo.restaurant.R;
import com.quandoo.restaurant.domain.model.ReservationModel;
import com.quandoo.restaurant.domain.model.TableModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Behzad on 12/30/2017.
 */

public class TablesAdapter extends RecyclerView.Adapter<TablesAdapter.TablesViewHolder> {

    private List<TableModel> mTables;
    private OnItemClickListener<TableModel> mListener;
    private View.OnClickListener mOnTableItemViewClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClicked(view, (TableModel) view.getTag());
            }
        }
    };

    @Override
    public TablesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_table_item, parent, false);
        return new TablesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TablesViewHolder holder, int position) {
        TableModel item = mTables.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        if (mTables != null) {
            return mTables.size();
        }
        return 0;
    }

    public List<TableModel> getTables() {
        return mTables;
    }

    public void setTables(List<TableModel> tables) {
        this.mTables = tables;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<TableModel> listener) {
        this.mListener = listener;
    }

    public void updateTableState(ReservationModel tableReserved) {
        int tableIndex = findTable(tableReserved.getTableNumber());
        if (tableIndex >= 0) {
            TableModel table = mTables.get(tableIndex);
            if (tableReserved.isCancellation()) {
                table.clearReservation();
            } else {
                table.setAsReserved(tableReserved.getCustomerId());
            }
            notifyItemChanged(tableIndex);
        }
    }

    private int findTable(int tableNo) {
        for (int i = 0; i < mTables.size(); i++) {
            if (mTables.get(i).getTableNumber() == tableNo) {
                return i;
            }
        }
        return -1;
    }

    public class TablesViewHolder extends RecyclerView.ViewHolder {

        CardView itemView;
        @BindView(R.id.table_number)
        TextView tableNumber;

        private ColorStateList originalColor;

        public TablesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = (CardView) itemView;
            this.itemView.setOnClickListener(mOnTableItemViewClick);
            originalColor = this.itemView.getCardBackgroundColor();
        }

        public void bind(TableModel item) {
            itemView.setTag(item);
            tableNumber.setText(item.getTableNumber() + "");
            if (item.isReserved()) {
                if (item.getCustomerId() > 0) {
                    itemView.setCardBackgroundColor(Color.CYAN);
                } else {
                    itemView.setCardBackgroundColor(Color.LTGRAY);
                }
            } else {
                itemView.setCardBackgroundColor(originalColor);
            }
        }
    }
}
