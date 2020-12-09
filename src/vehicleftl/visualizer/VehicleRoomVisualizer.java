package vehicleftl.visualizer;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import vehicleftl.model.Room;
import vehicleftl.model.RoomListener;

import java.util.ArrayList;
import java.util.List;

public class VehicleRoomVisualizer implements RoomVisualizer {

  private Group myGroup;
  private List<TileVisualizer> myTiles;
  private Group myTileVisualizerGroup;
  private double myX;
  private double myY;
  private int myWidth;
  private int myHeight;
  private Rectangle myRoomBorder;
  private Room myRoom;
  private boolean selected;

  public VehicleRoomVisualizer(double xPos, double yPos, int width, int height, Room room) {
    myX = xPos;
    myY = yPos;
    myWidth = width * 50;
    myHeight = height * 50;
    myGroup = new Group();
    myRoom = room;
    myRoom.subscribe(this);
    selected = false;

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
    myGroup.getChildren().add(myRoomBorder);
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
}
