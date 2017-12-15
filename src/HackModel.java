public class HackModel  {

  private Trivia trivia;

  public HackModel(Trivia t) {
    this.trivia = t;
  }

  public String cheat(CountStrategy strat) {

    String output = strat.execute(trivia);
    return output;

  }

  public Trivia getTrivia() {
    return trivia;
  }

  public void setTrivia(Trivia trivia) {
    this.trivia = trivia;
  }

}
