import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
 
public class RoundButton extends JButton {
 
  public RoundButton(String label) {
    super(label);
 
    setFocusable(false);

    Dimension dimensions = getPreferredSize();
    dimensions.width = dimensions.height = Math.max(dimensions.width, dimensions.height);
    setPreferredSize(dimensions);

    setContentAreaFilled(false);
  }

  protected void paintComponent(Graphics g) {
    if (getModel().isArmed()) {
      g.setColor(Color.gray);
    } else {
      g.setColor(getBackground());
    }
    g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);

    super.paintComponent(g);
  }
 
  protected void paintBorder(Graphics g) {
    g.setColor(Color.darkGray);
    g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
  }
 
  // Hit detection.
  Shape shape;
 
  public boolean contains(int x, int y) {
    // If the button has changed dimensions,  make a new shape object.
    if (shape == null || !shape.getBounds().equals(getBounds())) {
      shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
    }
    return shape.contains(x, y);
  }

}