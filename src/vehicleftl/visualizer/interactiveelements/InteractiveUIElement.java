package vehicleftl.visualizer.interactiveelements;

import javafx.scene.Group;

public interface InteractiveUIElement {
  public String getID();

  public boolean pointInBounds(double x, double y);

  public Group getGroup();

  public String getElementType();

  //Returns any component specific info, such as whether a weapon is powered.
  default public String getStateInfo() {
    return "None";
  }

  default void update(double elapsedTime) {
    //does nothing by default
  }

  default public void reactToUserInput(String UIState, String inputType, String UITarget) {
    //does nothing by default
  }
}
