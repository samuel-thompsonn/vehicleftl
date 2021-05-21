package vehicleftl.visualizer.interactiveelements.weaponvisualizer;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import vehicleftl.model.Weapon;

import java.util.Map;

public class FlashingWeaponLook extends StandardWeaponLook {

  private double myCurrentTime;
  private Color myColor;
  private Rectangle myBorderRect;
  private double myPeriod;

  public FlashingWeaponLook(Weapon weapon, double x, double y, Map<String, String> params) {
    super(weapon, x, y, params);
  }

  @Override
  public void initBorder(Rectangle borderRect, Map<String, String> args) {
    myColor = Color.color(Double.parseDouble(args.get("r")), Double.parseDouble(args.get("g")), Double.parseDouble(args.get("b")), 0.5);
    myPeriod = Double.parseDouble(args.get("period"));
    borderRect.setFill(myColor);
    myBorderRect = borderRect;
  }

  @Override
  public void update(double elapsedTime) {
    myCurrentTime += elapsedTime;
    myCurrentTime = myCurrentTime % (myPeriod);
    if (myBorderRect == null) { return; }
    double currentVal = 0.5+(0.5*Math.sin((2*Math.PI*myCurrentTime) / myPeriod));
    Color currentColor = Color.color(myColor.getRed()*currentVal, myColor.getGreen()*currentVal,myColor.getBlue()*currentVal, myColor.getOpacity());
    myBorderRect.setFill(currentColor);
  }
}
