package hacker;

import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * Thank you stackexchange.
 */
public class JTextAreaOutputStream extends OutputStream {

  private final JTextArea destination;

  /**
   * Creates a new instance of JTextAreaOutputStream which allows System.out to be set to the view's
   * JTextArea.
   *
   * @param destination the JTextArea to be set as the output stream
   */
  public JTextAreaOutputStream (JTextArea destination)
  {
    if (destination == null)
      throw new IllegalArgumentException ("Destination is null");

    this.destination = destination;
  }

  @Override
  public void write(byte[] buffer, int offset, int length) throws IOException
  {
    final String text = new String (buffer, offset, length);
    SwingUtilities.invokeLater(new Runnable ()
    {
      @Override
      public void run()
      {
        destination.append (text);
      }
    });
  }

  @Override
  public void write(int b) throws IOException
  {
    write (new byte [] {(byte)b}, 0, 1);
  }
}
