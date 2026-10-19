package TicTacToe.state;

import TicTacToe.Game;
import TicTacToe.enums.GameStatus;
import TicTacToe.enums.Symbol;
import TicTacToe.exception.InvalidMoveException;
import TicTacToe.models.Player;

public class InProgressState implements GameState{

    @Override
    public void handleMove(Game game, Player player, int row, int col) {
        if(game.getCurrentPlayer()!=player){
            throw new InvalidMoveException("Not your turn");
        }

        game.getBoard().placeSymbol(row,col,player.getSymbol());

        if(game.checkWinner(player)){
            game.setWinner(player);
            game.setStatus(player.getSymbol() == Symbol.X? GameStatus.WINNER_X:GameStatus.WINNER_O);
            game.setState(new WinnerState());
        }
        else if(game.getBoard().isFull()){
            game.setStatus(GameStatus.DRAW);
            game.setState(new DrawState());
        }
        else{
            game.switchPlayer();
        }
    }
}
