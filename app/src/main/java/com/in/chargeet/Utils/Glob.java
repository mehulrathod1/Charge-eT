package com.in.chargeet.Utils;

import android.app.ProgressDialog;
import android.content.Context;

public class Glob {

    public static ProgressDialog dialog;

    public static String baseUrl = "https://chargeet.notionprojects.tech/api/";

    public static String token = "ae5fa4fa4e";

    public static String userId ;



    public static void progressDialog(Context context) {
        dialog = new ProgressDialog(context);
        dialog.setCancelable(false); // set cancelable to false
        dialog.setMessage("Please Wait"); // set message

    }

}
