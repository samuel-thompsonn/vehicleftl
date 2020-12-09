package vehicleftl.model.pathfinding;

public interface Person {
  Tile getTarget();

  void select();

  void deselect();

  boolean isSelected();

  void setCurrentTile(Tile tile);

  void subscribe(PersonListener listener);

  void setTargetTile(Tile targetTile);

  void update(double elapsedSeconds);

  double getX();

  double getY();

  void setPosition(double x, double y);

  boolean equals(Person person);

  boolean isMoving();
}
