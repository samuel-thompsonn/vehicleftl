package vehicleftl.visualizer.interactiveelements.weaponvisualizer;

import vehicleftl.model.Room;
import vehicleftl.model.Weapon;
import vehicleftl.model.WeaponListener;
import vehicleftl.visualizer.interactiveelements.*;
import vehicleftl.visualizer.interactiveelements.util.ReactionClassMap;

public class VehicleWeaponInterface extends ReactionClassedElement implements WeaponInterfaceVisualizer, WeaponListener {

  public static final int DISPLAY_WIDTH_BASE = 100;
  public static final int DISPLAY_HEIGHT_BASE = 75;
  public static final double SCALE = 0.8;
  public static final double DISPLAY_WIDTH = DISPLAY_WIDTH_BASE * SCALE;
  public static final double DISPLAY_HEIGHT = DISPLAY_HEIGHT_BASE * SCALE;

  private double myX;
  private double myY;
  private double myWidth;
  private double myHeight;
  private Weapon myWeapon;
  private RoomVisualizer myTargetVis;
  private boolean isPowered;
  private StandardWeaponLook myLook;
  private ReactionClassMap myReactions;

  public VehicleWeaponInterface(Weapon weapon, double x, double y) {
    super(weapon, x, y);
    myLook = new InactiveWeaponLook(weapon, x, y);
    myTargetVis = null;
    myX = x;
    myY = y;
    myWidth = DISPLAY_WIDTH;
    myHeight = DISPLAY_HEIGHT;
    myWeapon = weapon;
    weapon.subscribe(this);
    reactToUserInput("Default", "Any", "False");
  }

  @Override
  public void setTarget(Room room, RoomVisualizer visualizer) {
    //handled by component
  }

  @Override
  public boolean pointInBounds(double x, double y) {
    return (x > myX && x < myX + myWidth && y > myY && y < myY + myHeight);
  }

  @Override
  public void setSelected(boolean selected) {
    //do nothing since it's handled by my components
  }

  @Override
  public String getWeaponId() {
    return myWeapon.getID();
  }

  @Override
  public String getElementType() {
    return "WeaponPanel";
  }

  @Override
  public String getStateInfo() {
    return (isPowered)? "Powered" : "Unpowered";
  }

  @Override
  public String getID() {
    return myWeapon.getID();
  }

  @Override
  public void reactToPowerChange(int newPower, int maxPower) {
    isPowered = newPower == maxPower;
  }

  @Override
  public void reactToCharge(double charge, double maxCharge) {
    //handled by component
  }
}
