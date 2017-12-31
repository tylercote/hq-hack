import java.util.Objects;

import hacker.model.strategies.Trivia;

/**
 * A piece of trivia and its corresponding answer. This datatype is used for testing purposes only,
 * and allows us to quickly check if the google search correctly predicted the answer.
 */
public class QASet {
  protected Trivia trivia;
  protected String answer;

  /**
   * Constructs a new instance of a question and answer.
   *
   * @param t  the piece of trivia, containing a question and 3 options
   * @param an the answer
   * @throws IllegalArgumentException if any of the given inputs are null, or the answer is not one
   *                                  of the question's 3 options
   */
  public QASet(Trivia t, String an) throws IllegalArgumentException {
    Objects.requireNonNull(t, "Trivia cannot be null input.");
    Objects.requireNonNull(an, "Answer cannot be null input.");
    if (!t.getO1().equals(an) && !t.getO2().equals(an) && !t.getO3().equals(an)) {
      throw new IllegalArgumentException("The given answer is not one of the trivia's options.");
    }
    this.trivia = t;
    this.answer = an;
  }
}