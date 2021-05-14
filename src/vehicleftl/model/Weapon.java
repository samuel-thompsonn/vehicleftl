package vehicleftl.model;

public interface Weapon {
  public void setTarget(Room room);

  public void update(double elapsedTime);

  public void setPowered(boolean powered);

  public boolean isPowered();

  public int getLevel();

  public double getChargeTime();

  public void subscribe(WeaponListener listener);

  public String getID();
}
