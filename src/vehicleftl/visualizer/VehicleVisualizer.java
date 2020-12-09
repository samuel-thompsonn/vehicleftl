package vehicleftl.visualizer;

import javafx.scene.Group;

import java.util.List;

public interface VehicleVisualizer {
  public Group getGroup();

  public List<RoomVisualizer> getRoomVisualizers();
}
