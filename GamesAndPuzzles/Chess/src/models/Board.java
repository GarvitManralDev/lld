package models;

import enums.CellStatus;
import enums.Color;
import enums.PieceType;
import pieces.Bishop;
import pieces.Coordinate;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

public class Board {
    private Cell[][] grid;

    public Board(){
        this.grid = new Cell[8][8];
        initializeBoard();
    }

    private void initializeBoard(){
        // Start with every square empty
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                grid[x][y] = new Cell();
            }
        }

        // Black: back row on row 0, pawns on row 1
        placeBackRow(0, Color.BLACK);
        placePawns(1, Color.BLACK);

        // White: pawns on row 6, back row on row 7
        placePawns(6, Color.WHITE);
        placeBackRow(7, Color.WHITE);
    }

    private void placeBackRow(int row, Color color){
        grid[row][0] = new Cell(new Rook(color));
        grid[row][1] = new Cell(new Knight(color));
        grid[row][2] = new Cell(new Bishop(color));
        grid[row][3] = new Cell(new Queen(color));
        grid[row][4] = new Cell(new King(color));
        grid[row][5] = new Cell(new Bishop(color));
        grid[row][6] = new Cell(new Knight(color));
        grid[row][7] = new Cell(new Rook(color));
    }

    private void placePawns(int row, Color color){
        for(int y = 0; y < 8; y++){
            grid[row][y] = new Cell(new Pawn(color));
        }
    }

    public Cell getCell(int x, int y){
        return grid[x][y];
    }

    public void movePiece(Coordinate from, Coordinate to){
        Cell fromCell = getCell(from.x, from.y);
        Cell toCell = getCell(to.x, to.y);
        Piece piece = fromCell.getPiece();

        toCell.setPiece(piece);         // replaces any captured piece
        fromCell.removePiece();
    }

    public boolean isSquareAttacked(Coordinate square, Color byColor){
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                Cell cell = getCell(x, y);
                if(cell.getCellStatus() == CellStatus.EMPTY || cell.getPiece().getColor() != byColor){
                    continue;
                }
                Piece piece = cell.getPiece();

                // A pawn attacks diagonally forward even when that square is empty,
                // but its canMove only allows a diagonal move onto an enemy, so check it by hand
                if(piece.getPieceType() == PieceType.PAWN){
                    int forward = (byColor == Color.WHITE) ? -1 : 1;
                    if(square.x - x == forward && Math.abs(square.y - y) == 1){
                        return true;
                    }
                    continue;
                }

                if(piece.canMove(this, new Coordinate(x, y), square)){
                    return true;
                }
            }
        }
        return false;
    }
}
