import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class HackController implements ActionListener {

  private HackModel model;
  private HackView view;

  public HackController(HackModel model, HackView view) {
    this.model = model;
    this.view = view;
  }

  public void run() {
    this.view.addListeners(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    JButton button = (JButton) e.getSource();

    switch (button.getActionCommand()) {
      case "Max count":
        view.setResult(model.cheat(new MaxCount()));
        break;
      case "Min count":
        view.setResult(model.cheat(new MinCount()));
        break;
      case "Search type 3":

        break;
      default:
        throw new IllegalArgumentException("How the fuck did u press this");
    }
  }
}
