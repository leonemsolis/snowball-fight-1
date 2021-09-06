package com.doublew.snowballfight.game.core.gamescenes.map;


import com.doublew.snowballfight.R;
import com.doublew.snowballfight.game.core.gamescenes.Updater;
import com.doublew.snowballfight.game.cover.GameView;

class MapUpdater implements Updater {
    private MapScene mapScene;
    private float touchPressX, touchPressY;

    MapUpdater(MapScene mapScene) {
        this.mapScene = mapScene;
        touchPressX = 0f;
        touchPressY = 0f;
    }

    public void update(long passed, int displayingContent) {
        if (displayingContent == 0) {
            mapScene.mapPosition = MapScene.MapPosition.NO;
            if (mapScene.mapX == 1 && mapScene.mapY == 3) {
                mapScene.mapPosition = MapScene.MapPosition.STAGE4;
            }
            if (mapScene.mapX == 9 && mapScene.mapY == 3) {
                mapScene.mapPosition = MapScene.MapPosition.STAGE1;
            }
            if (mapScene.mapX == 3 && mapScene.mapY == 5) {
                mapScene.mapPosition = MapScene.MapPosition.STAGE3;
            }
            if (mapScene.mapX == 7 && mapScene.mapY == 5) {
                mapScene.mapPosition = MapScene.MapPosition.STAGE2;
            }
            if (mapScene.mapX == 3 && mapScene.mapY == 1) {
                mapScene.mapPosition = MapScene.MapPosition.SHOP1;
            }
            if (mapScene.mapX == 7 && mapScene.mapY == 1) {
                mapScene.mapPosition = MapScene.MapPosition.SHOP2;
            }
            switch (mapScene.mapPosition) {
                case SHOP1:
                    mapScene.createPrompt(4);
                    break;
                case SHOP2:
                    mapScene.createPrompt(5);
                    break;
                case STAGE1:
                    if (mapScene.isStage1Available) {
                        mapScene.createPrompt(0);
                    }
                    break;
                case STAGE2:
                    if (mapScene.isStage2Available) {
                        mapScene.createPrompt(1);
                    }
                    break;
                case STAGE3:
                    if (mapScene.isStage3Available) {
                        mapScene.createPrompt(2);
                    }
                    break;
                case STAGE4:
                    if (mapScene.isStage4Available) {
                        mapScene.createPrompt(3);
                    }
                    break;
            }

        }
    }

    public void onPressed(float x, float y, boolean first) {
        switch (mapScene.displayingContent) {
            case 0:
                touchPressX = x;
                touchPressY = y;
                break;
            case 1:
                touchPressX = x;
                touchPressY = y;
                if (mapScene.notificationCombo) {
                    switch (mapScene.newStageAvailable) {
                        case 2:
                            mapScene.createNotification(mapScene.context.getString(R.string.western));
                            break;
                        case 3:
                            mapScene.createNotification(mapScene.context.getString(R.string.southern));
                            break;
                        case 4:
                            mapScene.createNotification(mapScene.context.getString(R.string.northern));
                            break;
                    }
                    mapScene.vibrateChallenge();
                    mapScene.additionalNotificationText = mapScene.context.getString(R.string.challenged);
                    mapScene.notificationCombo = false;
                } else {
                    mapScene.displayingContent = 0;
                }
                mapScene.mapX = 5;
                mapScene.mapY = 3;
                break;
            case 2:
                touchPressX = x;
                touchPressY = y;
                //YES
                if (x >= 56 && x <= 125 && y >= GameView.gameHeight / 2 + 26 && y <= GameView.gameHeight / 2 + 71) {
                    mapScene.mapEnter(mapScene.mapPosition);
                } else if (x >= 195 && x <= 264 && y >= GameView.gameHeight / 2 + 26 && y <= GameView.gameHeight / 2 + 71) {
                    //NO
                    switch (mapScene.promptID) {
                        case 0:
                            mapScene.mapX = 8;
                            mapScene.mapY = 3;
                            break;
                        case 1:
                            mapScene.mapX = 7;
                            mapScene.mapY = 4;
                            break;
                        case 5:
                            mapScene.mapX = 7;
                            mapScene.mapY = 2;
                            break;
                        case 2:
                            mapScene.mapX = 3;
                            mapScene.mapY = 4;
                            break;
                        case 3:
                            mapScene.mapX = 2;
                            mapScene.mapY = 3;
                            break;
                        case 4:
                            mapScene.mapX = 3;
                            mapScene.mapY = 2;
                            break;
                    }
                    mapScene.mapPosition = MapScene.MapPosition.NO;
                    mapScene.displayingContent = 0;
                    return;
                }
                break;
        }
    }

    public void onReleased(float x, float y, boolean first) {
        if (mapScene.displayingContent == 0) {
            float deltaX = Math.abs(touchPressX - x);
            float deltaY = Math.abs(touchPressY - y);
            if (deltaX > deltaY) {
                if (deltaX > 10) {
                    //left or right
                    if (x > touchPressX) {
                        swipeRight();
                    } else {
                        swipeLeft();
                    }
                }
            } else {
                if (deltaY > 10) {
                    //up or down
                    if (y > touchPressY) {
                        swipeDown();
                    } else {
                        swipeUp();
                    }
                }
            }
        }
    }

    private void swipeLeft() {
//        if (mapScene.mapX != -mapScene.mapXBorder && mapScene.mapY == 0) {
//            mapScene.mapX--;
//        }
        if (mapScene.booleanLayer[mapScene.mapY][mapScene.mapX - 1])
            mapScene.mapX--;
    }

    private void swipeRight() {
//        if (mapScene.mapX != mapScene.mapXBorder && mapScene.mapY == 0) {
//            mapScene.mapX++;
//        }
        if (mapScene.booleanLayer[mapScene.mapY][mapScene.mapX + 1])
            mapScene.mapX++;
    }

    private void swipeUp() {
//        if (mapScene.mapY != -mapScene.mapYBorder && (mapScene.mapX == 1 || mapScene.mapX == -1)) {
//            mapScene.mapY--;
//        }
        if (mapScene.booleanLayer[mapScene.mapY - 1][mapScene.mapX])
            mapScene.mapY--;
    }

    private void swipeDown() {
//        if (mapScene.mapY != mapScene.mapYBorder && (mapScene.mapX == 1 || mapScene.mapX == -1)) {
//            mapScene.mapY++;
//        }
        if (mapScene.booleanLayer[mapScene.mapY + 1][mapScene.mapX])
            mapScene.mapY++;
    }

    public void onDragged(float x, float y, boolean first) {

    }
}
