package vehicleftl.visualizer.mousebehavior;

import vehicleftl.model.Crewmate;
import vehicleftl.model.Room;
import vehicleftl.model.Weapon;
import vehicleftl.visualizer.CrewVisualizer;
import vehicleftl.visualizer.MouseBehavior;
import vehicleftl.visualizer.RoomVisualizer;
import vehicleftl.visualizer.WeaponInterfaceVisualizer;

public class CommandingBehavior extends ObservableBehavior {

  private Crewmate myCrewmate;
  private CrewVisualizer myVisualizer;

  //This is about to change.
  public CommandingBehavior(Crewmate crewmate, CrewVisualizer visualizer) {
    myCrewmate = crewmate;
    myVisualizer = visualizer;
    myVisualizer.setSelected(true);
  }

  @Override
  public void reactToRoomPrimary(Room room, RoomVisualizer visualizer) {
    myCrewmate.setTargetTile(room.requestDestination(myCrewmate));
  }

  @Override
  public void reactToWeaponSecondary(Weapon weapon, WeaponInterfaceVisualizer visualizer) {
    visualizer.setSelected(true);
    myVisualizer.setSelected(false);
    changeBehavior(new TargetingBehavior(weapon, visualizer));
  }

  @Override
  public void reactToCrewSecondary(Crewmate crewmate, CrewVisualizer crewVis) {
    myVisualizer.setSelected(false);
    changeBehavior(new CommandingBehavior(crewmate,crewVis));
  }
}
