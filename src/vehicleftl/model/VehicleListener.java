package vehicleftl.model;

public interface VehicleListener {

  public void reactToShieldChange(double shieldAmount, int maxShield);

  public void reactToHullPointChange(int currentHull, int maxHull);

}
