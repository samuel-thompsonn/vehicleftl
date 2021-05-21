package vehicleftl.visualizer.interactiveelements;

import javafx.scene.Group;

public interface InterfaceLook {
  public Group getGroup();

  default void update(double elapsedTime) {
    //does nothing by default
  }
}
