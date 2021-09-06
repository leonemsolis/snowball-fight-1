package com.doublew.snowballfight.game.core.gamescenes.battle;

import com.doublew.snowballfight.game.core.GameThread;
import com.doublew.snowballfight.game.core.objects.Animation;
import com.doublew.snowballfight.game.core.GameManager;
import com.doublew.snowballfight.game.core.ResourceManager;
import com.doublew.snowballfight.game.core.objects.Enemy;
import com.doublew.snowballfight.game.core.objects.Projectile;
import com.doublew.snowballfight.game.core.gamescenes.Updater;
import com.doublew.snowballfight.game.cover.GameView;

import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

class BattleUpdater implements Updater {
    private BattleScene battleScene;
    private final float BATTLEFIELD_HEIGHT = GameView.gameHeight - ResourceManager.game_ui.getHeight() - ResourceManager.background1.getHeight() - ResourceManager.bottom_panel.getHeight();
    private final float BATTLEFIELD_CENTER_Y = ResourceManager.game_ui.getHeight() + ResourceManager.background1.getHeight() + (GameView.gameHeight - ResourceManager.game_ui.getHeight() - ResourceManager.background1.getHeight() - ResourceManager.bottom_panel.getHeight()) / 2;
    private final int SPECIAL_TICK = 4;
    private int specialTicker = 0;
    private final int SP_TICK = 1;
    private int spTicker = 0;
    private final int specialFrames = 4;
    private int specialCurrentFrame = 0;
    private final int spFrames = 10;
    private int spCurrentFrame = 0;



    public BattleUpdater(BattleScene battleScene) {
        this.battleScene = battleScene;
    }

