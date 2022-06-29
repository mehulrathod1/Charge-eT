package com.in.chargeet.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.chargeet.Model.WattModel;
import com.in.chargeet.R;

import java.util.List;

public class WattAdapter extends RecyclerView.Adapter<WattAdapter.ViewHolder> {

    List<WattModel> list;
    Context context;
    Click click;

    public interface Click {
        void OnItemClick(int position);
    }

    public WattAdapter(List<WattModel> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.watt_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        WattModel model = list.get(position);

        holder.kiloWatt.setText(model.getKiloWatt());


        if (list.get(position).isSelected() == true) {

            holder.layout.setBackgroundColor(Color.parseColor("#60CAAB"));
            holder.kiloWatt.setTextColor(Color.parseColor("#FFFFFF"));

        } else {
            holder.layout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.kiloWatt.setTextColor(Color.parseColor("#000000"));
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                click.OnItemClick(position);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView kiloWatt;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            kiloWatt = itemView.findViewById(R.id.kiloWatt);
            layout = itemView.findViewById(R.id.layout);

        }
    }

}
