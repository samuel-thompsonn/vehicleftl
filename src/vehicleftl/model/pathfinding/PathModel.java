package vehicleftl.model.pathfinding;

import java.util.List;

public interface PathModel {

  void update(double elapsedSeconds);

  List<Tile> getTiles();

  List<Person> getPeople();

  void selectTargetTile(double xPos, double yPos);
}
