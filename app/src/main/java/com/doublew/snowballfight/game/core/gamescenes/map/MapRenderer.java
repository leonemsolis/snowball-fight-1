package com.doublew.snowballfight.game.core.gamescenes.map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.doublew.snowballfight.R;
import com.doublew.snowballfight.game.core.ResourceManager;
import com.doublew.snowballfight.game.core.gamescenes.Renderer;
import com.doublew.snowballfight.game.cover.GameView;

class MapRenderer implements Renderer {
    private MapScene mapScene;

    private Paint textPaint;
    private float gameWidth = GameView.gameWidth;
    private float gameHeight = GameView.gameHeight;
    private float mapY0Position;
    private float heroIconX, heroIconY;

    MapRenderer(MapScene mapScene) {
        this.mapScene = mapScene;

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(24);

        mapY0Position = (gameHeight - ResourceManager.mapBitmap.getHeight()) / 2;
        heroIconX = gameWidth / 2 - ResourceManager.heroIconBitmap.getWidth() / 2;
        heroIconY = gameHeight / 2 - ResourceManager.heroIconBitmap.getHeight() / 2 - 20;
    }

    public void render(Canvas canvas, int displayingContent) {
        canvas.drawBitmap(ResourceManager.mapBitmap, 0, mapY0Position, null);
        canvas.drawBitmap(ResourceManager.heroIconBitmap, 61 + 17 * mapScene.mapX, mapY0Position+58+30*mapScene.mapY, null);
        if (!mapScene.isStage4Available) {
            canvas.drawBitmap(ResourceManager.school, 18, mapY0Position + 152, null);
        }
        if (!mapScene.isStage3Available) {
            canvas.drawBitmap(ResourceManager.school, 92, mapY0Position + 224, null);
        }

        if (!mapScene.isStage2Available) {
            canvas.drawBitmap(ResourceManager.school, 165, mapY0Position + 224, null);
        }

        if (!mapScene.isStage1Available) {
            canvas.drawBitmap(ResourceManager.school, 241, mapY0Position + 153, null);
        }
        switch(displayingContent) {
            case 1:
                canvas.drawBitmap(ResourceManager.notification, 20, gameHeight/2-100, null);
                if(mapScene.additionalNotificationText == null) {
                    canvas.drawText(mapScene.notificationText, gameWidth/2-textPaint.measureText(mapScene.notificationText)/2, gameHeight/2+textPaint.getTextSize()/2, textPaint);
                } else {
                    canvas.drawText(mapScene.notificationText, gameWidth/2-textPaint.measureText(mapScene.notificationText)/2, gameHeight/2, textPaint);
                    canvas.drawText(mapScene.additionalNotificationText, gameWidth/2-textPaint.measureText(mapScene.additionalNotificationText)/2, gameHeight/2+textPaint.getTextSize(), textPaint);
                }
                break;
            case 2:
                String promptText = "";
                switch (mapScene.promptID) {
                    case 0:
                        promptText = mapScene.context.getString(R.string.enter_stage)+" 1?";
                        break;
                    case 1:
                        promptText = mapScene.context.getString(R.string.enter_stage)+" 2?";
                        break;
                    case 2:
                        promptText = mapScene.context.getString(R.string.enter_stage)+" 3?";
                        break;
                    case 3:
                        promptText = mapScene.context.getString(R.string.enter_stage)+" 4?";
                        break;
                    case 4:
                        promptText =  mapScene.context.getString(R.string.enter_drug);
                        break;
                    case 5:
                        promptText =  mapScene.context.getString(R.string.enter_item);
                        break;
                }
                canvas.drawBitmap(ResourceManager.prompt, 20, gameHeight/2-100, null);
                canvas.drawText(promptText, gameWidth/2-textPaint.measureText(promptText)/2, gameHeight/2-textPaint.getTextSize(), textPaint);
                textPaint.setColor(Color.BLACK);
                canvas.drawText(mapScene.context.getString(R.string.continue_answer_yes), 20+(gameWidth/2-20)/2-textPaint.measureText(mapScene.context.getString(R.string.continue_answer_yes))/2, (gameHeight/2)+45+textPaint.getTextSize()/2, textPaint);
                canvas.drawText(mapScene.context.getString(R.string.continue_answer_no), gameWidth/2+(gameWidth/2-20)/2-textPaint.measureText(mapScene.context.getString(R.string.continue_answer_no))/2, (gameHeight/2)+45+textPaint.getTextSize()/2, textPaint);
                textPaint.setColor(Color.WHITE);
                break;
        }
    }
}
