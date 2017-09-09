package com.example.afreen.sounddemo;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

import static android.R.id.progress;
import static android.support.v7.appcompat.R.styleable.View;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer = new MediaPlayer();
    AudioManager audioManager;

    public void play(View view){
        mediaPlayer = MediaPlayer.create(this, R.raw.laugh);
        mediaPlayer.start();

    }

    public void pause(View view)
    {
        mediaPlayer.pause();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);//allows us to work with Audio on our system
        int maxVolume=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//max value for music stream
        int curVolume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);//current value for music stream

        SeekBar volumeControl = (SeekBar) findViewById(R.id.seekBar);


        volumeControl.setMax(maxVolume);//setting max volume of seekbar to be max volume of system
        volumeControl.setProgress(curVolume);////setting current volume of seekbar to be current volume of system
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {//listens for the even
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Seek Bar value", Integer.toString(progress));
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        final SeekBar scrubber= (SeekBar) findViewById(R.id.scrubber);


        scrubber.setMax(mediaPlayer.getDuration());

        new Timer().scheduleAtFixedRate(new TimerTask() {//scheduling a task to be done at a fixed time
            @Override
            public void run() {
                //shows progress of seek bar and shows current position of audio file
                scrubber.setProgress(mediaPlayer.getCurrentPosition());

            }
        },0,100);


        scrubber.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                mediaPlayer.seekTo(progress);

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

