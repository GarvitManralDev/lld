package datastructure;

import data.Suggestion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Trie {
    private final TrieNode root;

    public Trie(){
        this.root = new TrieNode();
    }

    public void insertWord(String word){
        TrieNode currNode = root;
        Map<Character,TrieNode> children;
        for(char ch: word.toCharArray()){
            children = currNode.getChildren();
            if(!children.containsKey(ch)) {
                children.put(ch, new TrieNode());
            }
            currNode = children.get(ch);
        }
        currNode.setEndOfWord(true);
        currNode.incrementFrequency();
    }

    private TrieNode prefixNode(String prefix){
        TrieNode currNode = root;
        Map<Character,TrieNode> children;
        for(char ch : prefix.toCharArray()){
            children = currNode.getChildren();
            if(!children.containsKey(ch)){
                return null;
            }
            currNode = children.get(ch);
        }
        return currNode;
    }

    public List<Suggestion> getAllWords(String prefix){
        List<Suggestion> words = new ArrayList<>();
        TrieNode currNode = prefixNode(prefix);
        if(currNode == null){
            return words;
        }
        collectWords(currNode, prefix, words);
        return words;
    }

    // Visits every node below currNode and saves each complete word it finds
    private void collectWords(TrieNode currNode, String currWord, List<Suggestion> words){
        if(currNode.isEndOfWord()){
            words.add(new Suggestion(currWord, currNode.getFrequency()));
        }

        Map<Character,TrieNode> children = currNode.getChildren();
        for(char ch : children.keySet()){
            collectWords(children.get(ch), currWord + ch, words);
        }
    }
}
