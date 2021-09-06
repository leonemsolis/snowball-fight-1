package com.doublew.snowballfight.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.doublew.snowballfight.R;
import com.doublew.snowballfight.UI.help.Help;
import com.doublew.snowballfight.UI.help.Rate;
import com.doublew.snowballfight.game.core.ResourceManager;
import com.doublew.snowballfight.game.cover.GameActivity;

public class Title extends Activity implements View.OnClickListener{
    private boolean continueScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new ResourceManager(this);
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.layout_title);

        ImageButton playButton = (ImageButton) findViewById(R.id.play_button);
        playButton.setOnClickListener(this);

        ImageButton settingsImageButton = (ImageButton) findViewById(R.id.settings_button);
        settingsImageButton.setOnClickListener(this);

        ImageButton rateImageButton = (ImageButton) findViewById(R.id.rate_button);
        rateImageButton.setOnClickListener(this);

        ImageButton helpImageButton = (ImageButton) findViewById(R.id.help_button);
        helpImageButton.setOnClickListener(this);

        createUpdateDialog();
    }

    @Override
    public void onResume() {
        SharedPreferences preferences = getSharedPreferences(this.getString(R.string.PREFERENCES_ID), MODE_PRIVATE);
        continueScreen = preferences.getBoolean(this.getString(R.string.IS_THERE_SAVED_GAME), false);
        ResourceManager.playSound(3);
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.play_button:
                if(continueScreen) {
                    createContinueDialog();
                } else {
                    SharedPreferences sp = getSharedPreferences(this.getString(R.string.PREFERENCES_ID), MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt(this.getString(R.string.GOLD_PREFERENCES), 0);
                    editor.putInt(this.getString(R.string.MANA_PREFERENCES), 1);
                    for(int i = 0; i < 5; ++i) {
                        editor.putInt(this.getString(R.string.ITEM_PREFERENCES)+i, -1);
                    }
                    editor.putBoolean(this.getString(R.string.STAGE1_PREFERENCE), true);
                    editor.putBoolean(this.getString(R.string.STAGE2_PREFERENCE), false);
                    editor.putBoolean(this.getString(R.string.STAGE3_PREFERENCE), false);
                    editor.putBoolean(this.getString(R.string.STAGE4_PREFERENCE), false);
                    editor.putInt(this.getString(R.string.STAGE1_LEVELS_PREFERENCE), 1);
                    editor.putInt(this.getString(R.string.STAGE2_LEVELS_PREFERENCE), 1);
                    editor.putInt(this.getString(R.string.STAGE3_LEVELS_PREFERENCE), 1);
                    editor.putInt(this.getString(R.string.STAGE4_LEVELS_PREFERENCE), 1);
                    editor.putInt(getString(R.string.SAVED_HP), 100);
                    editor.putInt(getString(R.string.ABORTED_STAGE), -1);
                    editor.apply();
                    intent = new Intent(this, GameActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.settings_button:
                intent = new Intent(this, Settings.class);
                startActivity(intent);
                break;
            case R.id.help_button:
                intent = new Intent(this, Help.class);
                startActivity(intent);
                break;
            case R.id.rate_button:
                intent = new Intent(this, Rate.class);
                startActivity(intent);
                break;
        }
    }

    private void createUpdateDialog() {
        final Activity main = this;
        AlertDialog.Builder updateDialog = new AlertDialog.Builder(this);
        updateDialog.setMessage(main.getString(R.string.update_text)).setPositiveButton(main.getString(R.string.continue_answer_yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Open URL
                final String gameAddress = "com.leonemsolis.sbfr&hl=en";
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + gameAddress)));
                } catch (android.content.ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + gameAddress)));
                }
            }
        }).setNegativeButton(main.getString(R.string.continue_answer_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Close
            }
        });
        updateDialog.show();
    }


   private void createContinueDialog() {
       final Activity main = this;
       AlertDialog.Builder continueDialog = new AlertDialog.Builder(this);
       continueDialog.setMessage(main.getString(R.string.continue_text)).setPositiveButton(main.getString(R.string.continue_answer_yes), new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               Intent intent = new Intent(main, GameActivity.class);
               startActivity(intent);
           }
       }).setNegativeButton(main.getString(R.string.continue_answer_no), new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {

               Intent intent;
               SharedPreferences sp = main.getSharedPreferences(main.getString(R.string.PREFERENCES_ID), Context.MODE_APPEND);
               SharedPreferences.Editor editor = sp.edit();
               editor.putInt(main.getString(R.string.GOLD_PREFERENCES), 0);
               editor.putInt(main.getString(R.string.MANA_PREFERENCES), 1);
               for(int i = 0; i < 5; ++i) {
                   editor.putInt(main.getString(R.string.ITEM_PREFERENCES)+i, -1);
               }
               editor.putBoolean(main.getString(R.string.STAGE1_PREFERENCE), true);
               editor.putBoolean(main.getString(R.string.STAGE2_PREFERENCE), false);
               editor.putBoolean(main.getString(R.string.STAGE3_PREFERENCE), false);
               editor.putBoolean(main.getString(R.string.STAGE4_PREFERENCE), false);
               editor.putInt(main.getString(R.string.STAGE1_LEVELS_PREFERENCE), 1);
               editor.putInt(main.getString(R.string.STAGE2_LEVELS_PREFERENCE), 1);
               editor.putInt(main.getString(R.string.STAGE3_LEVELS_PREFERENCE), 1);
               editor.putInt(main.getString(R.string.STAGE4_LEVELS_PREFERENCE), 1);
               editor.putInt(getString(R.string.SAVED_HP), 100);
               editor.putInt(getString(R.string.ABORTED_STAGE), -1);
               editor.apply();

               intent = new Intent(main, GameActivity.class);
               startActivity(intent);
           }
       });
       continueDialog.show();
   }

   @Override
   public void onDestroy() {
       ResourceManager.stopSound();
       super.onDestroy();
   }
}