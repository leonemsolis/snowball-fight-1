package com.doublew.snowballfight.game.core.gamescenes.shop;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.doublew.snowballfight.R;
import com.doublew.snowballfight.game.core.ResourceManager;
import com.doublew.snowballfight.game.core.gamescenes.Renderer;
import com.doublew.snowballfight.game.cover.GameView;

class ShopRenderer implements Renderer {
    private float goldTextFrameLeftX, goldTextFrameTopY, goldTextFrameWidth, goldTextFrameHeight;
    private float priceTextFrameLeftX, priceTextFrameWidth;
    private static final float gameWidth = GameView.gameWidth;
    private static final float gameHeight = GameView.gameHeight;
    private ShopScene shopScene;
    private Bitmap background = null;
    private Paint textPaint;

    ShopRenderer(ShopScene shopScene, int id) {
        this.shopScene = shopScene;
        goldTextFrameLeftX = shopScene.leftX + 203;
        goldTextFrameTopY = shopScene.topY + 301;
        goldTextFrameWidth = 109;
        goldTextFrameHeight = 27;

        priceTextFrameLeftX = shopScene.leftX + 29 + 22;
        priceTextFrameWidth = 100;

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(24);
        switch (id) {
            case 5:
                background = ResourceManager.shop0;
                break;
            case 6:
                background = ResourceManager.shop1;
                break;
        }
    }

    @Override
    public void render(Canvas canvas, int displayingContent) {
        canvas.drawBitmap(background, shopScene.leftX, shopScene.topY, null);
        switch (shopScene.chosenItem) {
            case 1:
                canvas.drawBitmap(ResourceManager.item_frame_selected, shopScene.firstLeft-4, shopScene.itemsTop-4, null);
                break;
            case 2:
                canvas.drawBitmap(ResourceManager.item_frame_selected, shopScene.secondLeft-4, shopScene.itemsTop-4, null);
                break;
            case 3:
                canvas.drawBitmap(ResourceManager.item_frame_selected, shopScene.thirdLeft-4, shopScene.itemsTop-4, null);
                break;
            case 4:
                canvas.drawBitmap(ResourceManager.item_frame_selected, shopScene.fourthLeft-4, shopScene.itemsTop-4, null);
                break;
        }
        textPaint.setColor(Color.argb(255, 252, 225, 69));
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText(shopScene.gold+"", goldTextFrameLeftX+(goldTextFrameWidth/2-textPaint.measureText(shopScene.gold+"")/2), goldTextFrameTopY+goldTextFrameHeight-4, textPaint);
        if(shopScene.chosenItem > 0) {
            canvas.drawText(shopScene.priceList[shopScene.chosenItem-1]+"", priceTextFrameLeftX+(priceTextFrameWidth/2-textPaint.measureText(shopScene.priceList[shopScene.chosenItem-1]+"")/2), goldTextFrameTopY+goldTextFrameHeight-4, textPaint);
        }
        textPaint.setColor(Color.WHITE);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        switch (shopScene.displayingContent) {
            case 1:
                canvas.drawBitmap(ResourceManager.notification, 20, gameHeight/2-100, null);
                canvas.drawText(shopScene.notification, gameWidth/2-textPaint.measureText(shopScene.notification)/2, gameHeight/2+textPaint.getTextSize()/2, textPaint);
                break;
            case 2:
                canvas.drawBitmap(ResourceManager.prompt, 20, gameHeight/2-100, null);
                if(shopScene.promptID == 0) {
                    canvas.drawText(shopScene.context.getString(R.string.want_to_exit), gameWidth/2-textPaint.measureText(shopScene.context.getString(R.string.want_to_exit))/2, gameHeight/2-textPaint.getTextSize(), textPaint);
                } else {
                    canvas.drawText(shopScene.context.getString(R.string.want_to_buy), gameWidth/2-textPaint.measureText(shopScene.context.getString(R.string.want_to_buy))/2, gameHeight/2-textPaint.getTextSize(), textPaint);
                }
                textPaint.setColor(Color.BLACK);
                canvas.drawText(shopScene.context.getString(R.string.continue_answer_yes), 20+(gameWidth/2-20)/2-textPaint.measureText(shopScene.context.getString(R.string.continue_answer_yes))/2, (gameHeight/2)+45+textPaint.getTextSize()/2, textPaint);
                canvas.drawText(shopScene.context.getString(R.string.continue_answer_no), gameWidth/2+(gameWidth/2-20)/2-textPaint.measureText(shopScene.context.getString(R.string.continue_answer_no))/2, (gameHeight/2)+45+textPaint.getTextSize()/2, textPaint);
                textPaint.setColor(Color.WHITE);
                break;
        }
    }
}
