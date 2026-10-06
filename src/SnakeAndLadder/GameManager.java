package SnakeAndLadder;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GameManager {
    private final Board board;
    private final DiceStrategy dice;
    private final Queue<Player> players=new LinkedList<>();
    private Player winner=null;

    public GameManager(Board board, DiceStrategy dice, List<Player> initialPlayers){
        this.board=board;
        this.dice=dice;
        this.players.addAll(initialPlayers);
    }

    public boolean isGameOver(){
        return winner!=null;
    }

    public Player getWinner(){
        return winner;
    }

    public void takeTurn(){
        if(isGameOver()){
            return;
        }

        Player currentPlayer=players.poll();
        int roll=dice.roll();
        int currentPos=currentPlayer.getPosition();
        int nextPos=currentPos+roll;
        System.out.println("[Game] " + currentPlayer.getName() + " rolled a " + roll + " (Current: " + currentPos + ")");
        if(nextPos> board.size){
            System.out.println("[Game] " + currentPlayer.getName() + " overshoot! Needs exact roll to win. Turn skipped.");
            nextPos=currentPos;
        }
        else{
            nextPos= board.getNextPosition(nextPos);
        }
        currentPlayer.setPosition(nextPos);
        System.out.println("[Game] " + currentPlayer.getName() + " moves to cell: " + nextPos);
        if(nextPos== board.size){
            winner=currentPlayer;
            System.out.println("[Victory] Player " + currentPlayer.getName() + " has won the game!");
            return;
        }
        players.add(currentPlayer);
    }
}
