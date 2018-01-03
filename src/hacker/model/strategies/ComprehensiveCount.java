package hacker.model.strategies;

import com.google.api.services.customsearch.model.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Does the same as CountQuestionOnly, but loads the individual web pages that are produced as
 * results to parse for more information.
 */
public class ComprehensiveCount implements CountStrategy {
  public boolean isMax;

  /**
   * Creates a new instance of this search.
   *
   * @param isMax whether we want max hits or min hits
   */
  public ComprehensiveCount(boolean isMax) {
    this.isMax = isMax;
  }

  @Override
  public String execute(Trivia t) {
    HashMap<String, Integer> counts = new HashMap();
    counts.put(t.getO1(), Integer.valueOf(0));
    counts.put(t.getO2(), Integer.valueOf(0));
    counts.put(t.getO3(), Integer.valueOf(0));
    String query = t.getQuestion();
    List<Result> results = CountStrategy.search(query);
    String content = "";
    List<String> urls = new ArrayList();

    for (Result r : results) {
      content = content.concat(r.getTitle() + " " + r.getSnippet() + " ");
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

    CountStrategy.countsPerHitQuestionOnly(counts, content);
    System.out.println("Option A: " + t.getO1() + " occurred " + counts.get(t.getO1()) + " times.");
    System.out.println("Option B: " + t.getO2() + " occurred " + counts.get(t.getO2()) + " times.");
    System.out.println("Option C: " + t.getO3() + " occurred " + counts.get(t.getO3()) + " times.");
    String answer;
    if (this.isMax) {
      answer = CountStrategy.maxCountResult(counts, t);
    } else {
      answer = CountStrategy.minCountResult(counts, t);
    }

    int sum = 0;
    for (int i : counts.values()) {
      sum += i;
    }

    System.out.println("=====================================================================");
    System.out.println("=====================================================================");
    System.out.println("Most likely answer: " + answer);
    System.out.println("=====================================================================");
    System.out.println("=====================================================================");
    if ((double) counts.get(answer) >= 0.4 && sum > 1) {
      return answer;
    } else {
      return CountStrategy.loadWebpages(this.isMax, t, counts, urls);
    }
  }
}
