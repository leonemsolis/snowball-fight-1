package com.doublew.snowballfight.game.cover;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import com.doublew.snowballfight.R;
import com.doublew.snowballfight.game.core.ResourceManager;

public class GameActivity extends Activity {
    public static float canvasWidth, canvasHeight;
    private GameView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        canvasHeight = displayMetrics.heightPixels;
        canvasWidth = displayMetrics.widthPixels;
        gameView = new GameView(this);
        setContentView(gameView);

        SharedPreferences s = getSharedPreferences(this.getString(R.string.PREFERENCES_ID), MODE_PRIVATE);
        SharedPreferences.Editor editor = s.edit();
        editor.putBoolean(this.getString(R.string.IS_THERE_SAVED_GAME), true);
        editor.apply();
    }

    @Override
    protected void onResume() {
        gameView.gameThread.setPaused(false);
        super.onResume();
    }

    @Override
    protected void onPause() {
        ResourceManager.stopSound();
        ResourceManager.stopSound();
        ResourceManager.stopSound();
        gameView.gameThread.setPaused(true);
        super.onPause();
    }

    @Override
    protected void onStop() {
        gameView.gameThread.getGameManager().abortBattle();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        gameView.stopThread();
        super.onDestroy();
    }
}
