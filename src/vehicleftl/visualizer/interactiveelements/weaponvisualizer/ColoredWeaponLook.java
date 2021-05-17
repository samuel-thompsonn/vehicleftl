package vehicleftl.visualizer.interactiveelements.weaponvisualizer;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import vehicleftl.model.Weapon;

import java.util.Map;

public class ColoredWeaponLook extends StandardWeaponLook {

  private Color myColor;

  public ColoredWeaponLook(Weapon weapon, double x, double y, Map<String, String> colorArgs) {
    super(weapon, x, y, colorArgs);
  }

  @Override
  public void initBorder(Rectangle borderRect, Map<String, String> args) {
    myColor = Color.color(Double.parseDouble(args.get("r")), Double.parseDouble(args.get("g")), Double.parseDouble(args.get("b")), 0.5);
    borderRect.setFill(myColor);
    System.out.println("myColor = " + myColor);
  }
}
