package pieces;

import enums.CellStatus;
import enums.Color;
import enums.PieceType;
import models.Board;
import models.Cell;

public class Bishop extends Piece{

    public Bishop(Color color){
        super(color, PieceType.BISHOP);
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

        // Bishop moves along a diagonal
        boolean sameDiagonal = (rowDiff == colDiff);
        if(!sameDiagonal){
            return false;
        }

        // Direction of each step: -1, 0 or +1
        int rowStep = Integer.compare(to.x, from.x);
        int colStep = Integer.compare(to.y, from.y);

        // Every square between start and destination must be empty
        int steps = Math.max(rowDiff, colDiff);
        for(int i = 1; i < steps; i++){
            int row = from.x + i * rowStep;
            int col = from.y + i * colStep;
            if(board.getCell(row, col).getCellStatus() == CellStatus.OCCUPIED){
                return false;
            }
        }

        // Destination: empty, or holds an enemy piece (capture)
        Cell destination = board.getCell(to.x, to.y);
        if(destination.getCellStatus() == CellStatus.EMPTY){
            return true;
        }
        return destination.getPiece().color != this.color;
    }

}
