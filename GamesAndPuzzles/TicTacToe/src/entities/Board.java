package entities;

import exceptions.invalidMoveException;
import enums.Symbol;

public class Board {
    private Cell[][]grid;
    private int size;

    Board(int size){
        this.size = size;
        this.grid = new Cell[size][size];
        initializeBoard();
    }

    private void initializeBoard(){
        for(int row = 0; row < size; row ++){
            for(int col = 0; col < size; col++){
                grid[row][col] = new Cell();
            }
        }
    }

    public void placeSymbol(int row,int col,Symbol symbol){
        if(!grid[row][col].isEmpty()){
            throw new invalidMoveException("This move is Invalid");
        }

        if( row < 0 || row >= size || col < 0 || col > size){
            throw new invalidMoveException("This move is Invalid");
        }

        grid[row][col].setSymbol(symbol);
    }

    public void printBoard(){
        for(int row=0;row<size;row++){
            for(int col=0;col<size;col++){
                System.out.print(grid[row][col].getSymbol().getDisplayChar() + " ");
            }
            System.out.println();
        }
    }

    public boolean isFull(){
        for(int row=0;row<size;row++){
            for(int col=0;col<size;col++){
                if(grid[row][col].isEmpty()){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkWin(int row, int col, Symbol symbol){

        // Check Row
        boolean rowWin = true;
        for(int i=0; i<size; i++){
            if(grid[row][i].getSymbol()!=symbol){
                rowWin=false;
                break;
            }
        }
        if(rowWin){
            return true;
        }

        // Check Col
        boolean colWin = true;
        for(int i=0; i<size; i++){
            if(grid[i][col].getSymbol()!=symbol){
                colWin=false;
                break;
            }
        }
        if(colWin){
            return true;
        }


        // Check Diagonal (Left up --> Right Bottom)
        if(row == col){
            boolean diagWin = true;
            for(int i=0; i<size ;i++){
                if(grid[i][i].getSymbol()!=symbol){
                    diagWin=false;
                    break;
                }
            }
            if(diagWin){
                return true;
            }
        }

        // Check Diagonal (Right up --> Left Bottom)
        if(row + col == size-1){
            boolean diagWin = true;
            for(int i=0; i<size ;i++){
                if(grid[i][size - i - 1].getSymbol()!=symbol){
                    diagWin=false;
                    break;
                }
            }
            if(diagWin){
                return true;
            }
        }

        return false;
    }
}
