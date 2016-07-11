package com.an1metall.gb_a_database;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVViewHolder> {

    Context context;
    List<Purchase> items;

    public RVAdapter(Context context, List<Purchase> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvitem_layout, parent, false);
        return new RVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class RVViewHolder extends RecyclerView.ViewHolder {

        LinearLayout rvItem;
        TextView purchaseDescription;
        TextView purchaseCost;

        private RVViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            rvItem = (LinearLayout) itemView.findViewById(R.id.rv_item_view);
            purchaseDescription = (TextView) itemView.findViewById(R.id.rv_item_purchase_description);
            purchaseCost = (TextView) itemView.findViewById(R.id.rv_item_purchase_cost);
        }

        private void bind(Purchase item){
            purchaseCost.setText(String.valueOf(item.get_cost()));
            purchaseDescription.setText(item.get_description());
        }
        
    }
}
