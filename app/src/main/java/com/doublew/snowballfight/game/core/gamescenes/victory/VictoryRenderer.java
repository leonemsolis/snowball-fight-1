package com.doublew.snowballfight.game.core.gamescenes.victory;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.doublew.snowballfight.game.core.ResourceManager;
import com.doublew.snowballfight.game.core.gamescenes.Renderer;
import com.doublew.snowballfight.game.cover.GameView;

class VictoryRenderer implements Renderer {
    private String sign;
    private Paint textPaint;
    VictoryRenderer(String sign) {
        this.sign = sign;
        textPaint = new Paint();
        textPaint.setTypeface(Typeface.create("serif",Typeface.ITALIC));
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30);
    }

    @Override
    public void render(Canvas canvas, int displayingContent) {
        canvas.drawBitmap(ResourceManager.all_clear, 0, GameView.gameHeight/2-ResourceManager.all_clear.getHeight()/2, null);
        textPaint.setColor(Color.YELLOW);
        canvas.drawText(sign, GameView.gameWidth/2-textPaint.measureText(sign)/2+1, 291, textPaint);
        textPaint.setColor(Color.BLACK);
        canvas.drawText(sign, GameView.gameWidth/2-textPaint.measureText(sign)/2, 290, textPaint);
    }
}
