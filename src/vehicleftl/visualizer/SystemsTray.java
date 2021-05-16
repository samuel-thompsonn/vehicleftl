package vehicleftl.visualizer;

import vehicleftl.visualizer.interactiveelements.ClickableUIElement;
import vehicleftl.visualizer.interactiveelements.SystemVisualizer;

import java.util.List;

public interface SystemsTray extends ClickableUIElement {

  public List<SystemVisualizer> getSystemVisualizers();
}
