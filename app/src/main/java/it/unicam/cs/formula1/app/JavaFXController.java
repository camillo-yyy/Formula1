/*
 * Created on Tue Jun 11 2024
 *
 * The MIT License (MIT)
 * Copyright (c) 2024 Samuele Camilletti
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package it.unicam.cs.formula1.app;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.*;

import it.unicam.cs.formula1.api.BotInterface;
import it.unicam.cs.formula1.api.Direction;
import it.unicam.cs.formula1.api.Driver;
import it.unicam.cs.formula1.api.RaceStatus;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader; 
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Popup;

/**
 * Controller that manages all events and graphic changes on the JavaFX thread
 * Reminder that when Next Step event is handled a new thread starts, executing all background model related tasks.
 * Then the main JavaFX UI thread is refreshed.
 */
public class JavaFXController {

   public static final int N_ROWS = 67;
   public static final int N_COLUMNS = 53;
   public static final int SCALE = 10;   

   @FXML Button loadTrack;
   @FXML Button loadCars;
   @FXML Button start;
   @FXML Button step;
   @FXML AnchorPane circuitPanel;
   @FXML Button up;
   @FXML Button center;
   @FXML Button left;
   @FXML Button right;
   @FXML Button down;
   @FXML Button downRight;
   @FXML Button downLeft;
   @FXML Button upRight;
   @FXML Button upLeft;
   @FXML Label leaderboard;
   @FXML TextArea leaderText;
   private List<Circle> nextMoves;

   private Alert a;

   private SetupController controller;
   private FileChooser fileChooser;
   private Stage primaryStage;
   private Polygon circuit;
   private Map<Driver, List<Circle>> carPoints;
   private Map<Driver, Label> carNames;


   public JavaFXController(){
      fileChooser = new FileChooser();
      fileChooser.setInitialDirectory(new File("..\\api\\src\\main\\resources"));
      a = new Alert(AlertType.NONE);

      this.carNames = new HashMap<>();
      this.carPoints = new HashMap<>();
      this.nextMoves = new LinkedList<>();
   }


   public void init(SetupController c, Stage ps){
      if(this.controller == null) {
         this.controller = c;
         this.primaryStage = ps;

         printGrid();

         //disable keypad
         this.setDisableKeyPad(true);
         this.leaderText.setEditable(false);
         this.leaderText.setWrapText(true);
         this.circuit = new Polygon();
         this.circuitPanel.getChildren().addAll(this.circuit);
      }
      else return;
   }

   private void printGrid(){
      // create grid
      List<Line> gridLines = new LinkedList<>();
      for(int i=0; i<N_ROWS; i++){
         Line gridLine = new Line(ResolutionScaler.upScaler(i, 10), 0f, ResolutionScaler.upScaler(i, 10), ResolutionScaler.upScaler(N_COLUMNS, 10));
         gridLine.setOpacity(0.20f);
         gridLines.add(gridLine);
      }
      for(int i=0; i<N_COLUMNS; i++){
         Line gridLine = new Line(0f, ResolutionScaler.upScaler(i, 10), ResolutionScaler.upScaler(N_ROWS, 10), ResolutionScaler.upScaler(i, 10));
         gridLine.setOpacity(0.20f);
         gridLines.add(gridLine);
      }
      // add grid to panel
      this.circuitPanel.getChildren().addAll(gridLines);
   }

   public void handleLoadTrackPressed(ActionEvent e){
      File selectedFile = fileChooser.showOpenDialog(this.primaryStage);
      fileChooser.getExtensionFilters().add(new ExtensionFilter(".csv file only", "*.csv"));
      // todo maybe optimize this
      if(selectedFile != null){      
         this.loadTrack.setDisable(true);
         this.loadTrack.setAccessibleText("Loaded");
         this.controller.loadTrack(selectedFile.getAbsolutePath());
      }
   }

   public void handleLoadCarsPressed(ActionEvent e){
      File selectedFile = fileChooser.showOpenDialog(this.primaryStage);
      fileChooser.getExtensionFilters().add(new ExtensionFilter(".csv file only", "*.csv"));
      if(selectedFile != null){    
         this.loadCars.setDisable(true);
         this.loadCars.setAccessibleText("Loaded");
         this.controller.loadDrivers(selectedFile.getAbsolutePath());
      }
   }

   private void displayText(){
      List<Driver> driverCars = this.controller.getModel().getCurrentDrivers();
      for (Driver driver : driverCars) {
         this.leaderText.appendText("Driver: "+driver.getUsername());
         if(driver.getInputStrategy() instanceof BotInterface) this.leaderText.appendText(" (BOT)"+System.lineSeparator());
         else this.leaderText.appendText(System.lineSeparator());
      }
   }

