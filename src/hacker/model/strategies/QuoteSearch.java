package hacker.model.strategies;

import com.google.api.services.customsearch.model.Result;

import java.util.HashMap;
import java.util.List;

/**
 * Search method for if a quote is given in the question. Searches only the quote, and returns the
 * option with the highest hits. IMPORTANT: This search is only for questions with quotes 6 words or
 * longer.
 */
public class QuoteSearch implements CountStrategy {

  @Override
  public String execute(Trivia t) {
    HashMap<String, Integer> counts = new HashMap<>();
    counts.put(t.getO1(), 0);
    counts.put(t.getO2(), 0);
    counts.put(t.getO3(), 0);
    String query = constructQuery(t);
    List<Result> results = CountStrategy.search(query);
    String content = "";
    for (Result r : results) {
      content = content.concat(r.getTitle() + " " + r.getSnippet() + " ");
    }

    CountStrategy.countsPerHitQuestionOnly(counts, content);

    System.out.println("Option A: " + t.getO1() + " occurred " + counts.get(t.getO1()) + " times.\n");
    System.out.println("Option B: " + t.getO2() + " occurred " + counts.get(t.getO2()) + " times.\n");
    System.out.println("Option C: " + t.getO3() + " occurred " + counts.get(t.getO3()) + " times.\n");

    String answer = CountStrategy.maxCountResult(counts, t);
    System.out.println("=====================================================================");
    System.out.println("=====================================================================");
    System.out.println("Most likely answer: " + answer);
    System.out.println("=====================================================================");
    System.out.println("=====================================================================");
    return answer;
  }

  /**
   * Constructs the search query from this piece of trivia, by extracting the quote only.
   *
   * @param t the piece of trivia to be processed
   * @return the search query we will use
   */
  private String constructQuery(Trivia t) {
    String q = t.getQuestion();
    q = q.substring(q.indexOf("\"") + 1);
    q = q.substring(0, q.indexOf("\""));
    return q;
  }
}