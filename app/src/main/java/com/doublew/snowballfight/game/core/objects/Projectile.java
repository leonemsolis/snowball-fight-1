package com.doublew.snowballfight.game.core.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.doublew.snowballfight.game.core.GameThread;
import com.doublew.snowballfight.game.core.ResourceManager;

public class Projectile {
    public float finishX, finishY;
    private float currentX, currentY;
    private float deltaX, deltaY;
    private boolean reached;
    public boolean fromEnemy;
    int damage;
    int type;
    private boolean movingLeft, movingUp;
    private Bitmap skin = null;
    private float shadowOffsetX;

    public Projectile(int type, boolean fromEnemy, int damage, float startX, float startY, float finishX, float finishY) {
        this.type = type;
        skin = ResourceManager.items[type];
        shadowOffsetX = (ResourceManager.snowball_shadow.getWidth()-skin.getWidth())/2;
        this.currentX = startX;
        this.currentY = startY;
        this.finishX = finishX;
        this.finishY = finishY;
        reached = false;
        this.fromEnemy = fromEnemy;
        this.damage = damage;
        float snowballSpeed;
        if (fromEnemy) {
            snowballSpeed = 20;
        } else {
            snowballSpeed = 30;
        }

        float distance = (float)Math.sqrt(Math.pow(Math.abs(currentX - finishX), 2)+Math.pow(Math.abs(currentY - finishY), 2));
        float time = distance/snowballSpeed;

        deltaX = Math.abs(currentX - finishX) / time;
        deltaY = Math.abs(currentY - finishY) / time;

        movingLeft = currentX > finishX;

        movingUp = currentY > finishY;
    }

    public void render(Canvas canvas) {
        canvas.drawBitmap(ResourceManager.snowball_shadow, currentX-shadowOffsetX, currentY+20, null);
        canvas.drawBitmap(skin, currentX, currentY, null);
    }

    public boolean isReached() {
        return reached;
    }

    public void update(long passed) {
        if(passed > GameThread.TICK_TIME) {
            if(!reached) {
                if(movingUp) {
                    currentY-=deltaY;
                } else{
                    currentY+=deltaY;
                }

                if(movingLeft) {
                    currentX-=deltaX;
                } else {
                    currentX+=deltaX;
                }
            }

            if(Math.abs(currentX-finishX)-deltaX <= 0 && Math.abs(currentY-finishY)-deltaY <= 0) {
                currentX = finishX;
                currentY = finishY;
                reached = true;
            }
        }
    }
}
