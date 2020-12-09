package vehicleftl.model.pathfinding;

import vehicleftl.model.VehicleTileListener;

import java.util.ArrayList;
import java.util.List;

public class PathfindingTile implements Tile {

  private List<Tile> myAdjacentTiles;
  private int myX;
  private int myY;
  private List<TileListener> myListeners;
  private List<VehicleTileListener> myVehicleTileListeners;
  private boolean isTargeted;
  private boolean isOccupied;
  private Person myOccupant;

  public PathfindingTile(int x, int y) {
    myVehicleTileListeners = new ArrayList<>();
    myListeners = new ArrayList<>();
    myAdjacentTiles = new ArrayList<>();
    myX = x;
    myY = y;
  }

  @Override
  public void subscribe(TileListener listener) {
    myListeners.add(listener);
  }

  @Override
  public List<Tile> getAdjacentTiles() {
    return myAdjacentTiles;
  }

  @Override
  public void addAdjacentTile(Tile tile) {
    myAdjacentTiles.add(tile);
  }

  @Override
  public void removeAdjacentTile(Tile tile) {
    myAdjacentTiles.remove(tile);
  }

  @Override
  public int getX() {
    return myX;
  }

  @Override
  public int getY() {
    return myY;
  }

  @Override
  public void addOccupant(Person person) {
    myOccupant = person;
    myListeners.forEach(tileListener -> tileListener.reactToNewOccupant(person));
  }

  @Override
  public void removeOccupant(Person person, Tile newDestination) {
    myOccupant = null;
    myListeners.forEach(tileListener -> tileListener.reactToPersonRemoval(person,newDestination));
  }

  @Override
  public Person getOccupant() {
    return myOccupant;
  }

  @Override
  public boolean isTargeted() {
    return myOccupant != null;
  }

  @Override
  public void setOccupied(boolean occupied) {
    isOccupied = occupied;
  }

  @Override
  public boolean isOccupied() {
    return isOccupied;
  }

  @Override
  public boolean needsRepairs() {
    for (VehicleTileListener listener : myVehicleTileListeners) {
      if (listener.needsRepairs()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void repair(double repairAmount) {
    for (VehicleTileListener listener : myVehicleTileListeners) {
      listener.reactToRepairs(repairAmount);
    }
  }

  @Override
  public void operateTile(boolean operated) {
    for (VehicleTileListener listener : myVehicleTileListeners) {
      listener.reactToOperator(operated, this);
    }
  }

  @Override
  public void subscribeToVehicleTile(VehicleTileListener listener) {
    myVehicleTileListeners.add(listener);
  }
}
