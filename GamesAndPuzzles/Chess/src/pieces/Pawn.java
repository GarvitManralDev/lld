package pieces;

import enums.CellStatus;
import enums.Color;
import enums.PieceType;
import models.Board;
import models.Cell;

public class Pawn extends Piece{

    public Pawn(Color color){
        super(color, PieceType.PAWN);
    }

    @Override
    public boolean canMove(Board board, Coordinate from, Coordinate to){
        // Stay inside the board
        if(to.x < 0 || to.x >= 8 || to.y < 0 || to.y >= 8){
            return false;
        }

        // White starts on row 6 and moves up (row - 1), black starts on row 1 and moves down (row + 1)
        int forward = (this.color == Color.WHITE) ? -1 : 1;
        int startRow = (this.color == Color.WHITE) ? 6 : 1;

        int rowMove = to.x - from.x;
        int colDiff = Math.abs(to.y - from.y);
        Cell destination = board.getCell(to.x, to.y);

        // 1 square forward, into an empty square
        if(colDiff == 0 && rowMove == forward){
            return destination.getCellStatus() == CellStatus.EMPTY;
        }

        // 2 squares forward from the starting row, both squares empty
        if(colDiff == 0 && rowMove == 2 * forward && from.x == startRow){
            Cell middle = board.getCell(from.x + forward, from.y);
            return middle.getCellStatus() == CellStatus.EMPTY
                    && destination.getCellStatus() == CellStatus.EMPTY;
        }

        // 1 square diagonally forward, only to capture an enemy piece
        if(colDiff == 1 && rowMove == forward){
            return destination.getCellStatus() == CellStatus.OCCUPIED
                    && destination.getPiece().color != this.color;
        }

        return false;
    }
}
