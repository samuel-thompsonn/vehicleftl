package vehicleftl.visualizer;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import vehicleftl.model.Room;
import vehicleftl.model.Vehicle;
import vehicleftl.model.VehicleListener;
import vehicleftl.visualizer.interactiveelements.RoomVisualizer;
import vehicleftl.visualizer.interactiveelements.VehicleRoomVisualizer;

import java.util.ArrayList;
import java.util.List;

public class FtlVehicleVisualizer implements VehicleVisualizer, VehicleListener {

  private Vehicle myVehicle;
  private double myX;
  private double myY;
  private Group myGroup;
  private Group myShieldVisualizer;
  private Group myRoomVisualizerGroup;
  private List<RoomVisualizer> myRoomVisualizers;
  private List<Rectangle> myShieldRectangles;
  private Rectangle myShieldVisual;

  public FtlVehicleVisualizer(Vehicle vehicle, double x, double y) {
    myVehicle = vehicle;
    myX = x;
    myY = y;
    myGroup = new Group();
    myShieldVisualizer = new Group();
    myRoomVisualizerGroup = new Group();
    myGroup.getChildren().add(myRoomVisualizerGroup);
    myRoomVisualizers = new ArrayList<>();
    initRoomVisualizers();
    myShieldRectangles = new ArrayList<>();
    myVehicle.subscribe(this);
    myShieldVisualizer = new Group();
    myGroup.getChildren().add(myShieldVisualizer);
  }

  private void initRoomVisualizers() {
    for (int i = 0; i < myVehicle.getRooms().size(); i ++) {
      Room room = myVehicle.getRooms().get(i);
      RoomVisualizer roomVis = new VehicleRoomVisualizer(myX + (2 * 50 * i),myY,2,2,room);
      myRoomVisualizerGroup.getChildren().add(roomVis.getGroup());
      myRoomVisualizers.add(roomVis);
    }
  }

  @Override
  public void reactToShieldChange(double shieldAmount, int maxShield) {
    myShieldVisualizer.getChildren().clear();
    for (int i = 0; i < Math.floor(shieldAmount); i ++) {
      double margin = (25.0/maxShield) * (maxShield - i);
      Rectangle shieldRect = new Rectangle(myX+margin,myY+margin,350-(2*margin),200-(2*margin));
      shieldRect.setFill(Color.color(0,0.4,1.0,0.25));
      myShieldRectangles.add(shieldRect);
      myShieldVisualizer.getChildren().add(shieldRect);
    }
  }

  @Override
  public Group getGroup() {
    return myGroup;
  }

  @Override
  public List<RoomVisualizer> getRoomVisualizers() {
    return myRoomVisualizers;
  }
}
