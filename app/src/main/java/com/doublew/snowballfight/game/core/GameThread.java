package com.doublew.snowballfight.game.core;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
    public static final long TICK_TIME = 100;
    private SurfaceHolder surfaceHolder;
    private boolean running, paused;
    private GameManager gameManager;
    private Context context;

    public GameThread(SurfaceHolder surfaceHolder, Context context) {
        this.surfaceHolder = surfaceHolder;
        this.context = context;
        gameManager = new GameManager(context);
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
        if (paused) {
            gameManager.setBattlePause();
        }
    }

    public boolean isRunning() {
        return running;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    @Override
    public void run() {
        final double FPS = 60.0;
        Canvas canvas;
        long lastTime = System.nanoTime();
        double partTime = 1_000_000_000.0 / FPS;
        double delta = 0;
        long timer = System.currentTimeMillis();
//        int updates = 0;
//        int frames = 0;
        while (running) {
            if (gameManager.gameOver) {
//                Intent intent = new Intent(context, Title.class);
//                context.startActivity(intent);
                ((Activity) context).finish();
            }
            long currentTime = System.nanoTime();
            delta += currentTime - lastTime;
            lastTime = currentTime;
            if (delta >= partTime) {
                if (!paused)
                    gameManager.update(System.currentTimeMillis() - timer);
                if (System.currentTimeMillis() - timer > TICK_TIME) {
                    timer += TICK_TIME;
                }
                //                updates++;
                delta = 0;
            }
            canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas(null);
                if (canvas != null) {
                    if (!paused) {
                        gameManager.render(canvas);
                    }
                }
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
