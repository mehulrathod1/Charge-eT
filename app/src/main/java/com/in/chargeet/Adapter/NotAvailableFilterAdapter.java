package com.in.chargeet.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.directions.route.Parser;
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


        holder.name.setText(model.getName());
        holder.description.setText(model.getDescription());
        holder.power.setText(model.getPower());
        holder.rate.setText(model.getRate());


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


                holder.minTxt.setText(String.valueOf(minValue));
                holder.maxTxt.setText(String.valueOf(maxValue));
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
        RangeSeekBar rangeSeekbar;
        ImageView showDirection;
        TextView name, description, power, rate, minTxt, maxTxt;


        public viewHolder(@NonNull View itemView) {
            super(itemView);

            schedule = itemView.findViewById(R.id.schedule);
            rangeSeekbar = itemView.findViewById(R.id.rangeSeekbar);
            showDirection = itemView.findViewById(R.id.showDirection);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            power = itemView.findViewById(R.id.power);
            rate = itemView.findViewById(R.id.rate);
            minTxt = itemView.findViewById(R.id.minTxt);
            maxTxt = itemView.findViewById(R.id.maxTxt);

        }
    }
}
