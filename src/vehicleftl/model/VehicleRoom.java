package vehicleftl.model;

import vehicleftl.model.pathfinding.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VehicleRoom implements Room, PathRoom, VehicleTileListener {

  private List<RoomListener> myListeners;
  private int myLevel;
  private double myRepairProgress;
  private int myDamage;
  private boolean isManned;
  private int myWidth;
  private int myHeight;
  private PathRoom myPathRoom;
  private String myID;
  private boolean isTargeted;

  public VehicleRoom(int level, int width, int height, int x, int y) {
    myRepairProgress = 0;
    myID = "Room" + new Random().nextInt(4000);

    Tile[][] tiles = new Tile[width][height];
    for (int i = 0; i < width; i ++) {
      for (int j = 0; j < height; j ++) {
        Tile newTile = new PathfindingTile(x+i,y+j);
        tiles[i][j] = newTile;
        newTile.subscribeToVehicleTile(this);
      }
    }

    myPathRoom = new PathfindingRoom(width,height,x,y,tiles);
    myWidth = width;
    myHeight = height;
    myListeners = new ArrayList<>();
    myLevel = level;
    isManned = false;
    myDamage = 0;
  }

  @Override
  public void hitWithWeapon(int damageAmount) {
    for (RoomListener listener : myListeners) {
      if (listener.blockAttack(damageAmount)) {
        return;
      }
    }
    myDamage = Math.min(myLevel,myDamage+damageAmount);
    for (RoomListener listener : myListeners) {
      listener.reactToDamage(myLevel,myDamage);
    }
  }

  @Override
  public void repair(double repairAmount) {
    myRepairProgress += repairAmount;
    if (myRepairProgress >= 1) {
      myRepairProgress = 0;
      myDamage = Math.max(0,myDamage - 1);
      for (RoomListener listener : myListeners) {
        listener.reactToDamage(myLevel,myDamage);
      }
    }
  }

  @Override
  public void subscribe(RoomListener listener) {
    myListeners.add(listener);
  }

  @Override
  public boolean isManned() {
    return isManned;
  }

  @Override
  public Tile requestDestination(Person askingPerson) {
    return myPathRoom.requestDestination(askingPerson);
  }

  @Override
  public int getDamage() {
    return myDamage;
  }

  @Override
  public int getLevel() {
    return myLevel;
  }

  @Override
  public int getHeight() {
    return 2;
  }

  @Override
  public List<Tile> getTiles() {
    return myPathRoom.getTiles();
  }

  @Override
  public boolean tileInBounds(int x, int y) {
    return myPathRoom.tileInBounds(x,y);
  }

  @Override
  public int getX() {
    return myPathRoom.getX();
  }

  @Override
  public int getY() {
    return myPathRoom.getY();
  }

  @Override
  public int getWidth() {
    return 2;
  }

  @Override
  public String getID() {
    return myID;
  }

  @Override
  public void setTargeted(boolean targeted) {
    isTargeted = targeted;
    myListeners.forEach(listener -> {listener.reactToTargeted(targeted);});
  }

  @Override
  public boolean isTargeted() {
    return isTargeted;
  }

  @Override
  public boolean needsRepairs() {
    return myDamage > 0;
  }

  @Override
  public void reactToRepairs(double repairAmount) {
    repair(repairAmount);
  }

  @Override
  public void reactToOperator(boolean operated, Tile operatedTile) {
    if (operatedTile != myPathRoom.getTiles().get(0)) {
      return;
    }
    isManned = operated;
    for (RoomListener listener : myListeners) {
      listener.reactToManned(isManned);
    }
  }
}
