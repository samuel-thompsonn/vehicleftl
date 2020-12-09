package vehicleftl.model.pathfinding;

import vehicleftl.model.VehicleTileListener;

import java.util.List;

public interface Tile {
  void subscribe(TileListener listener);

  List<Tile> getAdjacentTiles();

  void addAdjacentTile(Tile tile);

  void removeAdjacentTile(Tile tile);

  int getX();

  int getY();

  void addOccupant(Person person);

  void removeOccupant(Person person, Tile newDestination);

  Person getOccupant();

  boolean isTargeted();

  void setOccupied(boolean occupied);

  boolean isOccupied();

  public boolean needsRepairs();

  public void repair(double repairAmount);

  public void operateTile(boolean operated);

  public void subscribeToVehicleTile(VehicleTileListener listener);
}
