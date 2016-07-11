package com.an1metall.gb_a_database;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RVViewHolder extends RecyclerView.ViewHolder {

    LinearLayout rvItem;
    TextView purchaseDescription;
    TextView purchaseCost;

    public RVViewHolder(View itemView) {
        super(itemView);
        rvItem = (LinearLayout) itemView.findViewById(R.id.rv_item_view);
        purchaseDescription = (TextView) itemView.findViewById(R.id.rv_item_purchase_description);
        purchaseCost = (TextView) itemView.findViewById(R.id.rv_item_purchase_cost);
    }
}
