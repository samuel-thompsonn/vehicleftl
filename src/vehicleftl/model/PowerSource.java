package vehicleftl.model;

public interface PowerSource {

  public boolean hasPower(int amount);

  public boolean takePower(int amount);

  public void givePower(int amount);

  public int getMaxPower();

  public void subscribeToPowerSource(PowerSourceListener listener);
}
