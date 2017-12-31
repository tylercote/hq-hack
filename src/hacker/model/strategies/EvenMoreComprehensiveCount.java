//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package hacker.model.strategies;

import com.google.api.services.customsearch.model.Result;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class EvenMoreComprehensiveCount implements CountStrategy {
  public boolean isMax;

  public EvenMoreComprehensiveCount(boolean isMax) {
    this.isMax = isMax;
  }

  public String execute(Trivia t) {
    HashMap<String, Integer> counts = new HashMap();
    counts.put(t.getO1(), Integer.valueOf(0));
    counts.put(t.getO2(), Integer.valueOf(0));
    counts.put(t.getO3(), Integer.valueOf(0));
    String query = t.getQuestion();
    List<Result> results = CountStrategy.countsPerHitQuestionAnswer(counts, query);
    List<String> urls = new ArrayList();

    String newUrl;
    for(Iterator var6 = results.iterator(); var6.hasNext(); urls.add(newUrl)) {
      Result r = (Result)var6.next();
      String url = r.getLink();
      if (!url.startsWith("http://") && !url.startsWith("https://")) {
        if (url.startsWith("www")) {
          newUrl = "http://" + url;
        } else {
          newUrl = "http://www." + url;
        }
      } else {
        newUrl = url;
      }
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

    double sum = 0.0D;

    double i;
    for(Iterator var14 = counts.values().iterator(); var14.hasNext(); sum += i) {
      i = (double)((Integer)var14.next()).intValue();
    }

    System.out.println("=====================================================================");
    System.out.println("=====================================================================");
    System.out.println("Most likely answer: " + answer);
    System.out.println("=====================================================================");
    System.out.println("=====================================================================");

    if ((double)((Integer)counts.get(answer)).intValue() / sum >= 0.4D && sum > 1.0D) {
      return answer;
    } else {
      return CountStrategy.loadWebpages(this.isMax, t, counts, urls);
    }
  }
}
