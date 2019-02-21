package com.cs2340.interstellarmerchant.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cs2340.interstellarmerchant.R;

import java.util.ArrayList;

public class MarketBuyRecyclerViewAdapter extends RecyclerView.Adapter<MarketBuyRecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "MarketBuyRecyclerViewAd";

    private ArrayList<String> itemNames = new ArrayList<>();
    private ArrayList<String> itemPrices = new ArrayList<>();
    private Context itemContext;

    public MarketBuyRecyclerViewAdapter(ArrayList<String> itemNames, ArrayList<String> itemPrices, Context itemContext) {
        this.itemNames = itemNames;
        this.itemPrices = itemPrices;
        this.itemContext = itemContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.market_shop_item, viewGroup, false);

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
