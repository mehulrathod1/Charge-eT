package com.in.chargeet.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.in.chargeet.Adapter.GetStartAdapter;
import com.in.chargeet.Model.GetStartModel;
import com.in.chargeet.R;

import java.util.ArrayList;

public class GetStartOneActivity extends AppCompatActivity {

    ViewPager viewPager;
    ArrayList<GetStartModel> getStartList = new ArrayList<>();
    LinearLayout layout_dot;
    TextView[] dot;
    Button getStart;
    TextView skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_start_one);


        init();
        addData();
    }


    public void init() {

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        layout_dot = (LinearLayout) findViewById(R.id.layout_dot);
        getStart = findViewById(R.id.getStart);
        skip = findViewById(R.id.skip);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addDot(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        getStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (viewPager.getCurrentItem() == 2) {

                    Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                    startActivity(intent);
                } else {

                    viewPager.setCurrentItem(getItem(+1), true);
                }

            }
        });


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(intent);

            }
        });

    }

    public void addData() {


        GetStartModel model = new GetStartModel("Charg-eT", "Easy To Use",
                "Lorem, ipsum dolor sit amet consectet adipisicing elit. Pariatur, iste veritatis enim aut molestiae id fuga ne doloremque sed esse"
        );


        getStartList.add(model);
        getStartList.add(model);
        getStartList.add(model);


        GetStartAdapter getStartAdapter = new GetStartAdapter(getApplicationContext(), getStartList);
        viewPager.setAdapter(getStartAdapter);

        addDot(0);

        Log.e("TAG", "addData: " + getStartList.size());


    }

    public void addDot(int page_position) {
        dot = new TextView[getStartList.size()];
        layout_dot.removeAllViews();

        for (int i = 0; i < dot.length; i++) {

            dot[i] = new TextView(this);
            dot[i].setText(Html.fromHtml("&#9673;"));
            dot[i].setTextSize(35);
            dot[i].setTextColor(getResources().getColor(R.color.black));
            layout_dot.addView(dot[i]);
        }
        //active dot
        dot[page_position].setTextColor(getResources().getColor(R.color.light_green));
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }
}