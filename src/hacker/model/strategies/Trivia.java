//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package hacker.model.strategies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Trivia {
  private QuestionType type;
  private String question;
  private String o1;
  private String o2;
  private String o3;

  public Trivia(String question, String o1, String o2, String o3) throws IllegalArgumentException {
    this.question = question;
    this.o1 = o1;
    this.o2 = o2;
    this.o3 = o3;
    this.type = this.getType();
  }

  public String getQuestion() {
    return this.question;
  }

  public String getO1() {
    return this.o1;
  }

  public String getO2() {
    return this.o2;
  }

  public String getO3() {
    return this.o3;
  }

  public CountStrategy getStrat() {
    switch (this.type) {
      case MAX:
        return new EvenMoreComprehensiveCount(true);
      case MIN:
        return new EvenMoreComprehensiveCount(false);
      case MAXQO:
        return new ComprehensiveCount(true);
      case MINQO:
        return new ComprehensiveCount(false);
      case QUOTE:
        return new QuoteSearch();
      default:
        throw new IllegalArgumentException("this isn't even possible how u get here?");
    }
  }

  public QuestionType getType() {
    String s = this.question;
    if (s.contains("\"")) {
      String q = this.getQuestion();
      q = q.substring(q.indexOf("\"") + 1);
      q = q.substring(0, q.indexOf("\""));
      List<String> strings = new ArrayList(Arrays.asList(q.split("\\s")));
      if (strings.size() > 5) {
        System.out.println("Using QUOTE SEARCH...");
        return QuestionType.QUOTE;
      }
    } else if (s.toLowerCase().contains("not") || s.toLowerCase().contains("never")) {
      if (!s.toLowerCase().contains("what is")) {
        System.out.println("Using MINIMUM SEARCH...");
        return QuestionType.MIN;
      } else {
        System.out.println("Using MINIMUM SEARCH, QUESTION ONLY...");
        return QuestionType.MINQO;
      }
    } else if (s.toLowerCase().contains("what is") || s.toLowerCase().contains("which of")
            || s.toLowerCase().contains("who is")) {
      System.out.println("Using MAXIMUM SEARCH, QUESTION ONLY...");
      return QuestionType.MAXQO;
    }
    System.out.println("Using MAXIMUM SEARCH...");
    return QuestionType.MAX;
  }
}
