package hacker.controller;

import hacker.model.HackModel;
import hacker.model.IHackModel;
import hacker.model.strategies.Count;
import hacker.model.strategies.CountQuestionOnly;
import hacker.model.strategies.QuoteSearch;
import hacker.model.strategies.Trivia;
import hacker.view.IHackView;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.swing.JButton;

/**
 * Controls this hacker and any inputted user commands - as of now, these are the inputted desired
 * searching strategy. As such, it implements Java Swing's ActionListener interface to respond to
 * action events.
 */
public class HackController implements IHackController, ActionListener {
  private IHackModel model;
  private IHackView view;
  private String currQuestion;

  // ============================== Some nerd shit for Tyler only ==============================
  private static Rectangle QUESTION_BOX = new Rectangle(450, 350, 700, 330);
  private static Rectangle O1_BOX = new Rectangle(450, 730, 700, 75);
  private static Rectangle O2_BOX = new Rectangle(450, 900, 700, 75);
  private static Rectangle O3_BOX = new Rectangle(450, 1060, 700, 75);
  // ===========================================================================================


  /*
  BE SURE TO CHECK THIS FALSE IF TYLER - TRUE IF DAN.
 */
  public boolean isDan = true;


  /**
   * Constructs an instance of the hack controller.
   *
   * @param model the model
   * @param view  the view
   * @throws IllegalArgumentException if either of the inputs are null
   */
  public HackController(IHackModel model, IHackView view) throws IllegalArgumentException {
    Objects.requireNonNull(model);
    Objects.requireNonNull(view);
    this.model = model;
    this.view = view;
    this.currQuestion = "";
  }

  @Override
  public void run(String screenshotFolderPath) {
    this.view.addListeners(this);

    while (true) {
      List<File> files = this.changeAllScreenshotNamesToTrivia(screenshotFolderPath);
      if (files.size() > 0) {
        String result = IHackController.pictureToText(screenshotFolderPath + "/trivia.png");
        Trivia t = this.createTriviaFromOCR(result);
        if (!this.currQuestion.equals(t.getQuestion())) {
          System.out.println("Question is: " + t.getQuestion());
          this.currQuestion = t.getQuestion();
          System.out.println("Options are: " + t.getO1() + ", " + t.getO2() + ", " + t.getO3());
          IHackModel model = new HackModel(t);
          model.cheat(model.getTrivia().getStrat());
        }
      }

      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        System.out.println("sorry to interrupt this 100ms beauty sleep");
      }
    }
  }

  /**
   * Creates a piece of trivia with the output of tesseract OCR.
   *
   * @param result the output of OCR
   * @return the piece of trivia with arguments taken from OCR output
   */
  private Trivia createTriviaFromOCR(String result) {
    List<String> triviaBits = new ArrayList(Arrays.asList(result.split("\n")));
    List<String> triviaBitsNoWhiteSpace = new ArrayList();

    for (String s : triviaBits) {
      if (!s.isEmpty()) {
        triviaBitsNoWhiteSpace.add(s);
      }
    }

    int last = triviaBitsNoWhiteSpace.size();
    List<String> answersOnly = new ArrayList();
    answersOnly.add(triviaBitsNoWhiteSpace.get(last - 1));
    answersOnly.add(triviaBitsNoWhiteSpace.get(last - 2));
    answersOnly.add(triviaBitsNoWhiteSpace.get(last - 3));
    triviaBitsNoWhiteSpace.removeAll(answersOnly);
    String question = "";

    for (String s : triviaBitsNoWhiteSpace) {
      question = question + " " + s;
    }

    return new Trivia(question, answersOnly.get(0), answersOnly.get(1), answersOnly.get(2));
  }

  /**
   * Works for Dan only - similar logic should work for Tyler. <br> Gets the directory where you
   * have set your screenshots to save to by default. <br> Loop through this directory and whenever
   * you find a new screenshot saved to it, immediately rename it as "trivia.png," so the OCR can
   * keep searching for "trivia.png" to process.
   */
  private List<File> changeAllScreenshotNamesToTrivia(String screenshotFolderPath) {
    String screenshotRootFolder = screenshotFolderPath;
    File file = new File(screenshotRootFolder);
    List<File> files = new ArrayList(Arrays.asList(file.listFiles()));
    List<File> filesWeWant = new ArrayList();

    for (File f : files) {
      String name = f.getName();
      if (name.contains("png")) {
        filesWeWant.add(f);
        f.renameTo(new File(screenshotRootFolder + "/trivia.png"));
      }
    }
    return filesWeWant;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JButton button = (JButton) e.getSource();

    switch (button.getActionCommand()) {
      case "Max count":
        view.setResult(model.cheat(new Count(true)));
        break;
      case "Min count":
        view.setResult(model.cheat(new Count(false)));
        break;
      case "Quote search":
        view.setResult(model.cheat(new QuoteSearch()));
        break;
      case "Max count (question only)":
        view.setResult(model.cheat(new CountQuestionOnly(true)));
        break;
      case "Min count (question only)":
        view.setResult(model.cheat(new CountQuestionOnly(false)));
        break;
      default:
        throw new IllegalArgumentException("How did u press this");
    }
  }

  // ============================== Some nerd shit for Tyler only ==============================

  /*
  private void saveScreenshot(Rectangle bounds, String path) {
    try {
      Robot robot = new Robot();
      BufferedImage screenshot = robot.createScreenCapture(bounds);
      ImageIO.write(screenshot, "jpg", new File(path));
    } catch (AWTException | IOException ex) {
      ex.printStackTrace();
    }
  }

  private Trivia createTriviaTyler() {
    this.saveScreenshot(QUESTION_BOX, "C:\\Users\\tyler\\IntelliJWorkspace\\hq-hack\\hq-hack\\images\\question.jpg");
    this.saveScreenshot(O1_BOX, "C:\\Users\\tyler\\IntelliJWorkspace\\hq-hack\\hq-hack\\images\\o1.jpg");
    this.saveScreenshot(O2_BOX, "C:\\Users\\tyler\\IntelliJWorkspace\\hq-hack\\hq-hack\\images\\o2.jpg");
    this.saveScreenshot(O3_BOX, "C:\\Users\\tyler\\IntelliJWorkspace\\hq-hack\\hq-hack\\images\\o3.jpg");
    String q = IHackController.pictureToText("C:\\Users\\tyler\\IntelliJWorkspace\\hq-hack\\hq-hack\\images\\question.jpg").trim().replaceAll(" +", " ");
    String o1 = IHackController.pictureToText("C:\\Users\\tyler\\IntelliJWorkspace\\hq-hack\\hq-hack\\images\\o1.jpg").trim().replaceAll(" +", " ");
    String o2 = IHackController.pictureToText("C:\\Users\\tyler\\IntelliJWorkspace\\hq-hack\\hq-hack\\images\\o2.jpg").trim().replaceAll(" +", " ");
    String o3 = IHackController.pictureToText("C:\\Users\\tyler\\IntelliJWorkspace\\hq-hack\\hq-hack\\images\\o3.jpg").trim().replaceAll(" +", " ");
    return new Trivia(q, o1, o2, o3);
  }
  */
}
