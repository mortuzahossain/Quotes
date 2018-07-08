package com.wordpress.mortuza99.quotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import au.com.bytecode.opencsv.CSVReader;

public class MainActivity extends AppCompatActivity {
    private ShakeListener mShaker;

    private String[] quot = {
            "If Allah wants to do good to somebody, he afflicts him with trials.",
            "Doctors can treat you, but only ALLAH can heal you.",
            "Allah is enough.",
            "Have fear of Allah wherever you are."
    };

    int numberOfQuots;
    Random random;
    List<String[]> list = new ArrayList<String[]>();
    SharedPreferences sharedPreferences;
    public static final String SHARED_NAME = "QuotesSharedPreferences";

    boolean VibrationStatus = true;
    boolean SoundStatus = true;

    // For Sound
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String next[];
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(getAssets().open("data.csv")));
            for (; ; ) {
                next = reader.readNext();
                if (next != null) {
                    list.add(next);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Extract Data From Shared Preference And Change The Setting
        sharedPreferences = getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.contains("sound"))
            SoundStatus = sharedPreferences.getString("sound", "").equals("soundOn");
        if (sharedPreferences.contains("vibration"))
            VibrationStatus = sharedPreferences.getString("vibration", "").equals("vibrationOn");

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music);

        if (SoundStatus){
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }

        final TextView quots = findViewById(R.id.quots);
        final FloatingActionButton fab1 = findViewById(R.id.fab1);
        FloatingActionButton fab = findViewById(R.id.fab);
        // For the first time while starting application

        // Added Custom Font And Setting Initial Text
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/custom_fonts.ttf");
        quots.setTypeface(typeface);
        quots.setShadowLayer(5, 2, 2, R.color.black);

        if (list.get(0)[0] != "") quots.setText(list.get(0)[0]);
        else quots.setText(R.string.initial_text);


        // Gating the length of the text array
        numberOfQuots = list.isEmpty() ? quot.length : list.size();
        random = new Random();

        final Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mShaker = new ShakeListener(this);
        mShaker.setOnShakeListener(new ShakeListener.OnShakeListener() {
            public void onShake() {

                if (VibrationStatus) vibe.vibrate(100);

                final int position = random.nextInt(numberOfQuots - 1);
                quots.setText(list.isEmpty() ? quot[position] : list.get(position)[0]);

                // If Click on share Button
                fab1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //  Sending By message
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, quot[position]);
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                    }
                });
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(getApplicationContext()
                        .getApplicationInfo()
                        .sourceDir)));
                startActivity(Intent.createChooser(intent, "Share App"));
            }
        });

        // Go to setting Activity
        FloatingActionButton setting = findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                startActivity(new Intent(getApplicationContext(), Setting.class));
            }
        });


    }

    // Not to vibrate during resume and pause.
    @Override
    public void onResume() {
        mShaker.resume();
        super.onResume();
//        if (SoundStatus) mediaPlayer.start();
    }

    @Override
    public void onPause() {
        mShaker.pause();
        super.onPause();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    // On Back Pressed Refresh the activity
    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

}
