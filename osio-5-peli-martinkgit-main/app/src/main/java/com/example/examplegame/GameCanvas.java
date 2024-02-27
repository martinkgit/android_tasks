package com.example.examplegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GameCanvas extends View {

    public HashSet<GameObject> activeGameObjects = new HashSet<>();
    public HashSet<GameObject> removeNextFrame = new HashSet<>();
    Paint debugTextPaint;


    public GameCanvas(Context context, AttributeSet attributes) {
        super(context, attributes);
        invalidate();

        debugTextPaint = new Paint();
        debugTextPaint.setColor(Color.BLACK);
        debugTextPaint.setTextSize(80);
    }

    public synchronized void removeActiveObject(GameObject g){
        GameObjectSet.remove(g);
    }

    public void ClearCanvas()
    {
        removeNextFrame.addAll(GameObjectSet.getAll());
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // clear gameobjects deleted on gamelogic side before drawing
        if(removeNextFrame.size() > 0)
        {
            for (GameObject G : removeNextFrame) {
                removeActiveObject(G);
            }
            removeNextFrame.clear();
        }

        if(Game.INSTANCE != null && Game.INSTANCE.isRunning)
        {
            MainActivity.INSTANCE.updateScore(Game.INSTANCE.score);

             for(GameObject G : activeGameObjects)
            {
                G.draw(canvas);
            }
             GameObjectSet.setIsUsed(false);
        }
    }
}
