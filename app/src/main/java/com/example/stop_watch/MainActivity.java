package com.example.stop_watch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView time;
    FloatingActionButton onStop, onPlay, onReset;

    String selectedAns = "";

    private int seconds;
    private boolean running;
    private boolean wasRunning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time = findViewById(R.id.time);
        onStop = findViewById(R.id.stop);
        onPlay = findViewById(R.id.play);
        onReset = findViewById(R.id.reset);

        if(savedInstanceState !=null){
            savedInstanceState.getInt("seconds");
            savedInstanceState.getBoolean("running");
            savedInstanceState.getBoolean("wasRunning");
        }

        runTimer();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("seconds",seconds);
        outState.putBoolean("running",running);
        outState.putBoolean("wasRunning",wasRunning);
    }

    private void runTimer() {

        Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int mins = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time_text = String.format(Locale.getDefault(),"%02d:%02d:%02d", hours, mins, secs);
                time.setText(time_text);

                if(running){
                    seconds++;
                }

                handler.postDelayed(this,1000);

            }
        });
    }

    public void onStop(View view) {
        running = false;
    }

    public void onPlay(View view) {
        running = true;
    }

    public void onReset(View view) {
        running = false;
        seconds = 0;
    }

    @Override
    protected void onPause() {
        super.onPause();

        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(wasRunning){
            running = true;
        }
    }
}