package com.in.chargeet.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.chargeet.Model.MyBookingModel;
import com.in.chargeet.R;

import java.util.List;

public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.ViewHolder> {

    List<MyBookingModel.Booking> list;
    Context context;
    Click click;

    public interface Click {
        void onRebookClick(int position);
    }

    public MyBookingAdapter(List<MyBookingModel.Booking> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_booking_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        MyBookingModel.Booking model = list.get(position);


        holder.dateAndTime.setText(model.getBooking_date());
        holder.stationName.setText(model.getPower_station_name());
        holder.stationDescription.setText(model.getDescription());

        holder.reBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                click.onRebookClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateAndTime, stationName, stationDescription, reBooking;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateAndTime = itemView.findViewById(R.id.dateAndTime);
            stationName = itemView.findViewById(R.id.stationName);
            stationDescription = itemView.findViewById(R.id.stationDescription);
            reBooking = itemView.findViewById(R.id.reBooking);

        }
    }
}
