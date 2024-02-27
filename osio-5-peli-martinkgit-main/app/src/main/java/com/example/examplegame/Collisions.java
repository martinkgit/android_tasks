package com.example.examplegame;

import java.util.HashSet;
import java.util.Set;
import java.util.Random;

public class Collisions
{
    public static void canvasBoundsCheck(Set<GameObject> gameObjects, GameCanvas canvas)
    {


        Game.INSTANCE.initializeBrickField();


    }




    public static void paddleCollisions(GameObject paddle, Set<GameObject> gameObjects)
    {
        for (GameObject G:gameObjects) {
            if(!G.isKinetic) continue;
            if(!paddle.screenRect.intersect(G.screenRect))continue;
            if(G.speed.y < 0) continue; // dont allow hits on the underside of paddle

            Game.INSTANCE.gameOver();
        }
    }
}
