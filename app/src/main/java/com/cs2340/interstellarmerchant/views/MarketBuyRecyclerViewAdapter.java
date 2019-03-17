package com.cs2340.interstellarmerchant.views;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.cs2340.interstellarmerchant.R;
import com.cs2340.interstellarmerchant.model.universe.market.items.OrderStatus;
import com.cs2340.interstellarmerchant.viewmodels.MarketViewModel;
import com.cs2340.interstellarmerchant.views.MarketMain;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MarketBuyRecyclerViewAdapter extends RecyclerView.Adapter<MarketBuyRecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "MarketBuyRecyclerViewAd";

    public ArrayList<String> itemNames = new ArrayList<>();
    public ArrayList<String> itemPrices = new ArrayList<>();
    public ArrayList<String> itemTotals = new ArrayList<>();
    public static List<String> mEditTextValues = new ArrayList<>(10);


    private Context itemContext;
    public MarketViewModel mv;

    public MarketBuyRecyclerViewAdapter(ArrayList<String> itemNames, ArrayList<String> itemPrices, Context itemContext, ArrayList<String> itemTotals, MarketViewModel mv) {

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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.market_shop_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
            viewHolder.mEditText.setTag(i);
            viewHolder.mEditText.setText("");
            viewHolder.itemName.setText(itemNames.get(i));
            viewHolder.itemPrice.setText(itemPrices.get(i));
            viewHolder.itemTotal.setText(itemTotals.get(i));
    }

    @Override
    public int getItemCount() {
        return itemNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        TextView itemPrice;
        TextView itemTotal;
        RelativeLayout buyLayout;
        Button buyButton;
        private EditText mEditText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.item_price);
            itemTotal = itemView.findViewById(R.id.total_available_shop);
            buyLayout = itemView.findViewById(R.id.shop_parent_layout);
            buyButton = itemView.findViewById(R.id.buyButton);
            mEditText = (EditText)itemView.findViewById(R.id.quantity_edit);
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
