package pieces;

import enums.Color;
import enums.PieceType;
import models.Board;

public abstract class Piece {
    protected final Color color;
    protected final PieceType pieceType;
    private boolean hasMoved;           // needed for castling

    public Piece(Color color, PieceType pieceType){
        this.color = color;
        this.pieceType = pieceType;
        this.hasMoved = false;
    }

    public abstract boolean canMove(Board board, Coordinate from, Coordinate to);

    public PieceType getPieceType(){
        return this.pieceType;
    }

    public Color getColor(){
        return this.color;
    }

    public boolean hasMoved(){
        return this.hasMoved;
    }

    public void setMoved(){
        this.hasMoved = true;
    }
}
