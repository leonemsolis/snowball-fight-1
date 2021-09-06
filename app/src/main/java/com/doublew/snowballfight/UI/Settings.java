package com.doublew.snowballfight.UI;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Switch;

import com.doublew.snowballfight.R;
import com.doublew.snowballfight.game.core.ResourceManager;

public class Settings extends Activity implements View.OnClickListener{

    private Switch soundSwitch, vibrationSwitch;
    private boolean soundSwitchChecked, vibrationSwitchChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.layout_settings);

        SharedPreferences preferences = getSharedPreferences(this.getString(R.string.PREFERENCES_ID), MODE_PRIVATE);

        soundSwitch = (Switch) findViewById(R.id.sound_switch);
        soundSwitch.setOnClickListener(this);
        soundSwitchChecked = preferences.getBoolean(this.getString(R.string.SOUND_STATE), true);
        soundSwitch.setChecked(soundSwitchChecked);

        vibrationSwitch = (Switch) findViewById(R.id.vibration_switch);
        vibrationSwitch.setOnClickListener(this);
        vibrationSwitchChecked = preferences.getBoolean(this.getString(R.string.VIBRATION_STATE), true);
        vibrationSwitch.setChecked(vibrationSwitchChecked);
    }

    @Override
    public void onClick(View view) {
        SharedPreferences preferences = getSharedPreferences(this.getString(R.string.PREFERENCES_ID), MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        switch(view.getId()) {
            case R.id.sound_switch:
                soundSwitchChecked = !soundSwitchChecked;
                editor.putBoolean(this.getString(R.string.SOUND_STATE), soundSwitchChecked);
                editor.apply();
                soundSwitch.setChecked(soundSwitchChecked);
                ResourceManager.sound_settings = soundSwitchChecked;
                break;
            case R.id.vibration_switch:
                vibrationSwitchChecked = !vibrationSwitchChecked;
                editor.putBoolean(this.getString(R.string.VIBRATION_STATE), vibrationSwitchChecked);
                editor.commit();
                vibrationSwitch.setChecked(vibrationSwitchChecked);
                break;
        }
    }

}
