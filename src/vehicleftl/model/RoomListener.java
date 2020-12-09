package vehicleftl.model;

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
}
