package vehicleftl.model.system;

import vehicleftl.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class VehicleSystem implements ShipSystem, RoomListener {

  private int myLevel;
  private int myDamage;
  private int myPower;
  private String myID;
  private List<SystemListener> myListeners;
  private Room myRoom;
  private PowerSource mySource;

  public VehicleSystem(Room room, PowerSource source) {
    myID = "System" + new Random().nextInt(10000);
    myListeners = new ArrayList<>();
    myLevel = room.getLevel();
    myPower = 0;
    myDamage = room.getDamage();
    myRoom = room;
    mySource = source;
    room.subscribe(this);
  }

  @Override
  public int getLevel() {
    return myLevel;
  }

  @Override
  public int getPower() {
    return myPower;
  }

  @Override
  public int getDamage() {
    return myDamage;
  }

  @Override
  public void setPower(int newPower) {
    newPower = Math.min(myLevel - myDamage, newPower);
    newPower = Math.max(newPower, 0);
    int powerChange = newPower - myPower;
    if (powerChange > 0) {
      if (!mySource.hasPower(powerChange)) {
        return;
      }
      mySource.takePower(powerChange);
    }
    else {
      mySource.givePower(-powerChange);
    }
    myPower = newPower;
    for (SystemListener listener : myListeners) {
      listener.reactToPowerChange(newPower);
    }
  }

  @Override
  public void update(double elapsedTime) {

  }

  @Override
  public boolean blockAttack(int damage) {
    return false;
  }

  @Override
  public void reactToDamage(int level, int damage) {
    myDamage = damage;
    setPower(myPower);
    for (SystemListener listener : myListeners) {
      listener.reactToDamageChange(damage);
    }
  }

  @Override
  public void subscribe(SystemListener listener) {
    myListeners.add(listener);
    listener.reactToDamageChange(myDamage);
    listener.reactToPowerChange(myPower);
  }

  @Override
  public String getID() {
    return myID;
  }

  @Override
  public void reactToManned(boolean manned) {
    myListeners.forEach(listener -> listener.reactToManned(manned));
  }
}
