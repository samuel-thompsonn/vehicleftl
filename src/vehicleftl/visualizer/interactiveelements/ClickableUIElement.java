package vehicleftl.visualizer.interactiveelements;

import javafx.scene.Group;

public interface ClickableUIElement {
  public boolean pointInBounds(double x, double y);

  public Group getGroup();
}