    @Override
    public void update(long passed, int displayingContent) {
        switch (battleScene.displayingContent) {
            case 0:
                sortEnemiesByY();
                for (int i = 0; i < battleScene.enemies.size(); ++i) {
                    battleScene.enemies.get(i).update(passed);
                    if (battleScene.enemies.get(i).snowballRequest != -1) {
                        if (battleScene.projectiles.size() < 6) {
                            makeSnowBall(battleScene.enemies.get(i).snowballRequest, true, battleScene.enemies.get(i).getDamage(), battleScene.enemies.get(i).x + battleScene.enemies.get(i).getWidth() / 2, battleScene.enemies.get(i).y + battleScene.enemies.get(i).getHeight() / 2, battleScene.hero.x + battleScene.hero.getWidth() / 2 + (new Random().nextInt(3) - 1) * battleScene.hero.getWidth(), battleScene.hero.y + (battleScene.hero.getHeight() / 3));
                        }
                        battleScene.enemies.get(i).snowballRequest = -1;
                    }
                }
                battleScene.hero.update(passed);
                Iterator<Projectile> is = battleScene.projectiles.iterator();
                while(is.hasNext()) {
                    Projectile p = is.next();
                    p.update(passed);
                }

                is = battleScene.projectiles.iterator();
                while (is.hasNext()) {
                    Projectile s = is.next();
                    if (s.isReached()) {
                        Projectile reached = battleScene.projectiles.get(battleScene.projectiles.indexOf(s));
                        float snowballWidth = ResourceManager.snowball.getWidth();
                        if (reached.fromEnemy) {
                            if (reached.finishX >= battleScene.hero.x && reached.finishX + snowballWidth <= battleScene.hero.x + battleScene.hero.getWidth()) {
                                battleScene.hero.takeDamage(reached);
                                battleScene.vibrateHit();
                            }
                        } else {
                            float snowballHeight = ResourceManager.snowball.getHeight();
                            for (int i = 0; i < battleScene.enemies.size(); ++i) {
                                if (reached.finishX + snowballWidth / 2 >= battleScene.enemies.get(i).x && reached.finishX + snowballWidth / 2 <= battleScene.enemies.get(i).x + battleScene.enemies.get(i).getWidth()) {
                                    if (reached.finishY >= battleScene.enemies.get(i).y && reached.finishY + snowballHeight <= battleScene.enemies.get(i).y + battleScene.enemies.get(i).getHeight()) {
                                        battleScene.enemies.get(i).takeDamage(reached);
                                    }
                                }
                            }
                        }
                        is.remove();
                    }
                }

                if (!battleScene.hero.isAlive()) {
                    battleScene.gameOver();
                }

                Iterator<Enemy> ie = battleScene.enemies.iterator();
                while (ie.hasNext()) {
                    Enemy e = ie.next();
                    if (!e.isAlive()) {
                        ie.remove();
                    }
                }

                if (battleScene.enemies.isEmpty()) {
                    BattleScene.createAnimation(3, battleScene.hero.x + 26, battleScene.hero.y + 15);
                    battleScene.levelComplete();
                }

                for (int i = 0; i < BattleScene.animations.size(); ++i) {
                    BattleScene.animations.get(i).update(passed);
                }
                Iterator<Animation> ia = BattleScene.animations.iterator();
                while (ia.hasNext()) {
                    Animation a = ia.next();
                    if (a.isDone()) {
                        ia.remove();
                    }
                }
                break;
            case 2:
                for (int i = 0; i < BattleScene.animations.size(); ++i) {
                    BattleScene.animations.get(i).update(passed);
                }
                Iterator<Animation> iaa = BattleScene.animations.iterator();
                while (iaa.hasNext()) {
                    Animation a = iaa.next();
                    if (a.isDone()) {
                        iaa.remove();
                    }
                }
                if (battleScene.gameOver) {
                    if (passed > GameThread.TICK_TIME) {
                        if (battleScene.gameOverWarpY < battleScene.gameOverWarpBorder)
                            battleScene.gameOverWarpY += battleScene.gameOverWarpDeltaY;
                    }
                }
                break;
            case 3:
                if(passed > GameThread.TICK_TIME) {
                    switch (battleScene.specialState) {
                        case 0:
                            if(specialTicker == SPECIAL_TICK) {
                                specialTicker = 0;
                                updateSpecial();
                            } else {
                                specialTicker++;
                            }
                            break;
                        case 1:
                            if(spTicker == SP_TICK) {
                                spTicker = 0;
                                updateSp();
                            } else {
                                spTicker++;
                            }
                            break;
                        case 2:
                            battleScene.specialState = 0;
                            battleScene.displayingContent = 0;
                            for (Enemy e:battleScene.enemies) {
                                switch (battleScene.specialID) {
                                    case 1:
                                        e.takeDamage(new Projectile(1, false, battleScene.hero.damage, 0, 0, 0, 0));
                                        break;
                                    case 2:
                                        e.takeDamage(new Projectile(2, false, battleScene.hero.damage, 0, 0, 0, 0));
                                        break;
                                    case 3:
                                        e.takeDamage(new Projectile(4, false, battleScene.hero.damage, 0, 0, 0, 0));
                                        break;
                                }
                            }
                            return;
                    }
                }
                break;
        }
    }

    private void updateSpecial() {
        if(specialCurrentFrame != specialFrames) {
            specialCurrentFrame++;
            battleScene.specialArmLeft = !battleScene.specialArmLeft;
        } else {
            specialCurrentFrame = 0;
            battleScene.specialArmLeft = true;
            battleScene.specialState++;
        }
    }

    private void updateSp() {
        if(spCurrentFrame != spFrames) {
            spCurrentFrame++;
            battleScene.spPosition -= ResourceManager.sp1.getWidth()/spFrames;
        } else {
            spCurrentFrame = 0;
            battleScene.spPosition = GameView.gameWidth;
            battleScene.specialState++;
        }
    }

