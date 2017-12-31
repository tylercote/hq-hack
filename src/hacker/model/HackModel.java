//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package hacker.model;

import hacker.model.strategies.CountStrategy;
import hacker.model.strategies.Trivia;
import java.util.Objects;

public class HackModel implements IHackModel {
  private Trivia trivia;

  public HackModel(Trivia t) {
    this.trivia = t;
  }

  public String cheat(CountStrategy strat) {
    Objects.requireNonNull(strat, "Strategy input cannot be null.");
    String output = strat.execute(this.trivia);
    return output;
  }

  public Trivia getTrivia() {
    return this.trivia;
  }

  public void setTrivia(Trivia trivia) {
    Objects.requireNonNull(trivia, "Trivia input cannot be null.");
    this.trivia = trivia;
  }
}
