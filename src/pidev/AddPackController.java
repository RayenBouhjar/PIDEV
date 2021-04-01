/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev;

import java.io.IOException;
import static java.lang.Float.parseFloat;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Pack;
import services.ServicePack;

/**
 * FXML Controller class
 *
 * @author Rednaks
 */
public class AddPackController implements Initializable {

    /**
     * Initializes the controller class.
     */
      ServicePack sp ;
    @FXML
    private TextField nom,prix;
    @FXML
    private Button add;
    @FXML
    private Button cancelbutton;
    @FXML
    private TextField description;
    
    @FXML
    private void AddAction(ActionEvent event) throws IOException {
        
     //   System.out.println(nom.getText());
     Pack p = new Pack(nom.getText(),description.getText(),parseFloat(prix.getText()));
      sp.ajouter(p);
      Stage stage;
        Parent root;
        stage = (Stage) add.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("ListAll.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void CancelAction(ActionEvent event) throws IOException {
        
    Stage stage;
        Parent root;
        stage = (Stage) add.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("ListAll.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     sp = new ServicePack();
    }    
    
}
