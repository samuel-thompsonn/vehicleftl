package vehicleftl.model.pathfinding;

import java.util.List;

public interface PersonListener {
  public void reactToMove(double currX, double currY, Tile previousTarget);

  public void reactToNewPath(List<Tile> path, Person modifiedPerson);
}
