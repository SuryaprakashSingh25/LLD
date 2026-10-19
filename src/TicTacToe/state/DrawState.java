package TicTacToe.state;

import TicTacToe.Game;
import TicTacToe.exception.InvalidMoveException;
import TicTacToe.models.Player;

public class DrawState implements GameState{
    @Override
    public void handleMove(Game game, Player player, int row, int col) {
        throw new InvalidMoveException("Game is already over. It was a draw.");
    }
}
