package com.doublew.snowballfight.game.core.gamescenes.shop;


import com.doublew.snowballfight.game.core.GameManager;
import com.doublew.snowballfight.game.core.gamescenes.Updater;
import com.doublew.snowballfight.game.cover.GameView;

class ShopUpdater implements Updater {
    private ShopScene shopScene;
    ShopUpdater(ShopScene shopScene) {
        this.shopScene = shopScene;
    }

    @Override
    public void update(long passed, int displayingContent) {

    }

    @Override
    public void onPressed(float x, float y, boolean first) {
        switch (shopScene.displayingContent) {
            case 0:
                if(x >= shopScene.uiLeft && x <= shopScene.uiRight) {
                    if(y >= shopScene.buyTop && y <= shopScene.buyBot) {
                        switch (shopScene.chosenItem) {
                            case 1:
                                shopScene.createPrompt(1);
                                break;
                            case 2:
                                shopScene.createPrompt(2);
                                break;
                            case 3:
                                shopScene.createPrompt(3);
                                break;
                            case 4:
                                shopScene.createPrompt(4);
                                break;
                        }
                    }
                    if(y >= shopScene.exitTop && y <= shopScene.exitBot) {
                        shopScene.createPrompt(0);
                    }
                }
                if(y >= shopScene.itemsTop && y <= shopScene.itemsBot) {
                    if(x >= shopScene.firstLeft && x <= shopScene.firstRight) {
                        shopScene.chosenItem = 1;
                    }
                    if(x >= shopScene.secondLeft && x <= shopScene.secondRight) {
                        shopScene.chosenItem = 2;
                    }
                    if(x >= shopScene.thirdLeft && x <= shopScene.thirdRight) {
                        shopScene.chosenItem = 3;
                    }
                    if(x >= shopScene.fourthLeft && x <= shopScene.fourthRight) {
                        shopScene.chosenItem = 4;
                    }
                }
                break;
            case 1:
                shopScene.displayingContent = 0;
                break;
            case 2:
                if(x >= 56 && x <= 125 && y >= GameView.gameHeight/2+26 && y <= GameView.gameHeight/2+71) {
                    //YES
                    if(shopScene.promptID == 0) {
                        shopScene.gameManager.enterMap(GameManager.MAP_SCENE_ID, 0, 0);
                    } else {
                        shopScene.buyItem(shopScene.promptID-1);
                    }
                } else if(x >= 195 && x <= 264 && y >= GameView.gameHeight/2+26 && y <= GameView.gameHeight/2+71) {
                    //NO
                    shopScene.displayingContent = 0;
                }
                break;
        }
    }

    @Override
    public void onReleased(float x, float y, boolean first) {

    }

    @Override
    public void onDragged(float x, float y, boolean first) {

    }
}
