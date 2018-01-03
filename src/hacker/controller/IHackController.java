package hacker.controller;

import java.io.File;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * Interface for all hacker controllers. Only one basic controller implements this, which allows for
 * user interaction view action events triggered on buttons, representing desired searching
 * methods.
 */
public interface IHackController {

  /**
   * Runs the program. Takes in a file path where the screenshots taken of the game are saved to -
   * this is the folder that the program will repeatedly scan for new files. Make sure this folder
   * is empty before starting.
   *
   * @param screenshotFolderPath the pathname of the screenshot folder
   */
  void run(String screenshotFolderPath);

  /**
   * Extracts string from a picture.
   *
   * @param filepath the filepath of the picture
   * @return the picture rendered as a string using tesseract OCR
   */
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