   public void handleStartPressed(ActionEvent e){
      // start game
      if(this.controller.start()){
         this.start.setDisable(true);
         this.step.setDisable(false);
         this.displayPolygon();
         this.displayText();
         this.refreshCars();
      }
      else {
         // set alert type
         a.setAlertType(AlertType.ERROR);
         a.setHeaderText("File missing or invalid");
         a.setTitle("Either track or cars are missing or invalid");
         // show the dialog
         a.show();
      }
   }

   private void displayCars(List<Driver> d){
      for (Driver driver : d) {
         Label l = this.createLabel(driver.getUsername(), driver.getCar().getPosition().getX(), driver.getCar().getPosition().getY(), Color.RED);
         Circle c = createCircle(driver.getCar().getPosition().getX(), driver.getCar().getPosition().getY(), Color.BLACK);
         this.carPoints.put(driver, new LinkedList<Circle>());
         this.carNames.put(driver, l);

         this.carPoints.get(driver).add(c);

         this.circuitPanel.getChildren().add(c);
         this.circuitPanel.getChildren().add(l);
      }
   }

   // refresh car on the grid and create a movement path
   public void refreshCars() {
      List<Driver> driverCars = this.controller.getModel().getCurrentDrivers();
      if(this.carPoints.isEmpty()){
         this.displayCars(driverCars);
      }
      else {
         for (Driver driver : driverCars) {
            // create new circle
            Circle c = createCircle(driver.getCar().getPosition().getX(), driver.getCar().getPosition().getY(), Color.BLACK);
            // getting last circle position
            Circle last = this.carPoints.get(driver).getLast();
            // create a path
            Line l = new Line(last.getCenterX(), last.getCenterY(), ResolutionScaler.upScaler(driver.getCar().getPosition().getX(), SCALE), 
                              ResolutionScaler.upScaler(driver.getCar().getPosition().getY(), SCALE));
   
            this.carPoints.get(driver).add(c);
            this.circuitPanel.getChildren().add(c);
            this.circuitPanel.getChildren().add(l);
   
            this.carNames.get(driver).setTranslateX(ResolutionScaler.upScaler(driver.getCar().getPosition().getX(), SCALE));
            this.carNames.get(driver).setTranslateY(ResolutionScaler.upScaler(driver.getCar().getPosition().getY(), SCALE));
         }
      }
   }

   public void displayTurnDriverMove(){
      if(this.controller.getModel().turnDriver() != null){

         Circle c1 = createCircle(this.controller.getModel().turnDriver().getCar().getNextPosition().getX(), 
            this.controller.getModel().turnDriver().getCar().getNextPosition().getY(), Color.TRANSPARENT);
         c1.setStroke(Color.CORAL);

         this.nextMoves.add(c1);
         for(Direction d : Direction.values()){
            Circle c2 = createCircle(this.controller.getModel().turnDriver().getCar().getNextPosition().getAdjacent(d).getX(), 
            this.controller.getModel().turnDriver().getCar().getNextPosition().getAdjacent(d).getY(), Color.TRANSPARENT);
            c2.setStroke(Color.CORAL);
            this.nextMoves.add(c2);
         }
         this.circuitPanel.getChildren().addAll(this.nextMoves);        
      }
   }

   public void undisplayTurnDriverMove(){
      this.circuitPanel.getChildren().removeAll(nextMoves);
      this.nextMoves.clear();
   }

   public void handleStepPressed(ActionEvent e){
      this.prepareToInput();

      // create a thread to execute turn in background while main UI thread continue his execution
      CompletableFuture.runAsync(() -> {
         this.controller.run();

         // javafx thread method that ensure the Runnable is executed
         // later on the main javafx thread to ensure graphic changes to be displayed asynchrounosly
         Platform.runLater(() -> {
            this.refreshCars();
            if(this.controller.getModel().getRaceStatus() == RaceStatus.FINISHED) {
               this.displayPopup();
            }
            this.disableInput();
         });
      });

   }

   public void handleQuitPressed(ActionEvent e){
      this.primaryStage.close();
   }

   private void displayPopup(){
      Popup popup = new Popup();
      FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/fxml/Popup.fxml")));
      try{
         loader.setController(this);
         popup.getContent().add((Parent)loader.load());
         Label label;
         // create a label 
         if(this.controller.getModel().getWinner() != null){
            label = createLabel("Race winner is "+this.controller.getModel().getWinner().getUsername(), 0, 0, Color.BLACK);
         }
         else label = createLabel("No winner for this race", 0, 0, Color.BLACK);
         
         popup.getContent().add(label);
         popup.show(primaryStage);
      }
      catch(IOException eu){
         eu.printStackTrace();
      }
   }

