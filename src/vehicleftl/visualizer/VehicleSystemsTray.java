package vehicleftl.visualizer;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import vehicleftl.model.PowerSourceListener;
import vehicleftl.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class VehicleSystemsTray implements SystemsTray, PowerSourceListener {

  private double myX;
  private double myY;
  private Group myGroup;
  private List<SystemVisualizer> mySystemVisualizers;
  private List<Rectangle> myPowerIndicators;

  public VehicleSystemsTray(Vehicle vehicle, double x, double y) {
    myX = x;
    myY = y;
    myGroup = new Group();
    mySystemVisualizers = new ArrayList<>();
    myPowerIndicators = new ArrayList<>();
    for (int i = 0; i < vehicle.getPowerSource().getMaxPower(); i ++) {
      Rectangle powerRect = new Rectangle(myX,myY - (VehicleSystemVisualizer.POWER_INDICATOR_HEIGHT * 1.2 * i),VehicleSystemVisualizer.POWER_INDICATOR_WIDTH,VehicleSystemVisualizer.POWER_INDICATOR_HEIGHT);
      powerRect.setStroke(Color.BLACK);
      powerRect.setFill(Color.TRANSPARENT);
      myPowerIndicators.add(powerRect);
      myGroup.getChildren().add(powerRect);
    }
    vehicle.getPowerSource().subscribeToPowerSource(this);
    for (int i = 0; i < vehicle.getSystems().size(); i ++) {
      SystemVisualizer sysVis = new VehicleSystemVisualizer(vehicle.getSystems().get(i),myX + (VehicleSystemVisualizer.POWER_INDICATOR_WIDTH * 1.3 * (i+1)), myY);
      myGroup.getChildren().add(sysVis.getGroup());
      mySystemVisualizers.add(sysVis);
    }
  }

  @Override
  public Group getGroup() {
    return myGroup;
  }

  @Override
  public void reactToPowerChange(int newPower) {
    System.out.println("REACTING TO REACTOR POWER CHANGING TO " + newPower);
    for (int i = 0; i < myPowerIndicators.size(); i ++) {
      Color fill = (i < newPower)? Color.color(0.0,1.0,0.0,1.0) : Color.TRANSPARENT;
      myPowerIndicators.get(i).setFill(fill);
    }
  }

  @Override
  public boolean pointInBounds(double x, double y) {
    for (SystemVisualizer sysVis : mySystemVisualizers) {
      if (sysVis.pointInBounds(x,y)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public List<SystemVisualizer> getSystemVisualizers() {
    return mySystemVisualizers;
  }
}
