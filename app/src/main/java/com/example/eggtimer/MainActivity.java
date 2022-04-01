package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView timerTextView;        // shadowing purposes
    SeekBar timerSeekBar;
    Boolean counterIsActive = false;     // initially counter is not active
    Button startButton;
    CountDownTimer countDownTimer;

    public void resetTimer(){

        timerTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        timerSeekBar.setEnabled(true);
        countDownTimer.cancel();
        startButton.setText("START !");
        counterIsActive = false;      // helps in switching the button back and forth

    }


    public void buttonClicked(View view) {

        if (counterIsActive) {        // here the app is already running
           resetTimer();  // brings in all attributes from the reset timer method
        } else {
            counterIsActive = true;          // the counter is now active
            timerSeekBar.setEnabled(false);    // the seekbar vanishes as the counter becomes active
            startButton.setText("STOP !");


            Log.i("Button Pressed", "Nice!");
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) { //10000 = duration  1000 = change frequency
                // timerSeekBar.getProgress() * 1000 converts the above seekbar value code from seconds to milliseconds, which was desired by java
                // also we added 100 to it to sync the log and code since code is a bit faster than the desired inputs
                @Override
                public void onTick(long l) {
                    updateTimer((int) l / 1000);            // converts l to seconds, in an int form

                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.soundsforegg);
                    // we did not write "this" above as "this" is used for timers here
                    mediaPlayer.start();
                    Log.i("Finished", "Timer all done !!");
                    resetTimer();       // enables us to start all over again

                }
            }.start();
        }
    }

    public void updateTimer(int secondsLeft){
        int minutes = secondsLeft / 60;  // seconds to minutes conversion
        int remainingSeconds = secondsLeft - (minutes * 60);    // eg 122 s = 122 - (2*60);     122s = 2min and  2sec

        String secondString = Integer.toString(remainingSeconds);
        if(remainingSeconds <= 9){
            secondString = "0" + secondString;

        }

        timerTextView.setText(Integer.toString(minutes) + ":" + secondString);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = findViewById(R.id.timerSeekBar);
        timerTextView = findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.timerButton);
        timerSeekBar.setMax(600);
//        time is in seconds
        timerSeekBar.setProgress(300);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
               updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}