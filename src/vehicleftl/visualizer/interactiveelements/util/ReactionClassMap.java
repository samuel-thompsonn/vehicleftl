package vehicleftl.visualizer.interactiveelements.util;

import vehicleftl.visualizer.interactiveelements.InterfaceLook;

import java.lang.reflect.Method;

public class ReactionClassMap {

  private ThreeKeyMap<String, String, String, InterfaceLook> myInternalMap;

  public ReactionClassMap(ThreeKeyMap<String, String, String, InterfaceLook> mappings) {
    myInternalMap = mappings;
  }

  public InterfaceLook get(String state, String input, String targeted) {
    InterfaceLook reaction = myInternalMap.get(state, input, targeted);
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
