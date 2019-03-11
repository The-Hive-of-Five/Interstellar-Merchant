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
import com.cs2340.interstellarmerchant.viewmodels.MarketViewModel;

import java.util.ArrayList;

public class MarketSellRecyclerViewAdapter extends RecyclerView.Adapter<MarketSellRecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "MarketSellRecyclerViewAd";

    public ArrayList<String> itemNames;
    public ArrayList<String> itemPrices;
    private Context itemContext;
    public MarketViewModel mv;


    public MarketSellRecyclerViewAdapter(ArrayList<String> itemNames, ArrayList<String> itemPrices, Context itemContext, MarketViewModel mv) {
        this.itemNames = itemNames;
        this.itemPrices = itemPrices;
        this.itemContext = itemContext;
        this.mv = mv;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.market_cargo_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: called");
        viewHolder.itemName.setText(itemNames.get(i));
        viewHolder.itemPrice.setText(itemPrices.get(i));
        viewHolder.quantityEdit.setText("");
        viewHolder.sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int finalValue;
                try {

                    if (viewHolder.quantityEdit != null) {
                        String val = viewHolder.quantityEdit.getText().toString();
                        viewHolder.quantityEdit.setText("");
                        finalValue = Integer.parseInt(val);
                        mv.sellItem(finalValue, i);
                        mv.update();
                    }
                } catch (Exception e) { // CHANGE THIS, IF THE INT PARSE THROWS AN EXCEPTION IT JUST SETS AMOUNT TO 0
                    Toast.makeText(itemContext,"Sell did not go through",Toast.LENGTH_LONG).show();
                }
                //Log.d(TAG, "amount is " + finalValue);


                //Log.d(TAG, "onClick: clicked on sell");
                //get quantity from @+id/quantity_edit
                //need calculations for if player can buy, display in toast message below
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
        EditText quantityEdit;
        RelativeLayout sellLayout;
        Button sellButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.cargo_item_name);
            itemPrice = itemView.findViewById(R.id.cargo_item_price);
            quantityEdit = itemView.findViewById(R.id.cargo_quantity_edit);
            sellLayout = itemView.findViewById(R.id.cargo_parent_layout);
            sellButton = itemView.findViewById(R.id.sell_Button);
        }

    }




}
