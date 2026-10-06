package SnakeAndLadder;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Board board=new Board(100);

        board.addSnake(14,4);
        board.addSnake(37,17);
        board.addSnake(62,19);
        board.addSnake(96,56);

        board.addLadder(3,38);
        board.addLadder(9,31);
        board.addLadder(40,59);
        board.addLadder(75,95);

        DiceStrategy standardDice=new StandardDice(6);
        List<Player> players= Arrays.asList(
                new Player("ALice"),
                new Player("Bob"),
                new Player("Charlie")
        );
        GameManager game=new GameManager(board,standardDice,players);
        System.out.println("--- Snake & Ladder Game Starting ---");
        int turnCount=0;
        while(!game.isGameOver() && turnCount<100){
            game.takeTurn();
            turnCount++;
        }
        System.out.println("--- Simulation Ended. Total Turns: " + turnCount + " ---");
    }
}
