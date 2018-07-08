package com.wordpress.mortuza99.quotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ShakeListener mShaker;

    //    For Static Quotes
    private String[] quot = {
            "If Allah wants to do good to somebody, he afflicts him with trials.",
            "Doctors can treat you, but only ALLAH can heal you.",
            "Allah is enough.",
            "Have fear of Allah wherever you are.",
            "When things are too hard to handle, retreat & count your blessings instead.",
            "A chance judge yourself on how close you are to ALLAH… how much do you want Jannah? enough to leave your bed and pray Fajr?",
            "Allah’s timing is perfect in every matter. We don’t always understand the wisdom behind it. But we have to learn to trust it.",
            "Allah understands our prayers even when we can’t find the words to say Them.",
            "Tawakkul is having full faith that ALLAH will take care of you even when things look Impossible.",
            "Allah knows what is the best for you and when it’s best for you to have it.",
            "One who remembers ALLAH is never AloneLY.",
            "Being a Muslim is about changing yourself, Not changing Islam.",
            "Every new breath that ALLAH allows you to take is not just a blessing but also a responsibility.",
            "Severing your parents in their old age is as good as opening the doors of PARADISE, so don’t miss out.",
            "When we repair our relationship with Allah, He repairs everything else for us.",
            "You Cannot Delete you internet history from ALLAH.",
            "Take everyday as a chance to become a better Muslim.",
            "Only in ISLAM do the king and peasant bow down together side by side proclaiming God’s greatness.",
            "Why wish upon a star? when you cam pray to the one who created it.",
            "The solution to every problem is in SABR (patience) and ISTIGFAAR (seeking forgiveness).",
            "Born from different mothers skins of all colours come together as brothers . that’s the beauty of Islam.",
            "When Love is for the sake of ALLAH, It never dies.",
            "So What if this life isn’t perfect? it’s not jannah. Nouman Ali Khan.",
            "Men and Women have equal rewards for Their deeds.",
            "The Worst of our faults is our interest in other people’s Faults.",
            "Trust Allah when things don’t work out the way you wanted. Allah has something better planned for you.",
            "Stay close to anything that reminds you of ALLAH.",
            "The overly jealous seek to harm and hurt other, but in the end only harm themselves.",
            "Being Muslim is for all day, Not just 5 time a day.",
            "No matter how hurt you are, You will always find comfort with ALLAH.",
            "If you want to destroy any nation without war, make adultery & nudity common in the next generation.",
            "Worries End When SALAH Begins.",
            "Rather than stressing about things we cannot control, pray to The One in control and find relief.",
            "Balance Your Dunya (world) around your deen (faith), its all a matter of priorities.",
            "Bad things in life open your eyes to those things your weren’t paying much attention to before.",
            "That’s a blessing from Allah Too!",
            "Who can help you get through your problems But ALLAH.",
            "Be embarrassed to sin in public, don’t be shy to show your faith.",
            "Your Interview with ALLAH is Coming.",
            "Fill your heart with Eemaan and it will become the post peaceful place on earth.",
            "Peoples of ALHAMDULILLAH” Don’t Have to Complain.",
            "Indeed, ALLAH does not wrong the people at all, but it is the people who are wronging themselves.",
            "Faith is Trusting GOD even when you don’t understand his plan.",
            "If the heart becomes hardend, the eye becomes dry.",
            "The Quran is for ourselves, not our Shelves.",
            "Pyayer isn’t For Allah, It’s for you. He doesn’t need us But we need him.",
            "The Greatest thing a Friend can do for you is bring you closer to ALLAH.",
            "If you don’t want your kids to hurt others, don’t show them how to do it.",
            "The greatest Jihad is to battle your own soul. to fight the evil within yourself.",
            "ALLAH makes the impossible Possible.",
            "Guilt is a gift from ALLAH warning you that what you are doing is violating your soul.",
            "Your prefer the life of this world, while the hereafter is better & more lasting.",
            "No one besides ALLAH can rescue a sould from Hardship.",
            "Be Patient – For what was written for you was written by the greatest of writers.",
            "There is reward for Kindness to every living thing.",
            "Be somebody in the eyes of ALLAH, even if you are nobody in the eyes of people."
    };

    SharedPreferences sharedPreferences;
    public static final String SHARED_NAME = "QuotesSharedPreferences";

    boolean VibrationStatus = true;
    boolean SoundStatus = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Go to setting Activity
        FloatingActionButton setting = findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Setting.class));
            }
        });

        // Extract Data From Shared Preference And Change The Setting
        sharedPreferences = getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.contains("sound")) {
            SoundStatus = sharedPreferences.getString("sound", "").equals("soundOn");
        }
        if (sharedPreferences.contains("vibration")) {
            VibrationStatus = sharedPreferences.getString("vibration", "").equals("vibrationOn");
        }

        final TextView quots = findViewById(R.id.quots);
        final FloatingActionButton fab1 = findViewById(R.id.fab1);
        FloatingActionButton fab = findViewById(R.id.fab);
        // For the first time while starting application

        // Added Custom Font And Seting Initial Text
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/custom_fonts.ttf");
        quots.setTypeface(typeface);
        quots.setShadowLayer(5, 2, 2, R.color.black);
        quots.setText(R.string.initial_text);

        // Gating the length of the text array
        final int numberOfQuots = quot.length;
        final Random random = new Random();

        final Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mShaker = new ShakeListener(this);
        mShaker.setOnShakeListener(new ShakeListener.OnShakeListener() {
            public void onShake() {

                if (VibrationStatus) vibe.vibrate(100);

                final int position = random.nextInt(numberOfQuots - 1);
                quots.setText(quot[position]);

                // If Click on share Buttom
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


    }

    // Not to vibrate during resume and pause.
    @Override
    public void onResume() {
        mShaker.resume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mShaker.pause();
        super.onPause();
    }

    // On Back Pressed Refresh the activity
    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

}
