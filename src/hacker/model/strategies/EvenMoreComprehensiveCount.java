package hacker.model.strategies;

import com.google.api.services.customsearch.model.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * It doesn't get better than this. Same as Count but with loading web pages.
 */
public class EvenMoreComprehensiveCount implements CountStrategy {
  public boolean isMax;

  /**
   * Creates a new instance of this search.
   *
   * @param isMax whether we want max hits or min hits
   */
  public EvenMoreComprehensiveCount(boolean isMax) {
    this.isMax = isMax;
  }

  @Override
  public String execute(Trivia t) {
    HashMap<String, Integer> counts = new HashMap();
    counts.put(t.getO1(), Integer.valueOf(0));
    counts.put(t.getO2(), Integer.valueOf(0));
    counts.put(t.getO3(), Integer.valueOf(0));
    String query = t.getQuestion();
    List<Result> results = CountStrategy.countsPerHitQuestionAnswer(counts, query);
    List<String> urls = new ArrayList();

    for (Result r : results) {
      String url = r.getLink();
      String newUrl;
      if (!url.startsWith("http://") && !url.startsWith("https://")) {
        if (url.startsWith("www")) {
          newUrl = "http://" + url;
        } else {
          newUrl = "http://www." + url;
        }
      } else {
        newUrl = url;
      }
      urls.add(newUrl);
    }

    CountStrategy.countsPerHitQuestionAnswer(counts, query);
    System.out.println("Option A: " + t.getO1() + " occurred " + counts.get(t.getO1()) + " times.");
    System.out.println("Option B: " + t.getO2() + " occurred " + counts.get(t.getO2()) + " times.");
    System.out.println("Option C: " + t.getO3() + " occurred " + counts.get(t.getO3()) + " times.");
    String answer;
    if (this.isMax) {
      answer = CountStrategy.maxCountResult(counts, t);
    } else {
      answer = CountStrategy.minCountResult(counts, t);
    }

    // calculate the total number of hits among all answers
    double sum = 0;
    for (double i : counts.values()) {
      sum = sum + i;
    }

    System.out.println("=====================================================================");
    System.out.println("=====================================================================");
    System.out.println("Most likely answer: " + answer);
    System.out.println("=====================================================================");
    System.out.println("=====================================================================");

    if ((double) counts.get(answer) / sum >= 0.4 && sum > 1.0) {
      return answer;
    } else {
      return CountStrategy.loadWebpages(this.isMax, t, counts, urls);
    }
  }
}
