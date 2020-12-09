package vehicleftl.visualizer;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import vehicleftl.model.ShipSystem;
import vehicleftl.model.SystemListener;

import java.util.ArrayList;
import java.util.List;


public class VehicleSystemVisualizer implements SystemVisualizer, SystemListener {

  public static final double POWER_INDICATOR_WIDTH_BASE = 50;
  public static final double POWER_INDICATOR_HEIGHT_BASE = 25;

  public static final double SCALE = 0.8;

  public static final double POWER_INDICATOR_WIDTH = POWER_INDICATOR_WIDTH_BASE*SCALE;
  public static final double POWER_INDICATOR_HEIGHT = POWER_INDICATOR_HEIGHT_BASE*SCALE;

  private Group myGroup;
  private ShipSystem mySystem;
  private double myX;
  private double myY;
  private List<Rectangle> myPowerBars;
  private Circle myMannedIndicator;

  public VehicleSystemVisualizer(ShipSystem system, double x, double y) {
    myX = x;
    myY = y;
    mySystem = system;
    myGroup = new Group();
    myPowerBars = new ArrayList<>();
    for (int i = 0; i < mySystem.getLevel(); i++) {
      Rectangle rect = new Rectangle(myX,myY - (POWER_INDICATOR_HEIGHT * 1.2 * i),POWER_INDICATOR_WIDTH,POWER_INDICATOR_HEIGHT);
      rect.setStroke(Color.BLACK);
      myPowerBars.add(rect);
      myGroup.getChildren().add(rect);
    }
    myMannedIndicator = new Circle(myX + (POWER_INDICATOR_WIDTH / 2), myY - (POWER_INDICATOR_HEIGHT * 1.2 * (mySystem.getLevel()-1)) - 20,10);
    myMannedIndicator.setStroke(Color.BLACK);
    myMannedIndicator.setFill(Color.TRANSPARENT);
    myGroup.getChildren().add(myMannedIndicator);
    system.subscribe(this);
  }

  @Override
  public Group getGroup() {
    System.out.println("myGroup.getChildren().size() = " + myGroup.getChildren().size());
    return myGroup;
  }

  @Override
  public void increasePower() {
    mySystem.setPower(mySystem.getPower()+1);
  }

  @Override
  public boolean pointInBounds(double x, double y) {
    return (x > myX && y < myY + 25 && x < myX + 50 && y > myY - ((myPowerBars.size()-1) * 40));
  }

  @Override
  public void decreasePower() {
    mySystem.setPower(mySystem.getPower()-1);
  }

  @Override
  public void reactToManned(boolean manned) {
    myMannedIndicator.setFill((manned)? Color.GREEN : Color.TRANSPARENT);
  }

  @Override
  public void reactToPowerChange(int newPower) {
    refreshIndicators(mySystem.getDamage(), mySystem.getPower());
  }

  @Override
  public void reactToDamageChange(int newDamage) {
    refreshIndicators(newDamage, mySystem.getPower());
  }

  private void refreshIndicators(int damage, int power) {
    for (int i = 0; i < mySystem.getLevel(); i ++) {
      Color fill = Color.TRANSPARENT;
      if (i < power) {
        fill = Color.color(0.0, 1.0, 0.0, 1.0);
      }
      else if (i >= mySystem.getLevel()-damage) {
        fill = Color.color(1.0, 0.0, 0.0, 1.0);
      }
      myPowerBars.get(i).setFill(fill);
    }
  }
}
