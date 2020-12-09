package vehicleftl.model.system;

import vehicleftl.model.PowerSource;
import vehicleftl.model.Room;
import vehicleftl.model.system.VehicleSystem;

public class ShieldSystem extends VehicleSystem {
  public ShieldSystem(Room room, PowerSource source) {
    super(room, source);
  }

  @Override
  public int getShieldLevel() {
    return (getPower()) / 2;
  }

  @Override
  public double getDodgeChance() {
    return 0;
  }
}
