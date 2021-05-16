package vehicleftl.visualizer.mousebehavior;

public class MouseReaction {
  private String elementType;
  private String inputType;
  private String stateInfo;
  private String nextState;
  private String executedAction;

  public MouseReaction(String element, String input, String state, String next, String action) {
    elementType = element;
    inputType = input;
    stateInfo = state;
    nextState = next;
    executedAction = action;
  }

  public String getElementType() {
    return elementType;
  }

  public String getInputType() {
    return inputType;
  }

  public String getStateInfo() {
    return stateInfo;
  }

  public String getExecutedAction() {
    return executedAction;
  }

  public String getNextState() {
    return nextState;
  }

  public boolean matchesInput(String elementType, String inputType, String stateInfo) {
    if (stateInfo == null) {
      stateInfo = "";
    }
    return ((getElementType().equals(elementType) || getElementType().equals("Any")) &&
            getInputType().equals(inputType) &&
            getStateInfo().equals(stateInfo));
  }
}
