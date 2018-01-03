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
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.swing.JButton;

public class HackController implements IHackController, ActionListener {
  private IHackModel model;
  private IHackView view;
  private static Rectangle QUESTION_BOX = new Rectangle(450, 350, 700, 330);
  private static Rectangle O1_BOX = new Rectangle(450, 730, 700, 75);
  private static Rectangle O2_BOX = new Rectangle(450, 900, 700, 75);
  private static Rectangle O3_BOX = new Rectangle(450, 1060, 700, 75);
  public boolean isDan = true;

  public HackController(IHackModel model, IHackView view) throws IllegalArgumentException {
    Objects.requireNonNull(model);
    Objects.requireNonNull(view);
    this.model = model;
    this.view = view;
  }

  public void run(String screenshotFolderPath) {
    this.view.addListeners(this);

    while(true) {
      List<File> files = this.changeAllScreenshotNamesToTrivia(screenshotFolderPath);
      if (files.size() > 0) {
        String result = IHackController.pictureToText(screenshotFolderPath + "/trivia.png");
        Trivia t = this.createTriviaFromOCR(result);
        System.out.println("Question is: " + t.getQuestion());
        System.out.println("Options are: " + t.getO1() + ", " + t.getO2() + ", " + t.getO3());
        IHackModel model = new HackModel(t);
        model.cheat(model.getTrivia().getStrat());
      }

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        System.out.println("sorry to interrupt this 100ms beauty sleep");
      }
    }
  }

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

  public void actionPerformed(ActionEvent e) {
    JButton button = (JButton)e.getSource();

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
}
