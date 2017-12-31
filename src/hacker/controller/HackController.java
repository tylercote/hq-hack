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

  public void run() {
    this.view.addListeners(this);

    while(true) {
      List<File> files = this.changeAllScreenshotNamesToTrivia();
      if (files.size() > 0) {
        String result = IHackController.pictureToText("/Users/Dan/Documents/HQScreenShots/trivia.png");
        Trivia t = this.createTriviaFromOCR(result);
        System.out.println("Question is: " + t.getQuestion());
        System.out.println("Options are: " + t.getO1() + ", " + t.getO2() + ", " + t.getO3());
        IHackModel model = new HackModel(t);
        model.cheat(model.getTrivia().getStrat());
      }

      try {
        Thread.sleep(1000L);
      } catch (InterruptedException var5) {
        System.out.println("sorry to interrupt this 100ms beauty sleep");
      }
    }
  }

  private Trivia createTriviaFromOCR(String result) {
    List<String> triviaBits = new ArrayList(Arrays.asList(result.split("\n")));
    List<String> triviaBitsNoWhiteSpace = new ArrayList();
    Iterator var4 = triviaBits.iterator();

    while(var4.hasNext()) {
      String s = (String)var4.next();
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

    String s;
    for(Iterator var7 = triviaBitsNoWhiteSpace.iterator(); var7.hasNext(); question = question + " " + s) {
      s = (String)var7.next();
    }

    return new Trivia(question, answersOnly.get(0), answersOnly.get(1), answersOnly.get(2));
  }

  private List<File> changeAllScreenshotNamesToTrivia() {
    String screenshotRootFolder = "/Users/Dan/Documents/HQScreenShots";
    File file = new File(screenshotRootFolder);
    List<File> files = new ArrayList(Arrays.asList(file.listFiles()));
    List<File> filesWeWant = new ArrayList();
    Iterator var5 = files.iterator();

    while(var5.hasNext()) {
      File f = (File)var5.next();
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
    String var3 = button.getActionCommand();
    byte var4 = -1;
    switch(var3.hashCode()) {
      case -2097300756:
        if (var3.equals("Min count (question only)")) {
          var4 = 4;
        }
        break;
      case -1998503810:
        if (var3.equals("Max count (question only)")) {
          var4 = 3;
        }
        break;
      case -114434068:
        if (var3.equals("Quote search")) {
          var4 = 2;
        }
        break;
      case -48918733:
        if (var3.equals("Max count")) {
          var4 = 0;
        }
        break;
      case 723559841:
        if (var3.equals("Min count")) {
          var4 = 1;
        }
    }

    switch(var4) {
      case 0:
        this.view.setResult(this.model.cheat(new Count(true)));
        break;
      case 1:
        this.view.setResult(this.model.cheat(new Count(false)));
        break;
      case 2:
        this.view.setResult(this.model.cheat(new QuoteSearch()));
        break;
      case 3:
        this.view.setResult(this.model.cheat(new CountQuestionOnly(true)));
        break;
      case 4:
        this.view.setResult(this.model.cheat(new CountQuestionOnly(false)));
        break;
      default:
        throw new IllegalArgumentException("How did u press this");
    }

  }
}
