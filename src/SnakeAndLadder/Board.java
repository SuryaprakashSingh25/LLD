package SnakeAndLadder;

import java.util.HashMap;
import java.util.Map;

public class Board {
    public final int size;
    private final Map<Integer,Integer> specialCells=new HashMap<>();

    public Board(int size){
        this.size=size;
    }

    public void addSnake(int head,int tail){
        if(head<tail){
            throw new IllegalArgumentException("Snake head must be above tail");
        }
        specialCells.put(head,tail);
    }

    public void addLadder(int start,int end){
        if(start>=end){
            throw new IllegalArgumentException("Ladder start must be below end");
        }
        specialCells.put(start,end);
    }

    public int getNextPosition(int position){
        if(specialCells.containsKey(position)){
            int destination=specialCells.get(position);
            if (destination < position) {
                System.out.println("[Board] Swallowed by Snake at " + position + " -> slid down to " + destination);
            } else {
                System.out.println("[Board] Climbed Ladder at " + position + " -> went up to " + destination);
            }
            return destination;
        }
        return position;
    }
}
