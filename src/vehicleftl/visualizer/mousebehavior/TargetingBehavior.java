package vehicleftl.visualizer.mousebehavior;

import vehicleftl.model.Room;
import vehicleftl.model.Weapon;
import vehicleftl.visualizer.RoomVisualizer;
import vehicleftl.visualizer.WeaponInterfaceVisualizer;

public class TargetingBehavior extends ObservableBehavior {

  private Weapon myWeapon;
  private WeaponInterfaceVisualizer myVisualizer;

  public TargetingBehavior(Weapon weapon, WeaponInterfaceVisualizer visualizer) {
    super();
    myWeapon = weapon;
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
  public void reactToWeaponPrimary(Weapon weapon, WeaponInterfaceVisualizer visualizer) {
    myVisualizer.setTarget(null,null);
    myVisualizer.setSelected(false);
    changeBehavior(new TargetingBehavior(weapon,visualizer));
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
