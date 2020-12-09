package vehicleftl.model.pathfinding;

import java.util.*;

public class PathfindingPerson implements Person {

  public static final double TILES_PER_SECOND = 8.0;
  private double myX;
  private double myY;
  private List<Tile> myTilePath;
  private List<PersonListener> myListeners;
  private Tile myTarget;
  private Tile myCurrentTile;
  private boolean selected;

  public PathfindingPerson(Tile startingTile, Tile initialTarget) {
    myListeners = new ArrayList<>();
    setPosition(startingTile.getX()+0.5,startingTile.getY()+0.5);
    myTilePath = new ArrayList<>();
    myCurrentTile = startingTile;
    setTargetTile(initialTarget);
  }

  @Override
  public Tile getTarget() {
    return myTarget;
  }

  @Override
  public void select() {
    selected = true;
  }

  @Override
  public void deselect() {
    selected = false;
  }

  @Override
  public boolean isSelected() {
    return selected;
  }

  @Override
  public void setCurrentTile(Tile tile) {
    if (myCurrentTile != null) {
      myCurrentTile.setOccupied(false);
    }
    myCurrentTile = tile;
    myCurrentTile.setOccupied(true);
  }

  @Override
  public void subscribe(PersonListener listener) {
    myListeners.add(listener);
    listener.reactToMove(myX,myY, myTarget);
    listener.reactToNewPath(myTilePath, this);
  }

  @Override
  public void setTargetTile(Tile targetTile) {
    if (targetTile == null || myCurrentTile == null) {
      return;
    }
    List<Tile> visitedTiles = new ArrayList<>();
    Queue<Tile> tilesToExplore = new ArrayDeque<>();
    Map<Tile, Tile> treeEdges = new HashMap<>();
    tilesToExplore.add(myCurrentTile);
    while (true) {
      if (tilesToExplore.isEmpty()) {
        setTargetTile(myCurrentTile);
        return;
      }
      Tile tile = tilesToExplore.remove();
      if (tile == targetTile) {
        break;
      }
      visitedTiles.add(tile);
      for (Tile neighbor : tile.getAdjacentTiles()) {
        if (visitedTiles.contains(neighbor)) {
          continue;
        }
        tilesToExplore.add(neighbor);
        treeEdges.put(neighbor,tile);
      }
    }

    List<Tile> tilePath = new ArrayList<>();
    Tile tile = targetTile;
    tilePath.add(tile);
    while (treeEdges.get(tile) != myCurrentTile && tile != myCurrentTile) {
      tile = treeEdges.get(tile);
      tilePath.add(0,tile);
    }
    myTilePath = tilePath;
    System.out.println("Targeting tile " + targetTile.getX() + ", " + targetTile.getY());
    //We had a problem where multiple people were going to the same tile because the room reassigns people
    // iff someone leaves the room (targets another room's tile).
    if (myTarget != null) {
      System.out.println("Removing myself as an occupant from my previous tile...");
      myTarget.removeOccupant(this, targetTile);
    }
    targetTile.addOccupant(this);
    myTarget = targetTile;
    for (PersonListener listener : myListeners) {
      listener.reactToNewPath(tilePath,this );
    }
  }

  @Override
  public void update(double elapsedSeconds) {
    if (myTilePath.isEmpty()) {
      setCurrentTile(myCurrentTile);
      return;
    }
    Tile tile = myTilePath.get(0);

    double targetX = tile.getX() + 0.5;
    double targetY = tile.getY() + 0.5;

    double xDiff = (targetX) - getX();
    double yDiff = (targetY) - getY();
    double magnitude = Math.sqrt(Math.pow(xDiff,2)+Math.pow(yDiff,2));

    double xDir,yDir;
    xDir = (magnitude == 0)? 0 : xDiff / magnitude;
    yDir = (magnitude == 0)? 0 : yDiff / magnitude;

    double xMove = xDir * TILES_PER_SECOND * elapsedSeconds;
    double yMove = yDir * TILES_PER_SECOND * elapsedSeconds;

    setPosition(getX() + xMove,getY()+yMove);

    if (Math.abs(getX() - targetX) < 0.10 && Math.abs(getY() - targetY) < 0.10) {
      setPosition(targetX,targetY);
      setCurrentTile(myTilePath.remove(0));
      for (PersonListener listener : myListeners) {
        listener.reactToNewPath(myTilePath, this);
      }
    }
  }

  @Override
  public double getX() {
    return myX;
  }

  @Override
  public double getY() {
    return myY;
  }

  @Override
  public void setPosition(double x, double y) {
    myX = x;
    myY = y;
    for (PersonListener listener : myListeners) {
      listener.reactToMove(myX,myY, myTarget);
    }
  }

  public boolean equals(Person person) {
    return myTarget == person.getTarget();
  }

  @Override
  public boolean isMoving() {
    return !((myTarget.getX() == myCurrentTile.getX()) && (myTarget.getY() == myCurrentTile.getY()));
  }
}
