package com.erenbelli.timerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textValue;
    Button buttonPlus, buttonMinus, buttonSettings;

    SettingsShared settingsShared;
    Vibrator vibrator = null;
    MediaPlayer mediaPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textValue = (TextView) findViewById(R.id.textValue);
        buttonPlus = (Button) findViewById(R.id.buttonPlus);
        buttonMinus = (Button) findViewById(R.id.buttonMinus);
        buttonSettings = (Button) findViewById(R.id.buttonSettings);

        Context context = getApplicationContext();
        settingsShared = SettingsShared.getSettingsShared(context);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mediaPlayer = MediaPlayer.create(context, R.raw.warning);

        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateValues(1);
            }
        });

        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateValues(-1);
            }
        });

        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });








    }
    public void updateValues(int deger){
        if(deger<0){
            if(settingsShared.currentValue + deger < settingsShared.lowerLimit){
                settingsShared.currentValue = settingsShared.lowerLimit;
                if(settingsShared.lowerVib){
                    vibAlert();
                }
                if(settingsShared.lowerVoice){
                    soundAlert();
                }

            }
            else
                settingsShared.currentValue += deger;

        }
        if(deger>0){
            if(settingsShared.currentValue + deger > settingsShared.upperLimit){
                settingsShared.currentValue = settingsShared.upperLimit;

                if(settingsShared.upperVib){
                    vibAlert();
                }
                if(settingsShared.upperVoice){
                    soundAlert();
                }

            }
            else
                settingsShared.currentValue += deger;

        }
        textValue.setText(String.valueOf(settingsShared.currentValue));
    }

    public void soundAlert(){
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
    }

    public void vibAlert(){
        if(vibrator.hasVibrator()){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                vibrator.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
            }
            vibrator.vibrate(500);
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode){
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if(action== KeyEvent.ACTION_DOWN)
                    updateValues(-5);
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                if(action== KeyEvent.ACTION_UP)
                    updateValues(5);
                return true;

        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onResume() {
        textValue.setText(String.valueOf(settingsShared.currentValue));
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        settingsShared.saveValues();
    }
}