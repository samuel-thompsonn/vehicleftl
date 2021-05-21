package vehicleftl.visualizer;

import javafx.scene.Group;
import vehicleftl.visualizer.interactiveelements.roomvisualizer.RoomVisualizer;

import java.util.List;

public interface VehicleVisualizer {
  public Group getGroup();

  public List<RoomVisualizer> getRoomVisualizers();
}
