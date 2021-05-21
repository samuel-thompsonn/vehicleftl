package vehicleftl.visualizer.interactiveelements.crewvisualizer;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import vehicleftl.model.Crewmate;

import java.util.Map;

public class ColoredCrewLook extends StandardCrewLook {
  private Color myColor;

  public ColoredCrewLook(double xOffset, double yOffset, Crewmate crewmate, Map<String, String> params) {
    super(xOffset, yOffset, crewmate, params);
  }

  @Override
  protected void handleParams(Map<String, String> params) {
    double r = Double.parseDouble(params.get("r"));
    double g = Double.parseDouble(params.get("g"));
    double b = Double.parseDouble(params.get("b"));
    myColor = Color.color(r, g, b, 0.5);
  }

  @Override
  protected void initLook(Circle baseCircle, Circle highlightCircle) {
    highlightCircle.setFill(myColor);
    highlightCircle.setStroke(Color.TRANSPARENT);
  }
}
