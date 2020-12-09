package vehicleftl.visualizer;

import javafx.scene.Group;
import vehicleftl.model.Room;
import vehicleftl.model.RoomListener;

public interface RoomVisualizer extends RoomListener {

  public Group getGroup();

  //May want to consider removing this, in case Rooms are the ones that should be selected.
  public Room getRoom();

  public boolean isSelected();

  public boolean pointInBounds(double x, double y);

  public void setHighlighted(boolean highlighted);

  public double getCenterX();

  public double getCenterY();

}
