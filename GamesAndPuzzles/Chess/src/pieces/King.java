package pieces;

import enums.CellStatus;
import enums.Color;
import enums.PieceType;
import models.Board;
import models.Cell;

public class King extends Piece{

    public King(Color color){
        super(color, PieceType.KING);
    }

    @Override
    public boolean canMove(Board board, Coordinate from, Coordinate to){
        // Stay inside the board
        if(to.x < 0 || to.x >= 8 || to.y < 0 || to.y >= 8){
            return false;
        }

        int rowDiff = Math.abs(to.x - from.x);
        int colDiff = Math.abs(to.y - from.y);

        // Must actually move
        if(rowDiff == 0 && colDiff == 0){
            return false;
        }

        // King moves 1 square in any direction
        if(rowDiff > 1 || colDiff > 1){
            return false;
        }

        // Destination: empty, or holds an enemy piece (capture)
        Cell destination = board.getCell(to.x, to.y);
        if(destination.getCellStatus() == CellStatus.EMPTY){
            return true;
        }
        return destination.getPiece().color != this.color;
    }
}
