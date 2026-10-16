package pieces;

import enums.CellStatus;
import enums.Color;
import enums.PieceType;
import models.Board;
import models.Cell;

public class Knight extends Piece{

    public Knight(Color color){
        super(color, PieceType.KNIGHT);
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

        // Knight moves in an L: 2 one way and 1 the other, and jumps over pieces
        boolean lShape = (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
        if(!lShape){
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
