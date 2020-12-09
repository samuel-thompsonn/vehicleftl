package vehicleftl.model.pathfinding;

import vehicleftl.model.RoomListener;

import java.util.List;

public interface PathRoom {

  public List<Tile> getTiles();

  public boolean tileInBounds(int x, int y);

  public int getX();

  public int getY();

  public int getWidth();

  public int getHeight();

  public boolean isManned();

  Tile requestDestination(Person askingPerson);

  public void subscribe(RoomListener listener);
}
