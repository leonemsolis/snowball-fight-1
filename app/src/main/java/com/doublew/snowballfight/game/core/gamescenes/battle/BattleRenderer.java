package com.doublew.snowballfight.game.core.gamescenes.battle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.doublew.snowballfight.game.core.ResourceManager;
import com.doublew.snowballfight.game.core.objects.Animation;
import com.doublew.snowballfight.game.core.objects.Enemy;
import com.doublew.snowballfight.game.core.objects.Projectile;
import com.doublew.snowballfight.game.core.gamescenes.Renderer;
import com.doublew.snowballfight.game.cover.GameView;

import java.util.ArrayList;
import java.util.Iterator;

public class BattleRenderer implements Renderer {
    private BattleScene battleScene;
    private Paint paint;
    private float gameWidth = GameView.gameWidth;
    private float gameHeight = GameView.gameHeight;

    private final float BATTLEFIELD_HEIGHT = gameHeight-ResourceManager.game_ui.getHeight()-ResourceManager.background1.getHeight()-ResourceManager.bottom_panel.getHeight();
    private final float BATTLEFIELD_CENTER_Y = ResourceManager.game_ui.getHeight()+ResourceManager.background1.getHeight()+(GameView.gameHeight-ResourceManager.game_ui.getHeight()-ResourceManager.background1.getHeight()-ResourceManager.bottom_panel.getHeight())/2;

    public BattleRenderer(BattleScene battleScene) {
        this.battleScene = battleScene;
        paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(Color.WHITE);
    }

    @Override
    public void render(Canvas canvas, int displayingContent) {
        canvas.drawRect(0, 0, gameWidth, gameHeight, paint);

        canvas.drawBitmap(ResourceManager.game_ui, 0, 0, null);

        ArrayList<Integer> items = battleScene.items;
        for(int i = 0; i < 5; ++i) {
            if(items.get(i) != -1) {
                canvas.drawBitmap(ResourceManager.items[items.get(i)], 92+30*i, 5, null);
            }
        }

        paint.setColor(Color.argb(255, 143, 143, 143));
        canvas.drawRect(12, 10, 35, 10+(26-((26/(float)battleScene.hero.MAX_HP)*(float)battleScene.hero.hp)), paint);
        paint.setColor(Color.argb(255, 17, 73, 159));
        canvas.drawRect(75, 38, 77+battleScene.mana*30, 40, paint);
        for(int i = 0; i < battleScene.mana; ++i) {
            canvas.drawRect(97+i*30, 35, 105+i*30, 43, paint);
        }
        paint.setColor(Color.BLACK);
        canvas.drawRect(0, ResourceManager.game_ui.getHeight(), gameWidth, ResourceManager.game_ui.getHeight()+5, paint);
        paint.setColor(Color.WHITE);
        canvas.drawBitmap(ResourceManager.currentBackground, 0, ResourceManager.game_ui.getHeight()+2, null);

        ArrayList<Enemy> ae = (ArrayList<Enemy>) battleScene.enemies.clone();
        Iterator<Enemy>ie = ae.iterator();
        while(ie.hasNext()) {
            ie.next().render(canvas);
        }
        ArrayList<Projectile> p = (ArrayList<Projectile>)battleScene.projectiles.clone();
        Iterator<Projectile>ip = p.iterator();
        while(ip.hasNext()) {
            ip.next().render(canvas);
        }

        battleScene.hero.render(canvas);
        if(battleScene.hero.isAiming()) {
            if(battleScene.hero.getPower() > 0) {
                paint.setColor(Color.RED);
                canvas.drawRect(7+battleScene.hero.x-20, battleScene.hero.y+ResourceManager.power_bar.getHeight()-2-battleScene.hero.getPower()*8, 5+battleScene.hero.x-20+ResourceManager.power_bar.getWidth()-3, battleScene.hero.y+ResourceManager.power_bar.getHeight(), paint);
                paint.setColor(Color.WHITE);
            }
            canvas.drawBitmap(ResourceManager.power_bar, battleScene.hero.x-ResourceManager.power_bar.getWidth(), battleScene.hero.y, null);
        }


        ArrayList<Animation> aa = (ArrayList<Animation>) battleScene.animations.clone();
        Iterator<Animation>ia = aa.iterator();
        while(ia.hasNext()) {
            ia.next().render(canvas);
        }
        canvas.drawBitmap(ResourceManager.bottom_panel, 0, gameHeight-ResourceManager.bottom_panel.getHeight(), null);
        if(battleScene.displayingContent == 0 && battleScene.hero.canMove()) {
            if(!battleScene.hero.canShoot()) {
                canvas.drawBitmap(ResourceManager.cant_shoot_panel, 0, gameHeight-ResourceManager.cant_shoot_panel.getHeight(), null);
            }
            else {
                if(battleScene.hero.isAiming()) {
                    canvas.drawBitmap(ResourceManager.fire_panel, 0, gameHeight-ResourceManager.fire_panel.getHeight(), null);
                } else {
                    canvas.drawBitmap(ResourceManager.aim_panel, 0, gameHeight-ResourceManager.aim_panel.getHeight(), null);
                }
            }
        }
        if(battleScene.displayingContent == 1) {
            canvas.drawBitmap(ResourceManager.backpack, 0, 0, null);
            for(int i = 0; i < 5; ++i) {
                if(battleScene.items.get(i) != -1) {
                    canvas.drawBitmap(ResourceManager.items_b[battleScene.items.get(i)], 6 + 63*i, 5, null);
                }
            }
        }

        if(battleScene.displayingContent == 2) {
            if(battleScene.gameOver) {
                paint.setColor(Color.BLACK);
                canvas.drawRect(0, 0, gameWidth, battleScene.gameOverWarpY, paint);
                paint.setColor(Color.WHITE);
                canvas.drawBitmap(ResourceManager.top_panel, 0, battleScene.gameOverWarpY, null);
            }
        }
        if(battleScene.displayingContent == 3) {
            switch (battleScene.specialState) {
                case 0:
                    canvas.drawColor(Color.argb(255, 0, 68, 203));
                    canvas.drawBitmap(ResourceManager.special[0], 0, 0, null);
                    if(battleScene.specialArmLeft) {
                        canvas.drawBitmap(ResourceManager.special[1], gameWidth/2-10, gameHeight-140, null);
                    } else {
                        canvas.drawBitmap(ResourceManager.special[2], gameWidth/2-10, gameHeight-140, null);
                    }
                    break;
                case 1:
                    switch (battleScene.specialID) {
                        case 1:
                            canvas.drawBitmap(ResourceManager.sp1, battleScene.spPosition, ResourceManager.game_ui.getHeight(), null);
                            break;
                        case 2:
                            canvas.drawBitmap(ResourceManager.sp2, battleScene.spPosition, ResourceManager.game_ui.getHeight(), null);
                            break;
                        case 3:
                            canvas.drawBitmap(ResourceManager.sp3, battleScene.spPosition, ResourceManager.game_ui.getHeight(), null);
                            break;
                    }
                    break;
            }
        }
    }
}
