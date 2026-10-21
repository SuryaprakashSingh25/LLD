package ChessGame;

import ChessGame.pieces.Piece;

import java.util.Scanner;

public class ChessGame {
    private final Board board;
    private Player whitePlayer;
    private Player blackPlayer;
    private Player currentPlayer;

    public ChessGame(){
        board=new Board();
    }

    public void setPlayers(String playerWhiteName,String playerBlackName){
        this.whitePlayer=new Player(playerWhiteName,Color.WHITE);
        this.blackPlayer=new Player(playerBlackName,Color.BLACK);
        this.currentPlayer=whitePlayer;
    }

    public void start(){
        while(!isGameOver()){
            Player player=currentPlayer;
            System.out.println(player.getName() + "'s turn.");
            Move move=getPlayerMove(player);
            try{
                board.movePiece(move);
            }
            catch (InvalidMoveException e){
                System.out.println(e.getMessage());
                System.out.println("Try again!");
                continue;
            }
            switchTurn();
        }
        displayResult();
    }

    private void switchTurn(){
        currentPlayer=currentPlayer==whitePlayer?blackPlayer:whitePlayer;
    }

    private boolean isGameOver(){
        return board.isCheckmate(whitePlayer.getColor()) || board.isCheckmate(blackPlayer.getColor()) || board.isStalemate(whitePlayer.getColor()) || board.isStalemate(blackPlayer.getColor());
    }

    private Move getPlayerMove(Player player){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter source row: ");
        int sourceRow = scanner.nextInt();
        System.out.print("Enter source column: ");
        int sourceCol = scanner.nextInt();
        System.out.print("Enter destination row: ");
        int destRow = scanner.nextInt();
        System.out.print("Enter destination column: ");
        int destCol = scanner.nextInt();

        Piece piece=board.getPiece(sourceRow,sourceCol);
        if(piece==null || piece.getColor()!=player.getColor()){
            throw new IllegalArgumentException("Invalid piece selection!");
        }
        return new Move(board.getCell(sourceRow,sourceCol),board.getCell(destRow,destCol));
    }

    private void displayResult(){
        if (board.isCheckmate(Color.WHITE)) {
            System.out.println("Black wins by checkmate!");
        } else if (board.isCheckmate(Color.BLACK)) {
            System.out.println("White wins by checkmate!");
        } else if (board.isStalemate(Color.WHITE) || board.isStalemate(Color.BLACK)) {
            System.out.println("The game ends in a stalemate!");
        }
    }
}
