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

public class SaveRecyclerViewAdapter extends RecyclerView.Adapter<SaveRecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "SaveBuyRecyclerViewAd";

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> times = new ArrayList<>();
    private Context itemContext;

    public SaveRecyclerViewAdapter(ArrayList<String> names, ArrayList<String> times, Context itemContext) {
        this.names = names;
        this.times = times;
        this.itemContext = itemContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.load_games, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: called");
        viewHolder.name.setText(names.get(i));
        viewHolder.time.setText(times.get(i));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView time;
        TextView name_prompt;
        TextView time_prompt;
        RelativeLayout loadLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.names);
            name_prompt = itemView.findViewById(R.id.name_prompt);
            time = itemView.findViewById(R.id.times);
            time_prompt = itemView.findViewById(R.id.time_prompt);
            loadLayout = itemView.findViewById(R.id.load_parent_layout);
        }
    }

}