package vehicleftl.visualizer;

import javafx.scene.Group;
import vehicleftl.model.Crewmate;

public interface CrewVisualizer {
  public boolean pointInBounds(double x, double y);

  public Group getGroup();

  public Crewmate getCrewmate();

  public void setSelected(boolean selected);
}
