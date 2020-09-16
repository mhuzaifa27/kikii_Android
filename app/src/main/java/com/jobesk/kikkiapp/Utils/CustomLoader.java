package com.jobesk.kikkiapp.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.jobesk.kikkiapp.R;
import com.wang.avi.AVLoadingIndicatorView;

public class CustomLoader extends LinearLayout {

    private AVLoadingIndicatorView loader = null;
    private AlertDialog.Builder builder = null;
    private Activity mContext;
    private AlertDialog dialog;
    private LottieAnimationView loading;

    public CustomLoader(Activity context, boolean val) {
        super(context);
        this.mContext = context;
        loadIndicator(val);
    }

    private void loadIndicator(boolean cancelable) {

        builder = new AlertDialog.Builder(mContext);
        LayoutInflater layoutInflater = mContext.getLayoutInflater();


        View a = layoutInflater.inflate(R.layout.custom_loader, CustomLoader.this);

        loading = a.findViewById(R.id.animationView);
        loading.setAnimation(R.raw.loading_wave);
        loading.setRepeatCount(Animation.INFINITE);

        builder.setView(a).setCancelable(cancelable);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public void showIndicator() {
        loading.playAnimation();
        dialog.show();
    }

    public void hideIndicator() {
        loading.pauseAnimation();
        dialog.dismiss();
    }

    public boolean isShowing() {
        if (dialog.isShowing()) return true;
        else return false;
    }
}
