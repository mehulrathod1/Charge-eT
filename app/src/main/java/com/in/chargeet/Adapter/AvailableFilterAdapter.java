package com.in.chargeet.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.in.chargeet.Activity.MyBookingActivity;
import com.in.chargeet.Model.AvailableFilterModel;
import com.in.chargeet.R;

import org.w3c.dom.Text;

import java.util.List;

public class AvailableFilterAdapter extends RecyclerView.Adapter<AvailableFilterAdapter.ViewHolder> {
    List<AvailableFilterModel> list;
    Context context;
    Click click;
    View thumbView;

    public interface Click {
        void onBookClick(int position);

        void onPercentageClick(int position);

        void onUnitClick(int position);

        void onTimeClick(int position);

        void onDirectionClick(int position);

    }

    public AvailableFilterAdapter(List<AvailableFilterModel> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.available_filter_result_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        AvailableFilterModel model = list.get(position);

        holder.name.setText(model.getName());
        holder.description.setText(model.getDescription());
        holder.power.setText(model.getPower());
        holder.rate.setText(model.getRate());

        holder.seekBar.setProgress(0);
        holder.unitSeekbar.setProgress(0);


        thumbView = LayoutInflater.from(context).inflate(R.layout.layout_seekbar_thumb, null, false);

        holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                // You can have your own calculation for progress
                progress = progress / 5;
                progress = progress * 5;
                seekBar.setThumb(getThumb(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        holder.unitSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                // You can have your own calculation for progress
                progress = progress / 5;
                progress = progress * 5;
                seekBar.setThumb(getThumb(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        holder.percentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.onPercentageClick(position);

                holder.percentage.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.filter_heading_blue_bg));
                holder.units.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.filter_heading_white_bg));
                holder.time.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.filter_heading_white_bg));

                holder.percentage.setTextColor(Color.parseColor("#ffffff"));
                holder.units.setTextColor(Color.parseColor("#1C1C1C"));
                holder.time.setTextColor(Color.parseColor("#1C1C1C"));

                holder.percentageLayout.setVisibility(View.VISIBLE);
                holder.unitLayout.setVisibility(View.GONE);
                holder.timeLayout.setVisibility(View.GONE);
            }
        });
        holder.units.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.onUnitClick(position);

                holder.units.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.filter_heading_blue_bg));
                holder.percentage.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.filter_heading_white_bg));
                holder.time.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.filter_heading_white_bg));

                holder.units.setTextColor(Color.parseColor("#ffffff"));
                holder.percentage.setTextColor(Color.parseColor("#1C1C1C"));
                holder.time.setTextColor(Color.parseColor("#1C1C1C"));


                holder.percentageLayout.setVisibility(View.GONE);
                holder.unitLayout.setVisibility(View.VISIBLE);
                holder.timeLayout.setVisibility(View.GONE);
            }
        });
        holder.time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.onTimeClick(position);

                holder.time.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.filter_heading_blue_bg));
                holder.percentage.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.filter_heading_white_bg));
                holder.units.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.filter_heading_white_bg));

                holder.time.setTextColor(Color.parseColor("#ffffff"));
                holder.percentage.setTextColor(Color.parseColor("#1C1C1C"));
                holder.units.setTextColor(Color.parseColor("#1C1C1C"));

                holder.percentageLayout.setVisibility(View.GONE);
                holder.unitLayout.setVisibility(View.GONE);
                holder.timeLayout.setVisibility(View.VISIBLE);
            }
        });

        holder.bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.onBookClick(position);
            }
        });

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

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView percentage, units, time, bookNow, name, description, power, rate;
        LinearLayout percentageLayout, unitLayout, timeLayout;
        SeekBar seekBar, unitSeekbar;
        ImageView showDirection;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            percentage = itemView.findViewById(R.id.percentage);
            units = itemView.findViewById(R.id.units);
            time = itemView.findViewById(R.id.times);
            bookNow = itemView.findViewById(R.id.bookNow);
            percentageLayout = itemView.findViewById(R.id.percentageLayout);
            unitLayout = itemView.findViewById(R.id.unitLayout);
            timeLayout = itemView.findViewById(R.id.timeLayout);
            seekBar = itemView.findViewById(R.id.seekBar);
            unitSeekbar = itemView.findViewById(R.id.unitSeekbar);
            showDirection = itemView.findViewById(R.id.showDirection);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            power = itemView.findViewById(R.id.power);
            rate = itemView.findViewById(R.id.rate);

        }
    }

    public Drawable getThumb(int progress) {
        ((TextView) thumbView.findViewById(R.id.tvProgress)).setText(progress + "");

        thumbView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        Bitmap bitmap = Bitmap.createBitmap(thumbView.getMeasuredWidth(), thumbView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        thumbView.layout(0, 0, thumbView.getMeasuredWidth(), thumbView.getMeasuredHeight());
        thumbView.draw(canvas);

        return new BitmapDrawable(context.getResources(), bitmap);
    }

}
