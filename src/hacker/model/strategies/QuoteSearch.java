//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package hacker.model.strategies;

import com.google.api.services.customsearch.model.Result;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class QuoteSearch implements CountStrategy {
  public QuoteSearch() {
  }

  public String execute(Trivia t) {
    HashMap<String, Integer> counts = new HashMap();
    counts.put(t.getO1(), Integer.valueOf(0));
    counts.put(t.getO2(), Integer.valueOf(0));
    counts.put(t.getO3(), Integer.valueOf(0));
    String query = this.constructQuery(t);
    List<Result> results = CountStrategy.search(query);
    String content = "";

    Result r;
    for(Iterator var6 = results.iterator(); var6.hasNext(); content = content.concat(r.getTitle() + " " + r.getSnippet() + " ")) {
      r = (Result)var6.next();
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

  private String constructQuery(Trivia t) {
    String q = t.getQuestion();
    q = q.substring(q.indexOf("\"") + 1);
    q = q.substring(0, q.indexOf("\""));
    return q;
  }
}
