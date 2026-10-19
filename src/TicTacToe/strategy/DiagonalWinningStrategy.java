package TicTacToe.strategy;

import TicTacToe.models.Board;
import TicTacToe.models.Player;

public class DiagonalWinningStrategy implements WinningStrategy{
    @Override
    public boolean checkWinner(Board board, Player player) {
        boolean mainDiagWin=true;
        for(int i=0;i<board.getSize();i++){
            if(board.getCell(i,i).getSymbol()!=player.getSymbol()){
                mainDiagWin=false;
                break;
            }
        }
        if(mainDiagWin){
            return true;
        }
        boolean antiDiagWin = true;
        for (int i = 0; i < board.getSize(); i++) {
            if (board.getCell(i, board.getSize() - 1 - i).getSymbol() != player.getSymbol()) {
                antiDiagWin = false;
                break;
            }
        }
        return antiDiagWin;
    }
}
