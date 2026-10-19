package TicTacToe.state;

import TicTacToe.Game;
import TicTacToe.models.Player;

public interface GameState {
    void handleMove(Game game, Player player, int row, int col);
}
