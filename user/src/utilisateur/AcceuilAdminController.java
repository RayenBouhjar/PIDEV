/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilisateur;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;


public class AcceuilAdminController implements Initializable {

    @FXML
    private AnchorPane affichage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void utilisateur(ActionEvent event) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("utilisateurs.fxml"));
        affichage.getChildren().setAll(root);
    }

    @FXML
    private void Avis(ActionEvent event) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("AfficherAvis.fxml"));
        affichage.getChildren().setAll(root);
    }
    
}
