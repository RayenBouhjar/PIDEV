/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahmedartiste;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author skand
 */
public class AcceuilArtisteController implements Initializable {

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
    private void mesAlbum(ActionEvent event) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("MesAlbums.fxml"));
        Parent parent = Loader.load();
        AnchorPane root = (AnchorPane) parent;

        affichage.getChildren().setAll(root);
    }

    @FXML
    private void lespistes(ActionEvent event) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("MesPistes.fxml"));
        Parent parent = Loader.load();
        AnchorPane root = (AnchorPane) parent;

        affichage.getChildren().setAll(root);
    }
    
}
