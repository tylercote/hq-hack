package hacker.view;

import java.awt.event.ActionListener;

public interface IHackView {
  void addListeners(ActionListener a) throws IllegalArgumentException;

  void setResult(String s) throws IllegalArgumentException;

  void setOut();
}
