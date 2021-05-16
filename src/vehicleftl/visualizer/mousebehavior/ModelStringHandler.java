package vehicleftl.visualizer.mousebehavior;

import vehicleftl.model.ModelExternal;

public class ModelStringHandler {
  public void executeAction(String type, String source, String target, ModelExternal model) {
    if (type == null) {
      return;
    }
    switch (type) {
      case "IncreaseWeaponPower" -> model.changeWeaponPower(target, true);
      case "DecreaseWeaponPower" -> model.changeWeaponPower(target, false);
      case "TargetRoomWithWeapon" -> model.targetWeaponToRoom(source, target);
      case "CancelWeaponTarget" -> model.targetWeaponToRoom(source, null);
      case "AssignCrewmateToRoom" -> model.assignCrewmateToRoom(source, target);
      case "ReduceSystemPower" -> model.changeSystemPower(target, 1);
      case "IncreaseSystemPower" -> model.changeSystemPower(target, -1);
    }
  }
}
