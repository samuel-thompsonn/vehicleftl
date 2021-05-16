package vehicleftl.visualizer.mousebehavior;

import vehicleftl.model.Crewmate;
import vehicleftl.model.Room;
import vehicleftl.visualizer.interactiveelements.CrewVisualizer;
import vehicleftl.visualizer.interactiveelements.RoomVisualizer;
import vehicleftl.visualizer.interactiveelements.WeaponInterfaceVisualizer;

public class CommandBehavior extends ObservableBehavior {

  private Crewmate myCrewmate;
  private CrewVisualizer myVisualizer;

  //This is about to change.
  public CommandBehavior(Crewmate crewmate, CrewVisualizer visualizer) {
    myCrewmate = crewmate;
    myVisualizer = visualizer;
    myVisualizer.setSelected(true);
  }

  @Override
  public void reactToRoomPrimary(Room room, RoomVisualizer visualizer) {
    myCrewmate.setTargetTile(room.requestDestination(myCrewmate));
  }

  @Override
  public void reactToWeaponSecondary(WeaponInterfaceVisualizer visualizer) {
    visualizer.setSelected(true);
    myVisualizer.setSelected(false);
    changeBehavior(new TargetBehavior(visualizer));
  }

  @Override
  public void reactToCrewSecondary(Crewmate crewmate, CrewVisualizer crewVis) {
    myVisualizer.setSelected(false);
    changeBehavior(new CommandBehavior(crewmate,crewVis));
  }
}
