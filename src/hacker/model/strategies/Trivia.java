package hacker.model.strategies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a piece of trivia.
 */
public class Trivia {
  private QuestionType type;
  private String question;
  private String o1;
  private String o2;
  private String o3;

  /**
   * Constructs a piece of trivia.
   *
   * @param question the question
   * @param o1       the first option
   * @param o2       the second option
   * @param o3       the third option
   */
  public Trivia(String question, String o1, String o2, String o3) {
    this.question = question;
    this.o1 = o1;
    this.o2 = o2;
    this.o3 = o3;
    this.type = this.getType();
  }

  /**
   * Getter for the question.
   *
   * @return the question
   */
  public String getQuestion() {
    return this.question;
  }

  /**
   * Getter for the first option.
   *
   * @return the first option
   */
  public String getO1() {
    return this.o1;
  }

  /**
   * Getter for the second option.
   *
   * @return the second option
   */
  public String getO2() {
    return this.o2;
  }

  /**
   * Getter for the third option.
   *
   * @return the third option
   */
  public String getO3() {
    return this.o3;
  }

  /**
   * Gets the appropriate strategy that should be used to tackle this piece of trivia, given its
   * assigned type.
   *
   * @return the type of strategy that we should use to solve this piece of trivia
   */
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

  /**
   * Gets the type of the question by parsing the question for string occurrences of certain key
   * phrases.
   */
  public QuestionType getType() {
    String s = this.question;
    if (s.contains("\"")) {
      String q = this.getQuestion();
      q = q.substring(q.indexOf("\"") + 1);
      q = q.substring(0, q.indexOf("\""));
      List<String> strings = new ArrayList(Arrays.asList(q.split("\\s")));
      if (strings.size() > 5) {
        return QuestionType.QUOTE;
      }
    } else if (s.toLowerCase().contains("not") || s.toLowerCase().contains("never")
        || s.toLowerCase().contains("least") || s.toLowerCase().contains("fewest"))) {
      return !s.toLowerCase().contains("what is") ? QuestionType.MIN : QuestionType.MINQO;
    } else if (s.toLowerCase().contains("what is") || s.toLowerCase().contains("who is")) {
      return QuestionType.MAXQO;
    }
    return QuestionType.MAX;
  }
}
