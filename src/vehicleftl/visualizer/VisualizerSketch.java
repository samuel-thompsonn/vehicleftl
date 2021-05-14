package vehicleftl.visualizer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.util.Duration;
import vehicleftl.model.*;
import vehicleftl.visualizer.mousebehavior.SelectBehavior;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VisualizerSketch extends Application implements MouseListener {

  private List<WeaponInterfaceVisualizer> myWeaponVisualizers;
  private List<Room> myRooms;
  private Vehicle myVehicle;
  private List<CrewVisualizer> myCrew;
  private MouseBehavior myMouseBehavior;

  @Override
  public void start(Stage primaryStage) throws Exception {
    myMouseBehavior = new SelectBehavior();
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
    group.getChildren().add(vehicleVis.getGroup());
    myCrew = new ArrayList<>();
    for (Crewmate crewmate : vehicle.getCrew()) {
      CrewVisualizer crewVis = new VehicleCrewVisualizer(50,50,crewmate);
      group.getChildren().add(crewVis.getGroup());
      myCrew.add(crewVis);
    }
    SystemsTray systemsVis = new VehicleSystemsTray(vehicle,50,330);
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
      if (event.getButton().equals(MouseButton.SECONDARY)) {
        for (RoomVisualizer roomVis : vehicleVis.getRoomVisualizers()) {
          if (roomVis.pointInBounds(event.getX(),event.getY())) {
            myMouseBehavior.reactToRoomPrimary(roomVis.getRoom(), roomVis);
            return;
          }
        }
        for (WeaponInterfaceVisualizer weaponVis : myWeaponVisualizers) {
          if (weaponVis.pointInBounds(event.getX(),event.getY())) {
            myMouseBehavior.reactToWeaponPrimary(weaponVis.getWeapon(),weaponVis);
            return;
          }
        }
        myMouseBehavior.reactToNothingPrimary();
        for (SystemVisualizer visualizer : systemsVis.getSystemVisualizers()) {
          if (visualizer.pointInBounds(event.getX(),event.getY())) {
            visualizer.decreasePower();
          }
        }
      }
      if (event.getButton().equals(MouseButton.PRIMARY)) {
        for (CrewVisualizer crewVis : myCrew) {
          if (crewVis.pointInBounds(event.getX(),event.getY())) {
            myMouseBehavior.reactToCrewSecondary(crewVis.getCrewmate(),crewVis);
            return;
          }
        }
        for (RoomVisualizer roomVis : vehicleVis.getRoomVisualizers()) {
          if (roomVis.pointInBounds(event.getX(),event.getY())) {
            myMouseBehavior.reactToRoomSecondary(roomVis.getRoom(), roomVis);
            return;
          }
        }
        for (RoomVisualizer roomVis : secondVehicleVis.getRoomVisualizers()) {
          if (roomVis.pointInBounds(event.getX(),event.getY())) {
            myMouseBehavior.reactToEnemyRoomSecondary(roomVis.getRoom(),roomVis);
            return;
          }
        }
        for (WeaponInterfaceVisualizer weaponVis : myWeaponVisualizers) {
          if (weaponVis.pointInBounds(event.getX(),event.getY())) {
            myMouseBehavior.reactToWeaponSecondary(weaponVis.getWeapon(),weaponVis);
            return;
          }
        }
        for (SystemVisualizer visualizer : systemsVis.getSystemVisualizers()) {
          if (visualizer.pointInBounds(event.getX(),event.getY())) {
            visualizer.increasePower();
          }
        }
      }
    });

    scene.setOnMouseMoved(event -> {
      for (RoomVisualizer roomVis : vehicleVis.getRoomVisualizers()) {
        if (roomVis.pointInBounds(event.getX(),event.getY())) {
          myMouseBehavior.reactToRoomHover(roomVis.getRoom(),roomVis);
        }
        else {
          myMouseBehavior.reactToRoomNothing(roomVis.getRoom(),roomVis);
        }
      }
//      for (WeaponInterfaceVisualizer weaponVis : myWeaponVisualizers) {
//        if (weaponVis.pointInBounds(event.getX(),event.getY())) {
//          myMouseBehavior.reactToWeaponHover(weaponVis.getWeapon(),weaponVis);
//        }
//        else {
//          myMouseBehavior.reactToWeaponNothing(weaponVis.getWeapon(),weaponVis);
//        }
//      }
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
    }
    return weaponVisList;
  }

  @Override
  public void reactToNewBehavior(MouseBehavior behavior) {
    myMouseBehavior = behavior;
    myMouseBehavior.subscribe(this);
  }
}
