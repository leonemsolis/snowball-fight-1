package com.doublew.snowballfight.game.core.gamescenes.victory;

import com.doublew.snowballfight.game.core.gamescenes.Updater;

public class VictoryUpdater implements Updater {
    private VictoryScene victoryScene;
    VictoryUpdater(VictoryScene victoryScene) {
        this.victoryScene = victoryScene;
    }

    @Override
    public void update(long passed, int displayingContent) {

    }

    @Override
    public void onPressed(float x, float y, boolean first) {
        victoryScene.finishGame();
    }

    @Override
    public void onReleased(float x, float y, boolean first) {

    }

    @Override
    public void onDragged(float x, float y, boolean first) {

    }
}
