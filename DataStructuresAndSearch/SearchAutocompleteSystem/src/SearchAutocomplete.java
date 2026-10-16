import data.Suggestion;
import datastructure.Trie;
import strategy.RankingStrategy;

import java.util.List;

public class SearchAutocomplete {
    private final Trie trie;
    private RankingStrategy strategy;
    private final int maxSuggestions;

    public SearchAutocomplete(RankingStrategy strategy, int maxSuggestions){
        this.trie = new Trie();
        this.strategy = strategy;
        this.maxSuggestions = maxSuggestions;
    }

    public void setStrategy(RankingStrategy strategy){
        this.strategy = strategy;
    }

    public void addWord(String word){
        trie.insertWord(word.toLowerCase());
    }

    public void addWords(List<String> words){
        for(String word: words){
            addWord(word);
        }
    }

    public List<String> getSuggestions(String prefix){
        List<Suggestion> suggestions = trie.getAllWords(prefix.toLowerCase());
        int lastIndex = Math.min(suggestions.size(), maxSuggestions);
        return strategy.rank(suggestions).subList(0,lastIndex);
    }

}
