package hacker.model.strategies;

import java.util.HashMap;


/**
 * Runs a search and gets the most likely answer based on the number of hits out of all of the
 * available answer options. Conducts 3 searches as follows: Question + answer 1 Question + answer 2
 * Question + answer 3.
 *
 *
 * NOTE: This search is pretty obsolete; EvenMoreComprehensiveCount uses the same logic but searches
 * deeper.
 */
public class Count implements CountStrategy {
  boolean isMax;

  /**
   * Returns the max-hitting option if true; else, returns the min-hitting option.
   *
   * @param isMax whether we return highest or lowest number of hits
   */
  public Count(boolean isMax) {
    this.isMax = isMax;
  }

  @Override
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
