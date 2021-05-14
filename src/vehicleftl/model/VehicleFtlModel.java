package vehicleftl.model;

import vehicleftl.model.*;

import java.util.HashMap;
import java.util.Map;

public class VehicleFtlModel implements ModelExternal {

  private Map<String, Crewmate> myCrewmateIDs;
  private Map<String, Room> myRoomIDs;
  private Map<String, Weapon> myWeaponIDs;
  private Map<String, ShipSystem> mySystemIDs;

  public VehicleFtlModel() {
    myCrewmateIDs = new HashMap<>();
    myRoomIDs = new HashMap<>();
    myWeaponIDs = new HashMap<>();
    mySystemIDs = new HashMap<>();
  }

  @Override
  public void assignCrewmateToRoom(String crewID, String roomID) {
    Crewmate crew = myCrewmateIDs.get(crewID);
    Room targetRoom = myRoomIDs.get(roomID);
    if (crew == null || targetRoom == null) {
      return;
    }
    crew.setTargetTile(targetRoom.requestDestination(crew));
  }

  @Override
  public void targetWeaponToRoom(String weaponID, String roomID) {
    Weapon weapon = myWeaponIDs.get(weaponID);//This one makes no sense at all! I shouldn't target my own room!
    Room targetRoom = myRoomIDs.get(roomID);
    if (weapon == null || targetRoom == null) {
      return;
    }
    weapon.setTarget(targetRoom);
  }

  @Override
  public void changeSystemPower(String systemID, int powerChange) {
    ShipSystem system = mySystemIDs.get(systemID);
    if (system == null) {
      return;
    }
    system.setPower(system.getPower()+powerChange);
  }

  @Override
  public void changeWeaponPower(String weaponID, boolean powered) {
    Weapon weapon = myWeaponIDs.get(weaponID);
    if (weapon == null) {
      return;
    }
    weapon.setPowered(powered);
  }

  @Override
  public void addVehicle(Vehicle vehicle) {
    for (Crewmate crewmate : vehicle.getCrew()) {
      myCrewmateIDs.put(crewmate.getID(),crewmate);
    }
    for (Room room : vehicle.getRooms()) {
      myRoomIDs.put(room.getID(),room);
    }
    for (Weapon weapon : vehicle.getWeapons()) {
      myWeaponIDs.put(weapon.getID(),weapon);
    }
    for (ShipSystem system : vehicle.getSystems()) {
      mySystemIDs.put(system.getID(),system);
    }
  }
}
