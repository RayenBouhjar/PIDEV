/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilisateur;

import Entities.User;
import Services.UserService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author skand
 */
public class VerificationController implements Initializable {

    User u = new User();
    UserService us = new UserService();

    @FXML
    private TextField token;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void verifier(ActionEvent event) throws SQLException, IOException {
        if (token.getText().length() != 0) {
            if (us.Verify(u, token.getText())) {
                Notifications notificationBuilder = Notifications.create()
                        .title("Alert").text("Votre compte est verifié !").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                        .position(Pos.CENTER_LEFT)
                        .onAction(new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent event) {
                                System.out.println("clicked ON ");
                            }
                        });
                notificationBuilder.darkStyle();
                notificationBuilder.show();

                FXMLLoader Loader = new FXMLLoader();
                Loader.setLocation(getClass().getResource("Login.fxml"));
                Parent parent = Loader.load();
                
                Scene scene = new Scene(parent);
                Stage window = (Stage) token.getScene().getWindow();
                window.setScene(scene);
                window.show();
            } else {
                Notifications notificationBuilder = Notifications.create()
                        .title("Alert").text("tocken erroné !").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                        .position(Pos.CENTER_LEFT)
                        .onAction(new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent event) {
                                System.out.println("clicked ON ");
                            }
                        });
                notificationBuilder.darkStyle();
                notificationBuilder.show();
            }

        } else {
            Notifications notificationBuilder = Notifications.create()
                    .title("Alert").text("Entrer le token!").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
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
    }

}
