package com.cs2340.interstellarmerchant.views;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cs2340.interstellarmerchant.R;
import com.cs2340.interstellarmerchant.model.universe.market.items.OrderStatus;
import com.cs2340.interstellarmerchant.viewmodels.MarketViewModel;
import com.cs2340.interstellarmerchant.views.MarketMain;

import java.util.ArrayList;

public class MarketBuyRecyclerViewAdapter extends RecyclerView.Adapter<MarketBuyRecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "MarketBuyRecyclerViewAd";

    public ArrayList<String> itemNames;
    public ArrayList<String> itemPrices;
    private Context itemContext;
    public MarketViewModel mv;

    public MarketBuyRecyclerViewAdapter(ArrayList<String> itemNames, ArrayList<String> itemPrices, Context itemContext, MarketViewModel mv) {
        this.itemNames = itemNames;
        this.itemPrices = itemPrices;
        this.itemContext = itemContext;
        this.mv = mv;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.market_shop_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: called");
        viewHolder.itemName.setText(itemNames.get(i));
        viewHolder.itemPrice.setText(itemPrices.get(i));
        viewHolder.quantityEdit.setText("");
        viewHolder.buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on buy");
                int finalValue = 0;
                try {
                    if (viewHolder.quantityEdit != null) {
                        String val = viewHolder.quantityEdit.getText().toString();
                        //viewHolder.quantityEdit.setText("");
                        finalValue = Integer.parseInt(val);
                        OrderStatus os = mv.buyItem(finalValue, i);
                        if(os.equals(OrderStatus.SUCCESS)) {
                            mv.update();
                        } else {
                            Toast.makeText(itemContext,"buy did not go through: " + os.toString() ,Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(itemContext,"buy did not go through",Toast.LENGTH_LONG).show();
                }

                //get quantity from @+id/quantity_edit
                //need calculations for if player can buy, display in toast message below

            }
        });

    }

    @Override
    public int getItemCount() {
        return itemNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        TextView itemPrice;
        EditText quantityEdit;
        RelativeLayout buyLayout;
        Button buyButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.item_price);
            quantityEdit = itemView.findViewById(R.id.quantity_edit);
            buyLayout = itemView.findViewById(R.id.shop_parent_layout);
            buyButton = itemView.findViewById(R.id.buyButton);
        }


    }



}
