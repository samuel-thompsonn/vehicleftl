package vehicleftl.model;

import vehicleftl.model.pathfinding.Tile;

public interface VehicleTileListener {
  public boolean needsRepairs();

  public void reactToRepairs(double repairAmount);

  public void reactToOperator(boolean operated, Tile operatedTile);
}
