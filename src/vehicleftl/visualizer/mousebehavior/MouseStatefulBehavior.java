package vehicleftl.visualizer.mousebehavior;

import vehicleftl.model.VehicleFtlModel;

import java.util.ArrayList;
import java.util.List;
public class MouseStatefulBehavior {
  private String myName;
  private List<MouseReaction> myReactions;
  private MouseBehaviorFactory myFactory;
  private List<StatefulBehaviorListener> myBehaviorListeners;
  private String myElementId;
  private ModelStringHandler myActionHandler;

  public MouseStatefulBehavior(MouseBehaviorFactory factory, List<MouseReaction> reactions, String name, String elementId) {
    myBehaviorListeners = new ArrayList<>();
    myReactions = reactions;
    myFactory = factory;
    myName = name;
    myElementId = elementId;
    myActionHandler = new ModelStringHandler();
  }

  public void respondToAction(String elementType, String inputType, String stateInfo, String targetId, VehicleFtlModel model) {
    MouseReaction reaction = findReaction(elementType,inputType,stateInfo);
    //executeAction(myName, myElementId, elementId)
    if (reaction == null) {
      return;
    }
    System.out.println("Execute action: " + reaction.getExecutedAction());
    myActionHandler.executeAction(reaction.getExecutedAction(), myElementId, targetId, model);
    if (reaction.getNextState() == null) {
      return;
    }
    myBehaviorListeners.forEach(listener-> listener.reactToStateChange(myFactory.newState(reaction.getNextState(), targetId)));
    System.out.println("Behavior is switching state: " + reaction.getNextState() + " (with id " + targetId + ")");
  }

  private MouseReaction findReaction(String elementType, String inputType, String stateInfo) {
    if (stateInfo == null || stateInfo.equals("None")) {
      stateInfo = "";
    }
    for (MouseReaction reaction : myReactions) {
      if (reaction.matchesInput(elementType, inputType, stateInfo)) {
        return reaction;
      }
    }
    return null;
  }

  public void subscribe(StatefulBehaviorListener listener) {
    myBehaviorListeners.add(listener);
  }

  @Override
  public String toString() {
    return myName;
  }

  public String getType() {
    return myName;
  }

  public String getElementId() {
    return myElementId;
  }
}
