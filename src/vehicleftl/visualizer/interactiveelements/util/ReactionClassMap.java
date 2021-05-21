package vehicleftl.visualizer.interactiveelements.util;

import vehicleftl.visualizer.interactiveelements.InterfaceLook;

import java.lang.reflect.Method;

public class ReactionClassMap {

  private MultiKeyMap<String, InterfaceLook> myInternalMap;

  public ReactionClassMap(MultiKeyMap<String, InterfaceLook> mappings) {
    myInternalMap = mappings;
  }

  public InterfaceLook get(String state, String input, String targeted, String extraInfo) {
    InterfaceLook reaction = myInternalMap.get(state, input, targeted, extraInfo);
    if (reaction == null) {
      reaction = myInternalMap.get(state, input, targeted, "Any");
    }
    if (reaction == null) {
      reaction = myInternalMap.get(state, input, "Any", extraInfo);
    }
    if (reaction == null) {
      reaction = myInternalMap.get(state, input, "Any", "Any");
    }
    if (reaction == null) {
      reaction = myInternalMap.get(state, "Any", targeted, "Any");
    }
    if (reaction == null) {
      reaction = myInternalMap.get(state, "Any", "Any", "Any");
    }
    if (reaction == null) {
      reaction = myInternalMap.get("Default", "Any", "Any", "Any");
    }
    return reaction;
  }
}
