package models;

import enums.CellStatus;
import enums.Color;
import enums.GameStatus;
import enums.PieceType;
import enums.SpecialMoveType;
import pieces.Coordinate;
import pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Player player1;
    private final Player player2;
    private final Board board;
    private GameStatus status;
    private Player winner;
    private Player currentPlayer;
    private final List<Move> history;
    private final SpecialMoveHandler specialMoves;

    public Game(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
        this.board = new Board();
        this.status = GameStatus.ACTIVE;
        this.winner = null;
        this.currentPlayer = (player1.getColor()==Color.WHITE)?player1:player2;
        this.history = new ArrayList<>();
        this.specialMoves = new SpecialMoveHandler(board, history);
    }

    public void makeMove(Coordinate from, Coordinate to){
        Cell fromCell = board.getCell(from.x, from.y);

        Piece piece = fromCell.getPiece();
        Color pieceColor = piece.getColor();
        Color opponentColor = (currentPlayer.getColor() == Color.WHITE) ? Color.BLACK : Color.WHITE;

        if(currentPlayer.getColor() != pieceColor){
            throw new IllegalArgumentException("Not your turn sweety");
        }

        Move move;

        SpecialMoveType moveType = specialMoves.getSpecialMoveType(piece, from, to);

        if(moveType != SpecialMoveType.NONE){
            move = specialMoves.applySpecialMove(moveType, piece, from, to);
        }else{
            if(!piece.canMove(board, from, to)){
                throw new IllegalArgumentException("This piece can't move there");
            }

            Piece captured = board.getCell(to.x, to.y).getPiece();
            board.movePiece(from, to);
            move = new Move(from, to, piece, captured, SpecialMoveType.NONE);
        }

        piece.setMoved();

        history.add(move);

        if(isStalementReached(opponentColor)){
            gameEnd(GameStatus.STALEMATE, null);
        }

        if(isCheckMateReached(opponentColor)){
            gameEnd(GameStatus.CHECKMATE,currentPlayer);
        }

        changePlayer();
    }

    public void resign(Player player){
        if(status != GameStatus.ACTIVE){
            throw new IllegalStateException("Game is already over");
        }
        if(player != player1 && player != player2){
            throw new IllegalArgumentException("This player is not in the game");
        }

        Player winner = (player == player1) ? player2 : player1;
        gameEnd(GameStatus.RESIGNED, winner);
    }

    private void changePlayer(){
        currentPlayer = (currentPlayer==player1)?player2:player1;
    }

    private boolean isStalementReached(Color color){
        // Try every piece of this color against every square on the board
        for(int fromX = 0; fromX < 8; fromX++){
            for(int fromY = 0; fromY < 8; fromY++){
                Cell cell = board.getCell(fromX, fromY);
                if(cell.getCellStatus() == CellStatus.EMPTY || cell.getPiece().getColor() != color){
                    continue;
                }

                Piece piece = cell.getPiece();
                Coordinate from = new Coordinate(fromX, fromY);

                for(int toX = 0; toX < 8; toX++){
                    for(int toY = 0; toY < 8; toY++){
                        if(piece.canMove(board, from, new Coordinate(toX, toY))){
                            return false;       // found a move, so no stalemate
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean isCheckMateReached(Color color){
        // Checkmate = the king is in check right now...
        if(!isInCheck(color)){
            return false;
        }

        // ...and no move of this color gets the king out of check
        for(int fromX = 0; fromX < 8; fromX++){
            for(int fromY = 0; fromY < 8; fromY++){
                Cell fromCell = board.getCell(fromX, fromY);
                if(fromCell.getCellStatus() == CellStatus.EMPTY || fromCell.getPiece().getColor() != color){
                    continue;
                }

                Piece piece = fromCell.getPiece();
                Coordinate from = new Coordinate(fromX, fromY);

                for(int toX = 0; toX < 8; toX++){
                    for(int toY = 0; toY < 8; toY++){
                        Coordinate to = new Coordinate(toX, toY);
                        if(!piece.canMove(board, from, to)){
                            continue;
                        }

                        // Try the move, see if the king is safe, then undo it
                        Cell toCell = board.getCell(toX, toY);
                        Piece captured = toCell.getPiece();

                        board.movePiece(from, to);
                        boolean stillInCheck = isInCheck(color);

                        fromCell.setPiece(piece);
                        if(captured == null){
                            toCell.removePiece();
                        }else{
                            toCell.setPiece(captured);
                        }

                        if(!stillInCheck){
                            return false;           // found an escape
                        }
                    }
                }
            }
        }
        return true;                                // in check and no escape
    }

    private boolean isInCheck(Color color){
        // Find this color's king
        Coordinate kingPosition = null;
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                Cell cell = board.getCell(x, y);
                if(cell.getCellStatus() == CellStatus.OCCUPIED
                        && cell.getPiece().getColor() == color
                        && cell.getPiece().getPieceType() == PieceType.KING){
                    kingPosition = new Coordinate(x, y);
                }
            }
        }
        if(kingPosition == null){
            return false;
        }

        Color opponent = (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
        return board.isSquareAttacked(kingPosition, opponent);
    }

    private void gameEnd(GameStatus status, Player winner){
        this.status = status;
        this.winner = winner;

        System.out.println("GAME ENDED - " + status);
        if(winner != null){
            System.out.println("WINNER - " + winner.getName());
        }else{
            System.out.println("IT'S A DRAW");
        }
    }
}
