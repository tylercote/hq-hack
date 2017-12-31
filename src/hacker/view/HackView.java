//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package hacker.view;

import hacker.JTextAreaOutputStream;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.Objects;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HackView extends JFrame implements IHackView {
  private JPanel mainPanel = new JPanel();
  private JTextArea text;
  private JButton search1;
  private JButton search2;
  private JButton search3;
  private JButton search4;
  private JButton search5;
  private JLabel results;

  public HackView() {
    this.mainPanel.setLayout(new BoxLayout(this.mainPanel, 3));
    JPanel p = new JPanel();
    p.setPreferredSize(new Dimension(1000, 500));
    p.add(new JLabel("RUNNING!!"));
    this.text = new JTextArea();
    this.text.setLineWrap(true);
    JScrollPane scroll = new JScrollPane(this.text);
    scroll.setVerticalScrollBarPolicy(20);
    scroll.setPreferredSize(new Dimension(1000, 450));
    p.add(scroll);
    this.mainPanel.add(p);
    this.setDefaultCloseOperation(3);
    this.add(this.mainPanel);
    this.pack();
    this.setVisible(true);
  }

  public void addListeners(ActionListener a) {
  }

  public void setResult(String s) {
    Objects.requireNonNull(s, "Result string cannot be null.");
    this.results.setText(s);
  }

  public void setOut() {
    JTextAreaOutputStream out = new JTextAreaOutputStream(this.text);
    System.setOut(new PrintStream(out));
  }
}
