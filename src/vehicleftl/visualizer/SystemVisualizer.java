package vehicleftl.visualizer;

import javafx.scene.Group;

public interface SystemVisualizer extends ClickableUIElement {
  public Group getGroup();

  public void increasePower();

  public void decreasePower();
}
