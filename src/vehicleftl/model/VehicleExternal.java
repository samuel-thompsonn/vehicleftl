package vehicleftl.model;

public interface VehicleExternal {
   public void assignCrewmateToRoom(String crewID, String roomID);

   public void targetWeaponToRoom(String weaponID, String roomID);

   public void changeSystemPower(String systemID, int powerChange);

   public void changeWeaponPower(String weaponID, boolean powered);
}
