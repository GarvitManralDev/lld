package entities;

public class Ladder extends BoardEntity{

    public Ladder(int start, int end){
        super(start,end);

        if(start >= end){
            throw new IllegalArgumentException("Ladder's end should be at a higher block than the bottom");
        }
    }
}
