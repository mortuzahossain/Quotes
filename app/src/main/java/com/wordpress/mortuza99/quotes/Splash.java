package com.wordpress.mortuza99.quotes;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        // Change the font style
        TextView textView = findViewById( R.id.splashText);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/custom_fonts.ttf");
        textView.setTypeface(typeface);
/*

        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(5100);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
            }
        };
        timer.start();
*/

        Timer runsTimerTask = new Timer();
        TimerTask showTimerTask = new TimerTask() {
            @Override
            public void run() {
                finish();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        };
        runsTimerTask.schedule(showTimerTask, 5000);

    }

}
