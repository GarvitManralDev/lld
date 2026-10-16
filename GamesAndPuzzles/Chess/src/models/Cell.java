package models;

import enums.CellStatus;
import pieces.Piece;

public class Cell {
    private Piece piece;
    private CellStatus status;

    public Cell(){
        this.piece = null;
        this.status = CellStatus.EMPTY;
    }

    public Cell(Piece piece){
        this.piece = piece;
        this.status = CellStatus.OCCUPIED;
    }

    public CellStatus getCellStatus(){
        return this.status;
    }

    public Piece getPiece(){
        return this.piece;
    }

    public void setPiece(Piece piece){
        this.piece = piece;
        this.status = CellStatus.OCCUPIED;
    }

    public void removePiece(){
        this.piece = null;
        this.status = CellStatus.EMPTY;
    }
}
