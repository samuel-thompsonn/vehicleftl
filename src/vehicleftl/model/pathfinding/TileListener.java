package vehicleftl.model.pathfinding;

public interface TileListener {
  public void reactToNewOccupant(Person newPerson);

  public void reactToPersonRemoval(Person removedPerson, Tile newDestination);
}
