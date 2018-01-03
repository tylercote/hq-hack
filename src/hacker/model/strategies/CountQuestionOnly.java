package hacker.model.strategies;

import com.google.api.services.customsearch.model.Result;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Runs a search and gets the most likely answer based on the MAXIMUM number of hits out of all of
 * the available answer options. Conducts 1 search, which is the question ONLY. IMPORTANT: The use
 * of this search is only for questions with an occurrence of "What is" within the question.
 *
 * NOTE: This search is pretty obsolete; ComprehensiveCount uses the same logic but searches
 * deeper.
 */
public class CountQuestionOnly implements CountStrategy {
  boolean isMax;

  public CountQuestionOnly(boolean isMax) {
    this.isMax = isMax;
  }

  public String execute(Trivia t) {
    HashMap<String, Integer> counts = new HashMap();
    counts.put(t.getO1(), Integer.valueOf(0));
    counts.put(t.getO2(), Integer.valueOf(0));
    counts.put(t.getO3(), Integer.valueOf(0));
    String query = t.getQuestion();
    List<Result> results = CountStrategy.search(query);
    String content = "";

    for (Result r : results) {
      content = content.concat(r.getTitle() + " " + r.getSnippet() + " ");
    }

    CountStrategy.countsPerHitQuestionOnly(counts, content);
    System.out.println("Option A: " + t.getO1() + " occurred " + counts.get(t.getO1()) + " times.\n");
    System.out.println("Option B: " + t.getO2() + " occurred " + counts.get(t.getO2()) + " times.\n");
    System.out.println("Option C: " + t.getO3() + " occurred " + counts.get(t.getO3()) + " times.\n");
    String answer;
    if (this.isMax) {
      answer = CountStrategy.maxCountResult(counts, t);
    } else {
      answer = CountStrategy.minCountResult(counts, t);
    }

    System.out.println("Most likely answer: " + answer);
    return answer;
  }
}
