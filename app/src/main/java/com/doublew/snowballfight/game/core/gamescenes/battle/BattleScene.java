package com.doublew.snowballfight.game.core.gamescenes.battle;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Vibrator;

import com.doublew.snowballfight.R;
import com.doublew.snowballfight.game.core.ResourceManager;
import com.doublew.snowballfight.game.core.objects.Animation;
import com.doublew.snowballfight.game.core.GameManager;
import com.doublew.snowballfight.game.core.objects.Enemy;
import com.doublew.snowballfight.game.core.objects.Hero;
import com.doublew.snowballfight.game.core.objects.Projectile;
import com.doublew.snowballfight.game.core.gamescenes.GameScene;
import com.doublew.snowballfight.game.cover.GameView;

import java.util.ArrayList;
import java.util.Random;

public class BattleScene extends GameScene {
    GameManager gameManager;
    ArrayList<Enemy> enemies;
    Hero hero;
    ArrayList<Projectile> projectiles;
    static ArrayList<Animation>animations;
    ArrayList<Integer>items;
    int goldAcquired;
    private int level;
    boolean gameOver;
    int newStage = 0;
    float gameOverWarpY = 0;
    float gameOverWarpBorder = (GameView.gameHeight/7)*5;
    float gameOverWarpDeltaY = GameView.gameHeight/7;
    int mana;
    int specialState, specialID;
    boolean specialArmLeft = true;
    float spPosition = GameView.gameWidth;

    private SharedPreferences sp;
    public BattleScene(GameManager gameManager, int stageID, Context context) {
        super(stageID, context);
        sp = context.getSharedPreferences(context.getString(R.string.PREFERENCES_ID), Context.MODE_PRIVATE);
        hero = new Hero(sp.getInt(context.getString(R.string.SAVED_HP), 100));
        super.updater = new BattleUpdater(this);
        super.renderer = new BattleRenderer(this);
        displayingContent = 0;
        this.gameManager = gameManager;
        gameOver = false;
        enemies = new ArrayList<>();
        animations = new ArrayList<>();
        projectiles = new ArrayList<>();
        level = 0;
        switch (super.stageID) {
            case GameManager.STAGE1_SCENE_ID:
                level = sp.getInt(context.getString(R.string.STAGE1_LEVELS_PREFERENCE), 1);
                break;
            case GameManager.STAGE2_SCENE_ID:
                level = sp.getInt(context.getString(R.string.STAGE2_LEVELS_PREFERENCE), 1);
                break;
            case GameManager.STAGE3_SCENE_ID:
                level = sp.getInt(context.getString(R.string.STAGE3_LEVELS_PREFERENCE), 1);
                break;
            case GameManager.STAGE4_SCENE_ID:
                level = sp.getInt(context.getString(R.string.STAGE4_LEVELS_PREFERENCE), 1);
                break;
        }
        specialState = 0;
        specialID = 0;
        generateEnemies();
        mana = sp.getInt(context.getString(R.string.MANA_PREFERENCES), 1);
        items = new ArrayList<>(5);
        for(int i = 0; i < 5; ++i) {
            items.add(sp.getInt(context.getString(R.string.ITEM_PREFERENCES)+i, -1));
        }
    }

