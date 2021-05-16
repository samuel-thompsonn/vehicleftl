package vehicleftl.visualizer.interactiveelements;

import vehicleftl.visualizer.interactiveelements.util.ReactionMap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class ReactionMappedElement implements InteractiveUIElement {

  private final ReactionMap myReactions;

  public ReactionMappedElement() {
    myReactions = new UILooksMapReader().getLooksMap(this, getElementType());
  }

  @Override
  public void reactToUserInput(String UIState, String inputType, String UITarget) {
    String targeted = (getID().equals(UITarget))? "True" : "False";
    Method reaction = myReactions.get(UIState, inputType, targeted);
    executeLook(reaction);
  }

  protected abstract void executeLook(Method m);
}
