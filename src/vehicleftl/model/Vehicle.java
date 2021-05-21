package vehicleftl.model;

import java.util.List;

public interface Vehicle extends RoomListener {

  public void update(double elapsedTime);

  public List<Room> getRooms();

  public List<ShipSystem> getSystems();

  public List<Crewmate> getCrew();

  public void subscribeToVehicle(VehicleListener listener);

  public PowerSource getPowerSource();

  public List<Weapon> getWeapons();
}
