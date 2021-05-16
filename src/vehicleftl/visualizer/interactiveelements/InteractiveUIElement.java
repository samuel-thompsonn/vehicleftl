package vehicleftl.visualizer.interactiveelements;

import javafx.scene.Group;

public interface InteractiveUIElement {
  public String getID();

  public boolean pointInBounds(double x, double y);

  public Group getGroup();

  public String getElementType();

  public String getStateInfo();

  default void reactToHover(String UIState) {
    //does nothing by default
  }

  default void reactToNoHover(String UIState) {
    //does nothing by default
  }

  default void reactToStateChange(String newState, String target) {
    //does nothing by default
  }

  default public void reactToUserInput(String UIState, String inputType, String UITarget) {
    //does nothing by default
  }
}
