package it.unicam.cs.app;

import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXView extends Application implements View {

   @Override
   public void open() {
      Application.launch(this.getClass());
   }

   @Override
   public void close() {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'close'");
   }

   @Override
   public void start(Stage primaryStage) throws Exception {
      Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/AlphaGUI.fxml")));
      primaryStage.setTitle("Formula 1 RaceTrack Game");
      primaryStage.setScene(new Scene(root, 960, 640));
      primaryStage.setResizable(false);
      primaryStage.show();
   }
   
}
