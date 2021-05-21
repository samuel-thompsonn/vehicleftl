package vehicleftl.visualizer.interactiveelements.weaponvisualizer;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import vehicleftl.model.Weapon;

import java.util.HashMap;
import java.util.Map;

public class ColoredWeaponLook extends StandardWeaponLook {

  private Color myBaseColor;
  private Color myCurrentColor;
  private Map<String, Color> myStateColors;
  private boolean isCharged;
  private boolean isPowered;

  public ColoredWeaponLook(Weapon weapon, double x, double y, Map<String, String> colorArgs) {
    super(weapon, x, y, colorArgs);
    isCharged = false;
    isPowered = false;
    myBaseColor = Color.color(Double.parseDouble(colorArgs.get("r")), Double.parseDouble(colorArgs.get("g")), Double.parseDouble(colorArgs.get("b")), 0.5);
  }

  @Override
  protected void changeBorderForPower(boolean powered, Rectangle borderRect) {
    isPowered = powered;
    updateStatus(borderRect);
  }

  @Override
  protected void changeBorderForCharge(boolean charged, Rectangle borderRect) {
    isCharged = charged;
    updateStatus(borderRect);
  }

  private void updateStatus(Rectangle borderRect) {
    if (isCharged) {
      myCurrentColor = myStateColors.get("Charged");
    }
    else if (isPowered) {
      myCurrentColor = myStateColors.get("Charging");
    }
    else {
      myCurrentColor = myStateColors.get("Unpowered");
    }
    borderRect.setFill(myCurrentColor);
  }

  @Override
  public void initBorder(Rectangle borderRect, Map<String, String> args) {
    myBaseColor = Color.color(Double.parseDouble(args.get("r")), Double.parseDouble(args.get("g")), Double.parseDouble(args.get("b")), 0.5);
    myCurrentColor = myBaseColor;
    borderRect.setFill(myCurrentColor);
    System.out.println("myColor = " + myCurrentColor);
    myStateColors = new HashMap<>();
    myStateColors.put("Unpowered", Color.color(myBaseColor.getRed()*0.25, myBaseColor.getGreen()*0.25, myBaseColor.getGreen()*0.25, myBaseColor.getOpacity()));
    myStateColors.put("Charging", myBaseColor);
    myStateColors.put("Charged", Color.color(Math.min(myBaseColor.getRed()*2+.25,1.0), Math.min(myBaseColor.getGreen()*2+.25,1.0), Math.min(myBaseColor.getGreen()*2+.25,1.0), myBaseColor.getOpacity()));
  }
}
