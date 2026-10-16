package models;

import enums.SpecialMoveType;
import pieces.Coordinate;
import pieces.Piece;

public class Move {
    private final Coordinate from;
    private final Coordinate to;
    private final Piece piece;
    private final Piece captured;       // null if nothing was captured
    private final SpecialMoveType type;

    public Move(Coordinate from, Coordinate to, Piece piece, Piece captured, SpecialMoveType type){
        this.from = from;
        this.to = to;
        this.piece = piece;
        this.captured = captured;
        this.type = type;
    }

    public Coordinate getFrom(){
        return this.from;
    }

    public Coordinate getTo(){
        return this.to;
    }

    public Piece getPiece(){
        return this.piece;
    }

    public Piece getCaptured(){
        return this.captured;
    }

    public SpecialMoveType getType(){
        return this.type;
    }
}
