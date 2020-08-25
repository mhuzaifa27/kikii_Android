package com.jobesk.kikkiapp.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jobesk.kikkiapp.Activities.SignUpModule.AddMoreProfileImagesActivity;
import com.jobesk.kikkiapp.R;

public class ShowSelectImageBottomSheet {

    public static void showDialog(final Context context, View view){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                context, R.style.AppBottomSheetDialogTheme
        );
        View bottomSheetView = LayoutInflater.from(context)
                .inflate(R.layout.bottom_sheet_add_image,
                        (RelativeLayout) view.findViewById(R.id.bottomSheetLayout),false);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bottomSheetView.getBackground().setAlpha(0);
        } else {
            bottomSheetView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        }
        TextView tv_select_from_camera = bottomSheetView.findViewById(R.id.tv_select_from_camera);
        TextView tv_select_from_gallery = bottomSheetView.findViewById(R.id.tv_select_from_gallery);
        TextView tv_select_from_facebook = bottomSheetView.findViewById(R.id.tv_select_from_facebook);
        TextView tv_select_from_instagram = bottomSheetView.findViewById(R.id.tv_select_from_instagram);

        tv_select_from_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, AddMoreProfileImagesActivity.class));
            }
        });
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }
}
