package vehicleftl.visualizer.interactiveelements;

import javafx.scene.Group;

public interface TileVisualizer {
  public void reactToClick(double clickX, double clickY);

  public Group getGroup();
}
