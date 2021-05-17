package vehicleftl.visualizer.mousebehavior;

import vehicleftl.model.Crewmate;
import vehicleftl.model.Room;
import vehicleftl.visualizer.interactiveelements.CrewVisualizer;
import vehicleftl.visualizer.interactiveelements.RoomVisualizer;
import vehicleftl.visualizer.interactiveelements.weaponvisualizer.WeaponInterfaceVisualizer;

public class SelectBehavior extends ObservableBehavior {
  @Override
  public void reactToRoomPrimary(Room room, RoomVisualizer visualizer) {
    System.out.println(room.getID());
    //do nothing
  }

  @Override
  public void reactToWeaponSecondary(WeaponInterfaceVisualizer visualizer) {
    System.out.println(visualizer.getWeaponId());
    visualizer.setSelected(true);
    changeBehavior(new TargetBehavior(visualizer));
  }

  @Override
  public void reactToWeaponPrimary(WeaponInterfaceVisualizer visualizer) {
    System.out.println(visualizer.getWeaponId());
//    weapon.setPowered(!weapon.isPowered());
    System.out.println("Toggle power of " + visualizer.getWeaponId());
  }

  @Override
  public void reactToCrewSecondary(Crewmate crewmate, CrewVisualizer crewVis) {
    System.out.println(crewmate.getID());
    changeBehavior(new CommandBehavior(crewmate,crewVis));
  }
}