    @Override
    public void onPressed(float x, float y, boolean first) {
        switch (battleScene.displayingContent) {
            case 0:
                if (y <= ResourceManager.game_ui.getHeight()) {
                    battleScene.displayingContent = 1;
                } else {
                    if (battleScene.hero.canMove()) {
                        if (battleScene.hero.canShoot() && x > GameView.gameWidth / 3 && x < (GameView.gameWidth / 3) * 2) {
                            battleScene.hero.switchAiming();
                            if (!battleScene.hero.isAiming()) {
                                if (battleScene.hero.getSetPower() >= 0) {
                                    makeSnowBall(battleScene.hero.nextProjectile, false, battleScene.hero.damage, battleScene.hero.x + battleScene.hero.getWidth() / 2, battleScene.hero.y, battleScene.hero.x + battleScene.hero.getWidth() / 2, BATTLEFIELD_CENTER_Y - (BATTLEFIELD_HEIGHT / 14 * battleScene.hero.getSetPower()));
                                    battleScene.hero.nextProjectile = 0;
                                    ResourceManager.playSound(1);
                                }
                            }
                        }
                        if (x < GameView.gameWidth / 3) {
                            battleScene.hero.moveLeft();
                        }
                        if (x > (GameView.gameWidth / 3) * 2) {
                            battleScene.hero.moveRight();
                        }
                    }
                }
                break;
            case 1:
                if (y > 5 && y < 61) {
                    if (x > 6 && x < 62) {
                        useItem(battleScene.items.get(0));
                    }
                    if (x > 69 && x < 125) {
                        useItem(battleScene.items.get(1));
                    }
                    if (x > 132 && x < 188) {
                        useItem(battleScene.items.get(2));
                    }
                    if (x > 195 && x < 251) {
                        useItem(battleScene.items.get(3));
                    }
                    if (x > 258 && x < 321) {
                        useItem(battleScene.items.get(4));
                    }
                } else if(y > 66 && y < 142) {
                    useSpecial();
                } else {
                    battleScene.displayingContent = 0;
                }
                break;
            case 2:
                if (!battleScene.gameOver) {
                    if(battleScene.newStage == -1) {
                        battleScene.gameManager.enterNonMapScene(7);
                    } else {
                        battleScene.gameManager.enterMap(GameManager.MAP_SCENE_ID, battleScene.goldAcquired, battleScene.newStage);
                    }
                } else {
                    battleScene.gameManager.gameOver();
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

    private void makeSnowBall(int type, boolean fromEnemy, int damage, float sX, float sY, float fX, float fY) {
        battleScene.projectiles.add(new Projectile(type, fromEnemy, damage, sX, sY, fX, fY));
    }

    private void sortEnemiesByY() {
        Collections.sort(battleScene.enemies);
    }

    private void useItem(int itemID) {
        if (itemID != -1) {
            switch (itemID) {
                case 1:
                case 2:
                case 3:
                case 4:
                    battleScene.hero.nextProjectile = itemID;
                    break;
                case 5:
                    if(battleScene.hero.hp+36 > battleScene.hero.MAX_HP) {
                        battleScene.hero.hp = battleScene.hero.MAX_HP;
                    } else {
                        battleScene.hero.hp += 36;
                    }
                    break;
                case 6:
                    if(battleScene.mana != 3) {
                        battleScene.mana++;
                    }
                    break;
                case 7:
                    battleScene.hero.hp = battleScene.hero.MAX_HP;
                    break;
                case 8:
                    battleScene.hero.damage *= 1.5;
                    break;
            }

            Iterator<Integer> it = battleScene.items.iterator();
            while (it.hasNext()) {
                if (it.next() == itemID) {
                    it.remove();
                    battleScene.items.add(-1);
                    break;
                }
            }
            battleScene.saveBackpack();
            battleScene.displayingContent = 0;
        }
    }

    private void useSpecial() {
        if(battleScene.mana == 3) {
            battleScene.projectiles.clear();
            battleScene.specialID = 3;
            battleScene.mana = 0;
            battleScene.displayingContent = 3;
            ResourceManager.playSound(7);
        } else if(battleScene.mana == 2) {
            battleScene.projectiles.clear();
            battleScene.specialID = 2;
            battleScene.mana = 0;
            battleScene.displayingContent = 3;
            ResourceManager.playSound(7);
        } else if(battleScene.mana == 1) {
            battleScene.projectiles.clear();
            battleScene.specialID = 1;
            battleScene.mana = 0;
            battleScene.displayingContent = 3;
            ResourceManager.playSound(7);
        }
    }
}
