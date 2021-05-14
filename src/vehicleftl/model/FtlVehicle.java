package vehicleftl.model;

import vehicleftl.model.pathfinding.PathModel;
import vehicleftl.model.pathfinding.PathfindingModel;
import vehicleftl.model.pathfinding.Person;
import vehicleftl.model.pathfinding.Tile;
import vehicleftl.model.system.ShieldSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FtlVehicle implements Vehicle, PowerSource, PathModel {

  private int myShieldLevel;
  private double myCurrentShield;
  private double myShieldRecharge;
  private List<Room> myRooms;
  private List<VehicleListener> myListeners;
  private List<PowerSourceListener> myReactorListeners;
  private List<Weapon> myWeapons;
  private List<ShipSystem> mySystems;
  private List<Crewmate> myCrew;
  private int myPower;
  private int myMaxPower;
  private PathModel myPathModel;

  public FtlVehicle() {
    myListeners = new ArrayList();
    myReactorListeners = new ArrayList<>();
    myShieldRecharge = 1;
    myRooms = initRooms();
    myPathModel = new PathfindingModel(6,2,myRooms);

    myCrew = new ArrayList<>();
    for (Person person : getPeople()) {
      myCrew.add(new VehicleCrewmate(person));
    }

    myWeapons = initWeapons();
    mySystems = initSystems(myRooms);
    myShieldLevel = findShieldLevel();
    myCurrentShield = myShieldLevel;
    myMaxPower = 5;
    myPower = myMaxPower;
  }

  private List<Room> initRooms() {
    List<Room> roomList = new ArrayList<>();
    for (int i = 1; i <= 3; i ++) {
      Room newRoom = new VehicleRoom(i*2,2,2,(i-1)*2,0);
      newRoom.subscribe(this);
      roomList.add(newRoom);
    }
    return roomList;
  }

  private List<ShipSystem> initSystems(List<Room> rooms) {
    List<ShipSystem> systems = new ArrayList<>();
    for (Room room : rooms) {
      ShipSystem system = new ShieldSystem(room,this);
      systems.add(system);
    }
    return systems;
  }

  private List<Weapon> initWeapons() {
    List<Weapon> weaponList = new ArrayList<>();
    VehicleWeapon weapon = new VehicleWeapon(1,1,2,this);
    weaponList.add(weapon);
    return weaponList;
  }

  @Override
  public void update(double elapsedTime) {
    updateShields(elapsedTime);
    for (Weapon weapon : myWeapons) {
      weapon.update(elapsedTime);
    }
    for (Crewmate crewmate : myCrew) {
      crewmate.update(elapsedTime);
    }
    myPathModel.update(elapsedTime);
  }

  @Override
  public List<Tile> getTiles() {
    return myPathModel.getTiles();
  }

  private int findShieldLevel() {
    int shieldLevel = 0;
    for (ShipSystem system : mySystems) {
      shieldLevel = Math.max(system.getShieldLevel(),shieldLevel);
    }
    return shieldLevel;
  }

  private void updateShields(double elapsedTime) {
    myCurrentShield += elapsedTime * myShieldRecharge;
    myShieldLevel = findShieldLevel();
    myCurrentShield = Math.min(myShieldLevel,myCurrentShield);
    for (VehicleListener listener : myListeners) {
      listener.reactToShieldChange(myCurrentShield,myShieldLevel);
    }
  }

  @Override
  public List<Room> getRooms() {
    return myRooms;
  }

  @Override
  public List<Person> getPeople() {
    return myPathModel.getPeople();
  }

  @Override
  public void selectTargetTile(double xPos, double yPos) {
    myPathModel.selectTargetTile(xPos,yPos);
  }

  @Override
  public List<ShipSystem> getSystems() {
    return mySystems;
  }

  @Override
  public List<Crewmate> getCrew() {
    return myCrew;
  }

  @Override
  public void subscribe(VehicleListener listener) {
    myListeners.add(listener);
    listener.reactToShieldChange(myCurrentShield,myShieldLevel);
  }

  @Override
  public PowerSource getPowerSource() {
    return this;
  }

  @Override
  public List<Weapon> getWeapons() {
    return myWeapons;
  }

  @Override
  public boolean blockAttack(int damage) {
    System.out.println("Blocking attack of " + damage + " damage with a shield of " + myCurrentShield);
    boolean attackBlocked = myCurrentShield != 0;
    myCurrentShield = Math.max(myCurrentShield - damage, 0);
    for (VehicleListener listener : myListeners) {
      listener.reactToShieldChange(myCurrentShield,myShieldLevel);
    }
    if (attackBlocked) {
      System.out.println("Blocked an attack of " + damage + " damage.");
    }
    return attackBlocked;
  }

  @Override
  public void reactToDamage(int level, int damage) {
  }

  @Override
  public boolean hasPower(int amount) {
    return myPower >= amount;
  }

  @Override
  public boolean takePower(int amount) {
    boolean canGivePower = hasPower(amount);
    if (canGivePower) {
      setPower(myPower - amount);
    }
    return canGivePower;
  }

  @Override
  public void givePower(int amount) {
    System.out.println("REACTOR IS GAINING" + amount + " POWER");
    setPower(Math.min(myPower + amount, myMaxPower));
  }

  private void setPower(int amount) {
    myPower = amount;
    myReactorListeners.forEach(listener -> listener.reactToPowerChange(myPower));
  }

  @Override
  public int getMaxPower() {
    return myMaxPower;
  }

  @Override
  public void subscribeToPowerSource(PowerSourceListener listener) {
    myReactorListeners.add(listener);
    listener.reactToPowerChange(myPower);
  }

}
