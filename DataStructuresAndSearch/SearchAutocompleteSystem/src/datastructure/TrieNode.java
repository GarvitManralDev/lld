package datastructure;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {
    private final Map<Character,TrieNode> children = new HashMap<>();
    private boolean isEndOfWord;
    private int frequency;

    public Map<Character,TrieNode> getChildren(){
        return children;
    }

    public void setEndOfWord(boolean endOfWord){
        isEndOfWord = endOfWord;
    }

    public boolean isEndOfWord(){
        return isEndOfWord;
    }

    public int getFrequency(){
        return frequency;
    }

    public void incrementFrequency(){
        frequency++;
    }
}
