package com.jobesk.kikkiapp.Utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TextView;

import com.jobesk.kikkiapp.R;

import java.util.Calendar;

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
}
