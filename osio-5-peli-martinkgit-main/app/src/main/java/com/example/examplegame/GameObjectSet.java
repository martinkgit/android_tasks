package com.example.examplegame;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class GameObjectSet {
    public static AtomicBoolean isUsed = new AtomicBoolean();
    private static HashSet<GameObject> gameObjects = new HashSet<>();
    private static ReentrantLock lock = new ReentrantLock();



    public static synchronized boolean remove(GameObject g){


         try {
             lock.lock();
             gameObjects.remove(g);
             return true;

         }
         finally {
             if(lock.isHeldByCurrentThread())
             lock.unlock();
         }

    }

    public static synchronized void removeAll(Set<GameObject> s){
       try{
           lock.lock();
           gameObjects.removeAll(s);
       }
       finally {
           if(lock.isHeldByCurrentThread())
           lock.unlock();
       }

    }

    public static void setIsUsed(boolean b){
        isUsed.set(b);
    }

    public static synchronized boolean add(GameObject g){

        try {
                lock.lock();
                gameObjects.add(g);
                return true;

        }
        finally {
            if(lock.isHeldByCurrentThread())
            lock.unlock();
        }

    }

    public static synchronized void clear(){

        try {
            lock.lock();
            gameObjects.clear();
        }
        finally {
            if(lock.isHeldByCurrentThread())
            lock.unlock();
        }


    }


    public static synchronized HashSet<GameObject> getAll() {

        lock.lock();
        try {

            return gameObjects;
        }
        finally {
            lock.unlock();
        }

    }

}
