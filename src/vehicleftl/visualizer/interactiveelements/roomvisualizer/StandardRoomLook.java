package vehicleftl.visualizer.interactiveelements.roomvisualizer;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import vehicleftl.model.Room;
import vehicleftl.model.RoomListener;
import vehicleftl.visualizer.interactiveelements.InterfaceLook;
import vehicleftl.visualizer.interactiveelements.TileVisualizer;
import vehicleftl.visualizer.interactiveelements.VehicleTileVisualizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class StandardRoomLook implements RoomListener, InterfaceLook {
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
  private Circle myTargetedIndicator;

  public StandardRoomLook(double xPos, double yPos, int width, int height, Room room, Map<String, String> params) {
    myX = xPos;
    myY = yPos;
    myWidth = width * 50;
    myHeight = height * 50;
    myGroup = new Group();
    myRoom = room;
    myRoom.subscribe(this);
    myTargetedIndicator = new Circle(20, Color.color(1,0,0,0.5));
    myTargetedIndicator.setStroke(Color.color(0,0,0,1));
    myTargetedIndicator.setVisible(myRoom.isTargeted());
    myTargetedIndicator.setCenterX(myX + (myWidth/2.0));
    myTargetedIndicator.setCenterY(myY + (myHeight/2.0));

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
    initHighlight(myHighlightPlane, params);
    myGroup.getChildren().add(myRoomBorder);
    myGroup.getChildren().add(myHighlightPlane);
    myGroup.getChildren().add(myTargetedIndicator);
  }

  protected abstract void initHighlight(Rectangle highlightPlane, Map<String, String> params);

  @Override
  public Group getGroup() {
    return myGroup;
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
}
