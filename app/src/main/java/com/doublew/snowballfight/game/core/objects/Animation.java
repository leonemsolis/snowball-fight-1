package com.doublew.snowballfight.game.core.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.doublew.snowballfight.game.core.GameThread;
import com.doublew.snowballfight.game.core.ResourceManager;

public class Animation {
    private final int TICK = 2;
    private int ticker = 0;
    private int frames, currentFrame;
    private float x, y;
    private boolean done;
    private Bitmap sprites[];
    public Animation(int id, float x, float y) {
        this.x = x;
        this.y = y;
        currentFrame = 0;
        done = false;
        switch (id) {
            case 1:
                frames = 2;
                sprites = ResourceManager.animation1;
                break;
            case 2:
                sprites = ResourceManager.animation2;
                frames = 1;
                break;
            case 3:
                sprites = ResourceManager.animation3;
                frames = 4;
                break;
        }
    }

    public void render(Canvas canvas) {
        if(currentFrame > sprites.length-1) {
            canvas.drawBitmap(sprites[sprites.length-1], x, y, null);
        } else {
            canvas.drawBitmap(sprites[currentFrame], x, y, null);
        }
    }

    public void update(long passed) {
        if(passed > GameThread.TICK_TIME) {
            if(ticker == TICK) {
                ticker = 0;
                innerUpdate();
            }
            else ticker++;
        }
    }

    private void innerUpdate() {
        if(currentFrame != frames-1) {
            currentFrame++;
        } else {
            done = true;
        }
    }

    public boolean isDone() {
        return done;
    }
}