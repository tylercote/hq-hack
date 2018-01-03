package hacker.model;

import hacker.model.strategies.CountStrategy;
import hacker.model.strategies.Trivia;

import java.util.Objects;

/**
 * Model for the hacker. Boom boom.
 */
public class HackModel implements IHackModel {
  private Trivia trivia;

  /**
   * Constructs an instance of this model with the piece of trivia to be processed.
   *
   * @param t the piece of trivia we are going to process
   */
  public HackModel(Trivia t) {
    this.trivia = t;
  }

  @Override
  public String cheat(CountStrategy strat) {
    Objects.requireNonNull(strat, "Strategy input cannot be null.");
    String output = strat.execute(this.trivia);
    return output;
  }

  @Override
  public Trivia getTrivia() {
    return this.trivia;
  }

  @Override
  public void setTrivia(Trivia trivia) {
    Objects.requireNonNull(trivia, "Trivia input cannot be null.");
    this.trivia = trivia;
  }
}
