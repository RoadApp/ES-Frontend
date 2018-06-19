package edu.ufcg.es.es_front.utils;

import android.app.Activity;
import android.app.ProgressDialog;

import edu.ufcg.es.es_front.R;

public class ActivityUtils {

    private static ProgressDialog progressDialog;

    public static void showProgressDialog(Activity activity, String message) {

        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(message);
        progressDialog.setCanceledOnTouchOutside(false);
        

        progressDialog.show();
    }

    public static void cancelProgressDialog() {

        if(progressDialog != null){
            progressDialog.cancel();
        }

    }
}
