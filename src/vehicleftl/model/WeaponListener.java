package vehicleftl.model;

public interface WeaponListener {
  public void reactToPowerChange(int newPower, int maxPower);
  public void reactToCharge(double charge, double maxCharge);
}
