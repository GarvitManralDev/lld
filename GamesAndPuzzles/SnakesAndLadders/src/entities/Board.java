package entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private final int size;
    private final int lastCell;
    private final Map<Integer, Integer> snakes;
    private final Map<Integer, Integer> ladders;

    public Board(int size, List<Snake>snakes, List<Ladder>ladders){
        this.size = size;
        this.lastCell = size * size;
        this.snakes = new HashMap<>();
        this.ladders = new HashMap<>();

        for(Snake snake : snakes){
            if(this.snakes.containsKey(snake.getStart())){
                throw new IllegalArgumentException("Two snakes start at cell " + snake.getStart());
            }
            this.snakes.put(snake.getStart(), snake.getEnd());
        }

        for(Ladder ladder : ladders){
            if(this.ladders.containsKey(ladder.getStart())){
                throw new IllegalArgumentException("Two ladders start at cell " + ladder.getStart());
            }
            if(this.snakes.containsKey(ladder.getStart())){
                throw new IllegalArgumentException("A snake and a ladder both start at cell " + ladder.getStart());
            }
            this.ladders.put(ladder.getStart(), ladder.getEnd());
        }
    }

    public int getLastCell(){
        return this.lastCell;
    }

    public int getFinalPosition(int position){
        if(snakes.containsKey(position)){
            return snakes.get(position);
        }
        if(ladders.containsKey(position)){
            return ladders.get(position);
        }
        return position;
    }
}
