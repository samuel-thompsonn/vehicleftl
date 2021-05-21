package vehicleftl.visualizer.interactiveelements;

import javafx.scene.Group;
import vehicleftl.visualizer.interactiveelements.util.ReactionClassMap;

public abstract class ReactionClassedElement implements InteractiveUIElement {

  private final ReactionClassMap myReactions;
  private final Group myGroup;
  private InterfaceLook myCurrentLook;

  public ReactionClassedElement(Object... args) {
    myReactions = new UILooksClassReader().getLooksMap(this, getElementType(), args);
    myGroup = new Group();
  }

  @Override
  public Group getGroup() {
    return myGroup;
  }

  @Override
  public void update(double elapsedTime) {
    if (myCurrentLook == null) { return; }
    myCurrentLook.update(elapsedTime);
  }

  @Override
  public void reactToUserInput(String UIState, String inputType, String UITarget) {
    String targeted = (getID().equals(UITarget))? "True" : "False";
    myCurrentLook = myReactions.get(UIState, inputType, targeted, getStateInfo());
    myGroup.getChildren().clear();
    myGroup.getChildren().add(myCurrentLook.getGroup());
  }
}
