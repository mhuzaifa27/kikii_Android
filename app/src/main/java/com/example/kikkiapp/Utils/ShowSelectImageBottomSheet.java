package com.example.kikkiapp.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.kikkiapp.Activities.ChattingActivity;
import com.example.kikkiapp.Activities.MyProfileActivity;
import com.example.kikkiapp.Netwrok.Constant;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.example.kikkiapp.R;

public class ShowSelectImageBottomSheet {


    public static void showDialog(final Activity activity, View view, final String type) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                activity, R.style.AppBottomSheetDialogTheme
        );
        View bottomSheetView = LayoutInflater.from(activity)
                .inflate(R.layout.bottom_sheet_add_image,
                        (RelativeLayout) view.findViewById(R.id.bottomSheetLayout), false);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bottomSheetView.getBackground().setAlpha(0);
        } else {
            bottomSheetView.setBackgroundColor(ContextCompat.getColor(activity, android.R.color.transparent));
        }
        TextView tv_select_from_camera = bottomSheetView.findViewById(R.id.tv_select_from_camera);
        TextView tv_select_from_gallery = bottomSheetView.findViewById(R.id.tv_select_from_gallery);
        TextView tv_select_from_facebook = bottomSheetView.findViewById(R.id.tv_select_from_facebook);
        TextView tv_select_from_instagram = bottomSheetView.findViewById(R.id.tv_select_from_instagram);

        tv_select_from_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage.getPermissions(activity,1,type);
                bottomSheetDialog.dismiss();
                //activity.startActivity(new Intent(activity, AddMoreProfileImagesActivity.class));
            }
        });
        tv_select_from_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage.getPermissions(activity,2,type);
                bottomSheetDialog.dismiss();
                //activity.startActivity(new Intent(activity, AddMoreProfileImagesActivity.class));
            }
        });
        tv_select_from_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //activity.startActivity(new Intent(activity, AddMoreProfileImagesActivity.class));
            }
        });
        tv_select_from_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //activity.startActivity(new Intent(activity, AddMoreProfileImagesActivity.class));
            }
        });
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }


    public static void showDialogForSelectMedia(final Activity activity, View view) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                activity, R.style.AppBottomSheetDialogTheme
        );
        View bottomSheetView = LayoutInflater.from(activity)
                .inflate(R.layout.bottom_sheet_select_media,
                        (RelativeLayout) view.findViewById(R.id.bottomSheetLayout), false);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bottomSheetView.getBackground().setAlpha(0);
        } else {
            bottomSheetView.setBackgroundColor(ContextCompat.getColor(activity, android.R.color.transparent));
        }
        TextView tv_select_image = bottomSheetView.findViewById(R.id.tv_select_image);
        TextView tv_select_video = bottomSheetView.findViewById(R.id.tv_select_video);

        tv_select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage.getPermissions(activity,1);
                bottomSheetDialog.dismiss();
                //activity.startActivity(new Intent(activity, AddMoreProfileImagesActivity.class));
            }
        });
        tv_select_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage.getPermissions(activity,2);
                bottomSheetDialog.dismiss();
                //activity.startActivity(new Intent(activity, AddMoreProfileImagesActivity.class));
            }
        });
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }
    public static void showDialogForSelectMedia(final Activity activity, View view, final String type) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                activity, R.style.AppBottomSheetDialogTheme
        );
        View bottomSheetView = LayoutInflater.from(activity)
                .inflate(R.layout.bottom_sheet_select_media,
                        (RelativeLayout) view.findViewById(R.id.bottomSheetLayout), false);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bottomSheetView.getBackground().setAlpha(0);
        } else {
            bottomSheetView.setBackgroundColor(ContextCompat.getColor(activity, android.R.color.transparent));
        }
        TextView tv_select_image = bottomSheetView.findViewById(R.id.tv_select_image);
        TextView tv_select_video = bottomSheetView.findViewById(R.id.tv_select_video);

        tv_select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage.getPermissions(activity,2,type);
                bottomSheetDialog.dismiss();
                //activity.startActivity(new Intent(activity, AddMoreProfileImagesActivity.class));
            }
        });
        tv_select_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage.getPermissions(activity,2,type);
                bottomSheetDialog.dismiss();
                //activity.startActivity(new Intent(activity, AddMoreProfileImagesActivity.class));
            }
        });
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    public static void showDialogProfileOptions(final Activity activity, View view, final Integer userId) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                activity, R.style.AppBottomSheetDialogTheme
        );
        View bottomSheetView = LayoutInflater.from(activity)
                .inflate(R.layout.bottom_sheet_profile_options,
                        (RelativeLayout) view.findViewById(R.id.bottomSheetLayout), false);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bottomSheetView.getBackground().setAlpha(0);
        } else {
            bottomSheetView.setBackgroundColor(ContextCompat.getColor(activity, android.R.color.transparent));
        }
        TextView tv_send_message = bottomSheetView.findViewById(R.id.tv_send_message);
        TextView tv_view_profile = bottomSheetView.findViewById(R.id.tv_view_profile);

        tv_send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                Intent intent=new Intent(activity, ChattingActivity.class);
                intent.putExtra(Constant.ID,String.valueOf(userId));
                activity.startActivity(intent);
            }
        });
        tv_view_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                Intent intent=new Intent(activity, MyProfileActivity.class);
                intent.putExtra(Constant.ID,userId);
                activity.startActivity(intent);
            }
        });
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

}
