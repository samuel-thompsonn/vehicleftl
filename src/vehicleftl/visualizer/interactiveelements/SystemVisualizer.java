package vehicleftl.visualizer.interactiveelements;

import javafx.scene.Group;
import vehicleftl.visualizer.interactiveelements.ClickableUIElement;
import vehicleftl.visualizer.interactiveelements.InteractiveUIElement;

public interface SystemVisualizer extends ClickableUIElement, InteractiveUIElement {
  public Group getGroup();

  public void increasePower();

  public void decreasePower();
}
