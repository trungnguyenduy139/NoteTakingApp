package com.example.trungnguyen.notetakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        SwitchCompat swNightMode = findViewById(R.id.sw_night_mode);
        boolean isNightMode = PreferencesHelper.getBoolPreferences(this, PreferencesHelper.KEY_NIGHT_MODE, false);
        swNightMode.setChecked(isNightMode);
        setResult(RESULT_CANCELED);
        swNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferencesHelper.saveBoolPreferences(SettingsActivity.this,
                        PreferencesHelper.KEY_NIGHT_MODE, isChecked);
                AppCompatDelegate.setDefaultNightMode(isChecked ?
                        AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
                setResult(RESULT_OK);
                restartApp();
            }
        });
    }

    public void restartApp() {
        startActivity(new Intent(this, SettingsActivity.class));
        finish();
    }
}
