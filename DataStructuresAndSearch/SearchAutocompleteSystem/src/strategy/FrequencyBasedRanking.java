package strategy;

import data.Suggestion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FrequencyBasedRanking implements RankingStrategy{

    @Override
    public List<String> rank(List<Suggestion> suggestions){
        List<Suggestion> sorted = new ArrayList<>(suggestions);   // copy, don't change the input
        sorted.sort(Comparator.comparing(Suggestion::getFrequency).reversed()   // highest frequency first
                              .thenComparing(Suggestion::getWord));             // same frequency -> A to Z

        List<String> words = new ArrayList<>();
        for(Suggestion suggestion : sorted){
            words.add(suggestion.getWord());
        }
        return words;
    }
}
