package com.rakuten.weather.utils;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.rakuten.weather.R;

public final class AppUtils {

    public static final String DB_NAME = "weather.db";
    public static final String STATUS_CODE_FAILED = "failed";
    public static final String STATUS_CODE_SUCCESS = "success";
    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";

    private AppUtils() {
        // This class is not publicly instantiable
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }
}
