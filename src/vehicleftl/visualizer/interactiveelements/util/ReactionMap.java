package vehicleftl.visualizer.interactiveelements.util;

import java.lang.reflect.Method;

public class ReactionMap {

  private ThreeKeyMap<String, String, String, Method> myInternalMap;

  public ReactionMap(ThreeKeyMap<String, String, String, Method> mappings) {
    myInternalMap = mappings;
  }

  public Method get(String state, String input, String targeted) {
    Method reaction = myInternalMap.get(state, input, targeted);
    if (reaction == null) {
      reaction = myInternalMap.get(state, input, "Any");
    }
    if (reaction == null) {
      reaction = myInternalMap.get(state, "Any", targeted);
    }
    if (reaction == null) {
      reaction = myInternalMap.get(state, "Any", "Any");
    }
    if (reaction == null) {
      reaction = myInternalMap.get("Default", "Any", "Any");
    }
    return reaction;
  }
}
