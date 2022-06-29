package com.in.chargeet.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.chargeet.Model.NotAvailableFilterModel;
import com.in.chargeet.R;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import java.util.List;

public class NotAvailableFilterAdapter extends RecyclerView.Adapter<NotAvailableFilterAdapter.viewHolder> {

    List<NotAvailableFilterModel> list;
    Context context;
    Click click;


    public interface Click {
        void onScheduleClick(int position);

        void onDirectionClick(int position);
    }

    public NotAvailableFilterAdapter(List<NotAvailableFilterModel> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.not_available_filter_item, parent, false);
        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {

        NotAvailableFilterModel model = list.get(position);

        holder.schedule.setText(model.getSchedule());

        holder.schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                click.onScheduleClick(position);
            }
        });

        holder.rangeSeekbar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                //Now you have the minValue and maxValue of your RangeSeekbar
                Toast.makeText(context, minValue + "-" + maxValue, Toast.LENGTH_LONG).show();
            }
        });

// Get noticed while dragging
        holder.rangeSeekbar.setNotifyWhileDragging(true);

        holder.showDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.onDirectionClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        Button schedule;
        RangeSeekBar  rangeSeekbar;
        ImageView showDirection;


        public viewHolder(@NonNull View itemView) {
            super(itemView);

            schedule = itemView.findViewById(R.id.schedule);
            rangeSeekbar = itemView.findViewById(R.id.rangeSeekbar);
            showDirection = itemView.findViewById(R.id.showDirection);

        }
    }
}
