package vehicleftl.visualizer.interactiveelements;

import javafx.scene.Group;
import vehicleftl.model.Crewmate;

public interface CrewVisualizer extends InteractiveUIElement {
  public Crewmate getCrewmate();

  public void setSelected(boolean selected);
}
