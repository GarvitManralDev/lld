package entities;

import enums.GameStatus;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class Game {
    private final Queue<Player> players;
    private final Dice dice;
    private final Board board;
    private GameStatus status;
    private Player winner;

    public Game(Queue<Player> players, List<Snake>snakes, List<Ladder>ladders,int size){
        this.players = new ArrayDeque<>(players);
        this.dice = new Dice();
        this.board = new Board(size,snakes,ladders);
        this.status = GameStatus.NOT_STARTED;
        this.winner = null;
    }

    public void play(){
        if(status != GameStatus.NOT_STARTED){
            throw new IllegalStateException("Game has already been played");
        }
        status = GameStatus.RUNNING;

        while(status == GameStatus.RUNNING){
            Player currPlayer = players.poll();
            
            int roll = dice.roll();
            int currPosition = currPlayer.getPosition();
            int newPosition = currPosition + roll;

            if(newPosition > board.getLastCell()){
                System.out.println(currPlayer.getName() + " rolled " + roll + " but can't move past " + board.getLastCell());
                players.offer(currPlayer);
                continue;
            }

            int finalPosition = board.getFinalPosition(newPosition);
            currPlayer.setPosition(finalPosition);

            if(checkWin(currPlayer)){
                winner = currPlayer;
                status = GameStatus.FINISHED;
                System.out.println("WINNER - " + winner.getName());
            }else{
                players.add(currPlayer);
            }
        }
    }

    public GameStatus getStatus(){
        return this.status;
    }

    public Player getWinner(){
        return this.winner;
    }

    private boolean checkWin(Player player){
        if(player.getPosition() == board.getLastCell()){
            return true;
        }
        return false;
    }
}
