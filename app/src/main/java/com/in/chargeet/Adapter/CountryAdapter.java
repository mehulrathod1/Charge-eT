package com.in.chargeet.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.chargeet.Model.CountryModel;
import com.in.chargeet.R;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    List<CountryModel.Country> list;
    Context context;
    Click click;

    public interface Click {

        void itemClick(int position);
    }

    public CountryAdapter(List<CountryModel.Country> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.country_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        CountryModel.Country model = list.get(position);

        holder.radioButton.setText(model.getName());

        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                click.itemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView radioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            radioButton = itemView.findViewById(R.id.radioButton);
        }
    }
}
