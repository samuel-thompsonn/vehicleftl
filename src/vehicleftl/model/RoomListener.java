package vehicleftl.model;

//TODO: Split this up into internal and external, since the visualizer should not be consulted for blocking attacks
public interface RoomListener {

  /**
   *
   * @param damage
   * @return True if the attack is successfully nullified.
   */
  public boolean blockAttack(int damage);

  public void reactToDamage(int level, int damage);

  default void reactToManned(boolean manned) {
    // do nothing by default
  };

  default void reactToTargeted(boolean targeted) {
    //do nothing by default
  }
}
