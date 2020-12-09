package vehicleftl.visualizer;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import vehicleftl.model.Crewmate;
import vehicleftl.model.pathfinding.Person;
import vehicleftl.model.pathfinding.PersonListener;
import vehicleftl.model.pathfinding.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VehicleCrewVisualizer implements CrewVisualizer, PersonListener {
  private double myX;
  private double myXOffset;
  private double myY;
  private double myYOffset;
  private double mySize;
  private boolean selected;
  private Group myGroup;
  private Circle myCircle;
  private Crewmate myCrewmate;

  public VehicleCrewVisualizer(double xOffset, double yOffset, Crewmate crewmate) {
    myCrewmate = crewmate;
    myXOffset = xOffset;
    myYOffset = yOffset;
    mySize = 20;
    myCircle = new Circle(myX,myY,mySize);
    Random colorRand = new Random();
    myCircle.setFill(Color.color(colorRand.nextDouble(),colorRand.nextDouble(),colorRand.nextDouble()));
    reactToMove(crewmate.getX(),crewmate.getY(),crewmate.getTarget());
    myGroup = new Group();
    myGroup.getChildren().add(myCircle);
    crewmate.subscribe(this);
    updateCircle();
  }

  private void updateCircle() {
    myCircle.setCenterX(myX);
    myCircle.setCenterY(myY);
  }

  private List<TileVisualizer> findPath(List<TileVisualizer> allTiles,TileVisualizer target) {
    return new ArrayList<>();
  }

  public boolean pointInBounds(double x, double y) {
    return (distance(x,y,myX,myY) < mySize);
  }

  private double distance(double x1, double x2, double y1, double y2) {
    return Math.sqrt(Math.pow(y1-x1,2)+Math.pow(y2-x2,2));
  }

  @Override
  public Group getGroup() {
    return myGroup;
  }

  @Override
  public Crewmate getCrewmate() {
    return myCrewmate;
  }

  @Override
  public void setSelected(boolean selected) {
    System.out.println("Am I selected? " + selected);
    myCircle.setStrokeWidth((selected)? 5 : 0);
    myCircle.setStroke(Color.BLACK);
  }

  @Override
  public void reactToMove(double currX, double currY, Tile previousTarget) {
    myX = myXOffset + (currX * 50);
    myY = myYOffset + (currY * 50);
    updateCircle();
  }

  @Override
  public void reactToNewPath(List<Tile> path, Person modifiedPerson) {
    //does nothing for now
  }
}
