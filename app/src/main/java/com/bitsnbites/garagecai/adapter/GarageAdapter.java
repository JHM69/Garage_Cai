package com.bitsnbites.garagecai.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bitsnbites.garagecai.Activity.BookingActivity;
import com.bitsnbites.garagecai.R;
import com.bitsnbites.garagecai.model.Garage;

import java.util.List;


public class GarageAdapter extends RecyclerView.Adapter<GarageAdapter.ViewHolder> implements View.OnCreateContextMenuListener {
    Context context;
    List<Garage> garageList;

    public GarageAdapter(Context context, List<Garage> t) {
        this.context = context;
        this.garageList = t;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_garage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Garage garage = garageList.get(position);
        holder.name.setText(garage.getName());
        holder.address.setText(garage.getAddress());
        holder.status_yes.setVisibility(garage.isAvailability()? View.VISIBLE:View.GONE);
        holder.status_no.setVisibility(!garage.isAvailability()? View.VISIBLE:View.GONE);
        //holder.rating.setRating((float) garage.getRating());
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, BookingActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("garage" , garage);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return garageList.size();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, address;
        CardView status_yes, status_no;
        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_g);
            address = itemView.findViewById(R.id.add_g);
            status_yes = itemView.findViewById(R.id.status_yes);
            status_no = itemView.findViewById(R.id.status_no);
        }
    }

}