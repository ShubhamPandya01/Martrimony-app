package com.example.matrimony_demo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.example.matrimony_demo.R;

public class SplashActivity extends AppCompatActivity {

    ProgressBar progressBar;
    int progressStatus=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = findViewById(R.id.progressBar);
        final Handler handler = new Handler();

        // run()
        new Thread(() -> {

            while (progressStatus < 100) {
                progressStatus += 5;
                // Update the progress bar and display the
                //current value in the text view

                handler.post(() -> progressBar.setProgress(progressStatus));

                try {
                    // Sleep for 200 milliseconds.
                    //Just to display the progress slowly
                    Thread.sleep(200);
                    if (progressStatus == 100){
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } // while loop
        }).start();
    }
}