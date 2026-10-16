package entities;

public class Player {
    private final String name;
    private int position;

    public Player(String name){
        this.name = name;
        this.position = 0;
    }

    public String getName(){
        return this.name;
    }

    public int getPosition(){
        return this.position;
    }

    public void setPosition(int position){
        if(position <= 0){
            throw new IllegalArgumentException("Position should be a positive cell number");
        }
        this.position = position;
    }
}
