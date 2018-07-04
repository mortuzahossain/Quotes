package com.wordpress.mortuza99.quotes;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private ShakeListener mShaker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Using Custom Fonts
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/custom_fonts.ttf");
        //textView.setTypeface(typeface);
        final Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        mShaker = new ShakeListener(this);
        mShaker.setOnShakeListener(new ShakeListener.OnShakeListener () {
            public void onShake()
            {
                vibe.vibrate(100);
                new AlertDialog.Builder(MainActivity.this)
                        .setPositiveButton(android.R.string.ok, null)
                        .setMessage("Shooken!")
                        .show();
            }
        });
    }
    @Override
    public void onResume()
    {
        mShaker.resume();
        super.onResume();
    }
    @Override
    public void onPause()
    {
        mShaker.pause();
        super.onPause();
    }
}
