package com.cs2340.interstellarmerchant.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cs2340.interstellarmerchant.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MarketBuyRecyclerViewAdapter extends RecyclerView.Adapter<MarketBuyRecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "MarketBuyRecyclerViewAd";

    private ArrayList<String> itemNames = new ArrayList<>();
    private ArrayList<String> itemPrices = new ArrayList<>();
    private ArrayList<String> itemTotals = new ArrayList<>();
    private Context itemContext;

    public MarketBuyRecyclerViewAdapter(ArrayList<String> itemNames, ArrayList<String> itemPrices, ArrayList<String> itemTotals, Context itemContext) {
        this.itemNames = itemNames;
        this.itemPrices = itemPrices;
        this.itemContext = itemContext;
        this.itemTotals = itemTotals;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.market_shop_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: called");
        viewHolder.itemName.setText(itemNames.get(i));
        viewHolder.itemPrice.setText(itemPrices.get(i));
        viewHolder.itemTotal.setText(itemTotals.get(i));
        viewHolder.buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on buy");
                //get quantity from @+id/quantity_edit
                //need calculations for if player can buy, display in toast message below
                Toast.makeText(itemContext,"[Buy result shown here]",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemName;
        TextView itemPrice;
        TextView itemTotal;
        EditText quantityEdit;
        RelativeLayout buyLayout;
        Button buyButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.item_price);
            itemTotal = itemView.findViewById(R.id.total_available_shop);
            quantityEdit = itemView.findViewById(R.id.quantity_edit);
            buyLayout = itemView.findViewById(R.id.shop_parent_layout);
            buyButton = itemView.findViewById(R.id.buyButton);
        }
    }

}
