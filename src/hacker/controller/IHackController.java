package hacker.controller;

import java.io.File;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public interface IHackController {
  void run(String screenshotFolderPath);

  static String pictureToText(String filepath) {
    ITesseract instance = new Tesseract();
    instance.setDatapath("/Users/Dan/Documents/HQTrivia/Tess4J");
    File f = new File(filepath);
    String result = "";

    try {
      result = instance.doOCR(f);
      return result;
    } catch (TesseractException e) {
      System.err.println(e.getMessage());
      return result;
    }
  }
}
