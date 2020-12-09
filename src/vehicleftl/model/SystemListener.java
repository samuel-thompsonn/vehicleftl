package vehicleftl.model;

public interface SystemListener {
  public void reactToPowerChange(int newPower);
  public void reactToDamageChange(int newDamage);
  public void reactToManned(boolean manned);
}
