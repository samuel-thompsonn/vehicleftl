package vehicleftl.visualizer.interactiveelements.roomvisualizer;

import vehicleftl.model.Room;
import vehicleftl.visualizer.interactiveelements.ReactionClassedElement;

public class VehicleRoomVisualizer extends ReactionClassedElement implements RoomVisualizer {

  private double myX;
  private double myY;
  private int myWidth;
  private int myHeight;
  private Room myRoom;

  public VehicleRoomVisualizer(double xPos, double yPos, int width, int height, Room room) {
    super(xPos, yPos, width, height, room);
    myX = xPos;
    myY = yPos;
    myWidth = width * 50;
    myHeight = height * 50;
    myRoom = room;
    myRoom.subscribe(this);
    reactToUserInput("Default", "Any", "False");
  }

  @Override
  public String getID() {
    return myRoom.getID();
  }

  public boolean pointInBounds(double clickX, double clickY) {
    return clickX > myX && clickX < myX + myWidth && clickY > myY && clickY < myY + myHeight;
  }

  @Override
  public double getCenterX() {
    return myX + myRoom.getWidth() * (50/2.);
  }

  @Override
  public double getCenterY() {
    return myY + myRoom.getHeight() * (50/2.);
  }

  @Override
  public String getElementType() {
    return "Room";
  }

  @Override
  public String getStateInfo() {
    return null;
  }

  @Override
  public Room getRoom() {
    return myRoom;
  }

  @Override
  public void reactToDamage(int level, int damage) {
    //handled by component
  }

  @Override
  public boolean blockAttack(int damage) {
    return false;
  }

  @Override
  public void reactToTargeted(boolean targeted) {
    //handled by component
  }
}
