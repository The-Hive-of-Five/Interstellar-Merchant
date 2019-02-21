package com.cs2340.interstellarmerchant.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cs2340.interstellarmerchant.R;

public class MarketBuyRecyclerViewAdapter extends RecyclerView.Adapter<MarketBuyRecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "MarketBuyRecyclerViewAd";

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemName;
        TextView itemPrice;
        Spinner quantitySpinner;
        RelativeLayout buyLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.item_price);
            quantitySpinner = itemView.findViewById(R.id.quantity_spinner);
            buyLayout = itemView.findViewById(R.id.shop_parent_layout);
        }
    }

}
