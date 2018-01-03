package hacker;

import hacker.controller.HackController;
import hacker.controller.IHackController;
import hacker.model.HackModel;
import hacker.model.IHackModel;
import hacker.model.strategies.Trivia;
import hacker.view.HackView;
import hacker.view.IHackView;

/**
 * RUN THE HACKER
 */
public class MainFrame {
  public MainFrame() {
  }

  public static void main(String[] args) {
    IHackView hackView = new HackView();
    IHackModel hack1 = new HackModel(new Trivia("", "" ,"" ,""));
    IHackController controller = new HackController(hack1, hackView);
    hackView.setOut();
    /*
    Input the file path name of Dan's screenshot folder (he takes screenshots of questions - this
    is the folder where they are saved, and where the program will repeatedly parse.
     */
    controller.run("/Users/Dan/Documents/HQScreenShots");
  }
}
