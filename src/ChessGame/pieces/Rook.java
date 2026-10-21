package ChessGame.pieces;

import ChessGame.Board;
import ChessGame.Cell;
import ChessGame.Color;

public class Rook extends Piece{

    public Rook(Color color){
        super(color);
    }

    @Override
    public boolean canMove(Board board, Cell from, Cell to) {
        return (from.getRow() == to.getRow() || from.getCol() == to.getCol());
    }
}
