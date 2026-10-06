package SnakeAndLadder;

import java.util.Random;

public class StandardDice implements DiceStrategy{
    private final int sides;
    private final Random random=new Random();

    public StandardDice(int sides){
        this.sides=sides;
    }

    @Override
    public int roll(){
        return random.nextInt(sides)+1;
    }
}
