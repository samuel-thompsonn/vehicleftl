package vehicleftl.visualizer;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import vehicleftl.model.Vehicle;
import vehicleftl.model.VehicleListener;

public class HullPointVisualizer implements VehicleListener {

  private Group myGroup;
  private Rectangle myHealthIndicator;
  private Rectangle myBorder;
  private double myWidth;

  public HullPointVisualizer(Vehicle vehicle, double x, double y, double width, double height) {
    myGroup = new Group();
    myWidth = width;
    myHealthIndicator = new Rectangle(x, y, width, height);
    myHealthIndicator.setFill(Color.GREEN);
    myHealthIndicator.setStroke(Color.TRANSPARENT);
    myBorder = new Rectangle(x, y, width, height);
    myBorder.setFill(Color.TRANSPARENT);
    myBorder.setStroke(Color.BLACK);

    myGroup.getChildren().add(myHealthIndicator);
    myGroup.getChildren().add(myBorder);
    vehicle.subscribeToVehicle(this);
  }

  @Override
  public void reactToShieldChange(double shieldAmount, int maxShield) {
    //does nothing
  }

  @Override
  public void reactToHullPointChange(int currentHull, int maxHull) {
    myHealthIndicator.setWidth(myWidth * (currentHull / (double)maxHull));
  }

  public Group getGroup() {
    return myGroup;
  }
}