    void saveBackpack() {
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.PREFERENCES_ID), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        for(int i = 0; i < 5; ++i) {
            editor.putInt(context.getString(R.string.ITEM_PREFERENCES)+i, items.get(i));
        }
        editor.apply();
    }

    private void generateEnemies() {
        int bossesNumber = 0;
        int enemiesNumber = 0;
        switch (super.stageID) {
            case GameManager.STAGE1_SCENE_ID:
                switch (level) {
                    case 1:
                        bossesNumber = 0;
                        enemiesNumber = 1;
                        break;
                    case 2:
                        bossesNumber = 0;
                        enemiesNumber = 2;
                        break;
                    case 3:
                        bossesNumber = 0;
                        enemiesNumber = 3;
                        break;
                    case 4:
                        bossesNumber = 1;
                        enemiesNumber = 1;
                        break;
                }
                break;
            case GameManager.STAGE2_SCENE_ID:
                switch (level) {
                    case 1:
                        bossesNumber = 0;
                        enemiesNumber = 2;
                        break;
                    case 2:
                        bossesNumber = 0;
                        enemiesNumber = 3;
                        break;
                    case 3:
                        bossesNumber = 1;
                        enemiesNumber = 1;
                        break;
                    case 4:
                        bossesNumber = 1;
                        enemiesNumber = 2;
                        break;
                }
                break;
            case GameManager.STAGE3_SCENE_ID:
                switch (level) {
                    case 1:
                        bossesNumber = 0;
                        enemiesNumber = 3;
                        break;
                    case 2:
                        bossesNumber = 0;
                        enemiesNumber = 4;
                        break;
                    case 3:
                        bossesNumber = 1;
                        enemiesNumber = 3;
                        break;
                    case 4:
                        bossesNumber = 1;
                        enemiesNumber = 3;
                        break;
                }
                break;
            case GameManager.STAGE4_SCENE_ID:
                switch (level) {
                    case 1:
                        bossesNumber = 0;
                        enemiesNumber = 4;
                        break;
                    case 2:
                        bossesNumber = 1;
                        enemiesNumber = 3;
                        break;
                    case 3:
                        bossesNumber = 1;
                        enemiesNumber = 3;
                        break;
                    case 4:
                        bossesNumber = 2;
                        enemiesNumber = 3;
                        break;
                }
                break;
        }
        enemies.clear();
        int i;
        for(i = 0; i < bossesNumber; ++i) {
            enemies.add(new Enemy(true, stageID));
        }
        for(; i < (bossesNumber+enemiesNumber); ++i) {
            enemies.add(new Enemy(false, stageID));
        }
    }

    void levelComplete() {
        int gold = sp.getInt(context.getString(R.string.GOLD_PREFERENCES), 0);
        if(hero.isAiming()) {
            hero.switchAiming();
        }
        hero.currentSprite = 6;
        hero.effects.clear();
        projectiles.clear();
        displayingContent = 2;
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(context.getString(R.string.SAVED_HP), 100);
        editor.putInt(context.getString(R.string.ABORTED_STAGE), -1);
        switch (stageID) {
            case GameManager.STAGE1_SCENE_ID:
                goldAcquired = new Random().nextInt(7)+11;
                gold += goldAcquired;

                if(level == 4) {
                    if(!sp.getBoolean(context.getString(R.string.STAGE2_PREFERENCE), false)) {
                        newStage = 2;
                    }
                    editor.putBoolean(context.getString(R.string.STAGE2_PREFERENCE), true);
                } else {
                    editor.putInt(context.getString(R.string.STAGE1_LEVELS_PREFERENCE), level+1);
                }
                break;
            case GameManager.STAGE2_SCENE_ID:
                goldAcquired = new Random().nextInt(7)+17;
                gold += goldAcquired;
                if(level == 4) {
                    if(!sp.getBoolean(context.getString(R.string.STAGE3_PREFERENCE), false)) {
                        newStage = 3;
                    }
                    editor.putBoolean(context.getString(R.string.STAGE3_PREFERENCE), true);
                } else {
                    editor.putInt(context.getString(R.string.STAGE2_LEVELS_PREFERENCE), level+1);
                }
                break;
            case GameManager.STAGE3_SCENE_ID:
                goldAcquired = new Random().nextInt(7)+23;
                gold += goldAcquired;
                if(level == 4) {
                    if(!sp.getBoolean(context.getString(R.string.STAGE4_PREFERENCE), false)) {
                        newStage = 4;
                    }
                    editor.putBoolean(context.getString(R.string.STAGE4_PREFERENCE), true);
                } else {
                    editor.putInt(context.getString(R.string.STAGE3_LEVELS_PREFERENCE), level+1);
                }
                break;
            case GameManager.STAGE4_SCENE_ID:
                goldAcquired = new Random().nextInt(7)+29;
                gold += goldAcquired;
                if(level == 4) {
                    newStage = -1;
                } else {
                    editor.putInt(context.getString(R.string.STAGE4_LEVELS_PREFERENCE), level+1);
                }
                break;
        }
        editor.putInt(context.getString(R.string.GOLD_PREFERENCES), gold);
        editor.putInt(context.getString(R.string.MANA_PREFERENCES), mana);
        editor.apply();

        ResourceManager.playSound(8);
    }

    void gameOver() {
        if(hero.isAiming()) {
            hero.switchAiming();
        }
        animations.clear();
        projectiles.clear();
        hero.effects.clear();
        hero.currentSprite = 5;
        displayingContent = 2;
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.PREFERENCES_ID), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(context.getString(R.string.IS_THERE_SAVED_GAME), false);
        editor.putInt(context.getString(R.string.SAVED_HP), 100);
        editor.putInt(context.getString(R.string.ABORTED_STAGE), -1);
        editor.apply();

        gameOver = true;

        ResourceManager.playSound(6);
    }

    public static void createAnimation(int id, float x, float y) {
        animations.add(new Animation(id, x, y));
    }

    void vibrateHit() {
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.PREFERENCES_ID), Context.MODE_PRIVATE);
        if(sp.getBoolean(context.getString(R.string.VIBRATION_STATE), true)) {
            Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            long[] pattern = {0, 100, 50, 100};
            v.vibrate(pattern, -1);
        }
    }

    public void abortBattle() {
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.PREFERENCES_ID), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(context.getString(R.string.SAVED_HP), hero.hp);
        editor.putInt(context.getString(R.string.ABORTED_STAGE), stageID);
        editor.apply();
    }
}
