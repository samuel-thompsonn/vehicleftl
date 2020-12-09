package vehicleftl.visualizer.mousebehavior;

import vehicleftl.model.Crewmate;
import vehicleftl.model.Room;
import vehicleftl.model.Weapon;
import vehicleftl.visualizer.*;

import java.util.ArrayList;
import java.util.List;

public abstract class ObservableBehavior implements MouseBehavior {

  private List<MouseListener> myListeners;

  public ObservableBehavior() {
    myListeners = new ArrayList<>();
  }

  protected void changeBehavior(MouseBehavior behavior) {
    myListeners.forEach(listener -> listener.reactToNewBehavior(behavior));
  }

  @Override
  public void subscribe(MouseListener listener) {
    myListeners.add(listener);
  }

  @Override
  public void reactToCrewSecondary(Crewmate crewmate, CrewVisualizer crewVis) {
    //does nothing by default
  }

  @Override
  public void reactToWeaponHover(Weapon weapon, WeaponInterfaceVisualizer weaponVis) {
    //does nothing by default
  }

  @Override
  public void reactToWeaponNothing(Weapon weapon, WeaponInterfaceVisualizer weaponVis) {
    //does nothing by default
  }

  @Override
  public void reactToRoomSecondary(Room room, RoomVisualizer roomVis) {
    //does nothing by default
  }

  @Override
  public void reactToEnemyRoomSecondary(Room room, RoomVisualizer roomVis) {
    //does nothing by default
  }

  @Override
  public void reactToRoomPrimary(Room room, RoomVisualizer visualizer) {
    //does nothing by default
  }

  @Override
  public void reactToRoomHover(Room room, RoomVisualizer visualizer) {
    //does nothing by default
  }

  @Override
  public void reactToRoomNothing(Room room, RoomVisualizer visualizer) {
    //does nothing by default
  }

  @Override
  public void reactToWeaponPrimary(Weapon weapon, WeaponInterfaceVisualizer visualizer) {
    //does nothing by default
  }

  @Override
  public void reactToWeaponSecondary(Weapon weapon, WeaponInterfaceVisualizer visualizer) {
    //does nothing by default
  }

  @Override
  public void reactToNothingPrimary() {
    //does nothing by default
  }
}
