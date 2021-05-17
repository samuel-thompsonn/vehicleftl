package vehicleftl.visualizer.interactiveelements.weaponvisualizer;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import vehicleftl.model.Weapon;

public class ActiveWeaponLook extends StandardWeaponLook {
  public ActiveWeaponLook(Weapon weapon, double x, double y) {
    super(weapon, x, y);
  }

  @Override
  public void initBorder(Rectangle borderRect) {
    borderRect.setFill(Color.color(0,0,0,0.0));
  }
}
