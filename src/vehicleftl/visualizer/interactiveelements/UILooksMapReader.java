package vehicleftl.visualizer.interactiveelements;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import vehicleftl.model.VehicleRoom;
import vehicleftl.visualizer.interactiveelements.util.ReactionMap;
import vehicleftl.visualizer.interactiveelements.util.ThreeKeyMap;
import vehicleftl.visualizer.interactiveelements.util.TwoKeyMap;
import vehicleftl.visualizer.interactiveelements.util.UserInputReaction;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class UILooksMapReader {
  private static final String FILENAME = "src/vehicleftl/visualizer/interactiveelements/data/stateful_look.xml";
  private static final String NICKNAMES_PATH = "src/vehicleftl/visualizer/interactiveelements/data/methodnicknames/";

  public ReactionMap getLooksMap(InteractiveUIElement uiElement, String type) {
    ThreeKeyMap<String, String, String, Method> looksMap = new ThreeKeyMap<>();
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(new File(FILENAME));
      NodeList looks = doc.getElementsByTagName("States");
      Element looksList = findElementWithAttribute(looks, "name", type);
      Map<String, String> nicknameMap = getNicknames(looksList);
      NodeList reactions = looksList.getElementsByTagName("Reaction");
      for (int i = 0; i < reactions.getLength(); i ++) {
        Element reaction = (Element)reactions.item(i);
        String state = reaction.getAttribute("state");
        String input = reaction.getAttribute("action");
        String targeted = reaction.getAttribute("targeted");
        if (targeted.equals("")) { targeted = "Any"; }
        String result = reaction.getAttribute("look");
        Method resultMethod = uiElement.getClass().getDeclaredMethod(nicknameMap.get(result));
        looksMap.put(state, input, targeted, resultMethod);
      }
    } catch (ParserConfigurationException | SAXException | IOException | NoSuchMethodException e) {
      e.printStackTrace();
    }
    return new ReactionMap(looksMap);
  }

  private Element findElementWithAttribute(NodeList nodeList, String attName, String attVal) {
    for (int i = 0; i < nodeList.getLength(); i ++) {
      Element element = (Element)nodeList.item(i);
      if (element.getAttribute(attName).equals(attVal)) {
        return element;
      }
    }
    return null;
  }

  private Map<String, String> getNicknames(Element element) {
    Map<String, String> nicknameMap = new HashMap<>();
    String filename = ((Element)element.getElementsByTagName("Nicknames").item(0)).getAttribute("file");
    String filepath = NICKNAMES_PATH.concat(filename);
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(new File(filepath));
      NodeList nickList = doc.getElementsByTagName("Nickname");
      for (int i = 0; i < nickList.getLength(); i ++) {
        Element nickname = (Element)nickList.item(i);
        String methodName = nickname.getAttribute("method");
        String methodNick = nickname.getAttribute("alias");
        nicknameMap.put(methodNick, methodName);
      }
    } catch (ParserConfigurationException | SAXException | IOException e) {
      e.printStackTrace();
    }
    return nicknameMap;
  }

  public static void main(String[] args) {
    VehicleRoomVisualizer vis = new VehicleRoomVisualizer(0,0,0,0,new VehicleRoom(0,0,0,0,0));
    UILooksMapReader reader = new UILooksMapReader();
    reader.getLooksMap(vis, "RoomVisualizer");
  }
}
