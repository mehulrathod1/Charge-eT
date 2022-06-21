package com.in.chargeet.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.in.chargeet.Model.PowerStationDetailModel;
import com.in.chargeet.R;

import java.util.List;

public class ConnectorsAdapter extends RecyclerView.Adapter<ConnectorsAdapter.ViewHolder> {

    List<PowerStationDetailModel.PowerStationData.Connectors> connectorsList;
    Context context;
    Click click;


    public interface Click {
        void onConnectorClick(int position);
    }

    public ConnectorsAdapter(List<PowerStationDetailModel.PowerStationData.Connectors> connectorsList, Context context, Click click) {
        this.connectorsList = connectorsList;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.connector_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PowerStationDetailModel.PowerStationData.Connectors model = connectorsList.get(position);


        Glide.with(context).load(model.getImage()).into(holder.connector);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.onConnectorClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return connectorsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout;
        ImageView connector;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.layout);
            connector = itemView.findViewById(R.id.connector);
        }
    }
}
