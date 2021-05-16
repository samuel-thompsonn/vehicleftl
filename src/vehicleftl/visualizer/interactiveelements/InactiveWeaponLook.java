package vehicleftl.visualizer.interactiveelements;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import vehicleftl.model.Weapon;

public class InactiveWeaponLook extends StandardWeaponLook {
  public InactiveWeaponLook(Weapon weapon, double x, double y) {
    super(weapon, x, y);
  }

  @Override
  public void initBorder(Rectangle borderRect) {
    borderRect.setFill(Color.color(0,0,0,0.5));
  }
}
