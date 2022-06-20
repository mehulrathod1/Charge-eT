package com.in.chargeet.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.country_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
