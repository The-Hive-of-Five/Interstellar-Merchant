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

    private ArrayList<String> itemNames;
    private ArrayList<String> itemPrices;
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
        viewHolder.sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int finalValue = 0;
                try {

                    if (viewHolder.quantityEdit != null) {
                        Log.d(TAG, "IN THE TRY IF STATEMENT");

                        String val = viewHolder.quantityEdit.getText().toString();

                        finalValue = Integer.parseInt(val);
                    }
                } catch (Exception e) { // CHANGE THIS, IF THE INT PARSE THROWS AN EXCEPTION IT JUST SETS AMOUNT TO 0
                    finalValue = 0;
                }
                Log.d(TAG, "amount is " + finalValue);

                mv.sellItem(finalValue, i);
                Log.d(TAG, "onClick: clicked on sell");
                //get quantity from @+id/quantity_edit
                //need calculations for if player can buy, display in toast message below
                Toast.makeText(itemContext,"[Sell result shown here]",Toast.LENGTH_LONG).show();
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
