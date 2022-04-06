package com.in.chargeet.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.in.chargeet.Model.GetStartModel;
import com.in.chargeet.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GetStartAdapter extends PagerAdapter {

    Context context;
    ArrayList<GetStartModel> pager;


    public GetStartAdapter(Context context, ArrayList<GetStartModel> pager) {
        this.context = context;
        this.pager = pager;
    }

    @Override
    public int getCount() {
        return pager.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.get_started_item, container, false);


        TextView heading, subHeading, description;
        ImageView image;

        heading = view.findViewById(R.id.heading);
        subHeading = view.findViewById(R.id.subHeading);
        description = view.findViewById(R.id.description);
        image = view.findViewById(R.id.image);


        GetStartModel model = pager.get(position);

        heading.setText(model.getHeading());
        subHeading.setText(model.getSubHeading());
        description.setText(model.getDescription());
        image.setImageResource(model.getImage());

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}
