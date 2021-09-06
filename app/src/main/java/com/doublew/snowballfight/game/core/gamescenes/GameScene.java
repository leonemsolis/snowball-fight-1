package com.doublew.snowballfight.game.core.gamescenes;

import android.content.Context;
import android.graphics.Canvas;

public abstract class GameScene {
    public int displayingContent = 0;
    public Context context;
    protected Updater updater;
    protected Renderer renderer;
    public int stageID;
    public GameScene(int stageID, Context context) {
        this.stageID = stageID;
        this.context = context;
    }
    public void update(long passed)  {
        updater.update(passed, displayingContent);
    }
    public void render(Canvas canvas) {
        renderer.render(canvas, displayingContent);
    }
    public void onPressed(float x, float y, boolean first) {
        updater.onPressed(x, y, first);
    }
    public void onReleased(float x, float y, boolean first) {
        updater.onReleased(x, y, first);
    }
    public void onDragged(float x, float y, boolean first) {
        updater.onDragged(x, y, first);
    }

}
