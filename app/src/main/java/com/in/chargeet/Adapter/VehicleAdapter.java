package com.in.chargeet.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.in.chargeet.Model.VehicleModel;
import com.in.chargeet.R;

import java.util.List;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.ViewHolder> {
    List<VehicleModel.Vehicle> list;
    Context context;
    Click click;

    public interface Click {
        void onSelectClick(int position);
    }

    public VehicleAdapter(List<VehicleModel.Vehicle> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.vehicle_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        VehicleModel.Vehicle model = list.get(position);
        holder.vehicleName.setText(model.getName());
        holder.vehicleDescription.setText(model.getDescription());
        holder.vehicleSpeed.setText(model.getRate() + " / ");



        Glide.with(context).load(model.getImage()).into(holder.vehicleImage);

        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.onSelectClick(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView vehicleImage;
        TextView vehicleName, vehicleDescription, vehicleSpeed;
        Button select;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            vehicleImage = itemView.findViewById(R.id.vehicleImage);
            vehicleName = itemView.findViewById(R.id.vehicleName);
            vehicleDescription = itemView.findViewById(R.id.vehicleDescription);
            vehicleSpeed = itemView.findViewById(R.id.vehicleSpeed);
            select = itemView.findViewById(R.id.select);

        }
    }
}
