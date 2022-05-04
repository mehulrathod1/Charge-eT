package com.in.chargeet.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.in.chargeet.R;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.imaginativeworld.whynotimagecarousel.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAccountActivity extends AppCompatActivity {

    BottomNavigationView bottom_navigation;

    ImageView backButton, edtAccount, wallet;
    TextView toolbarHading;
    ImageCarousel carousel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        init();
    }

    public void init() {

        bottom_navigation = findViewById(R.id.bottom_navigation);
        carousel = findViewById(R.id.carousel);
        edtAccount = findViewById(R.id.edtAccount);
        wallet = findViewById(R.id.wallet);
        bottom_navigation.getMenu().findItem(R.id.account).setChecked(true);


        backButton = findViewById(R.id.backButton);
        toolbarHading = findViewById(R.id.toolbarHading);
        toolbarHading.setText("Profile");


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });

        edtAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), MyWalletActivity.class);
                startActivity(intent);
            }
        });

        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;

                switch (item.getItemId()) {

                    case R.id.location:
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                        break;


                    case R.id.order:
                        intent = new Intent(getApplicationContext(), DirectionActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.filter:
                        intent = new Intent(getApplicationContext(), FilterActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.account:
                        intent = new Intent(getApplicationContext(), MyAccountActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                }
                return false;
            }
        });

        carousel.registerLifecycle(getLifecycle());
        List<CarouselItem> list = new ArrayList<>();

        list.add(
                new CarouselItem(
                        "https://images.unsplash.com/photo-1532581291347-9c39cf10a73c?w=1080",
                        "Photo by Aaron Wu on Unsplash"
                )
        );
        list.add(
                new CarouselItem(
                        "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=1080"
                )
        );
        Map<String, String> headers = new HashMap<>();
        headers.put("header_key", "header_value");

        list.add(
                new CarouselItem(
                        "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=1080",
                        headers
                )
        );
        list.add(
                new CarouselItem(
                        R.drawable.images,
                        "Photo by Kimiya Oveisi on Unsplash"
                )
        );
        list.add(
                new CarouselItem(
                        R.drawable.images
                )
        );
        carousel.setData(list);


//        carousel.setCarouselPadding(Utils.dpToPx(0, getApplicationContext()));
//        carousel.setCarouselPaddingStart(Utils.dpToPx(0, getApplicationContext()));
//        carousel.setCarouselPaddingTop(Utils.dpToPx(0, getApplicationContext()));
//        carousel.setCarouselPaddingEnd(Utils.dpToPx(0, getApplicationContext()));
//        carousel.setCarouselPaddingBottom(Utils.dpToPx(0, getApplicationContext()));
//
//        carousel.setShowTopShadow(true);
//        carousel.setTopShadowAlpha(0.6f); // 0 to 1, 1 means 100%
//        carousel.setTopShadowHeight(Utils.dpToPx(32, getApplicationContext())); // px value of dp
//
//        carousel.setShowBottomShadow(true);
//        carousel.setBottomShadowAlpha(0.6f); // 0 to 1, 1 means 100%
//        carousel.setBottomShadowHeight(Utils.dpToPx(64, getApplicationContext())); // px value of dp
//
//        carousel.setShowCaption(true);
//        carousel.setCaptionMargin(Utils.dpToPx(0, getApplicationContext())); // px value of dp
//        carousel.setCaptionTextSize(Utils.spToPx(14, getApplicationContext())); // px value of sp
//
//        carousel.setShowIndicator(true);
//        carousel.setIndicatorMargin(Utils.dpToPx(0, getApplicationContext())); // px value of dp
//
//        carousel.setShowNavigationButtons(true);
//        carousel.setImageScaleType(ImageView.ScaleType.CENTER);
//        carousel.setCarouselBackground(new ColorDrawable(Color.parseColor("#333333")));
    }

    @Override
    public void onBackPressed() {

        Intent intent;
        intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
        super.onBackPressed();

    }

}