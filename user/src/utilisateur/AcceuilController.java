/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilisateur;

import Entities.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.Notifications;

public class AcceuilController implements Initializable {

    User u = new User();

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
    private void param(ActionEvent event) throws IOException {
        if (u.getBlock() == 0) {
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("Parametre.fxml"));
            Parent parent = Loader.load();
            AnchorPane root = (AnchorPane) parent;

            ParametreController c = Loader.getController();
            c.setData(u);
            affichage.getChildren().setAll(root);
        } else {
            Notifications notificationBuilder = Notifications.create()
                    .title("Alert").text("vous ete blocké par l'administrateur!").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                    .position(Pos.CENTER_LEFT)
                    .onAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            System.out.println("clicked ON ");
                        }
                    });
            notificationBuilder.darkStyle();
            notificationBuilder.show();
        }

    }

    void setData(User user) {
        u = user;

        if (u.getBlock() == 0) {
            if (u.getStatus() == 1) {
                FXMLLoader Loader = new FXMLLoader();
                Loader.setLocation(getClass().getResource("Parametre.fxml"));
                Parent parent;
                try {
                    parent = Loader.load();
                    AnchorPane root = (AnchorPane) parent;
                    ParametreController c = Loader.getController();
                    c.setData(u);
                    affichage.getChildren().setAll(root);
                } catch (IOException ex) {
                    Logger.getLogger(AcceuilController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                FXMLLoader Loader = new FXMLLoader();
                Loader.setLocation(getClass().getResource("verification.fxml"));
                Parent parent;
                try {
                    parent = Loader.load();
                    AnchorPane root = (AnchorPane) parent;
                    VerificationController c = Loader.getController();
                    c.setData(u);
                    affichage.getChildren().setAll(root);
                } catch (IOException ex) {
                    Logger.getLogger(AcceuilController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else {
            Notifications notificationBuilder = Notifications.create()
                    .title("Alert").text("vous ete blocké par l'administrateur!").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                    .position(Pos.CENTER_LEFT)
                    .onAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            System.out.println("clicked ON ");
                        }
                    });
            notificationBuilder.darkStyle();
            notificationBuilder.show();
        }

    }

}
