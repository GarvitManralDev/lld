import strategy.AlphabeticalRanking;
import strategy.FrequencyBasedRanking;

import java.util.List;

// Search autocomplete demo: top 3 suggestions, ranked by frequency first
void main() {
    SearchAutocomplete autocomplete = new SearchAutocomplete(new FrequencyBasedRanking(), 3);

    // 1. Past searches: each add is +1 frequency for that word
    autocomplete.addWords(List.of("apple", "apple", "apple", "apply", "apply", "app",
                                  "application", "apricot", "banana", "band"));

    // 2. User types "a" -> "ap" -> "app", suggestions narrow down
    System.out.println("a    -> " + autocomplete.getSuggestions("a"));      // [apple, apply, app]
    System.out.println("ap   -> " + autocomplete.getSuggestions("ap"));     // [apple, apply, app]
    System.out.println("app  -> " + autocomplete.getSuggestions("app"));    // [apple, apply, app]
    System.out.println("ban  -> " + autocomplete.getSuggestions("ban"));    // [banana, band]

    // 3. Case-insensitive and no-match prefixes
    System.out.println("APP  -> " + autocomplete.getSuggestions("APP"));    // [apple, apply, app]
    System.out.println("xyz  -> " + autocomplete.getSuggestions("xyz"));    // []

    // 4. "application" gets searched a lot, so it moves up
    autocomplete.addWord("Application");
    autocomplete.addWord("application");
    autocomplete.addWord("application");
    System.out.println("app  -> " + autocomplete.getSuggestions("app"));    // [application, apple, apply]

    // 5. Switch the ranking strategy at runtime
    autocomplete.setStrategy(new AlphabeticalRanking());
    System.out.println("app  -> " + autocomplete.getSuggestions("app"));    // [app, apple, application]
}
