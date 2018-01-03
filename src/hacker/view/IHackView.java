package hacker.view;

import java.awt.event.ActionListener;

/**
 * Interface for all hacker views. As of 12/15/17, only one view implements this, which is the basic
 * view allowing for manual choosing of desired searching method.
 */
public interface IHackView {

  /**
   * Sets the action listeners for all the buttons in the view, to allow for user interaction.
   *
   * @param a action listener to be set
   * @throws IllegalArgumentException if the input is null
   */
  void addListeners(ActionListener a) throws IllegalArgumentException;

  /**
   * Sets the result as the output of the search, in order to be displayed.
   *
   * @param s the result (woohoo! we won!!)
   * @throws IllegalArgumentException if the input is null
   */
  void setResult(String s) throws IllegalArgumentException;

  /**
   * Sets System.out to the JTextArea in the view using a JTextAreaOutputStream definitely not
   * stolen from stackexchange.
   */
  void setOut();
}
