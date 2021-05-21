package vehicleftl.visualizer.interactiveelements;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import vehicleftl.model.FtlVehicle;
import vehicleftl.model.VehicleWeapon;
import vehicleftl.model.Weapon;
import vehicleftl.visualizer.interactiveelements.util.MultiKeyMap;
import vehicleftl.visualizer.interactiveelements.util.ReactionClassMap;
import vehicleftl.visualizer.interactiveelements.util.ThreeKeyMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UILooksClassReader {
  private static final String FILENAME = "src/vehicleftl/visualizer/interactiveelements/data/classful/stateful_look_classes.xml";
  private static final String NICKNAMES_PATH = "src/vehicleftl/visualizer/interactiveelements/data/classful/classnicknames/";
  private static final String CLASS_PATH = "vehicleftl.visualizer.interactiveelements.";

  public ReactionClassMap getLooksMap(InteractiveUIElement uiElement, String type, Object... args) {
//    ThreeKeyMap<String, String, String, InterfaceLook> looksMap = new ThreeKeyMap<>();
    MultiKeyMap<String, InterfaceLook> looksMap = new MultiKeyMap<>();
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(new File(FILENAME));
      NodeList looks = doc.getElementsByTagName("States");
      Element looksList = findElementWithAttribute(looks, "name", type);
      Map<String, String> nicknameMap = getNicknames(looksList);
      Map<String, Map<String, String>> argsMap = getNicknameArgs(looksList);
      NodeList reactions = looksList.getElementsByTagName("Reaction");
      for (int i = 0; i < reactions.getLength(); i ++) {
        Element reaction = (Element)reactions.item(i);
        String state = reaction.getAttribute("state");
        String input = reaction.getAttribute("action");
        String targeted = reaction.getAttribute("targeted");
        if (targeted.equals("")) { targeted = "Any"; }
        String extraInfo = reaction.getAttribute("extra");
        if (extraInfo.equals("")) { extraInfo = "Any"; }
        String result = reaction.getAttribute("look");
        Class look = Class.forName(CLASS_PATH.concat(nicknameMap.get(result)));
        Object[] newArgs = appendExtraArg(argsMap.get(result),args);
        InterfaceLook lookObject = (InterfaceLook)look.getConstructors()[0].newInstance(newArgs);
        looksMap.put(lookObject, state, input, targeted, extraInfo);
      }
    } catch (ParserConfigurationException | SAXException | IOException | ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
      e.printStackTrace();
    }
    return new ReactionClassMap(looksMap);
  }

  private Object[] appendExtraArg(Map<String, String> extraArgs, Object... args) {
    if (extraArgs.isEmpty()) {
      return args;
    }
    Object[] newArgs = new Object[args.length+1];
    System.arraycopy(args, 0, newArgs, 0, args.length);
      newArgs[args.length] = extraArgs;
    return newArgs;
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
        String methodName = nickname.getAttribute("class");
        String methodNick = nickname.getAttribute("alias");
        nicknameMap.put(methodNick, methodName);
      }
    } catch (ParserConfigurationException | SAXException | IOException e) {
      e.printStackTrace();
    }
    return nicknameMap;
  }

  private Map<String, Map<String, String>> getNicknameArgs(Element element) {
    Map<String, Map<String, String>> nicknameArgs = new HashMap<>();
    String filename = ((Element)element.getElementsByTagName("Nicknames").item(0)).getAttribute("file");
    String filepath = NICKNAMES_PATH.concat(filename);
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(new File(filepath));
      NodeList nickList = doc.getElementsByTagName("Nickname");
      for (int i = 0; i < nickList.getLength(); i ++) {
        Map<String, String> paramMap = new HashMap<>();
        Element nickname = (Element)nickList.item(i);
        NodeList argList = nickname.getElementsByTagName("Param");
        String methodNick = nickname.getAttribute("alias");
        for (int j = 0; j < argList.getLength(); j ++) {
          String label =  ((Element)argList.item(j)).getAttribute("label");
          String value =  ((Element)argList.item(j)).getAttribute("value");
          System.out.println("label, value = " + label + ", " + value);
          paramMap.put(label, value);
        }
        nicknameArgs.put(methodNick, paramMap);
      }
    } catch (ParserConfigurationException | SAXException | IOException e) {
      e.printStackTrace();
    }
    return nicknameArgs;
  }

  public static void main(String[] args) {
    FtlVehicle vehicle = new FtlVehicle();
    Weapon weapon = new VehicleWeapon(1, 1, 1, vehicle);
    Class cls = null;
    try {
      cls = Class.forName("vehicleftl.visualizer.interactiveelements.weaponvisualizer.ColoredWeaponLook");
    } catch (ClassNotFoundException e) {
      System.out.println("e.getMessage() = " + e.getMessage());
      e.printStackTrace();
      return;
    }
    List<Object> argList = new ArrayList<>();
    argList.add(weapon);
    argList.add(0);
    argList.add(1);
    Constructor lookConstructor = cls.getConstructors()[0];
    testAppendArgs(lookConstructor, weapon, 0, 1);
  }

  private static void testAppendArgs(Constructor constructor, Object... args) {
    Object[] newArgs = new Object[args.length+3];
    System.arraycopy(args, 0, newArgs, 0, args.length);
    newArgs[args.length] = "0.7";
    newArgs[args.length+1] = "0.5";
    newArgs[args.length+2] = "0.0";
    System.out.println("newArgs.length = " + newArgs.length);
    try {
      InterfaceLook look = (InterfaceLook)constructor.newInstance(newArgs);
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
  }
}
