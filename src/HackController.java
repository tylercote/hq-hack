
public class HackController {

  public static void main(String[] args) {
    // Objectives:
    // - Run program, displays window with button to take screenshot
    // - Screenshot is used to construct a model (takes in image, uses google api to parse it)
    // -
    // get screenshot of pic

    Trivia t = new Trivia("Courgette and aubergine are names for what kind of food?",
            "cheese", "wine", "vegetables");
    HackModel hack1 = new HackModel(t);
    System.out.println(hack1.cheat(hack1.getTrivia()));
  }
}
