package vehicleftl.visualizer.interactiveelements.weaponvisualizer;

import javafx.scene.Group;
import vehicleftl.model.Room;
import vehicleftl.visualizer.interactiveelements.ClickableUIElement;
import vehicleftl.visualizer.interactiveelements.InteractiveUIElement;
import vehicleftl.visualizer.interactiveelements.RoomVisualizer;
import vehicleftl.visualizer.interactiveelements.util.UserInputReaction;

import java.util.List;

public interface WeaponInterfaceVisualizer extends ClickableUIElement, InteractiveUIElement {
  public Group getGroup();

  public void setTarget(Room room, RoomVisualizer visualizer);

  boolean pointInBounds(double x, double y);

  void setSelected(boolean selected);

  String getWeaponId();

  String getElementType();

  String getStateInfo();
}
