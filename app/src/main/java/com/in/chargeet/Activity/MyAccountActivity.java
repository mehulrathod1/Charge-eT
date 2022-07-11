package com.in.chargeet.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.in.chargeet.Model.CommonModel;
import com.in.chargeet.Model.ProfileDetail;
import com.in.chargeet.R;
import com.in.chargeet.Retrofit.Api;
import com.in.chargeet.Retrofit.RetrofitClient;
import com.in.chargeet.Utils.Glob;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.imaginativeworld.whynotimagecarousel.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAccountActivity extends AppCompatActivity {

    BottomNavigationView bottom_navigation;

    ImageView backButton, edtAccount, wallet, profileImage, editImage,amazon;
    TextView toolbarHading, userName, email, mobileNumber, name, surname, gender, town, country, dob, description;
    ImageCarousel carousel;


    File photoFile, img_file;
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    public String photoFileName = "IMG_" + timeStamp + ".jpg";
    Uri img_url;
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int MY_Gallery_REQUEST_CODE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        init();
        getProfile(Glob.token, Glob.userId);
    }

    public void init() {

        Glob.progressDialog(this);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        carousel = findViewById(R.id.carousel);
        edtAccount = findViewById(R.id.edtAccount);
        wallet = findViewById(R.id.wallet);
        profileImage = findViewById(R.id.profileImage);
        editImage = findViewById(R.id.editImage);
        bottom_navigation.getMenu().findItem(R.id.account).setChecked(true);

        backButton = findViewById(R.id.backButton);
        toolbarHading = findViewById(R.id.toolbarHading);
        userName = findViewById(R.id.userName);
        email = findViewById(R.id.Email);
        mobileNumber = findViewById(R.id.mobileNumber);
        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        gender = findViewById(R.id.gender);
        town = findViewById(R.id.town);
        country = findViewById(R.id.country);
        dob = findViewById(R.id.dob);
        description = findViewById(R.id.description);
        toolbarHading.setText("Profile");
        amazon = findViewById(R.id.amazon);

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

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] options = {"Camera", "Add From Gallery", "Cancel"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(MyAccountActivity.this);
                builder.setCancelable(false);
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (options[i].equals("Camera")) {
                            Toast.makeText(MyAccountActivity.this, "camera", Toast.LENGTH_SHORT).show();
                            onLaunchCamera();
                        } else if (options[i].equals("Add From Gallery")) {
                            openMediaContent();
                            Toast.makeText(MyAccountActivity.this, "Gallery", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();

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


    public void getProfile(String token, String userId) {

        Api call = RetrofitClient.getClient(Glob.baseUrl).create(Api.class);
        Glob.dialog.show();


        call.getProfile(token, userId).enqueue(new Callback<ProfileDetail>() {
            @Override
            public void onResponse(Call<ProfileDetail> call, Response<ProfileDetail> response) {

                ProfileDetail profileDetail = response.body();
                ProfileDetail.Detail model = profileDetail.getData();

                userName.setText(model.getUsername());
                email.setText(model.getEmail());
                mobileNumber.setText(model.getPhone_number());

                name.setText(model.getName());
                surname.setText(model.getSurname());
                gender.setText(model.getGender());
                town.setText(model.getTown());
                country.setText(model.getCountry());
                dob.setText(model.getDob());
                description.setText(model.getDescription());


                Glide.with(getApplicationContext()).load(model.getProfile_image()).into(profileImage);


                Log.e("TAG", "onResponse: " + model.getProfile_image());

                Glob.dialog.dismiss();


            }

            @Override
            public void onFailure(Call<ProfileDetail> call, Throwable t) {
                Glob.dialog.dismiss();
            }
        });
    }

    public void onLaunchCamera() {


        // create Intent to take a picture and return control to the calling application

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Create a File reference for future access
            photoFile = getPhotoFileUri(photoFileName);
            img_url = Uri.fromFile(photoFile);

            Uri fileProvider = FileProvider.getUriForFile(getApplicationContext(), "com.in.chargeet.provider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

            if (intent.resolveActivity(getPackageManager()) != null) {
                // Start the image capture intent to take photo
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(MyAccountActivity.this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }

    }

    public File getPhotoFileUri(String fileName) {

        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "APP_TAG");
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d("APP_TAG", "failed to create directory");
        }
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
        return file;
    }

    public void openMediaContent() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        photoFile = getPhotoFileUri(photoFileName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri tempFileUri = FileProvider.getUriForFile(getApplicationContext(),
                    "com.in.chargeet.provider", // As defined in Manifest
                    photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, tempFileUri);
        } else {
            Uri tempFileUri = Uri.fromFile(photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, tempFileUri);
        }

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);
    }

    public Bitmap getBitmap(final Uri selectedimg) throws IOException {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        AssetFileDescriptor fileDescriptor = null;

        fileDescriptor =
                getContentResolver().openAssetFileDescriptor(selectedimg, "r");
        Bitmap bitmap
                = BitmapFactory.decodeFileDescriptor(
                fileDescriptor.getFileDescriptor(), null, options);

        options.inSampleSize = calculateInSampleSize(options, 1024, 1024);
        options.inJustDecodeBounds = false;

        Bitmap original
                = BitmapFactory.decodeFileDescriptor(
                fileDescriptor.getFileDescriptor(), null, options);
        System.gc();
        return original;
    }

    public int calculateInSampleSize(@NonNull BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if ((height > reqHeight) && (width > reqWidth)) {
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            inSampleSize++;
        }
        return inSampleSize;
    }


    public void updateProfileImage(String token, String userId, File profile_image) {

        Api call = RetrofitClient.getClient(Glob.baseUrl).create(Api.class);
        Glob.dialog.show();

        RequestBody requestBody_token = RequestBody.create(MediaType.parse("multipart/form-data"), token);
        RequestBody requestBody_user_id = RequestBody.create(MediaType.parse("multipart/form-data"), userId);
        MultipartBody.Part requestBody_profile_image = null;
        RequestBody requestBody_req_img = RequestBody.create(MediaType.parse("multipart/form-data"), profile_image);
        requestBody_profile_image = MultipartBody.Part.createFormData("profile_image", img_file.getName(), requestBody_req_img);


        call.updateProfileImage(requestBody_token, requestBody_user_id, requestBody_profile_image).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                CommonModel commonModel = response.body();
                Toast.makeText(MyAccountActivity.this, "" + commonModel.getMessage(), Toast.LENGTH_SHORT).show();
                Glob.dialog.dismiss();
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;

        if (resultCode == Activity.RESULT_OK) {
            try {
                switch (requestCode) {
                    case 1034:

                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new
                                Date());
                        img_file = new File(getCacheDir(), "IMG_" + timeStamp + ".jpg");

                        bitmap = null;

                        try {
                            if (img_url != null) {
                                bitmap = getBitmap(img_url);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (bitmap != null) {
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
                            byte[] bitmapdata = bos.toByteArray();

                            try {
                                FileOutputStream fos = new FileOutputStream(img_file);
                                fos.write(bitmapdata);
                                fos.flush();
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.e("img__file", "onClick: " + img_file);

//                            updateDoctorPersonal("123456789", Glob.user_id, edtFirstName.getText().toString(),
//                                    edtLastName.getText().toString(), "1",
//                                    edtEducation.getText().toString(), edtLanguageSpoken.getText().toString(),
//                                    edtExperience.getText().toString(), edtAddress.getText().toString(), img_file);
//
//                            Uri temporary_Image = Uri.fromFile(new File(String.valueOf(img_file)));
//                            profileImage.setImageURI(temporary_Image);

                            updateProfileImage(Glob.token, Glob.userId, img_file);
                        }
                        break;
                    case 2:
                        if (data.getData() != null) {
//                            post();
                            bitmap = getBitmap(data.getData());
                            img_url = data.getData();

                            timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new
                                    Date());
                            img_file = new File(getApplicationContext().getCacheDir(), "IMG_" + timeStamp + ".jpg");

                            bitmap = null;

                            try {
                                if (img_url != null) {
                                    bitmap = getBitmap(img_url);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (bitmap != null) {
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
                                byte[] bitmapdata = bos.toByteArray();

                                try {
                                    FileOutputStream fos = new FileOutputStream(img_file);
                                    fos.write(bitmapdata);
                                    fos.flush();
                                    fos.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Log.e("img_file", "onClick: " + img_file);
//                                Uri temporary_Image = Uri.fromFile(new File(String.valueOf(img_file)));
//                                profileImage.setImageURI(temporary_Image);

                                updateProfileImage(Glob.token, Glob.userId, img_file);

//                                updateDoctorPersonal("123456789", Glob.user_id, edtFirstName.getText().toString(),
//                                        edtLastName.getText().toString(), "1",
//                                        edtEducation.getText().toString(), edtLanguageSpoken.getText().toString(),
//                                        edtExperience.getText().toString(), edtAddress.getText().toString(), img_file);
                            }

                        }
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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