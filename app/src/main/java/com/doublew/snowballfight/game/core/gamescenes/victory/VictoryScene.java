package com.doublew.snowballfight.game.core.gamescenes.victory;

import android.content.Context;
import android.content.SharedPreferences;

import com.doublew.snowballfight.R;
import com.doublew.snowballfight.game.core.GameManager;
import com.doublew.snowballfight.game.core.gamescenes.GameScene;


public class VictoryScene extends GameScene {
    GameManager gameManager;
    public VictoryScene(GameManager gameManager, int stageID, Context context) {
        super(stageID, context);
        super.updater = new VictoryUpdater(this);
        super.renderer = new VictoryRenderer(context.getString(R.string.victory));
        this.gameManager = gameManager;
    }


    public void finishGame() {
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.PREFERENCES_ID), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(context.getString(R.string.IS_THERE_SAVED_GAME), false);
        gameManager.gameOver();
    }
}
