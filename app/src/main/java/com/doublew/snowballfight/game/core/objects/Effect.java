package com.doublew.snowballfight.game.core.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.doublew.snowballfight.game.core.GameThread;
import com.doublew.snowballfight.game.core.ResourceManager;

class Effect {
    private final int TICK = 10;
    private int ticker = 0;
    int type;
    boolean done;

    private int seconds_remaining;
    private Bitmap skin;
    Effect(int type) {
        this.type = type;
        done = false;
        seconds_remaining = 0;
        skin = null;
        switch (type) {
            case 0:
                seconds_remaining = 0;
                skin = null;
                break;
            case 1:
                seconds_remaining = 1;
                skin = ResourceManager.effect0;
                break;
            case 2:
                seconds_remaining = 0;
                skin = null;
                break;
            case 3:
                seconds_remaining = 1;
                skin = ResourceManager.effect1;
                break;
            case 4:
                seconds_remaining = 3;
                skin = ResourceManager.effect1;
                break;
        }
    }

    public void update(long passed) {
        if(passed > GameThread.TICK_TIME) {
            if(ticker == TICK) {
                ticker = 0;
                innerUpdate();
            } else ticker++;
        }
    }

    private void innerUpdate() {
        if(seconds_remaining > 0) {
            seconds_remaining--;
        } else {
            done = true;
        }
    }

    public void render(Canvas canvas, float charX, float charY, float charWidth, float charHeight) {
        if(skin!=null) {
            //For each effect different offsets
            if(type == 1) {
                canvas.drawBitmap(skin, charX+(charWidth-skin.getWidth())/2, charY-skin.getHeight()-4, null);
            } else {
                canvas.drawBitmap(skin, new Rect(0, 0, skin.getWidth(), skin.getHeight()), new Rect((int)charX-4, (int)charY-4, (int)(charX+4+charWidth), (int)(charY+charHeight+4)),null);
            }
        }
    }
}
