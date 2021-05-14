package vehicleftl.model;

import vehicleftl.model.pathfinding.PathRoom;

public interface Room extends PathRoom {

  public void hitWithWeapon(int damageAmount);

  //TODO: Consider removing this since repairing comes only from within.
  public void repair(double repairAmount);

  public void subscribe(RoomListener listener);

  public boolean isManned();

  public int getDamage();

  public int getLevel();

  public int getHeight();

  public int getWidth();

  public String getID();
}
