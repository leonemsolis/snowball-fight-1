package com.doublew.snowballfight.game.cover;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import com.doublew.snowballfight.game.core.GameThread;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener{
    public GameThread gameThread;
    public static final float gameWidth = 320;
    public static final float gameHeight = 480;

    public static float scaleFactor;
    public static float cutWidth;
    public static float cutHeight;



    public GameView(Context context) {
        super(context);

        if(GameActivity.canvasWidth/gameWidth > GameActivity.canvasHeight/gameHeight) {
            scaleFactor = GameActivity.canvasHeight/gameHeight;
        } else {
            scaleFactor = GameActivity.canvasWidth/gameWidth;
        }

        cutWidth = (GameActivity.canvasWidth - (gameWidth*scaleFactor)) / 2;
        cutHeight = (GameActivity.canvasHeight - (gameHeight*scaleFactor)) / 2;

        getHolder().addCallback(this);
        this.setOnTouchListener(this);
        gameThread = new GameThread(getHolder(), context);
        startThread();
        setFocusable(true);
    }

    public boolean onTouch(View v, MotionEvent event) {
        int actionMask = event.getActionMasked();

        switch (actionMask) {
            case MotionEvent.ACTION_DOWN:
                gameThread.getGameManager().onPressed((event.getX()-cutWidth)/scaleFactor, (event.getY())/scaleFactor, true);
                break;
            case MotionEvent.ACTION_MOVE:
                switch (event.getActionIndex()) {
                    case 0:
                        gameThread.getGameManager().onDragged((event.getX()-cutWidth)/scaleFactor, (event.getY())/scaleFactor, true);
                        break;
                    case 1:
                        gameThread.getGameManager().onDragged((event.getX()-cutWidth)/scaleFactor, (event.getY())/scaleFactor, false);
                        break;
                }
                break;
            case MotionEvent.ACTION_UP:
                gameThread.getGameManager().onReleased((event.getX()-cutWidth)/scaleFactor, (event.getY())/scaleFactor, true);
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                gameThread.getGameManager().onPressed((event.getX(1)-cutWidth)/scaleFactor, (event.getY(1))/scaleFactor, false);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                gameThread.getGameManager().onReleased((event.getX(1)-cutWidth)/scaleFactor, (event.getY(1))/scaleFactor, false);
                break;
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread.setPaused(false);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gameThread.setPaused(true);
    }

    public void startThread() {
        if(!gameThread.isRunning()) {
            gameThread.setRunning(true);
            gameThread.setPaused(false);
            if(!gameThread.isAlive())
                gameThread.start();
        }
    }

    public void stopThread() {
        if(gameThread.isRunning()) {
            boolean retry = true;
            while(retry) {
                try {
                    gameThread.setRunning(false);
                    gameThread.join();
                } catch(InterruptedException e) {
                    return;
                }
                retry = false;
            }
        }
    }
}
