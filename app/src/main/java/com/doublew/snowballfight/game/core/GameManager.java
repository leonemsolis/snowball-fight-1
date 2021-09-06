package com.doublew.snowballfight.game.core;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import com.doublew.snowballfight.game.core.gamescenes.GameScene;
import com.doublew.snowballfight.game.core.gamescenes.battle.BattleScene;
import com.doublew.snowballfight.game.core.gamescenes.map.MapScene;
import com.doublew.snowballfight.game.core.gamescenes.shop.ShopScene;
import com.doublew.snowballfight.game.core.gamescenes.victory.VictoryScene;
import com.doublew.snowballfight.game.cover.GameView;


public class GameManager {
    public static final int MAP_SCENE_ID = 0;
    public static final int STAGE1_SCENE_ID = 1;
    public static final int STAGE2_SCENE_ID = 2;
    public static final int STAGE3_SCENE_ID = 3;
    public static final int STAGE4_SCENE_ID = 4;
    public static final int SHOP1_SCENE_ID = 5;
    public static final int SHOP2_SCENE_ID = 6;
    private static final int VICTORY_SCENE_ID = 7;
    private GameScene currentScene = null;
    private float cutWidth = GameView.cutWidth;
    private float scale = GameView.scaleFactor;
    private int backgroundColor;
    private Context context;
    boolean gameOver;

    GameManager(Context context) {
        this.context = context;
        gameOver = false;
        enterMap(MAP_SCENE_ID, 0, 0);
    }

    public void update(long passed) {
        if(currentScene != null) {
            currentScene.update(passed);
        }
    }

    public void render(Canvas canvas) {
        canvas.drawColor(backgroundColor);
        canvas.clipRect(cutWidth, 0, cutWidth+GameView.gameWidth*scale, GameView.gameHeight*scale);
        canvas.translate(cutWidth, 0);
        canvas.scale(scale, scale);
        if(currentScene != null) {
            currentScene.render(canvas);
        }
    }

    public void onPressed(float x, float y, boolean first) {
        currentScene.onPressed(x, y, first);
    }

    public void onReleased(float x, float y, boolean first) {
        currentScene.onReleased(x, y, first);
    }

    public void onDragged(float x, float y, boolean first) {
        currentScene.onDragged(x, y, first);
    }

    public void enterNonMapScene(int id) {
        switch (id) {
            case STAGE1_SCENE_ID:
            case STAGE2_SCENE_ID:
            case STAGE3_SCENE_ID:
            case STAGE4_SCENE_ID:
                backgroundColor = Color.BLACK;
                currentScene = new BattleScene(this, id, context);
                break;
            case SHOP1_SCENE_ID:
            case SHOP2_SCENE_ID:
                backgroundColor = Color.argb(225, 54, 43, 41);
                currentScene = new ShopScene(this, id, context);
                break;
            case VICTORY_SCENE_ID:
                backgroundColor = Color.argb(225, 0, 0, 0);
                currentScene = new VictoryScene(this, id, context);
        }
    }

    public void enterMap(int id, int goldAcquired, int newStageAvailable) {
        backgroundColor = Color.argb(225, 0, 44, 129);
        currentScene = new MapScene(this, id, context, goldAcquired, newStageAvailable);
    }

    public void gameOver() {
        gameOver = true;
    }

    void setBattlePause() {
        if(currentScene instanceof BattleScene) {
            currentScene.displayingContent = 1;
        }
    }

    public void abortBattle() {
        if (currentScene instanceof BattleScene) {
            ((BattleScene) currentScene).abortBattle();
        }
    }
}
