package vehicleftl.visualizer;

import vehicleftl.model.Crewmate;
import vehicleftl.model.Room;
import vehicleftl.visualizer.interactiveelements.CrewVisualizer;
import vehicleftl.visualizer.interactiveelements.RoomVisualizer;
import vehicleftl.visualizer.interactiveelements.weaponvisualizer.WeaponInterfaceVisualizer;

public interface MouseBehavior {
  public void reactToRoomPrimary(Room room, RoomVisualizer visualizer);

  public void reactToRoomHover(Room room, RoomVisualizer visualizer);

  public void reactToRoomNothing(Room room, RoomVisualizer visualizer);

  public void reactToWeaponPrimary(WeaponInterfaceVisualizer visualizer);

  public void reactToWeaponSecondary(WeaponInterfaceVisualizer visualizer);

  void reactToWeaponHover(WeaponInterfaceVisualizer weaponVis);

  void reactToWeaponNothing(WeaponInterfaceVisualizer weaponVis);

  public void reactToNothingPrimary();

  public void subscribe(MouseListener listener);

  public void reactToCrewSecondary(Crewmate crewmate, CrewVisualizer crewVis);


  void reactToRoomSecondary(Room room, RoomVisualizer roomVis);

  void reactToEnemyRoomSecondary(Room room, RoomVisualizer roomVis);
}
