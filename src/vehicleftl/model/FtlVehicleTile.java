package vehicleftl.model;

import vehicleftl.model.pathfinding.PathfindingTile;
import vehicleftl.model.pathfinding.Person;
import vehicleftl.model.pathfinding.Tile;
import vehicleftl.model.pathfinding.TileListener;

import java.util.List;

public class FtlVehicleTile implements VehicleTile {

  private Tile myPathfindingTile;
  private VehicleTileListener myListener;

  public FtlVehicleTile(int x, int y) {
    myPathfindingTile = new PathfindingTile(x,y);
    myListener = null;
  }

  @Override
  public boolean needsRepairs() {
    return false;
  }

  @Override
  public void repair(double repairAmount) {

  }

  @Override
  public void operateTile(boolean operated) {

  }

  @Override
  public void subscribeToVehicleTile(VehicleTileListener listener) {
    myListener = listener;
  }

  @Override
  public void subscribe(TileListener listener) {
    myPathfindingTile.subscribe(listener);
  }

  @Override
  public List<Tile> getAdjacentTiles() {
    return myPathfindingTile.getAdjacentTiles();
  }

  @Override
  public void addAdjacentTile(Tile tile) {
    myPathfindingTile.addAdjacentTile(tile);
  }

  @Override
  public void removeAdjacentTile(Tile tile) {
    myPathfindingTile.removeAdjacentTile(tile);
  }

  @Override
  public int getX() {
    return myPathfindingTile.getX();
  }

  @Override
  public int getY() {
    return myPathfindingTile.getY();
  }

  @Override
  public void addOccupant(Person person) {
    myPathfindingTile.addOccupant(person);
  }

  @Override
  public void removeOccupant(Person person, Tile newDestination) {
    myPathfindingTile.removeOccupant(person,newDestination);
  }

  @Override
  public Person getOccupant() {
    return myPathfindingTile.getOccupant();
  }

  @Override
  public boolean isTargeted() {
    return myPathfindingTile.isTargeted();
  }

  @Override
  public void setOccupied(boolean occupied) {
    myPathfindingTile.setOccupied(occupied);
  }

  @Override
  public boolean isOccupied() {
    return myPathfindingTile.isOccupied();
  }
}
