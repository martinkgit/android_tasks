package com.example.examplegame;

import android.os.Handler;

import java.util.Random;

public class Game {
    // singleton
    public static Game INSTANCE;

    public static long timer;
    public GameCanvas canvas;
    public GameLoop gameLoop;

    public boolean isRunning = true;

    public GameObject paddle;
    public float paddleLocation = 0;

    public int score = 0;
    private float initialSpeed = 80f;
    public float speed = initialSpeed;

    public Game(GameCanvas canvas) {
        this.canvas = canvas;

        gameLoop = new GameLoop(canvas);
        Game.INSTANCE = this;
    }

    public void startGame()
    {
        canvas.ClearCanvas();
        GameObjectSet.clear();
        initializeGameField();
        this.isRunning = true;
        Handler handler = new Handler();
        handler.postDelayed(() -> gameLoop.start(), 500);
    }

    public void stopGame()
    {
        this.isRunning = false;
        gameLoop.stop();

    }

    public void setTimer(long time){
        timer = time;
    }

    private void resetGame()
    {
        score = 0;
        speed = initialSpeed;

        paddle = null;
    }

    // called from gameLoop (not main thread)
    public void gameOver()
    {
        int finalScore = score;
        MainActivity.INSTANCE.runOnUiThread((Runnable) () ->
                        MainActivity.INSTANCE.gameOver(finalScore));
        stopGame();
        resetGame();
        canvas.ClearCanvas();
    }

    // called from gameLoop (not main thread)
    public void incrementScore()
    {

        score += 1;
        float speedIncrement = 25f;
        speed = (float) (initialSpeed);
         for(GameObject g : GameObjectSet.getAll()){
             g.speed = new Vector2(0, speed/2);
         }
    }


    //initializations:
    public void initializeGameField()
    {

        initializeBrickField();
        paddle = initializePaddle();
        paddleLocation = canvas.getWidth()/2f;
    }



    public GameObject initializePaddle()
    {
        int canvasHeight = canvas.getHeight();
        int canvasWidth = canvas.getWidth();
        GameObject paddle = GameObject.CreatePaddle(canvasWidth/6);
        paddle.moveTo(new Vector2(canvasWidth/2f, canvasHeight-250));
        gameLoop.addGameObject(paddle);
        return paddle;
    }


    public void initializeBrickField()
    {

        if(System.currentTimeMillis()-timer > 2000) {

            initialSpeed = initialSpeed+0.75f;
            float lastBrickX = 0;
            int next = (int)(Math.random()*4) + 1;
            setTimer(System.currentTimeMillis());
            for (int i = 0; i < next; i++) {
                int width = (int)(Math.random()*6) + 3;
                GameObject brick = GameObject.CreateBrick( canvas.getWidth() / width, 1);
                brick.screenRect.width();
                if(lastBrickX == 0)
                brick.moveTo(new Vector2((float) Math.random() * canvas.getWidth()/2, brick.sizeY - (brick.sizeY / 2f)));
                else{
                    brick.moveTo(new Vector2(lastBrickX + ((brick.sizeX*2)+(int)Math.random()*100), brick.sizeY - (brick.sizeY / 2f)));
                }
                lastBrickX = brick.position.x;
                brick.speed = new Vector2(0, speed/2);
                gameLoop.addGameObject(brick);
            }

        }
    }


}
