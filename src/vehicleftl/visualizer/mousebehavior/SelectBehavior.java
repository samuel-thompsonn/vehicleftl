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
    System.out.println(room.getID());
    //do nothing
  }

  @Override
  public void reactToWeaponSecondary(Weapon weapon, WeaponInterfaceVisualizer visualizer) {
    System.out.println(weapon.getID());
    visualizer.setSelected(true);
    changeBehavior(new TargetingBehavior(weapon, visualizer));
  }

  @Override
  public void reactToWeaponPrimary(Weapon weapon, WeaponInterfaceVisualizer visualizer) {
    System.out.println(weapon.getID());
    weapon.setPowered(!weapon.isPowered());
  }



  @Override
  public void reactToCrewSecondary(Crewmate crewmate, CrewVisualizer crewVis) {
    System.out.println(crewmate.getID());
    changeBehavior(new CommandingBehavior(crewmate,crewVis));
  }
}
