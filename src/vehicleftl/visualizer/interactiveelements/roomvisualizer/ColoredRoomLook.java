package vehicleftl.visualizer.interactiveelements.roomvisualizer;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import vehicleftl.model.Room;

import java.util.Map;

public class ColoredRoomLook extends StandardRoomLook {

  public ColoredRoomLook(double xPos, double yPos, int width, int height, Room room, Map<String, String> params) {
    super(xPos, yPos, width, height, room, params);
  }

  @Override
  protected void initHighlight(Rectangle highlightPlane, Map<String, String> params) {
    highlightPlane.setStroke(Color.TRANSPARENT);
    double opacity = 0.5;
    if (params.get("opacity") != null) {
      System.out.println("Dealing with null opacity");
      opacity = Double.parseDouble(params.get("opacity"));
    }
    Color fillColor = Color.color(Double.parseDouble(params.get("r")), Double.parseDouble(params.get("g")), Double.parseDouble(params.get("b")), opacity);
    highlightPlane.setFill(fillColor);
  }
}
