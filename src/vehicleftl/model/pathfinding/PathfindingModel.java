package vehicleftl.model.pathfinding;

import vehicleftl.model.Room;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PathfindingModel implements PathModel, PersonListener {
  private List<Tile> myTiles;
  private Tile[][] myTileGrid;
  private List<Person> myPeople;
  private List<Room> myRooms;
  private int myWidth;
  private int myHeight;

  public PathfindingModel(int width, int height, List<Room> rooms) {
    myWidth = width;
    myHeight = height;
    myPeople = new LinkedList<>();

    myRooms = rooms;
//    //TODO: I don't actually want to add rooms at runtime, right? I want to maybe load them from a file.
//    addRoom(2,2,0,0);
//    addRoom(2,2,2,0);
//    addRoom(2,2,4,0);

    initTiles(myWidth,myHeight);

    myPeople.add(new PathfindingPerson(myTileGrid[0][0],myRooms.get(0).requestDestination(null)));
    myPeople.add(new PathfindingPerson(myTileGrid[1][1],myRooms.get(0).requestDestination(null)));
    myPeople.add(new PathfindingPerson(myTileGrid[0][1],myRooms.get(0).requestDestination(null)));
    for (Person person : myPeople) {
      person.setTargetTile(myRooms.get(0).requestDestination(person));
    }
  }

  private void addPerson(Tile startingLocation, Tile startingTarget) {
    Person person = new PathfindingPerson(startingLocation,startingLocation);
    myPeople.add(person);
    person.subscribe(this);
  }

  private void initTiles(int width, int height) {
    myTileGrid = new Tile[width][height];
    for (int i = 0; i < width; i ++) {
      for (int j = 0; j < height; j ++) {
        myTileGrid[i][j] = null;
      }
    }

    myTiles = new ArrayList<>();
    for (PathRoom room : myRooms) {
      for (Tile tile : room.getTiles()) {
        try {
          myTileGrid[tile.getX()][tile.getY()] = tile;
        } catch (ArrayStoreException e) {
          System.out.println("tile.getX() = " + tile.getX());
          System.out.println("tile.getY() = " + tile.getY());
        }
        myTiles.add(tile);
      }
    }

    assignTileAdjacency();
  }

  private void assignTileAdjacency() {
    for (int i = 0; i < myWidth; i ++) {
      for (int j = 0; j < myHeight; j ++) {
        Tile tile = myTileGrid[i][j];
        if (tile == null) {
          continue;
        }
        if (i > 0 && myTileGrid[i-1][j] != null) {
          tile.addAdjacentTile(myTileGrid[i-1][j]);
          if (i == 3 && j == 0) {
            System.out.println(myTileGrid[i-1][j].getX());
          }
        }
        if (i < myWidth - 1 && myTileGrid[i+1][j] != null) {
          tile.addAdjacentTile(myTileGrid[i+1][j]);
          if (i == 3 && j == 0) {
            System.out.println(myTileGrid[i+1][j].getX());
          }
        }
        if (j > 0 && myTileGrid[i][j-1] != null) {
          tile.addAdjacentTile(myTileGrid[i][j-1]);
          if (i == 3 && j == 0) {
            System.out.println(myTileGrid[i][j-1].getX());
          }
        }
        if (j < myHeight - 1 && myTileGrid[i][j+1] != null) {
          tile.addAdjacentTile(myTileGrid[i][j+1]);
          if (i == 3 && j == 0) {
            System.out.println(myTileGrid[i][j+1].getX());
          }
        }
      }
    }
  }

  @Override
  public void update(double elapsedSeconds) {
    for (Person person : myPeople) {
      Tile personTile = myTileGrid[(int)Math.floor(person.getX())][(int)Math.floor(person.getY())];
      person.setCurrentTile(personTile);
//      person.update(elapsedSeconds);
    }
    for (PathRoom room : myRooms) {
      room.isManned();
    }
  }

  @Override
  public List<Tile> getTiles() {
    return myTiles;
  }

  @Override
  public List<Person> getPeople() {
    return myPeople;
  }

  @Override
  public void selectTargetTile(double xPos, double yPos) {
    System.out.println("SELECTING TILE TARGET ======================");

    int targetTileX = (int)Math.floor(xPos / 50);
    int targetTileY = (int)Math.floor(yPos / 50);
    if (targetTileX > 10-1 || targetTileY > 10-1 || myTileGrid[targetTileX][targetTileY] == null) {
      return;
    }

    for (Person person : myPeople) {
      //de-occupy the tiles for all selected people,
      // so the tiles can be reassigned
      if (person.isSelected()) {
        int personTileX = (int)Math.floor(person.getX());
        int personTileY = (int)Math.floor(person.getY());
//        myTileGrid[personTileX][personTileY].setOccupied(false);
        if (person.getTarget() != null) {
//          person.getTarget().setTargeted(false);
        }
      }
    }

    PathRoom targetRoom = null;
    for (PathRoom room : myRooms) {
      if (room.tileInBounds(targetTileX,targetTileY)) {
        targetRoom = room;
      }
    }
    if (targetRoom == null) {
      return;
    }

    Tile previousTarget = null;
    //For each person, find the target tile to go to and send them to that tile.
    for (Person person : myPeople) {
      if (!person.isSelected()) {
        continue;
      }
      Tile target = targetRoom.requestDestination(null);
      System.out.println("Targeted tile with click: " + target.getX() + ", " + target.getY());

      int personTileX = (int)Math.floor(person.getX());
      int personTileY = (int)Math.floor(person.getY());
      previousTarget = myTileGrid[personTileX][personTileY];
      person.setTargetTile(target);
    }

    PathRoom prevTargetRoom = null;
    for (PathRoom room : myRooms) {
      if (room.tileInBounds(previousTarget.getX(),previousTarget.getY())) {
        prevTargetRoom = room;
      }
    }

    //For each person not selected, recalculate their position in their own room
    // (if they're not moving elsewhere)
    for (Person person : myPeople) {
      //We should only mess with people whose TARGET is in a room that a selected person is moving AWAY from.
      if (person.isSelected() || person.getTarget() == null) {
        continue;
      }
      if (person.getTarget() != null) {
        if (!prevTargetRoom.tileInBounds(person.getTarget().getX(),person.getTarget().getY())) {
          continue;
        }
//        person.getTarget().setTargeted(false);
      }

      int personTileX = (int)Math.floor(person.getX());
      int personTileY = (int)Math.floor(person.getY());

      int personTargetX = person.getTarget().getX();
      int personTargetY = person.getTarget().getY();

      PathRoom personRoom = null;
      for (PathRoom room : myRooms) {
        if (room.tileInBounds(personTargetX,personTargetY)) {
          personRoom = room;
        }
      }
      if (personRoom == null) {
        continue;
      }
      Tile target = personRoom.requestDestination(null);
      person.setTargetTile(target);
    }
  }

  private void refreshPersonTargets() {
    for (Person person : myPeople) {
      int personTileX = (int)Math.floor(person.getX() / 50);
      int personTileY = (int)Math.floor(person.getY() / 50);
      person.setTargetTile(person.getTarget());
    }
  }

  @Override
  public void reactToMove(double currX, double currY, Tile previousTarget) {
    //has no reaction to movement
  }

  @Override
  public void reactToNewPath(List<Tile> path, Person modifiedPerson) {
    Tile previousTarget = modifiedPerson.getTarget();
    System.out.println("Reacting as a model to some crewmate mvoing");
    PathRoom prevTargetRoom = null;
    for (PathRoom room : myRooms) {
      if (room.tileInBounds(previousTarget.getX(),previousTarget.getY())) {
        prevTargetRoom = room;
      }
    }

    //For each person not selected, recalculate their position in their own room
    // (if they're not moving elsewhere)
    for (Person person : myPeople) {
      //We should only mess with people whose TARGET is in a room that a selected person is moving AWAY from.
      if (person.isSelected() || person.getTarget() == null || person == modifiedPerson) {
        continue;
      }
      if (person.getTarget() != null) {
        if (!prevTargetRoom.tileInBounds(person.getTarget().getX(),person.getTarget().getY())) {
          continue;
        }
//        person.getTarget().setTargeted(false);
      }

      int personTileX = (int)Math.floor(person.getX());
      int personTileY = (int)Math.floor(person.getY());

      int personTargetX = person.getTarget().getX();
      int personTargetY = person.getTarget().getY();

      PathRoom personRoom = null;
      for (PathRoom room : myRooms) {
        if (room.tileInBounds(personTargetX,personTargetY)) {
          personRoom = room;
        }
      }
      if (personRoom == null) {
        continue;
      }
      Tile target = personRoom.requestDestination(null);
      person.setTargetTile(target);
    }
  }
}
