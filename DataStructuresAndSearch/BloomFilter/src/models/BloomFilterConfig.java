package models;

public class BloomFilterConfig {
    private final int expectedElements;     // n
    private final double falsePositiveRate; // p
    private final int bitArraySize;         // m
    private final int hashCount;            // k

    public BloomFilterConfig(int expectedElements, double falsePositiveRate){
        if(expectedElements <= 0){
            throw new IllegalArgumentException("Expected elements must be positive");
        }
        if(falsePositiveRate <= 0 || falsePositiveRate >= 1){
            throw new IllegalArgumentException("False positive rate must be between 0 and 1");
        }

        this.expectedElements = expectedElements;
        this.falsePositiveRate = falsePositiveRate;

        double ln2 = Math.log(2);
        this.bitArraySize = (int) Math.ceil(-(expectedElements * Math.log(falsePositiveRate)) / (ln2 * ln2));
        this.hashCount = Math.max(1, (int) Math.round((double) bitArraySize / expectedElements * ln2));
    }

    public int getExpectedElements(){
        return this.expectedElements;
    }

    public double getFalsePositiveRate(){
        return this.falsePositiveRate;
    }

    public int getBitArraySize(){
        return this.bitArraySize;
    }

    public int getHashCount(){
        return this.hashCount;
    }
}
