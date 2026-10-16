package models;

import java.util.Arrays;

public class BitArray {
    private boolean[] bits;

    public BitArray(int size){
        this.bits = new boolean[size];
    }

    public void set(int index){
        this.bits[index] = true;
    }

    public boolean get(int index){
        return this.bits[index];
    }

    public void clear(){
        Arrays.fill(this.bits, false);
    }
}
