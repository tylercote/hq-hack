// A class to represent one trivia question.
public class Trivia {

  public String getQuestion() {
    return question;
  }

  public String getO1() {
    return o1;
  }

  public String getO2() {
    return o2;
  }

  public String getO3() {
    return o3;
  }

  String question;
  String o1;
  String o2;
  String o3;

  public Trivia(String question, String o1, String o2, String o3) {
    this.question = question;
    this.o1 = o1;
    this.o2 = o2;
    this.o3 = o3;
  }
}
