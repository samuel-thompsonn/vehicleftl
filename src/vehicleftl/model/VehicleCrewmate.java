package vehicleftl.model;

import vehicleftl.model.pathfinding.Person;
import vehicleftl.model.pathfinding.PersonListener;
import vehicleftl.model.pathfinding.Tile;

import java.util.Random;

public class VehicleCrewmate implements Crewmate {

  private Person myPathfindingPerson;
  private double myRepairSpeed;
  private String myID;

  public VehicleCrewmate(Person person) {
    myID = "Crewmate" + new Random().nextInt(10000);
    myPathfindingPerson = person;
    myRepairSpeed = 1.0;
  }

  @Override
  public Tile getTarget() {
    return myPathfindingPerson.getTarget();
  }

  @Override
  public void select() {
    myPathfindingPerson.select();
  }

  @Override
  public void deselect() {
    myPathfindingPerson.deselect();
  }

  @Override
  public boolean isSelected() {
    return myPathfindingPerson.isSelected();
  }

  @Override
  public void setCurrentTile(Tile tile) {
    myPathfindingPerson.setCurrentTile(tile);
  }

  @Override
  public void subscribe(PersonListener listener) {
    myPathfindingPerson.subscribe(listener);
  }

  @Override
  public void setTargetTile(Tile targetTile) {
    if (myPathfindingPerson.getTarget() != null) {
      myPathfindingPerson.getTarget().operateTile(false);
    }
    myPathfindingPerson.setTargetTile(targetTile);
  }

  @Override
  public void update(double elapsedSeconds) {
    myPathfindingPerson.update(elapsedSeconds);
    if (myPathfindingPerson.getTarget() == null) {
      return;
    }
    boolean operateTile = !(myPathfindingPerson.isMoving() || myPathfindingPerson.getTarget().needsRepairs());
    myPathfindingPerson.getTarget().operateTile(operateTile);

    if (myPathfindingPerson.getTarget().needsRepairs() && !myPathfindingPerson.isMoving()) {
      myPathfindingPerson.getTarget().repair(myRepairSpeed * elapsedSeconds);
    }
  }

  @Override
  public double getX() {
    return myPathfindingPerson.getX();
  }

  @Override
  public double getY() {
    return myPathfindingPerson.getY();
  }

  @Override
  public void setPosition(double x, double y) {
    myPathfindingPerson.setPosition(x,y);
  }

  @Override
  public boolean equals(Person person) {
    return myPathfindingPerson.equals(person);
  }

  @Override
  public boolean isMoving() {
    return myPathfindingPerson.isMoving();
  }

  @Override
  public String getID() {
    return myID;
  }
}
