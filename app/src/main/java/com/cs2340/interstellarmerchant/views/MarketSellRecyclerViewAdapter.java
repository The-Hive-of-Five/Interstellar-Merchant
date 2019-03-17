package com.cs2340.interstellarmerchant.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.List;

public class MarketSellRecyclerViewAdapter extends RecyclerView.Adapter<MarketSellRecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "MarketSellRecyclerViewAd";

    public ArrayList<String> itemNames = new ArrayList<>();
    public ArrayList<String> itemPrices = new ArrayList<>();
    public ArrayList<String> itemTotals = new ArrayList<>();
    public static List<String> mEditTextValues = new ArrayList<>(10);


    private Context itemContext;
    public MarketViewModel mv;


    public MarketSellRecyclerViewAdapter(ArrayList<String> itemNames, ArrayList<String> itemPrices, Context itemContext, ArrayList<String> itemTotals, MarketViewModel mv) {
        this.itemNames = itemNames;
        this.itemPrices = itemPrices;
        this.itemContext = itemContext;
        this.itemTotals = itemTotals;
        this.mv = mv;
        for(int i = 0; i < 20; i++){
            mEditTextValues.add("");
        }
        notifyDataSetChanged();
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
        viewHolder.itemTotal.setText(itemTotals.get(i));
        viewHolder.mEditText.setTag(i);
        viewHolder.mEditText.setText("");
    }

    @Override
    public int getItemCount() {
        return itemNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemName;
        TextView itemPrice;
        TextView itemTotal;
        RelativeLayout sellLayout;
        Button sellButton;
        private EditText mEditText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.cargo_item_name);
            itemPrice = itemView.findViewById(R.id.cargo_item_price);
            itemTotal = itemView.findViewById(R.id.total_available_cargo);
            sellLayout = itemView.findViewById(R.id.cargo_parent_layout);
            mEditText = (EditText)itemView.findViewById(R.id.cargo_quantity_edit);
            mEditText.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                public void afterTextChanged(Editable editable) {}
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(mEditText.getTag()!=null){
                        mEditTextValues.set((int)mEditText.getTag(),charSequence.toString());
                    }
                }
            });
        }

    }




}
