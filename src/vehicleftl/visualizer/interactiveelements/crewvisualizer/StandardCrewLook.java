package vehicleftl.visualizer.interactiveelements.crewvisualizer;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import vehicleftl.model.Crewmate;
import vehicleftl.model.pathfinding.Person;
import vehicleftl.model.pathfinding.PersonListener;
import vehicleftl.model.pathfinding.Tile;
import vehicleftl.visualizer.interactiveelements.InterfaceLook;
import vehicleftl.visualizer.interactiveelements.TileVisualizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class StandardCrewLook implements PersonListener, InterfaceLook {
  private double myX;
  private double myXOffset;
  private double myY;
  private double myYOffset;
  private double mySize;
  private Group myGroup;
  private Circle myCircle;
  private Circle myHighlight;
  private Crewmate myCrewmate;

  public StandardCrewLook(double xOffset, double yOffset, Crewmate crewmate, Map<String, String> params) {
    handleParams(params);
    myCrewmate = crewmate;
    myXOffset = xOffset;
    myYOffset = yOffset;
    mySize = 20;
    myCircle = new Circle(myX,myY,mySize);
    Random colorRand = new Random();
    Color randomColor = Color.color(colorRand.nextDouble(),colorRand.nextDouble(),colorRand.nextDouble());
    myCircle.setFill(Color.MAROON);
    myHighlight = new Circle(myX, myY, mySize);
    myGroup = new Group();
    initLook(myCircle, myHighlight);
    myGroup.getChildren().addAll(myCircle, myHighlight);
    reactToMove(crewmate.getX(),crewmate.getY(),crewmate.getTarget());
    crewmate.subscribe(this);
    updateCircle();
  }

  protected abstract void handleParams(Map<String, String> params);

  protected abstract void initLook(Circle baseCircle, Circle highlightCircle);

  private void updateCircle() {
    myCircle.setCenterX(myX);
    myCircle.setCenterY(myY);
    myHighlight.setCenterX(myX);
    myHighlight.setCenterY(myY);
  }

  private List<TileVisualizer> findPath(List<TileVisualizer> allTiles, TileVisualizer target) {
    return new ArrayList<>();
  }

  private double distance(double x1, double x2, double y1, double y2) {
    return Math.sqrt(Math.pow(y1-x1,2)+Math.pow(y2-x2,2));
  }

  @Override
  public Group getGroup() {
    return myGroup;
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
