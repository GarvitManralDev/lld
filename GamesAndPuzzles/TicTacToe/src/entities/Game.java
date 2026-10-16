package entities;

import enums.Symbol;

public class Game {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currPlayer;

    public Game(int size, String player1Name, Symbol player1Symbol,String player2Name, Symbol player2Symbol){
        board = new Board(size);
        player1 = new Player(player1Name,player1Symbol);
        player2 = new Player(player2Name,player2Symbol);
        this.currPlayer = player1;
    }

    public void makeMove(int row, int col){
        board.placeSymbol(row,col,currPlayer.getSymbol());
        if(board.checkWin(row,col,currPlayer.getSymbol())){
            gameEnd();
            return;
        }
        if(board.isFull()){
            gameDraw();
            return;
        }
        changePlayer();
    }

    private void changePlayer(){
        if(currPlayer == player1){
            currPlayer = player2;
        }else{
            currPlayer = player1;
        }
    }

    private void gameEnd(){
        System.out.println("GAME ENDED !!!!");
        System.out.println("WINNER - " + currPlayer.getName());
        System.out.println("FINAL BOARD  - ");
        board.printBoard();
    }

    private void gameDraw(){
        System.out.println("GAME ENDED !!!!");
        System.out.println("IT'S A DRAW");
        System.out.println("FINAL BOARD  - ");
        board.printBoard();
    }
}
