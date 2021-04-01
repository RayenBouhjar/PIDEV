/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.ServicePack;

/**
 * FXML Controller class
 *
 * @author Rednaks
 */
public class AddPackController implements Initializable {

    @FXML
    private Button add;
    @FXML
    private TextField nom;
    @FXML
    private TextField prix;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       ServicePack sp = new ServicePack();
    }    

    @FXML
    private void AddAction(ActionEvent event) {
        System.out.println(nom);
    }
    
}
