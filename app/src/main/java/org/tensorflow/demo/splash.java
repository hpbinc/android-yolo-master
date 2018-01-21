package org.tensorflow.demo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class splash extends AppCompatActivity implements Animation.AnimationListener {

    private ImageView mFbLogoImageView;
    private TextView textView,mimos;
    private Button mNewAccountButton;
    private ImageView mFbLogoStaticImageView;

    private Boolean ANIMATION_ENDED = false;
    private Boolean START_ANIMATION = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(savedInstanceState != null) {
            START_ANIMATION = savedInstanceState.getBoolean("START_ANIMATION");
        }

        mFbLogoImageView = (ImageView) findViewById(R.id.fbLogoImageView);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)

            textView = (TextView) findViewById(R.id.textView);
        mimos= (TextView) findViewById(R.id.mimos);
        //mNewAccountButton = (Button) findViewById(R.id.newAccountButton);
        mFbLogoStaticImageView = (ImageView) findViewById(R.id.fbLogoStaticImageView);

        if(START_ANIMATION) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                textView.setVisibility(View.GONE);
            mimos.setVisibility(View.GONE);
            //mNewAccountButton.setVisibility(View.GONE);
            mFbLogoStaticImageView.setVisibility(View.GONE);

            Animation moveFBLogoAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
            moveFBLogoAnimation.setFillAfter(true);
            moveFBLogoAnimation.setAnimationListener(this);
            mFbLogoImageView.startAnimation(moveFBLogoAnimation);
        }
        else {
            mFbLogoImageView.setVisibility(View.GONE);
        }

        final View activityRootView = findViewById(R.id.mainConstraintLayout);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(ANIMATION_ENDED || !START_ANIMATION) {
                    int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                    if (heightDiff > dpToPx(splash.this, 200)) {
                        //Soft keyboard is shown
                        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)

                            textView.setVisibility(View.GONE);
                        mimos.setVisibility(View.GONE);
                    } else {
                        //Soft keyboard is hidden
                        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                            textView.setVisibility(View.VISIBLE);
                        mimos.setVisibility(View.VISIBLE);
                    }
                }
            }
        });




        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {

                    sleep(2000);
                    Intent intent = new Intent(getApplicationContext(),CameraActivity.class);
                    startActivity(intent);
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        myThread.start();


    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        mFbLogoStaticImageView.setVisibility(View.VISIBLE);
        mFbLogoImageView.clearAnimation();
        mFbLogoImageView.setVisibility(View.GONE);

        textView.setAlpha(0f);
        textView.setVisibility(View.VISIBLE);
        mimos.setAlpha(0f);
        mimos.setVisibility(View.VISIBLE);

        //  mNewAccountButton.setAlpha(0f);
        //  mNewAccountButton.setVisibility(View.VISIBLE);

        int mediumAnimationTime = getResources().getInteger(android.R.integer.config_mediumAnimTime);


        textView.animate()
                .alpha(1f)
                .setDuration(mediumAnimationTime)
                .setListener(null);

        mimos.animate()
                .alpha(1f)
                .setDuration(mediumAnimationTime)
                .setListener(null);

      /*  mNewAccountButton.animate()
                .alpha(1f)
                .setDuration(mediumAnimationTime)
                .setListener(null);*/

        ANIMATION_ENDED = true;
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("START_ANIMATION", false);
    }
}
