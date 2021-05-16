package vehicleftl.visualizer.interactiveelements;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import vehicleftl.model.Room;
import vehicleftl.visualizer.VehicleTileVisualizer;
import vehicleftl.visualizer.interactiveelements.util.ThreeKeyMap;
import vehicleftl.visualizer.interactiveelements.util.UserInputReaction;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehicleRoomVisualizer implements RoomVisualizer {

  private Group myGroup;
  private List<TileVisualizer> myTiles;
  private Group myTileVisualizerGroup;
  private double myX;
  private double myY;
  private int myWidth;
  private int myHeight;
  private Rectangle myRoomBorder;
  private Rectangle myHighlightPlane;
  private Room myRoom;
  private boolean selected;
  private Circle myTargetedIndicator;
  private Map<String, UserInputReaction> myLooksMap;
  private ThreeKeyMap<String, String, String, Method> myReactions;

  public VehicleRoomVisualizer(double xPos, double yPos, int width, int height, Room room) {
    initLooks();
    initReactions();
    myX = xPos;
    myY = yPos;
    myWidth = width * 50;
    myHeight = height * 50;
    myGroup = new Group();
    myRoom = room;
    myRoom.subscribe(this);
    selected = false;
    myTargetedIndicator = new Circle(20, Color.color(1,0,0,0.5));
    myTargetedIndicator.setStroke(Color.color(0,0,0,1));
    myTargetedIndicator.setVisible(myRoom.isTargeted());
    myTargetedIndicator.setCenterX(myX + (myWidth/2));
    myTargetedIndicator.setCenterY(myY + (myHeight/2));

    myTileVisualizerGroup = new Group();
    myTiles = new ArrayList<>();
    for (int i = 0; i < width; i ++) {
      for (int j = 0; j < height; j++) {
        VehicleTileVisualizer tile = new VehicleTileVisualizer(myX + (i * 50), myY + (j * 50));
        myTileVisualizerGroup.getChildren().add(tile.getGroup());
        myTiles.add(tile);
      }
    }
    myGroup.getChildren().add(myTileVisualizerGroup);
    myRoomBorder = new Rectangle(myX,myY,50*width,50*height);
    myRoomBorder.setFill(Color.TRANSPARENT);
    myRoomBorder.setStroke(Color.BLACK);
    myHighlightPlane = new Rectangle(myX,myY,50*width,50*height);
    myHighlightPlane.setFill(Color.TRANSPARENT);
    myHighlightPlane.setStroke(Color.TRANSPARENT);
    myGroup.getChildren().add(myRoomBorder);
    myGroup.getChildren().add(myHighlightPlane);
    myGroup.getChildren().add(myTargetedIndicator);
  }

  private void initLooks() {
    myLooksMap = new HashMap<>();
    myLooksMap.put("Normal", () -> myRoomBorder.setStrokeWidth(1));
    myLooksMap.put("HighlightTarget", () -> myRoomBorder.setStrokeWidth(5));
    myLooksMap.put("HighlightCommand", () -> myRoomBorder.setStrokeWidth(10));
  }

  private void lookNormal() {
    myRoomBorder.setStrokeWidth(1);
    myHighlightPlane.setFill(Color.TRANSPARENT);
  }

  private void highlightRed() {
    myRoomBorder.setStrokeWidth(1);
    myHighlightPlane.setFill(Color.color(1.0,0,0,0.5));
  }

  private void highlightYellow() {
    myHighlightPlane.setFill(Color.color(0.7,0.5,0.0,0.5));
  }

  private void initReactions() {
    myReactions = new UILooksMapReader().getLooksMap(this, "RoomVisualizer");
  }

  @Override
  public String getID() {
    return myRoom.getID();
  }

  public boolean pointInBounds(double clickX, double clickY) {
    return clickX > myX && clickX < myX + myWidth && clickY > myY && clickY < myY + myHeight;
  }

  @Override
  public void setHighlighted(boolean highlighted) {
    Color fill = (highlighted)? Color.color(1.0,0.7,0.0) : Color.BLACK;
    myRoomBorder.setStroke(fill);
  }

  @Override
  public double getCenterX() {
    return myX + myRoom.getWidth() * (50/2.);
  }

  @Override
  public double getCenterY() {
    return myY + myRoom.getHeight() * (50/2.);
  }

  @Override
  public Group getGroup() {
    return myGroup;
  }

  @Override
  public String getElementType() {
    return "Room";
  }

  @Override
  public String getStateInfo() {
    return null;
  }

  @Override
  public Room getRoom() {
    return myRoom;
  }

  @Override
  public boolean isSelected() {
    return selected;
  }

  @Override
  public void reactToDamage(int level, int damage) {
    System.out.println("REACTING TO DAMAGE: " + damage + " damage on a level " + level + " ROOM");
    double damagedAmount = damage / (double)level;
    myRoomBorder.setFill(Color.color(1,0,0,damagedAmount * 0.5));
  }

  @Override
  public boolean blockAttack(int damage) {
    return false;
  }

  @Override
  public void reactToTargeted(boolean targeted) {
    myTargetedIndicator.setVisible(targeted);
  }

  @Override
  public void reactToUserInput(String UIState, String inputType, String UITarget) {
    String targeted = (myRoom.getID().equals(UITarget))? "True" : "False";
    Method reaction = myReactions.get(UIState, inputType, targeted);
    if (reaction == null) {
      reaction = myReactions.get("Default", "Any", "Any");
    }
    try {
      reaction.invoke(this);
    } catch (IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  private void testInvoke() {
    System.out.println("Hello!");
  }
}
