package models;

import enums.CellStatus;
import enums.Color;
import enums.PieceType;
import enums.SpecialMoveType;
import pieces.Coordinate;
import pieces.Piece;
import pieces.Queen;

import java.util.List;

public class SpecialMoveHandler {
    private final Board board;
    private final List<Move> history;

    public SpecialMoveHandler(Board board, List<Move> history){
        this.board = board;
        this.history = history;
    }

    public SpecialMoveType getSpecialMoveType(Piece piece, Coordinate from, Coordinate to){
        if(isCastling(piece, from, to)){
            return SpecialMoveType.CASTLING;
        }
        if(isEnPassant(piece, from, to)){
            return SpecialMoveType.EN_PASSANT;
        }
        if(isPromotion(piece, to) && piece.canMove(board, from, to)){
            return SpecialMoveType.PROMOTION;
        }
        return SpecialMoveType.NONE;
    }

    // Only call with the type returned by getSpecialMoveType for this same move
    public Move applySpecialMove(SpecialMoveType type, Piece piece, Coordinate from, Coordinate to){
        switch(type){
            case CASTLING: {
                applyCastling(from, to);
                return new Move(from, to, piece, null, type);
            }
            case EN_PASSANT: {
                board.movePiece(from, to);
                Piece captured = applyEnPassant(from, to);
                return new Move(from, to, piece, captured, type);
            }
            case PROMOTION: {
                Piece captured = board.getCell(to.x, to.y).getPiece();
                board.movePiece(from, to);
                applyPromotion(piece, to);
                return new Move(from, to, piece, captured, type);
            }
            default:
                throw new IllegalArgumentException("Not a special move");
        }
    }

    private boolean isEnPassant(Piece piece, Coordinate from, Coordinate to){
        if(piece.getPieceType() != PieceType.PAWN || history.isEmpty()){
            return false;
        }
        if(to.x < 0 || to.x >= 8 || to.y < 0 || to.y >= 8){
            return false;
        }

        // Our pawn moves 1 forward and 1 sideways, into an empty square
        int forward = (piece.getColor() == Color.WHITE) ? -1 : 1;
        if(to.x - from.x != forward || Math.abs(to.y - from.y) != 1){
            return false;
        }
        if(board.getCell(to.x, to.y).getCellStatus() != CellStatus.EMPTY){
            return false;
        }

        // The last move was an enemy pawn moving 2 squares...
        Move lastMove = history.get(history.size() - 1);
        Piece lastPiece = lastMove.getPiece();
        if(lastPiece.getPieceType() != PieceType.PAWN || lastPiece.getColor() == piece.getColor()){
            return false;
        }
        if(Math.abs(lastMove.getTo().x - lastMove.getFrom().x) != 2){
            return false;
        }

        // ...and it landed right beside our pawn
        return lastMove.getTo().x == from.x && lastMove.getTo().y == to.y;
    }

    // Call after the pawn has moved: removes the captured pawn beside it and returns it
    private Piece applyEnPassant(Coordinate from, Coordinate to){
        Cell beside = board.getCell(from.x, to.y);
        Piece captured = beside.getPiece();
        beside.removePiece();
        return captured;
    }

    // ---------- Castling ----------

    private boolean isCastling(Piece piece, Coordinate from, Coordinate to){
        if(piece.getPieceType() != PieceType.KING || piece.hasMoved()){
            return false;
        }

        // King moves 2 squares sideways along its own row
        if(to.x != from.x || Math.abs(to.y - from.y) != 2){
            return false;
        }

        int step = (to.y > from.y) ? 1 : -1;
        int rookCol = (step == 1) ? 7 : 0;

        // The rook on that side must be there, be ours, and not have moved
        Cell rookCell = board.getCell(from.x, rookCol);
        if(rookCell.getCellStatus() == CellStatus.EMPTY){
            return false;
        }
        Piece rook = rookCell.getPiece();
        if(rook.getPieceType() != PieceType.ROOK || rook.getColor() != piece.getColor() || rook.hasMoved()){
            return false;
        }

        // Every square between the king and the rook must be empty
        for(int y = from.y + step; y != rookCol; y += step){
            if(board.getCell(from.x, y).getCellStatus() == CellStatus.OCCUPIED){
                return false;
            }
        }

        // The king can't be in check, pass through check, or land in check
        Color opponent = (piece.getColor() == Color.WHITE) ? Color.BLACK : Color.WHITE;
        for(int i = 0; i <= 2; i++){
            Coordinate square = new Coordinate(from.x, from.y + i * step);
            if(board.isSquareAttacked(square, opponent)){
                return false;
            }
        }
        return true;
    }

    // Moves the king 2 squares and the rook to the square the king crossed
    private void applyCastling(Coordinate from, Coordinate to){
        int step = (to.y > from.y) ? 1 : -1;
        int rookCol = (step == 1) ? 7 : 0;
        Coordinate rookFrom = new Coordinate(from.x, rookCol);
        Coordinate rookTo = new Coordinate(from.x, from.y + step);

        board.movePiece(from, to);
        board.movePiece(rookFrom, rookTo);
        board.getCell(rookTo.x, rookTo.y).getPiece().setMoved();
    }

    // ---------- Promotion ----------

    private boolean isPromotion(Piece piece, Coordinate to){
        if(piece.getPieceType() != PieceType.PAWN){
            return false;
        }

        int lastRow = (piece.getColor() == Color.WHITE) ? 0 : 7;
        return to.x == lastRow;
    }

    private void applyPromotion(Piece piece, Coordinate to){
        board.getCell(to.x, to.y).setPiece(new Queen(piece.getColor()));
    }
}
