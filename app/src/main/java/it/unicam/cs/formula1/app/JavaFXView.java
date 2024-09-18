package it.unicam.cs.formula1.app;

import java.util.Objects;

import it.unicam.cs.formula1.api.BaseBot;
import it.unicam.cs.formula1.api.InputLoader;
import it.unicam.cs.formula1.api.RaceEngineFactory;
import it.unicam.cs.formula1.api.RacetrackRule;
import it.unicam.cs.formula1.api.io.RacetrackCarLoader;
import it.unicam.cs.formula1.api.io.RacetrackDriverLoader;
import it.unicam.cs.formula1.api.io.RacetrackLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * This view maximum visible circuit size is 63*57
 */
public final class JavaFXView extends Application {

   @Override
   public void start(Stage primaryStage) throws Exception {
      
      FXMLLoader mainLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/fxml/BetaGUI.fxml")));
      Parent root = mainLoader.load();
      JavaFXController controller = mainLoader.getController();
      SetupController setupController = SetupController.getSetupController(
         new RacetrackLoader(), new RacetrackDriverLoader(new RacetrackCarLoader(), new InputLoader(), new BaseBot()), new RaceEngineFactory(), new RacetrackRule());

      controller.init(setupController, primaryStage);
      primaryStage.setTitle("RaceTrack Game");
      primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.jpg")));
      primaryStage.setScene(new Scene(root, 1024, 576));
      primaryStage.setResizable(false);
      primaryStage.show();
      
   }
   
}
