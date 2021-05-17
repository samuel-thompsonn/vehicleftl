package vehicleftl.visualizer.mousebehavior;

import vehicleftl.model.Room;
import vehicleftl.visualizer.interactiveelements.RoomVisualizer;
import vehicleftl.visualizer.interactiveelements.weaponvisualizer.WeaponInterfaceVisualizer;

public class TargetBehavior extends ObservableBehavior {

  private String myWeaponId;
  private WeaponInterfaceVisualizer myVisualizer;

  public TargetBehavior(WeaponInterfaceVisualizer visualizer) {
    super();
    myWeaponId = visualizer.getWeaponId();
    myVisualizer = visualizer;
    myVisualizer.setSelected(true);
  }

  @Override
  public void reactToEnemyRoomSecondary(Room room, RoomVisualizer visualizer) {
//    myWeapon.setTarget(room);
    myVisualizer.setTarget(room,visualizer);
    myVisualizer.setSelected(false);
    visualizer.setHighlighted(false);
    changeBehavior(new SelectBehavior());
  }

  @Override
  public void reactToWeaponPrimary(WeaponInterfaceVisualizer visualizer) {
    myVisualizer.setTarget(null,null);
    myVisualizer.setSelected(false);
    changeBehavior(new TargetBehavior(visualizer));
  }

  @Override
  public void reactToNothingPrimary() {
    myVisualizer.setTarget(null,null);
    myVisualizer.setSelected(false);
    changeBehavior(new SelectBehavior());
  }

  @Override
  public void reactToRoomHover(Room room, RoomVisualizer visualizer) {
    visualizer.setHighlighted(true);
  }

  @Override
  public void reactToRoomNothing(Room room, RoomVisualizer visualizer) {
    visualizer.setHighlighted(false);
  }
}
