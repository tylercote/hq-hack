//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package hacker.model.strategies;

import java.util.HashMap;

public class Count implements CountStrategy {
  boolean isMax;

  public Count(boolean isMax) {
    this.isMax = isMax;
  }

  public String execute(Trivia t) {
    HashMap<String, Integer> counts = new HashMap();
    counts.put(t.getO1(), Integer.valueOf(0));
    counts.put(t.getO2(), Integer.valueOf(0));
    counts.put(t.getO3(), Integer.valueOf(0));
    String query = t.getQuestion();
    CountStrategy.countsPerHitQuestionAnswer(counts, query);
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
