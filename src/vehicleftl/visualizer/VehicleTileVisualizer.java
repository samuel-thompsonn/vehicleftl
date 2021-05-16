package vehicleftl.visualizer;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import vehicleftl.visualizer.interactiveelements.TileVisualizer;

public class VehicleTileVisualizer implements TileVisualizer {

  public static final double TILE_SIZE = 50;

  private double myX;
  private double myY;
  private double mySize;
  private boolean selected;
  private Group myGroup;

  public VehicleTileVisualizer(double xPos, double yPos) {
    myX = xPos;
    myY = yPos;
    mySize = TILE_SIZE;
    selected = false;
    myGroup = new Group();
    updateRectangle();
  }

  @Override
  public void reactToClick(double clickX, double clickY) {
    if (clickX < myX || clickX > myX + mySize) {
      return;
    }
    if (clickY < myY || clickY > myY + mySize) {
      return;
    }
    selected = !selected;
    updateRectangle();
  }

  private void updateRectangle() {
    myGroup.getChildren().clear();
    Rectangle rect = new Rectangle(myX,myY,mySize,mySize);
    Color fill = (selected)? Color.BLUE : Color.WHITE;
    rect.setFill(fill);
    rect.setStroke(Color.GRAY);
    myGroup.getChildren().add(rect);
  }

  @Override
  public Group getGroup() {
    return myGroup;
  }
}
