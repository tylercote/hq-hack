import java.awt.event.ActionListener;

import javax.swing.*;

public class HackView extends JFrame {

  JPanel mainPanel;
  JButton search1;
  JButton search2;
  JButton search3;
  JLabel results;

  public HackView() {
    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    search1 = new JButton("Max count");
    search2 = new JButton("Min count");
    search3 = new JButton("Search type 3");
    results = new JLabel("Results here");

    mainPanel.add(search1);
    mainPanel.add(search2);
    mainPanel.add(search3);
    mainPanel.add(results);

    this.add(mainPanel);
    this.pack();
    this.setVisible(true);
  }

  public void addListeners(ActionListener a) {
    search1.addActionListener(a);
    search2.addActionListener(a);
    search3.addActionListener(a);
  }

  public void setResult(String s) {
    this.results.setText(s);
  }
}
