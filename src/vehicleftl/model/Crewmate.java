package vehicleftl.model;

import vehicleftl.model.pathfinding.Person;

/**
 * This will have behavior related specifically to working with
 * vehicles, like skills and combat abilities
 */
public interface Crewmate extends Person {
  public String getID();
}
