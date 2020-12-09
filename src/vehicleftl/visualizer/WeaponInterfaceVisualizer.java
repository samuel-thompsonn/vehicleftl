package vehicleftl.visualizer;

import javafx.scene.Group;
import vehicleftl.model.Room;
import vehicleftl.model.Weapon;

public interface WeaponInterfaceVisualizer {
  public Group getGroup();

  public void setTarget(Room room, RoomVisualizer visualizer);

  boolean pointInBounds(double x, double y);

  Weapon getWeapon();

  void setSelected(boolean selected);

  boolean getSelected();
}
