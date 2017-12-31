//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package hacker;

import hacker.controller.HackController;
import hacker.controller.IHackController;
import hacker.model.HackModel;
import hacker.model.IHackModel;
import hacker.model.strategies.Trivia;
import hacker.view.HackView;
import hacker.view.IHackView;

public class MainFrame {
  public MainFrame() {
  }

  public static void main(String[] args) {
    IHackView hackView = new HackView();
    Trivia t = new Trivia("Courgette and aubergine are names for what kind of food?", "cheese", "wine", "vegetables");
    IHackModel hack1 = new HackModel(t);
    IHackController controller = new HackController(hack1, hackView);
    hackView.setOut();
    controller.run();
  }
}

// did i do it
