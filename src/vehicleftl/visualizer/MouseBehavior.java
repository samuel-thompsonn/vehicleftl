package vehicleftl.visualizer;

import vehicleftl.model.Crewmate;
import vehicleftl.model.Room;
import vehicleftl.model.Weapon;

public interface MouseBehavior {
  public void reactToRoomPrimary(Room room, RoomVisualizer visualizer);

  public void reactToRoomHover(Room room, RoomVisualizer visualizer);

  public void reactToRoomNothing(Room room, RoomVisualizer visualizer);

  public void reactToWeaponPrimary(Weapon weapon, WeaponInterfaceVisualizer visualizer);

  public void reactToWeaponSecondary(Weapon weapon, WeaponInterfaceVisualizer visualizer);

  public void reactToNothingPrimary();

  public void subscribe(MouseListener listener);

  public void reactToCrewSecondary(Crewmate crewmate, CrewVisualizer crewVis);

  void reactToWeaponHover(Weapon weapon, WeaponInterfaceVisualizer weaponVis);

  void reactToWeaponNothing(Weapon weapon, WeaponInterfaceVisualizer weaponVis);

  void reactToRoomSecondary(Room room, RoomVisualizer roomVis);

  void reactToEnemyRoomSecondary(Room room, RoomVisualizer roomVis);
}
