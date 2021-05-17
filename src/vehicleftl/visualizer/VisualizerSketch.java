package vehicleftl.visualizer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import vehicleftl.model.*;
import vehicleftl.visualizer.interactiveelements.CrewVisualizer;
import vehicleftl.visualizer.interactiveelements.InteractiveUIElement;
import vehicleftl.visualizer.interactiveelements.RoomVisualizer;
import vehicleftl.visualizer.interactiveelements.VehicleCrewVisualizer;
import vehicleftl.visualizer.interactiveelements.VehicleWeaponInterface;
import vehicleftl.visualizer.interactiveelements.WeaponInterfaceVisualizer;
import vehicleftl.visualizer.mousebehavior.MouseBehaviorFactory;
import vehicleftl.visualizer.mousebehavior.MouseStatefulBehavior;
import vehicleftl.visualizer.mousebehavior.SelectBehavior;
import vehicleftl.visualizer.mousebehavior.StatefulBehaviorListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VisualizerSketch extends Application implements MouseListener, StatefulBehaviorListener {

  private List<WeaponInterfaceVisualizer> myWeaponVisualizers;
  private List<Room> myRooms;
  private Vehicle myVehicle;
  private List<CrewVisualizer> myCrew;
  private MouseBehavior myMouseBehavior;
  private MouseStatefulBehavior myMouseStatefulBehavior;
  private List<InteractiveUIElement> myInteractiveElements;

  @Override
  public void start(Stage primaryStage) throws Exception {
    myInteractiveElements = new ArrayList<>();
    myMouseBehavior = new SelectBehavior();
    initMouseBehavior();
    myMouseBehavior.subscribe(this);
    Group group = new Group();
    myWeaponVisualizers = new ArrayList<>();
    myRooms = new ArrayList<>();
    VehicleFtlModel model = new VehicleFtlModel();

    TextField commandField = new TextField();
    commandField.setTranslateX(450);
    commandField.setTranslateY(300);
    group.getChildren().add(commandField);
    Button submitButton = new Button("Submit");
    submitButton.setTranslateX(575);
    submitButton.setTranslateY(300);
    submitButton.setOnAction(event -> {
      processText(commandField.getText(),model);
    });
    group.getChildren().add(submitButton);

    Vehicle vehicle = new FtlVehicle();
    VehicleVisualizer vehicleVis = new FtlVehicleVisualizer(vehicle, 50, 50);
    trackInteractiveElements(vehicleVis);
    group.getChildren().add(vehicleVis.getGroup());
    myCrew = new ArrayList<>();
    for (Crewmate crewmate : vehicle.getCrew()) {
      CrewVisualizer crewVis = new VehicleCrewVisualizer(50,50,crewmate);
      group.getChildren().add(crewVis.getGroup());
      myCrew.add(crewVis);
      myInteractiveElements.add(crewVis);
    }
    SystemsTray systemsVis = new VehicleSystemsTray(vehicle,50,330);
    myInteractiveElements.addAll(systemsVis.getSystemVisualizers());
    group.getChildren().add(systemsVis.getGroup());
    myWeaponVisualizers = initWeaponVis(group,vehicle);
    model.addVehicle(vehicle);

    Vehicle secondVehicle = new FtlVehicle();
    VehicleVisualizer secondVehicleVis = new FtlVehicleVisualizer(secondVehicle, 400,50);
    group.getChildren().add(secondVehicleVis.getGroup());
    for (Crewmate crewmate : secondVehicle.getCrew()) {
      CrewVisualizer crewVis = new VehicleCrewVisualizer(400,50,crewmate);
      group.getChildren().add(crewVis.getGroup());
    }
    model.addVehicle(secondVehicle);

    Scene scene = new Scene(group,1024,600);
    scene.setOnMouseClicked(event -> {
      String inputType = "None";
      if (event.getButton().equals(MouseButton.SECONDARY)) {
        inputType = "Primary";
      }
      else if (event.getButton().equals(MouseButton.PRIMARY)) {
        inputType = "Secondary";
      }
      String elementType = "None";
      String stateInfo = "None";
      String elementID = "(None)";
      InteractiveUIElement element = getClickTarget(event.getX(), event.getY());
      if (element != null) {
        elementType = element.getElementType();
        elementID = element.getID();
        stateInfo = element.getStateInfo();
      }
      System.out.println("Element " + elementType + " (" + elementID + ") with action " + inputType + " with state " + stateInfo);
      myMouseStatefulBehavior.respondToAction(elementType, inputType, stateInfo, elementID, model);
      for (RoomVisualizer roomVis : secondVehicleVis.getRoomVisualizers()) {
        if (roomVis.pointInBounds(event.getX(),event.getY())) {
          return;
        }
      }
      resolveMouseHover(event, vehicleVis);
    });

    scene.setOnMouseMoved(event -> {
      resolveMouseHover(event, vehicleVis);
    });

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1/60.), event -> {
      vehicle.update(1/60.);
    }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();

    primaryStage.setScene(scene);
    primaryStage.setTitle("Vehicle FTL");
    primaryStage.show();
  }

  private InteractiveUIElement getClickTarget(double x, double y) {
    InteractiveUIElement returnedElement = null;
    for (InteractiveUIElement element : myInteractiveElements) {
      if (element.pointInBounds(x, y)) {
        returnedElement = element;
      }
    }
    return returnedElement;
  }

  private void resolveMouseHover(MouseEvent event, VehicleVisualizer vehicleVis) {
    boolean mouseHovered = false;
    for (InteractiveUIElement element : myInteractiveElements) {
      if (!mouseHovered && element.pointInBounds(event.getX(), event.getY())) {
        element.reactToUserInput(myMouseStatefulBehavior.getType(), "Hover", myMouseStatefulBehavior.getElementId());
//        element.reactToHover(myMouseStatefulBehavior.getType());
        mouseHovered = true;
      }
      else {
        element.reactToUserInput(myMouseStatefulBehavior.getType(), "NoHover", myMouseStatefulBehavior.getElementId());
//        element.reactToNoHover(myMouseStatefulBehavior.getType());
      }
    }
    for (RoomVisualizer roomVis : vehicleVis.getRoomVisualizers()) {
      if (roomVis.pointInBounds(event.getX(),event.getY())) {
        myMouseBehavior.reactToRoomHover(roomVis.getRoom(),roomVis);
      }
      else {
        myMouseBehavior.reactToRoomNothing(roomVis.getRoom(),roomVis);
      }
    }
  }

  private void trackInteractiveElements(VehicleVisualizer vehicleVis) {
    myInteractiveElements.addAll(vehicleVis.getRoomVisualizers());
  }

  private void initMouseBehavior() {
    MouseBehaviorFactory factory = new MouseBehaviorFactory();
    myMouseStatefulBehavior = factory.newState("Select", null);
    myMouseStatefulBehavior.subscribe(this);
  }

  private void processText(String text, ModelExternal controlInterface) {
    Scanner textReader = new Scanner(text);
    if (textReader.next().equals("crewToRoom")) {
      controlInterface.assignCrewmateToRoom(textReader.next(),textReader.next());
    }
  }

  private List<WeaponInterfaceVisualizer> initWeaponVis(Group group, Vehicle vehicle) {
    List<WeaponInterfaceVisualizer> weaponVisList = new ArrayList<>();
    for (int i = 0; i < vehicle.getWeapons().size(); i ++) {
      Weapon weapon = vehicle.getWeapons().get(i);
      WeaponInterfaceVisualizer weaponVis = new VehicleWeaponInterface(weapon,260,230);
      group.getChildren().add(weaponVis.getGroup());
      weaponVisList.add(weaponVis);
      myInteractiveElements.add(weaponVis);
    }
    return weaponVisList;
  }

  @Override
  public void reactToNewBehavior(MouseBehavior behavior) {
    myMouseBehavior = behavior;
    myMouseBehavior.subscribe(this);
  }

  @Override
  public void reactToStateChange(MouseStatefulBehavior newState) {
    System.out.println("Switched to state " + newState.toString());
    myMouseStatefulBehavior = newState;
    myMouseStatefulBehavior.subscribe(this);
  }
}
