package com.example.kikkiapp.Utils;

import android.app.Activity;

import com.example.kikkiapp.Model.PostMedia;
import com.example.kikkiapp.R;
import com.stfalcon.imageviewer.StfalconImageViewer;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UtilityFunctions {
    public static int calculateAge(Date birthdate) {
        Calendar birth = Calendar.getInstance();
        birth.setTime(birthdate);
        Calendar today = Calendar.getInstance();

        int yearDifference = today.get(Calendar.YEAR)
                - birth.get(Calendar.YEAR);

        if (today.get(Calendar.MONTH) < birth.get(Calendar.MONTH)) {
            yearDifference--;
        } else {
            if (today.get(Calendar.MONTH) == birth.get(Calendar.MONTH)
                    && today.get(Calendar.DAY_OF_MONTH) < birth
                    .get(Calendar.DAY_OF_MONTH)) {
                yearDifference--;
            }

        }

        return yearDifference;
    }

    /*public static void ShowImageViewer(Activity activity,List<PostMedia> postMedia){
        StfalconImageViewer.Builder<>
    }*/
}
