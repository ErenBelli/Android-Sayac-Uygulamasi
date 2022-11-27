package com.erenbelli.timerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    Button stgAltPlus, stgAltMinus, stgUstPlus, stgUstMinus, stgGeri;
    EditText stgAltText,stgUstText;
    Switch stgUstTitresim, stgUstSes, stgAltTitresim, stgAltSes;

    SettingsShared settingsShared;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



        stgAltPlus = (Button) findViewById(R.id.stgAltPlus);
        stgAltMinus = (Button) findViewById(R.id.stgAltMinus);
        stgUstPlus = (Button) findViewById(R.id.stgUstPlus);
        stgUstMinus = (Button) findViewById(R.id.stgUstMinus);
        stgGeri = (Button) findViewById(R.id.stgGeri);
        stgAltText = (EditText) findViewById(R.id.stgAltText);
        stgUstText = (EditText) findViewById(R.id.stgUstText);
        stgUstTitresim = (Switch) findViewById(R.id.stgUstTitresim);
        stgUstSes = (Switch) findViewById(R.id.stgUstSes);
        stgAltTitresim = (Switch) findViewById(R.id.stgAltTitresim);
        stgAltSes = (Switch) findViewById(R.id.stgAltSes);

        settingsShared = SettingsShared.getSettingsShared(getApplicationContext());


        stgGeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        stgAltPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsShared.lowerLimit++;
                stgAltText.setText(String.valueOf(settingsShared.lowerLimit));
            }
        });
        stgAltMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsShared.lowerLimit--;
                stgAltText.setText(String.valueOf(settingsShared.lowerLimit));
            }
        });
        stgUstPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsShared.upperLimit++;
                stgUstText.setText(String.valueOf(settingsShared.upperLimit));
            }
        });
        stgUstMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsShared.upperLimit--;
                stgUstText.setText(String.valueOf(settingsShared.upperLimit));
            }
        });

        stgUstTitresim.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                settingsShared.upperVib = b;
            }
        });
        stgUstSes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                settingsShared.upperVoice = b;
            }
        });
        stgAltSes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                settingsShared.lowerVoice = b;
            }
        });
        stgAltTitresim.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                settingsShared.lowerVib = b;
            }
        });

        stgAltText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(stgUstText.getText().toString().length() != 0 )
                    settingsShared.upperLimit = Integer.parseInt(stgUstText.getText().toString());
            }
        });

        stgUstText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(stgAltText.getText().toString().length() != 0 )
                    settingsShared.lowerLimit = Integer.parseInt(stgAltText.getText().toString());
            }
        });





    }

    @Override
    protected void onResume() {
        super.onResume();
        stgUstText.setText(String.valueOf(settingsShared.upperLimit));
        stgAltText.setText(String.valueOf(settingsShared.lowerLimit));
        stgUstTitresim.setChecked(settingsShared.upperVib);
        stgAltTitresim.setChecked(settingsShared.lowerVib);
        stgUstSes.setChecked(settingsShared.upperVoice);
        stgAltSes.setChecked(settingsShared.lowerVoice);


    }


    @Override
    protected void onPause() {
        super.onPause();
        settingsShared.saveValues();
    }
}