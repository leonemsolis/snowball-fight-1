package com.doublew.snowballfight.game.core.gamescenes.map;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Vibrator;

import com.doublew.snowballfight.R;
import com.doublew.snowballfight.game.core.GameManager;
import com.doublew.snowballfight.game.core.ResourceManager;
import com.doublew.snowballfight.game.core.gamescenes.GameScene;


public class MapScene extends GameScene {
    private GameManager gameManager;
    enum MapPosition {NO, STAGE1, STAGE2, STAGE3, STAGE4, SHOP1, SHOP2}
    MapPosition mapPosition;
    int mapX, mapY;
    boolean isStage1Available, isStage2Available, isStage3Available, isStage4Available;
    String notificationText, additionalNotificationText;
    int promptID;
    int newStageAvailable;
    boolean notificationCombo;
    private SharedPreferences sp;
    public boolean [][]booleanLayer;

    public MapScene(GameManager gameManager, int stageID, Context context, int goldAcquired, int newStageAvailable) {
        super(stageID, context);
        this.gameManager = gameManager;
        additionalNotificationText = null;
        notificationText = "";
        displayingContent = 0;
        mapPosition = MapPosition.NO;
        mapX = 0;
        mapY = 0;
        promptID = 0;
        sp = context.getSharedPreferences(context.getString(R.string.PREFERENCES_ID), Context.MODE_PRIVATE);
        isStage1Available = sp.getBoolean(context.getString(R.string.STAGE1_PREFERENCE), true);
        isStage2Available = sp.getBoolean(context.getString(R.string.STAGE2_PREFERENCE), false);
        isStage3Available = sp.getBoolean(context.getString(R.string.STAGE3_PREFERENCE), false);
        isStage4Available = sp.getBoolean(context.getString(R.string.STAGE4_PREFERENCE), false);

        booleanLayer = new boolean[][]{ {false, false, false, false, false, false, false, false, false, false, false},
                                        {false, false, false, true , false, false, false, true , false, false, false},
                                        {false, false, false, true , false, false, false, true , false, false, false},
                                        {false, true , true , true , true , true , true , true , true , true , false},
                                        {false, false, false, true , false, false, false, true , false, false, false},
                                        {false, false, false, true , false, false, false, true , false, false, false},
                                        {false, false, false, false, false, false, false, false, false, false, false}};
        mapY = 3;
        mapX = 5;

        super.updater = new MapUpdater(this);
        super.renderer = new MapRenderer(this);

        this.newStageAvailable = newStageAvailable;
        if(goldAcquired != 0) {
            notificationCombo = newStageAvailable != 0;
            createNotification(goldAcquired+" "+context.getString(R.string.gold_acquired));
        }
    }

    void mapEnter(MapPosition location) {
        switch (location) {
            case SHOP1:
                if(checkAborted() == -1)
                    gameManager.enterNonMapScene(GameManager.SHOP1_SCENE_ID);
                else
                    createNotification(context.getString(R.string.finish_alert)+checkAborted()+"!");
                break;
            case SHOP2:
                if(checkAborted() == -1)
                    gameManager.enterNonMapScene(GameManager.SHOP2_SCENE_ID);
                else
                    createNotification(context.getString(R.string.finish_alert)+checkAborted()+"!");
                break;
            case STAGE1:
                if(isStage1Available) {
                    if(checkAborted() == -1 || checkAborted() == GameManager.STAGE1_SCENE_ID) {
                        ResourceManager.currentBackground = ResourceManager.background1;
                        gameManager.enterNonMapScene(GameManager.STAGE1_SCENE_ID);
                    } else {
                        createNotification(context.getString(R.string.finish_alert)+checkAborted()+"!");
                    }
                }
                break;
            case STAGE2:
                if(isStage2Available) {
                    if(checkAborted() == -1 || checkAborted() == GameManager.STAGE2_SCENE_ID) {
                        ResourceManager.currentBackground = ResourceManager.background2;
                        gameManager.enterNonMapScene(GameManager.STAGE2_SCENE_ID);
                    } else {
                        createNotification(context.getString(R.string.finish_alert)+checkAborted()+"!");
                    }
                }
                break;
            case STAGE3:
                if(isStage3Available) {
                    if(checkAborted() == -1 || checkAborted() == GameManager.STAGE3_SCENE_ID) {
                        ResourceManager.currentBackground = ResourceManager.background3;
                        gameManager.enterNonMapScene(GameManager.STAGE3_SCENE_ID);
                    } else {
                        createNotification(context.getString(R.string.finish_alert)+checkAborted()+"!");
                    }
                }
                break;
            case STAGE4:
                if(isStage4Available) {
                    if(checkAborted() == -1 || checkAborted() == GameManager.STAGE4_SCENE_ID) {
                        ResourceManager.currentBackground = ResourceManager.background4;
                        gameManager.enterNonMapScene(GameManager.STAGE4_SCENE_ID);
                    } else {
                        createNotification(context.getString(R.string.finish_alert)+checkAborted()+"!");
                    }
                }
                break;
        }
    }

    void createNotification(String text) {
        displayingContent = 1;
        notificationText = text;
    }

    void createPrompt(int promptID) {
        displayingContent = 2;
        this.promptID = promptID;
    }

    void vibrateChallenge() {
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.PREFERENCES_ID), Context.MODE_PRIVATE);
        if(sp.getBoolean(context.getString(R.string.VIBRATION_STATE), true)) {
            Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            long[] pattern = {0, 200, 50, 200, 50, 200};
            v.vibrate(pattern, -1);
        }
    }

    int checkAborted() {
        return sp.getInt(context.getString(R.string.ABORTED_STAGE), -1);
    }
}
