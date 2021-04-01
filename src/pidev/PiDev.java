/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Rednaks
 */
public class PiDev extends Application {
    @Override
    public void start(Stage stage) throws Exception {
      // Parent root = FXMLLoader.load(getClass().getResource("UserPacks.fxml"));
      Parent root = FXMLLoader.load(getClass().getResource("ListAll.fxml"));
       
       // Parent root = FXMLLoader.load(getClass().getResource("ListEventsArtist.fxml"));
     //Parent root = FXMLLoader.load(getClass().getResource("UserEvents.fxml"));
        Scene scene = new Scene(root);
  
       stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
