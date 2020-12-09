package vehicleftl.model.pathfinding;

import vehicleftl.model.RoomListener;

import java.util.ArrayList;
import java.util.List;

public class PathfindingRoom implements PathRoom, TileListener {
  private List<Tile> myTiles;
  private Tile[][] myTileGrid;
  private int myX;
  private int myY;
  private int myWidth;
  private int myHeight;
  private boolean isManned;
  private List<RoomListener> myListeners;
  private List<Person> myOccupants;

  public PathfindingRoom(int width, int height, int x, int y) {
    myOccupants = new ArrayList<>();
    myListeners = new ArrayList<>();
    myX = x;
    myY = y;
    myWidth = width;
    myHeight = height;

    myTiles = new ArrayList<>();
    List<Tile> returnedList = new ArrayList<>();
    Tile[][] tiles = new Tile[width][height];
    for (int i = 0; i < width; i ++) {
      for (int j = 0; j < height; j ++) {
        Tile newTile = new PathfindingTile(x + i,y+ j);
        tiles[i][j] = newTile;
        returnedList.add(newTile);
        newTile.subscribe(this);
      }
    }
    myTileGrid = tiles;
    myTiles = returnedList;
  }

  public PathfindingRoom(int width, int height, int x, int y, Tile[][] tiles) {
    myOccupants = new ArrayList<>();
    myListeners = new ArrayList<>();
    myX = x;
    myY = y;
    myWidth = width;
    myHeight = height;

    myTiles = new ArrayList<>();
    List<Tile> returnedList = new ArrayList<>();
    for (int i = 0; i < width; i ++) {
      for (int j = 0; j < height; j ++) {
        returnedList.add(tiles[i][j]);
        tiles[i][j].subscribe(this);
      }
    }
    myTileGrid = tiles;
    myTiles = returnedList;
  }

  @Override
  public List<Tile> getTiles() {
    return myTiles;
  }

  @Override
  public boolean tileInBounds(int x, int y) {
    return (x >= myX && x < myX + myWidth && y >= myY && y < myY + myHeight);
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
  public int getWidth() {
    return myWidth;
  }

  @Override
  public int getHeight() {
    return myHeight;
  }

  @Override
  public boolean isManned() {
    if (myTileGrid[0][0].isOccupied()) {
      isManned = true;
      for (RoomListener listener : myListeners) {
        listener.reactToManned(isManned);
      }
      return true;
    }
    isManned = false;
    for (RoomListener listener : myListeners) {
      listener.reactToManned(isManned);
    }
    return false;
  }

  /*
  The following information is needed in order to determine where to assign the next requester:
  1. The tile priority
  2. Which tiles are occupied
   */
  
  @Override
  public Tile requestDestination(Person askingPerson) {
    if (askingPerson == null) {
      return null;
    }
    //TODO: Abstract this ordering into an object or method
    //Problem: asking if two guys are equal DOESN'T WORK because a crewmate and pathfinding person
    // are not equal even if they are the same guy.
    //  Proposed solution: Compare their targets.
    System.out.println("Looking for a suitable destination...");
    for (int j = 0; j < myTileGrid[0].length; j ++) {
      for (int i = 0; i < myTileGrid.length; i ++) {
        if (myTileGrid[i][j].isTargeted() && myTileGrid[i][j].getOccupant().equals(askingPerson)) {
          System.out.println("The person who is asking already has a priority room position " + i + ", " + j);
        }
        if (myTileGrid[i][j].isTargeted() && !myTileGrid[i][j].getOccupant().equals(askingPerson)) {
          System.out.println("Tile " + i + ", " + j + " is already occupied.");
          System.out.println("Occupier: " + myTileGrid[i][j].getOccupant());
          System.out.println("Asker: " + askingPerson);
          continue;
        }
        System.out.println("Returning my local tile " + i + ", " + j + " as the destination.");
        return myTileGrid[i][j];
      }
    }
    //for each tile in order of priority:
    //  if tile is occupied, continue;
    //  otherwise, return tile
    //fallback: return no tile (null)
    return null;
  }

  @Override
  public void subscribe(RoomListener listener) {
    myListeners.add(listener);
  }

  @Override
  public void reactToNewOccupant(Person newPerson) {
    if (!myOccupants.contains(newPerson)) {
      myOccupants.add(newPerson);
    }
  }

  @Override
  public void reactToPersonRemoval(Person removedPerson, Tile newDestination) {
    System.out.println("I am a room reacting to a person removal");
    if (myTiles.contains(newDestination)) {
      System.out.println("new destination is here");
      return;
    }
    myOccupants.remove(removedPerson);
    int initialLength = myOccupants.size();
    System.out.println("This room has " + initialLength + " occupants to reassign.");
    for (int i = 0; i < initialLength; i ++) {
      Person person = myOccupants.get(i);
      System.out.println("Reassigning occupant " + i + ": " + person);
      person.setTargetTile(requestDestination(person));
    }
  }
}
