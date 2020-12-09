package vehicleftl.visualizer.mousebehavior;

import vehicleftl.model.Crewmate;
import vehicleftl.model.Room;
import vehicleftl.model.Weapon;
import vehicleftl.visualizer.CrewVisualizer;
import vehicleftl.visualizer.RoomVisualizer;
import vehicleftl.visualizer.WeaponInterfaceVisualizer;

public class SelectBehavior extends ObservableBehavior {
  @Override
  public void reactToRoomPrimary(Room room, RoomVisualizer visualizer) {
    //do nothing
  }

  @Override
  public void reactToWeaponSecondary(Weapon weapon, WeaponInterfaceVisualizer visualizer) {
    visualizer.setSelected(true);
    changeBehavior(new TargetingBehavior(weapon, visualizer));
  }

  @Override
  public void reactToWeaponPrimary(Weapon weapon, WeaponInterfaceVisualizer visualizer) {
    weapon.setPowered(!weapon.isPowered());
  }

  @Override
  public void reactToCrewSecondary(Crewmate crewmate, CrewVisualizer crewVis) {
    changeBehavior(new CommandingBehavior(crewmate,crewVis));
  }
}
