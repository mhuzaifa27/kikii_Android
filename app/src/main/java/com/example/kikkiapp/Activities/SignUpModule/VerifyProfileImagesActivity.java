package com.example.kikkiapp.Activities.SignUpModule;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.CustomLoader;
import com.example.kikkiapp.Utils.MyAnimation;
import com.example.kikkiapp.Utils.SelectImage;
import com.joooonho.SelectableRoundedImageView;

public class VerifyProfileImagesActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "VerifyProfileImagesActi";
    private Context mContext=VerifyProfileImagesActivity.this;

    private ImageView img_verify_complete,img_verify_fail,img_face_detection;
    private Button btn_verify, btn_upload_again,btn_next;
    private TextView tv_title;
    private SelectableRoundedImageView img_user;
    private Bitmap bmp;
    private CustomLoader customLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_profile_images);

        initComponents();
        getIntentData();

        btn_upload_again.setOnClickListener(this);
        btn_verify.setOnClickListener(this);
        btn_next.setOnClickListener(this);


    }

    private void initComponents() {
        customLoader=new CustomLoader(this,false);

        img_verify_complete=findViewById(R.id.img_verify_complete);
        img_face_detection=findViewById(R.id.img_face_detection);
        img_verify_fail=findViewById(R.id.img_verify_fail);

        img_user=findViewById(R.id.img_user);

        btn_upload_again =findViewById(R.id.btn_upload_again);
        btn_verify=findViewById(R.id.btn_verify);
        btn_next=findViewById(R.id.btn_next);

        tv_title=findViewById(R.id.tv_title);
    }

    private void getIntentData() {
        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray("bitmap");
        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        img_user.setImageBitmap(bmp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_upload_again:
                startActivity(new Intent(mContext,AddProfileImageActivity.class));
                finish();
                break;
            case R.id.btn_verify:
                //customLoader.showIndicator();
                Animation anim = new MyAnimation(img_face_detection, 100);
                anim.setDuration(3000);
                img_face_detection.startAnimation(anim);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        detectFace();
                    }
                },3000);
                //setVerifiedView();
                break;
            case R.id.btn_next:
                goToNextActivity(bmp);
                break;
        }
    }

    private void detectFace() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable=true;
        Bitmap myBitmap = bmp;

        Paint myRectPaint = new Paint();
        myRectPaint.setStrokeWidth(5);
        myRectPaint.setColor(Color.RED);
        myRectPaint.setStyle(Paint.Style.STROKE);

        Bitmap tempBitmap = Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(), Bitmap.Config.RGB_565);
        Canvas tempCanvas = new Canvas(tempBitmap);
        tempCanvas.drawBitmap(myBitmap, 0, 0, null);

        FaceDetector faceDetector = new
                FaceDetector.Builder(getApplicationContext()).setTrackingEnabled(false)
                .build();
        if(!faceDetector.isOperational()){
            new AlertDialog.Builder(this).setMessage("Could not set up the face detector!").show();
            return;
        }
        Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
        SparseArray<Face> faces = faceDetector.detect(frame);

        if(faces.size()>0){
            for(int i=0; i<faces.size(); i++) {
                Face thisFace = faces.valueAt(i);
                float x1 = thisFace.getPosition().x;
                float y1 = thisFace.getPosition().y;
                float x2 = x1 + thisFace.getWidth();
                float y2 = y1 + thisFace.getHeight();
                tempCanvas.drawRoundRect(new RectF(x1, y1, x2, y2), 2, 2, myRectPaint);
            }
            //customLoader.hideIndicator();
            setVerifiedView();
        }
        else{
            setDeclinedView();
            //customLoader.hideIndicator();
            Toast.makeText(mContext, "Face is not detected!", Toast.LENGTH_SHORT).show();
        }
        //img_face_detection.setImageDrawable(new BitmapDrawable(getResources(),tempBitmap));
    }

    private void setDeclinedView() {
        img_verify_complete.setVisibility(View.GONE);
        img_face_detection.setVisibility(View.VISIBLE);
        img_verify_fail.setVisibility(View.VISIBLE);

        btn_upload_again.setVisibility(View.VISIBLE);
        btn_verify.setVisibility(View.VISIBLE);
        btn_next.setVisibility(View.GONE);

        tv_title.setText("Verification Failed");
    }

    private void setVerifiedView() {
        img_verify_complete.setVisibility(View.VISIBLE);
        img_face_detection.setVisibility(View.GONE);
        img_verify_fail.setVisibility(View.GONE);

        btn_upload_again.setVisibility(View.GONE);
        btn_verify.setVisibility(View.GONE);
        btn_next.setVisibility(View.VISIBLE);

        tv_title.setText("Verification Success");
    }
    private void goToNextActivity(Bitmap bitmap){
        byte[] byteArray = SelectImage.getByteArray(bitmap);
        Intent intent=new Intent(mContext,AddMoreProfileImagesActivity.class);
        intent.putExtra("bitmap",byteArray);
        Log.d(TAG, "onActivityResult: "+byteArray);
        startActivity(intent);
        finish();
    }
}
