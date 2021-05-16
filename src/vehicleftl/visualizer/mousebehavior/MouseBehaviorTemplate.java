package vehicleftl.visualizer.mousebehavior;

import java.util.List;

public class MouseBehaviorTemplate {
  private List<MouseReaction> myReactions;
  private String myName;

  public MouseBehaviorTemplate(List<MouseReaction> reactions, String name) {
    myReactions = reactions;
    myName = name;
  }

  public MouseStatefulBehavior createInstance(MouseBehaviorFactory factory, String elementId) {
    return new MouseStatefulBehavior(factory, myReactions, myName, elementId);
  }
}
