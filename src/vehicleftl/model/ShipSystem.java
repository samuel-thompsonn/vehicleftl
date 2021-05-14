package vehicleftl.model;

public interface ShipSystem {
  public int getLevel();

  public int getPower();

  public int getDamage();

  public void setPower(int newPower);

  public void update(double elapsedTime);

  public boolean blockAttack(int damage);

  public void subscribe(SystemListener listener);

  public int getShieldLevel();

  public double getDodgeChance();

  public String getID();
}
