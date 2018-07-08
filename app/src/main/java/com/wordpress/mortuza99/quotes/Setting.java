package com.wordpress.mortuza99.quotes;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class Setting extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String SHARED_NAME = "QuotesSharedPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // Getting both switches
        final Switch sound = findViewById(R.id.sound);
        final Switch vibration = findViewById(R.id.vibration);

        // On / Off The switch
        // Change log in shared preference
        sharedPreferences = getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // For Sound

        if (sharedPreferences.contains("sound")) {
            String soundStatus = sharedPreferences.getString("sound", "");
            if (soundStatus.equals("soundOn")){
                sound.setChecked(true);
            } else {
                sound.setChecked(false);
            }
        }

        if (sharedPreferences.contains("vibration")) {
            String soundStatus = sharedPreferences.getString("vibration", "");
            if (soundStatus.equals("vibrationOn")){
                vibration.setChecked(true);
            } else {
                vibration.setChecked(false);
            }
        }

        sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (sound.isChecked()) {
                    editor.putString("sound", "soundOn");
                    editor.apply();
                    Toast.makeText(getApplicationContext(),"Sound On",Toast.LENGTH_LONG).show();
                } else {
                    editor.putString("sound", "soundOff");
                    editor.apply();
                    Toast.makeText(getApplicationContext(),"Sound Off",Toast.LENGTH_LONG).show();
                }
            }
        });

        // For Vibration
        vibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (vibration.isChecked()) {
                    editor.putString("vibration", "vibrationOn");
                    editor.apply();
                    Toast.makeText(getApplicationContext(),"Vibration On",Toast.LENGTH_LONG).show();
                } else {
                    editor.putString("vibration", "vibrationOff");
                    editor.apply();
                    Toast.makeText(getApplicationContext(),"Vibration Off",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