   private void inputSend(Direction d){
      this.controller.sendInput(d);
   }

   public void handleUpLeft(ActionEvent e){
      inputSend(Direction.SW);
   }

   public void handleUp(ActionEvent e){
      inputSend(Direction.S);
   }

   public void handleUpRight(ActionEvent e){
      inputSend(Direction.SE);
   }

   public void handleCenter(ActionEvent e){
      inputSend(Direction.STILL);
   }

   public void handleLeft(ActionEvent e){
      inputSend(Direction.W);
   }

   public void handleDownLeft(ActionEvent e){
      inputSend(Direction.NW);
   }

   public void handleDownRight(ActionEvent e){
      inputSend(Direction.NE);
   }

   public void handleRight(ActionEvent e){
      inputSend(Direction.E);
   }

   public void handleDown(ActionEvent e){
      inputSend(Direction.N);
   }

   public void handleRestartPressed(ActionEvent e){
      return;
   }

   private void displayPolygon(){
      List<Double> listOfPoints = this.controller
                        .getModel()
                        .getTrack()
                        .getCorners()
                        .stream()
                        .flatMap(p -> Stream.of(ResolutionScaler.upScaler(p.getX(), SCALE), ResolutionScaler.upScaler(p.getY(), SCALE)))
                        .collect(Collectors.toList())
                        ;
      Double[] a = new Double[listOfPoints.size()];
      a = listOfPoints.toArray(a);

      this.circuit.getPoints().addAll(a);
      this.circuit.setFill(Color.TRANSPARENT);
      this.circuit.setStroke(Color.BLACK);
      this.circuit.setStrokeWidth(2);

      // print starting line
      Line startingLine = this.createLine(this.controller.getModel().getTrack().getStartingLine().getX().getX(),
                        this.controller.getModel().getTrack().getStartingLine().getX().getY(),
                        this.controller.getModel().getTrack().getStartingLine().getY().getX(),
                        this.controller.getModel().getTrack().getStartingLine().getY().getY(), Color.RED, 2);
      this.circuitPanel.getChildren().add(startingLine);

      // print ending line
      Line endingLine = this.createLine(this.controller.getModel().getTrack().getEndingLine().getX().getX(),
                     this.controller.getModel().getTrack().getEndingLine().getX().getY(),
                     this.controller.getModel().getTrack().getEndingLine().getY().getX(),
                     this.controller.getModel().getTrack().getEndingLine().getY().getY(), Color.GREEN, 2);
      this.circuitPanel.getChildren().add(endingLine);

   }

   private void setDisableKeyPad(boolean value){
      this.up.setDisable(value);
      this.upRight.setDisable(value);
      this.upLeft.setDisable(value);
      this.center.setDisable(value);
      this.left.setDisable(value);
      this.right.setDisable(value);
      this.down.setDisable(value);
      this.downLeft.setDisable(value);
      this.downRight.setDisable(value);
   }

   private void prepareToInput(){
      this.step.setDisable(true);
      this.setDisableKeyPad(false);
      this.displayTurnDriverMove();
   }

   private void disableInput(){
      this.setDisableKeyPad(true);
      this.step.setDisable(false);
      this.undisplayTurnDriverMove();
   }

   private Circle createCircle(double x, double y, Color color){
      Circle c = new Circle();
      c.setCenterX(ResolutionScaler.upScaler(x, SCALE));
      c.setCenterY(ResolutionScaler.upScaler(y, SCALE));
      c.setRadius(3.0f); 
      c.setFill(color);

      return c;
   }

   private Label createLabel(String t, double x, double y,  Color color){
      Label l = new Label(t);
      l.setTranslateX(ResolutionScaler.upScaler(x, SCALE)); 
      l.setTranslateY(ResolutionScaler.upScaler(y, SCALE));
      l.setTextFill(color);

      return l;
   }

   private Line createLine(double x1, double y1, double x2, double y2, Color x, double width){
      Line line = new Line(ResolutionScaler.upScaler(x1, SCALE), 
      ResolutionScaler.upScaler(y1, SCALE), 
      ResolutionScaler.upScaler(x2, SCALE), 
      ResolutionScaler.upScaler(y2, SCALE));
      line.setStroke(x);
      line.setStrokeWidth(width);
      
      return line;
   }

}
