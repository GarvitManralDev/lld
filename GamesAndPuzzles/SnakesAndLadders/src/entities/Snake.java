package entities;

public class Snake extends BoardEntity {

    public Snake(int start, int end){
        super(start, end);

        if(start <= end){
            throw new IllegalArgumentException("Snake head should not be at the same block as the tail");
        }
    }
}
