package vehicleftl.visualizer;

import javafx.scene.Group;
import vehicleftl.model.Crewmate;
import vehicleftl.model.Vehicle;
import vehicleftl.model.Weapon;
import vehicleftl.visualizer.interactiveelements.CrewVisualizer;
import vehicleftl.visualizer.interactiveelements.VehicleCrewVisualizer;
import vehicleftl.visualizer.interactiveelements.VehicleWeaponInterface;
import vehicleftl.visualizer.interactiveelements.WeaponInterfaceVisualizer;

import java.util.List;

public class FriendlyFullVisualizer {
  private VehicleVisualizer myVehicleVisualizer;
  private SystemsTray mySystemsTray;
  private List<WeaponInterfaceVisualizer> myWeaponVisualizers;
  private Group myGroup;
  private MouseBehavior myMouseBehavior;

  public FriendlyFullVisualizer(Vehicle vehicle, double x, double y) {
    myGroup = new Group();
    myVehicleVisualizer = new FtlVehicleVisualizer(vehicle, x, y);
    myGroup.getChildren().add(myVehicleVisualizer.getGroup());
    mySystemsTray = new VehicleSystemsTray(vehicle,x,y+200);
    myGroup.getChildren().add(mySystemsTray.getGroup());
    for (Weapon weapon : vehicle.getWeapons()) {
      WeaponInterfaceVisualizer weaponVis = new VehicleWeaponInterface(weapon,x+200,y+200);
      myWeaponVisualizers.add(weaponVis);
      myGroup.getChildren().add(weaponVis.getGroup());
    }
    for (Crewmate crewmate : vehicle.getCrew()) {
      CrewVisualizer crewVis = new VehicleCrewVisualizer(x,y,crewmate);
      myGroup.getChildren().add(crewVis.getGroup());
    }
  }
}
