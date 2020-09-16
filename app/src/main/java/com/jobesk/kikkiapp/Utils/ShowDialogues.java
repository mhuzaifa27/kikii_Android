package com.jobesk.kikkiapp.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.jobesk.kikkiapp.R;

public class ShowDialogues {

    public static void SHOW_DATE_PICKER_DIALOG(Context context, final TextView tv) {
        final Dialog alertDialog = new Dialog(context);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.dialogue_date_picker);
        DatePicker datePicker = (DatePicker) alertDialog.findViewById(R.id.date_picker);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String dateYouChoose = null;
                    if(dayOfMonth<10 && monthOfYear<10)
                        dateYouChoose= "0"+dayOfMonth + "-" + "0"+(monthOfYear + 1) + "-" + year;
                    else if(dayOfMonth<10)
                        dateYouChoose= "0"+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                    else
                        dateYouChoose= dayOfMonth + "-" + "0"+(monthOfYear + 1) + "-" + year;
                    tv.setText(dateYouChoose);
                    alertDialog.dismiss();
                }
            });
        }
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    public static void SHOW_NO_INTERNET_DIALOG(Context context) {
        final Dialog alertDialog = new Dialog(context);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.dialogue_no_internet);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.findViewById(R.id.tv_btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public static void SHOW_SERVER_ERROR_DIALOG(Context context) {
        final Dialog alertDialog = new Dialog(context);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.dialog_server_error);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.findViewById(R.id.tv_btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
    public static void SHOW_SNACK_BAR(View parentLayout, Activity activity, String text) {
        Snackbar snackbar = Snackbar.make(parentLayout, text, Snackbar.LENGTH_LONG);
        View customView = activity.getLayoutInflater().inflate(R.layout.snackbar_internet_connection, null);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(0, 0, 0, 0);
        TextView tv_subject = customView.findViewById(R.id.tv_subject);
        tv_subject.setText(text);
        snackbarLayout.addView(customView);
        snackbar.show();
    }
}
