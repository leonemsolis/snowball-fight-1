package com.doublew.snowballfight.game.core.objects;

import android.graphics.Canvas;

import com.doublew.snowballfight.game.core.GameThread;
import com.doublew.snowballfight.game.core.ResourceManager;
import com.doublew.snowballfight.game.core.gamescenes.battle.BattleScene;
import com.doublew.snowballfight.game.cover.GameView;

import java.util.ArrayList;
import java.util.Iterator;

public class Hero {
    private final int TICK = 1;
    private int ticker = 0;
    public ArrayList<Effect> effects;
    private final float DELTA_STEP = GameView.gameWidth / 10;
    private final float LEFT_BORDER = DELTA_STEP - ResourceManager.heroSprites[0].getWidth() / 2 - 4;  //DELTA_STEP/2-ResourceManager.heroSprites[0].getWidth()/2;
    private final float RIGHT_BORDER = GameView.gameWidth - DELTA_STEP - ResourceManager.heroSprites[0].getWidth() / 2 + 4;  //GameView.gameWidth-DELTA_STEP/2-ResourceManager.heroSprites[0].getWidth()/2;
    public int damage = 10;
    public float x, y;
    private boolean aiming;
    public int currentSprite;
    private float power;
    private int finalPower;
    public int hp;
    public int MAX_HP = 100;
    private final int POWER_LIMIT = 7;
    public int nextProjectile;

    public Hero(int hp) {
        nextProjectile = 0;
        effects = new ArrayList<>();
        this.hp = hp;
        x = DELTA_STEP * 5 - ResourceManager.heroSprites[0].getWidth() / 2;
        y = GameView.gameHeight - 12 - ResourceManager.heroSprites[0].getHeight() - ResourceManager.bottom_panel.getHeight();
        currentSprite = 0;
        aiming = false;
        power = -1;
        finalPower = 0;
    }

    private void moveSprite() {
        if (!aiming) {
            if (currentSprite < 1) currentSprite++;
            else currentSprite = 0;
        }
    }

    public void moveLeft() {
        if (x - DELTA_STEP > LEFT_BORDER) x -= DELTA_STEP;
        moveSprite();
    }

    public void moveRight() {
        if (x + DELTA_STEP < RIGHT_BORDER) x += DELTA_STEP;
        moveSprite();
    }

    public void render(Canvas canvas) {
        canvas.drawBitmap(ResourceManager.heroSprites[currentSprite], x, y, null);
        for (Effect e : effects) {
            e.render(canvas, x, y, ResourceManager.heroSprites[currentSprite].getWidth(), ResourceManager.heroSprites[currentSprite].getHeight());
        }
    }

    private void innerUpdate() {
        if (canMove()) {
            if (aiming) {
                if (power < POWER_LIMIT) power += .5;
                if (currentSprite < 3) currentSprite++;
                else currentSprite = 2;
            } else {
                if (currentSprite != 0 && currentSprite != 1) {
                    currentSprite = 0;
                }
            }
        }
    }

    public void update(long passed) {
        if (passed > GameThread.TICK_TIME) {
            if (ticker == TICK) {
                ticker = 0;
                innerUpdate();
            } else ticker++;
        }

        for (Effect e : effects) {
            e.update(passed);
        }
        Iterator<Effect> ie = effects.iterator();
        while (ie.hasNext()) {
            if (ie.next().done) {
                ie.remove();
            }
        }
    }

    public void switchAiming() {
        aiming = !aiming;
        if (!aiming) {
            currentSprite = 4;
            finalPower = (int) power;
            power = 0;
        }
    }

    public int getSetPower() {
        return finalPower;
    }

    public int getPower() {
        return (int) power;
    }

    public void takeDamage(Projectile p) {
        this.hp -= p.damage;
        effects.add(new Effect(p.type));
        if (p.type == 2 || p.type == 4) {
            this.hp -= p.damage;
        }
        BattleScene.createAnimation(2, x - 4, y - 14);
        ResourceManager.playSound(5);
    }

    public boolean isAiming() {
        return aiming;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public float getWidth() {
        return ResourceManager.heroSprites[currentSprite].getWidth();
    }

    public float getHeight() {
        return ResourceManager.heroSprites[currentSprite].getHeight();
    }

    public boolean canMove() {
        for (Effect e : effects) {
            if (!e.done) {
                if (e.type == 3 || e.type == 4) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canShoot() {
        for (Effect e : effects) {
            if (!e.done) {
                if (e.type == 1 || e.type == 3 || e.type == 4) {
                    return false;
                }
            }
        }
        return true;
    }
}
