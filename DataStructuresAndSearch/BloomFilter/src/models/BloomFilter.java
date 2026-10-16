package models;

public class BloomFilter {
    private final BloomFilterConfig config;
    private final BitArray bitArray;

    public BloomFilter(int expectedElements, double falsePositiveRate){
        this.config = new BloomFilterConfig(expectedElements, falsePositiveRate);
        this.bitArray = new BitArray(config.getBitArraySize());
    }

    // Sets the k bits for this element
    public synchronized void add(String element){
        for(int i = 0; i < config.getHashCount(); i++){
            bitArray.set(hash(element, i));
        }
    }

    // false = definitely not added, true = probably added
    public synchronized boolean mightContain(String element){
        for(int i = 0; i < config.getHashCount(); i++){
            if(!bitArray.get(hash(element, i))){
                return false;
            }
        }
        return true;
    }

    public synchronized void clear(){
        bitArray.clear();
    }

    private int hash(String element, int seed){
        int base = 31 + 2 * seed;           // a different odd multiplier for each hash function
        int h = 0;
        for(char c : element.toCharArray()){
            h = h * base + c;
        }
        return Math.floorMod(h, config.getBitArraySize());
    }
}
