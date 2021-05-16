package vehicleftl.visualizer.interactiveelements;

import javafx.scene.Group;
import vehicleftl.model.Crewmate;

public interface CrewVisualizer extends InteractiveUIElement {
  public boolean pointInBounds(double x, double y);

  public Group getGroup();

  public Crewmate getCrewmate();

  public void setSelected(boolean selected);
}
