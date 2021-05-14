package vehicleftl.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VehicleWeapon implements Weapon {
  private PowerSource mySource;
  private int myLevel;
  private int myDamage;
  private boolean isPowered;
  private double myChargeTime;
  private double myCurrentCharge;
  private Room myTarget;
  private List<WeaponListener> myListeners;
  private String myID;

  public VehicleWeapon(int damage, int level, double chargeTime, PowerSource source) {
    myID = "Weapon" + new Random().nextInt(10000);
    mySource = source;
    myDamage = damage;
    myLevel = level;
    myTarget = null;
    myChargeTime = chargeTime;
    myListeners = new ArrayList<>();
  }

  @Override
  public void setTarget(Room room) {
    myTarget = room;
  }

  @Override
  public void update(double elapsedTime) {
    if (!isPowered) {
      setCharge(0);
      return;
    }
    setCharge(Math.min(myCurrentCharge + elapsedTime, myChargeTime));
    if (myCurrentCharge >= myChargeTime && myTarget != null) {
      setCharge(0);
      myTarget.hitWithWeapon(myDamage);
    }
  }

  private void setCharge(double charge) {
    myCurrentCharge = charge;
    myListeners.forEach(weaponListener -> weaponListener.reactToCharge(myCurrentCharge,myChargeTime));
  }

  @Override
  public void setPowered(boolean powered) {
    if (powered && mySource.hasPower(myLevel)) {
      mySource.takePower(myLevel);
      isPowered = true;
    }
    else if (!powered) {
      mySource.givePower(myLevel);
      isPowered = false;
    }
    int poweredAmount = (powered)? myLevel : 0;
    myListeners.forEach(weaponListener -> weaponListener.reactToPowerChange(poweredAmount,myLevel));
  }

  @Override
  public boolean isPowered() {
    return isPowered;
  }

  @Override
  public int getLevel() {
    return myLevel;
  }

  @Override
  public double getChargeTime() {
    return myChargeTime;
  }

  @Override
  public void subscribe(WeaponListener listener) {
    myListeners.add(listener);
    listener.reactToPowerChange((isPowered)? myLevel : 0, myLevel);
    listener.reactToCharge(myCurrentCharge,myChargeTime);
  }

  @Override
  public String getID() {
    return myID;
  }
}
