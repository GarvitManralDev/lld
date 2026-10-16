package strategy;

import data.Suggestion;

import java.util.List;

public interface RankingStrategy {
    List<String> rank(List<Suggestion> suggestions);
}
