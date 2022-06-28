package com.in.chargeet.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.hardware.lights.LightsManager;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.chargeet.Model.FilterConnectorModel;
import com.in.chargeet.R;

import org.w3c.dom.Text;

import java.util.List;

public class FilterConnectorAdapter extends RecyclerView.Adapter<FilterConnectorAdapter.ViewHolder> {

    List<FilterConnectorModel> filterConnectorModelList;
    Context context;
    Click click;

    public interface Click {
        void onItemCLick(int position);
    }

    public FilterConnectorAdapter(List<FilterConnectorModel> filterConnectorModelList, Context context, Click click) {
        this.filterConnectorModelList = filterConnectorModelList;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.filter_connecter_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        FilterConnectorModel model = filterConnectorModelList.get(position);

        holder.connectorName.setText(model.getConnectorName());
        holder.connectorIcon.setImageResource(model.getConnectorIcon());


        if (filterConnectorModelList.get(position).getSelected() == true) {

            holder.layout.setBackgroundColor(Color.parseColor("#60CAAB"));
            holder.connectorName.setTextColor(Color.parseColor("#FFFFFF"));

        } else {
            holder.layout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.connectorName.setTextColor(Color.parseColor("#000000"));
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.onItemCLick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterConnectorModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView connectorIcon;
        TextView connectorName;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            connectorName = itemView.findViewById(R.id.connectorName);
            connectorIcon = itemView.findViewById(R.id.connectorIcon);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
