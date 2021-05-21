package vehicleftl.visualizer.interactiveelements.crewvisualizer;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import vehicleftl.model.Crewmate;
import vehicleftl.model.pathfinding.Person;
import vehicleftl.model.pathfinding.PersonListener;
import vehicleftl.model.pathfinding.Tile;
import vehicleftl.visualizer.interactiveelements.ReactionClassedElement;

import java.util.List;
import java.util.Random;

public class VehicleCrewVisualizer extends ReactionClassedElement implements CrewVisualizer, PersonListener {
  private double myX;
  private double myXOffset;
  private double myY;
  private double myYOffset;
  private double mySize;
  private Crewmate myCrewmate;

  public VehicleCrewVisualizer(double xOffset, double yOffset, Crewmate crewmate) {
    super(xOffset, yOffset, crewmate);
    myCrewmate = crewmate;
    myXOffset = xOffset;
    myYOffset = yOffset;
    mySize = 20;
    Random colorRand = new Random();
    reactToMove(crewmate.getX(),crewmate.getY(),crewmate.getTarget());
    crewmate.subscribe(this);
  }

  @Override
  public String getID() {
    return myCrewmate.getID();
  }

  public boolean pointInBounds(double x, double y) {
    return (distance(x,y,myX,myY) < mySize);
  }

  private double distance(double x1, double x2, double y1, double y2) {
    return Math.sqrt(Math.pow(y1-x1,2)+Math.pow(y2-x2,2));
  }

  @Override
  public String getElementType() {
    return "Crew";
  }

  @Override
  public void reactToMove(double currX, double currY, Tile previousTarget) {
    myX = myXOffset + (currX * 50);
    myY = myYOffset + (currY * 50);
  }

  @Override
  public void reactToNewPath(List<Tile> path, Person modifiedPerson) {
    //does nothing for now
  }
}
