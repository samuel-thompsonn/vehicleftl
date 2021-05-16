package vehicleftl.visualizer.mousebehavior;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MouseBehaviorFactory {
  public static final String FILENAME = "src/vehicleftl/visualizer/mousebehavior/data/select_state.xml";
  private final Map<String, MouseBehaviorTemplate> myTemplates;

  public MouseBehaviorFactory() {
    myTemplates = new HashMap<>();
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(new File(FILENAME));
      NodeList statesList = doc.getElementsByTagName("UIState");
      for (int j = 0; j < statesList.getLength(); j ++) {
        readUiState((Element)statesList.item(j), doc);
      }
    } catch (ParserConfigurationException | SAXException | IOException e) {
      e.printStackTrace();
    }
  }

  private void readUiState(Element uiState, Document doc) {
    String stateName = uiState.getAttribute("name");
    System.out.println("State " + stateName + ":\n===============");
    NodeList list = uiState.getElementsByTagName("Reaction");
    List<MouseReaction> reactions = new ArrayList<>();
    for (int i = 0; i < list.getLength(); i ++) {
      Element element = (Element)list.item(i);
      Element input = (Element)element.getElementsByTagName("Input").item(0);
      String uiElement = input.getAttribute("element");
      String inputType = input.getAttribute("input");
      String stateInfo = input.getAttribute("state");
      Element result = (Element)element.getElementsByTagName("Result").item(0);
      String nextState = result.getAttribute("state");
      String action = result.getAttribute("action");
      System.out.println("Element " + uiElement + " with action " + inputType + " with state " + stateInfo + ":");
      if (!nextState.equals("")) {
        System.out.println("Results in state " + nextState + ".");
      }
      else {
        nextState = null;
      }
      if (!action.equals("")) {
        System.out.println("Results in action " + action + ".");
      }
      else {
        action = null;
      }
      reactions.add(new MouseReaction(uiElement, inputType, stateInfo, nextState, action));
    }
    myTemplates.put(stateName, new MouseBehaviorTemplate(reactions, stateName));
  }

  public MouseStatefulBehavior newState(String type, String elementId) {
    return myTemplates.get(type).createInstance(this, elementId);
  }

  public static void main(String[] args) {
    MouseBehaviorFactory factory = new MouseBehaviorFactory();
  }
}
