package com.example.examplegame;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

import java.util.Random;
import java.util.stream.IntStream;

public class GameObject {
    private final Drawable myDrawable;
    public Vector2 position;
    public Vector2 speed;
    public int sizeX, sizeY;
    public boolean isKinetic = false;
    public boolean destroyedOnCollision = false;
    public boolean followsTouch = false;
    public int pointValue = 0;

    public Rect screenRect;

    public GameObject(Drawable myDrawable, int sizeX, int sizeY) {
        this.myDrawable = myDrawable;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.position = Vector2.zero();
        this.speed = Vector2.zero();
        update();
    }

    public void moveTo(Vector2 newPosition) {
        this.position.x = newPosition.x;
        this.position.y = newPosition.y;
        update();
    }

    private void update() {
        if (screenRect == null) screenRect = new Rect();
        int halfX = sizeX / 2;
        int halfY = sizeY / 2;

        screenRect.bottom = (int) (position.y + halfY);
        screenRect.top = (int) (position.y - halfY);
        screenRect.left = (int) (position.x - halfX);
        screenRect.right = (int) (position.x + halfX);
    }

    public void draw(Canvas canvas) {
        myDrawable.setBounds(screenRect);
        myDrawable.draw(canvas);
    }



    private static Drawable GetDrawable(int resourceId)
    {
        return ResourcesCompat.getDrawable(
                MainActivity.INSTANCE.getResources(), resourceId, null);
    }


    public static GameObject CreateBrick(int width, int pointValue)
    {

        Drawable drawable = null;
        int next = (int)(Math.random()*3) + 1;
        if(next==1){
            drawable = GetDrawable(R.drawable.rock);
        }
        else if(next == 2){
            drawable = GetDrawable(R.drawable.stone);
        }
        else if(next == 3){
            drawable = GetDrawable(R.drawable.stone1);
        }
        GameObject brick = new GameObject(drawable, width, width);
        brick.destroyedOnCollision = true;
        brick.isKinetic = true;
        brick.pointValue = pointValue;
        return brick;
    }

    public static GameObject CreatePaddle(int width)
    {
        Drawable perry = GetDrawable(R.drawable.perry);
        perry.setLevel(10);
        GameObject paddle = new GameObject(perry, 120, 200);

        paddle.followsTouch = true;

        return paddle;
    }
}
