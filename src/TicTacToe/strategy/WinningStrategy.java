package TicTacToe.strategy;

import TicTacToe.models.Board;
import TicTacToe.models.Player;

public interface WinningStrategy {
    boolean checkWinner(Board board, Player player);
}
