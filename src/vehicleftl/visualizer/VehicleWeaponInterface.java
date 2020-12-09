package vehicleftl.visualizer;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import vehicleftl.model.Room;
import vehicleftl.model.Weapon;
import vehicleftl.model.WeaponListener;

import java.util.ArrayList;
import java.util.List;

public class VehicleWeaponInterface implements WeaponInterfaceVisualizer, WeaponListener {

  public static final int DISPLAY_WIDTH_BASE = 100;
  public static final int DISPLAY_HEIGHT_BASE = 75;
  public static final double SCALE = 0.8;
  public static final double DISPLAY_WIDTH = DISPLAY_WIDTH_BASE * SCALE;
  public static final double DISPLAY_HEIGHT = DISPLAY_HEIGHT_BASE * SCALE;
  public static final double CHARGE_BAR_WIDTH_BASE = 20;
  public static final double CHARGE_BAR_WIDTH = CHARGE_BAR_WIDTH_BASE * SCALE;

  private Group myGroup;
  private List<Rectangle> myPowerIndicators;
  private List<Rectangle> myChargeIndicators;
  private Rectangle myChargeIndicator;
  private Rectangle myBorder;
  private double myX;
  private double myY;
  private double myWidth;
  private double myHeight;
  private Weapon myWeapon;
  private boolean isSelected;
  private RoomVisualizer myTargetVis;
  private Circle myTargetCircle;

  public VehicleWeaponInterface(Weapon weapon, double x, double y) {
    myTargetVis = null;
    myGroup = new Group();
    myX = x;
    myY = y;
    myWidth = DISPLAY_WIDTH;
    myHeight = DISPLAY_HEIGHT;
    myWeapon = weapon;
    myBorder = new Rectangle(myX,myY,myWidth,myHeight);
    myBorder.setStroke(Color.BLACK);
    myBorder.setFill(Color.TRANSPARENT);
    myGroup.getChildren().add(myBorder);
    myPowerIndicators = initPowerIndicators(weapon.getLevel());
    initChargeIndicators(weapon.getChargeTime());
    weapon.subscribe(this);

    myTargetCircle = new Circle(myX + 50, myY + 50, 15);
    myTargetCircle.setFill(Color.TRANSPARENT);
    myTargetCircle.setStroke(Color.TRANSPARENT);
    myGroup.getChildren().add(myTargetCircle);
  }

  private List<Rectangle> initPowerIndicators(int weaponPower) {
    List<Rectangle> returnedList = new ArrayList<>();
    for (int i = 0; i < weaponPower; i ++) {
      Rectangle powerBar = new Rectangle(myX + (2 * CHARGE_BAR_WIDTH), myY + (myHeight - VehicleSystemVisualizer.POWER_INDICATOR_HEIGHT*1.2) - (i * VehicleSystemVisualizer.POWER_INDICATOR_HEIGHT * 1.3),VehicleSystemVisualizer.POWER_INDICATOR_WIDTH,VehicleSystemVisualizer.POWER_INDICATOR_HEIGHT);
      powerBar.setStroke(Color.BLACK);
      powerBar.setFill(Color.TRANSPARENT);
      myGroup.getChildren().add(powerBar);
      returnedList.add(powerBar);
    }
    return returnedList;
  }

  private void initChargeIndicators(double maxCharge) {


    myChargeIndicator = new Rectangle(myX + (0.5 * CHARGE_BAR_WIDTH),myY + (myHeight - (0.5*CHARGE_BAR_WIDTH)),CHARGE_BAR_WIDTH,0);
    myChargeIndicator.setStroke(Color.TRANSPARENT);
    myChargeIndicator.setFill(Color.YELLOW);
    myGroup.getChildren().add(myChargeIndicator);

    Rectangle chargeContainer = new Rectangle(myX + (0.5 * CHARGE_BAR_WIDTH),myY + (0.5*CHARGE_BAR_WIDTH),CHARGE_BAR_WIDTH,myHeight - (CHARGE_BAR_WIDTH));
    chargeContainer.setStroke(Color.BLACK);
    chargeContainer.setFill(Color.TRANSPARENT);
    myGroup.getChildren().add(chargeContainer);

//    List<Rectangle> returnedList = new ArrayList<>();
//    int numChargeCells = (int)Math.floor(maxCharge);
//    double chargeCellHeight = (DISPLAY_HEIGHT - (CHARGE_BAR_WIDTH)) / numChargeCells;
//    for (int i = 0; i < maxCharge; i ++) {
//      Rectangle chargeCell = new Rectangle(myX + (0.5 * CHARGE_BAR_WIDTH), myY + (myHeight - (0.5*CHARGE_BAR_WIDTH)) - ((i+1) * chargeCellHeight),CHARGE_BAR_WIDTH,chargeCellHeight);
//      chargeCell.setStroke(Color.BLACK);
//      chargeCell.setFill(Color.TRANSPARENT);
//      myGroup.getChildren().add(chargeCell);
//      returnedList.add(chargeCell);
//    }
//    return returnedList;
  }

  @Override
  public Group getGroup() {
    return myGroup;
  }

  @Override
  public void setTarget(Room room, RoomVisualizer visualizer) {
    myWeapon.setTarget(room);
    myTargetVis = visualizer;
    if (myTargetVis == null) {
      myTargetCircle.setStroke(Color.TRANSPARENT);
    }
    else {
      myTargetCircle.setStroke(Color.RED);
      myTargetCircle.setCenterX(myTargetVis.getCenterX());
      myTargetCircle.setCenterY(myTargetVis.getCenterY());
    }
  }

  @Override
  public boolean pointInBounds(double x, double y) {
    return (x > myX && x < myX + myWidth && y > myY && y < myY + myHeight);
  }

  @Override
  public Weapon getWeapon() {
    return myWeapon;
  }

  @Override
  public void setSelected(boolean selected) {
    isSelected = selected;
    myBorder.setStrokeWidth((selected)? 5 : 1);
  }

  @Override
  public boolean getSelected() {
    return isSelected;
  }

  @Override
  public void reactToPowerChange(int newPower, int maxPower) {
    for (int i = 0; i < myPowerIndicators.size(); i ++) {
      Rectangle powerIndicator = myPowerIndicators.get(i);
      if (i < newPower) {
        powerIndicator.setFill(Color.color(0.0,1.0,0.0));
      }
      else {
        powerIndicator.setFill(Color.TRANSPARENT);
      }
    }
  }

  @Override
  public void reactToCharge(double charge, double maxCharge) {
    double height = (charge/maxCharge) * (myHeight - CHARGE_BAR_WIDTH);
    double maxHeight = 1.0 * (myHeight - CHARGE_BAR_WIDTH);
    myChargeIndicator.setY(myY + (0.5 * CHARGE_BAR_WIDTH) + maxHeight - height);
    myChargeIndicator.setHeight(height);
//    for (int i = 0; i < myChargeIndicators.size(); i ++) {
//      Rectangle chargeBar = myChargeIndicators.get(i);
//      if (i < charge) {
//        chargeBar.setFill(Color.YELLOW);
//      }
//      else {
//        chargeBar.setFill(Color.TRANSPARENT);
//      }
//    }
  }
}
