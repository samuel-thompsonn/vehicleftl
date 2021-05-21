package vehicleftl.visualizer.interactiveelements.roomvisualizer;

import vehicleftl.model.Room;
import vehicleftl.model.RoomListener;
import vehicleftl.visualizer.interactiveelements.InteractiveUIElement;

public interface RoomVisualizer extends RoomListener, InteractiveUIElement {

  //May want to consider removing this, in case Rooms are the ones that should be selected.
  public Room getRoom();

  public double getCenterX();

  public double getCenterY();

}
