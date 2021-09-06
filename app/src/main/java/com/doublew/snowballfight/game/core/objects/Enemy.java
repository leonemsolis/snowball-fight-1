package com.doublew.snowballfight.game.core.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.doublew.snowballfight.game.core.GameThread;
import com.doublew.snowballfight.game.core.ResourceManager;
import com.doublew.snowballfight.game.core.gamescenes.battle.BattleScene;
import com.doublew.snowballfight.game.cover.GameView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Enemy implements Comparable<Enemy> {
    private int TICK;
    private int ticker = 0;
    private ArrayList<Effect>effects;
    private boolean isBoss;
    private int hp, damage, maxHp;
    private Bitmap[] sprites;
    private int currentSprite;
    public float x, y;
    private boolean madeDeathRattle;
    private final float BATTLEFIELD_HEIGHT = GameView.gameHeight-ResourceManager.game_ui.getHeight()-ResourceManager.background1.getHeight()-ResourceManager.bottom_panel.getHeight();
    private final float DELTA_STEP_Y = BATTLEFIELD_HEIGHT/14;
    private final float DELTA_STEP_X = GameView.gameWidth/10;
    private float LEFT_BORDER;
    private float RIGHT_BORDER;
    private float TOP_BORDER;
    private float BOTTOM_BORDER;
    public int snowballRequest;
    private int hpMonitorTimer;
    private int deathRattleTime = 3;
    private Paint paint;
    public Enemy(boolean isBoss, int stageNumber) {
        super();
        TICK = new Random().nextInt(4)+2;
        effects = new ArrayList<>();
        snowballRequest = -1;
        currentSprite = 0;
        this.isBoss = isBoss;
        sprites = new Bitmap[4];
        hpMonitorTimer = 0;
        madeDeathRattle = false;
        paint = new Paint();
        paint.setColor(Color.RED);

        if(isBoss) {
            switch (stageNumber) {
                case 1:
                    hp = 50;
                    damage = 20;
                    break;
                case 2:
                    hp = 56;
                    damage = 27;
                    break;
                case 3:
                    hp = 62;
                    damage = 36;
                    break;
                case 4:
                    hp = 64;
                    damage = 39;
                    break;
            }

            int skin = new Random().nextInt(4);
            switch (skin) {
                case 0:
                    sprites = ResourceManager.boss1Sprites;
                    break;
                case 1:
                    sprites = ResourceManager.boss2Sprites;
                    break;
                case 2:
                    sprites = ResourceManager.boss3Sprites;
                    break;
                case 3:
                    sprites = ResourceManager.boss4Sprites;
                    break;
            }
        } else {
            switch (stageNumber) {
                case 1:
                    hp = 30;
                    damage = 9;
                    break;
                case 2:
                    hp = 34;
                    damage = 12;
                    break;
                case 3:
                    hp = 41;
                    damage = 17;
                    break;
                case 4:
                    hp = 46;
                    damage = 19;
                    break;
            }
            int skin = new Random().nextInt(2);
            switch (skin) {
                case 0:
                    sprites = ResourceManager.enemy1Sprites;
                    break;
                case 1:
                    sprites = ResourceManager.enemy2Sprites;
                    break;
            }
        }
        maxHp = hp;
        final float BATTLEFIELD_CENTER_Y = ResourceManager.game_ui.getHeight()+ResourceManager.background1.getHeight()+(GameView.gameHeight-ResourceManager.game_ui.getHeight()-ResourceManager.background1.getHeight()-ResourceManager.bottom_panel.getHeight())/2;
        LEFT_BORDER = DELTA_STEP_X - sprites[currentSprite].getWidth()/2-4;
        RIGHT_BORDER = GameView.gameWidth-DELTA_STEP_X-sprites[currentSprite].getWidth()/2+4;
        BOTTOM_BORDER = BATTLEFIELD_CENTER_Y-sprites[currentSprite].getHeight()/2;
        TOP_BORDER = (ResourceManager.background1.getHeight()+ResourceManager.game_ui.getHeight())-sprites[currentSprite].getHeight()/2;
        x = DELTA_STEP_X*(new Random().nextInt(7)+1)-sprites[currentSprite].getWidth()/2;
        y = BOTTOM_BORDER-DELTA_STEP_Y*(new Random().nextInt(7));
    }

    private void innerUpdate() {
        if(isMadeDeathRattle()) {
            if(deathRattleTime != 0) {
                deathRattleTime--;
            } else {
                madeDeathRattle = true;
            }
        } else {
            if(canMove()) {
                if(currentSprite == 0) currentSprite = 1;
                else currentSprite = 0;
                int action = new Random().nextInt(10);
                switch (action) {
                    case 0:
                    case 1:
                        if(canShoot()) {
                            currentSprite = 3;
                            int type = (new Random().nextInt(20));
                            switch (type) {
                                case 0:
                                    snowballRequest = 1;
                                    break;
                                case 1:
                                    snowballRequest = 2;
                                    break;
                                case 2:
                                    snowballRequest = 3;
                                    break;
                                case 3:
                                    snowballRequest = 4;
                                    break;
                                default:
                                    snowballRequest = 0;
                                    break;
                            }
                        }
                        break;
                    case 4:
                    case 5:
                        int direction = new Random().nextInt(4);
                        switch (direction) {
                            case 0:
                                if (y + DELTA_STEP_Y < BOTTOM_BORDER)
                                    y += DELTA_STEP_Y;
                                break;
                            case 1:
                                if (y - DELTA_STEP_Y > TOP_BORDER)
                                    y -= DELTA_STEP_Y;
                                break;
                            case 2:
                                if (x + DELTA_STEP_X < RIGHT_BORDER)
                                    x += DELTA_STEP_X;
                                break;
                            case 3:
                                if (x - DELTA_STEP_X > LEFT_BORDER)
                                    x -= DELTA_STEP_X;
                                break;
                        }
                        break;
                }
            }

            if(hpMonitorTimer != 0) hpMonitorTimer--;
        }
    }

    public void update(long passed) {
        if(passed > GameThread.TICK_TIME) {
            if(ticker == TICK) {
                ticker = 0;
                innerUpdate();
            } else ticker++;
        }

        for(Effect e: effects) {
            e.update(passed);
        }

        Iterator<Effect> ie = effects.iterator();
        while(ie.hasNext()) {
            if(ie.next().done) {
                ie.remove();
            }
        }
    }

    public void render(Canvas canvas) {
        if(!isMadeDeathRattle()) {
            canvas.drawBitmap(sprites[currentSprite], x, y, null);
            for(Effect e: effects) {
                e.render(canvas, x, y, sprites[currentSprite].getWidth(), sprites[currentSprite].getHeight());
            }
            if(hpMonitorTimer != 0) {
                if(isBoss) {
                    paint.setColor(Color.argb(255, 143, 143, 143));
                    canvas.drawRect(x-4, y+sprites[1].getHeight(), x+52, y+sprites[1].getHeight()+10, paint);
                    paint.setColor(Color.RED);
                    canvas.drawRect(x-2, y+sprites[1].getHeight()+2, x+((float)50/(float)maxHp)*(float)hp, y+sprites[1].getHeight()+8, paint);
                } else {
                    paint.setColor(Color.argb(255, 143, 143, 143));
                    canvas.drawRect(x, y+sprites[1].getHeight(), x+40, y+sprites[1].getHeight()+10, paint);
                    paint.setColor(Color.RED);
                    canvas.drawRect(x+2, y+sprites[1].getHeight()+2, x+((float)38/(float)maxHp)*(float)hp, y+sprites[1].getHeight()+8, paint);
                }
            }
        } else {
            canvas.drawBitmap(sprites[2], x, y, null);
        }
    }

    @Override
    public int compareTo(Enemy e) {
        return (int)((y+sprites[0].getHeight())-(e.y+sprites[0].getHeight()));
    }

    public void takeDamage(Projectile p) {
        hp -= p.damage;
        effects.add(new Effect(p.type));
        if(p.type == 2 || p.type == 4) {
            hp -= p.damage;
        }
        BattleScene.createAnimation(1, x - 6, y - 14);
        ResourceManager.playSound(4);
        hpMonitorTimer = 3;
    }

    public float getWidth() {
        return sprites[currentSprite].getWidth();
    }

    public float getHeight() {
        return sprites[currentSprite].getHeight();
    }

    public boolean isAlive() {
        return !madeDeathRattle;
    }

    private boolean isMadeDeathRattle() {
        return !(hp > 0);
    }

    private boolean canMove() {
        for(Effect e: effects) {
            if(!e.done) {
                if(e.type == 3 || e.type == 4) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean canShoot() {
        for(Effect e: effects) {
            if(!e.done) {
                if(e.type == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getDamage() {
        return damage;
    }
}
