package com.doublew.snowballfight.game.core.gamescenes;

public interface Updater {
    void update(long passed, int displayingContent);
    void onPressed(float x, float y, boolean first);
    void onReleased(float x, float y, boolean first);
    void onDragged(float x, float y, boolean first);
}
