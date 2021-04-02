/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilisateur;

import Entities.*;
import Services.UserService;
import Tools.SmsSender;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

public class LoginController implements Initializable {

    UserService us = new UserService();

    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    public static String generateNewToken() {
        byte[] randomBytes = new byte[5];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    @FXML
    private TextField emailSignin;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField gouv;
    @FXML
    private TextField num_tel;
    @FXML
    private DatePicker dateN;
    @FXML
    private ComboBox<String> type;
    @FXML
    private TextField email;
    @FXML
    private PasswordField pwdSignin;
    @FXML
    private PasswordField pwd;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        List<String> types = new ArrayList<>();
        types.add(String.valueOf(Type.Artiste));
        types.add(String.valueOf(Type.Fan));
        type.setItems(FXCollections.observableArrayList(types));
    }

    @FXML
    private void signin(ActionEvent event) throws SQLException, IOException {
        User u = us.connect(emailSignin.getText(), pwdSignin.getText());
        if (u != null) {

            System.out.println("you're connected");

            if (u.getType() == Type.Admin) {
                FXMLLoader Loader = new FXMLLoader();
                Loader.setLocation(getClass().getResource("AcceuilAdmin.fxml"));
                Parent parent = Loader.load();
                Scene scene = new Scene(parent);
                Stage window = (Stage) emailSignin.getScene().getWindow();
                window.setScene(scene);
                window.show();
            } else {
                FXMLLoader Loader = new FXMLLoader();
                Loader.setLocation(getClass().getResource("Acceuil.fxml"));
                Parent parent = Loader.load();

                AcceuilController c = Loader.getController();
                c.setData(u);

                Scene scene = new Scene(parent);
                Stage window = (Stage) emailSignin.getScene().getWindow();
                window.setScene(scene);
                window.show();
            }

        } else {
            System.out.println("Erreur");
        }
    }

    @FXML
    private void signup(ActionEvent event) throws SQLException, IOException {
        if ((nom.getText().length() == 0) || (prenom.getText().length() == 0) || (email.getText().length() == 0) || (pwd.getText().length() == 0) || (gouv.getText().length() == 0) || (num_tel.getText().length() == 0)) {
            System.out.println("input!!");
        } else {
            if (us.VerifyEmail(email.getText())) {
                Notifications notificationBuilder = Notifications.create()
                        .title("Alert").text("Email existe deja !").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                        .position(Pos.CENTER_LEFT)
                        .onAction(new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent event) {
                                System.out.println("clicked ON ");
                            }
                        });
                notificationBuilder.darkStyle();
                notificationBuilder.show();
            } else {
                DatePicker tmpdate = (DatePicker) dateN;
                String date = (String) tmpdate.getValue().toString();
                date = date.substring(0, 4) + '/' + date.substring(5, 7) + '/' + date.substring(8);
                java.util.Date myDate = new java.util.Date(date);
                java.sql.Date sqldate = new java.sql.Date(myDate.getTime());

                String token = generateNewToken();
                us.add(new User(nom.getText(), prenom.getText(), pwd.getText(), Type.valueOf(type.getValue()), email.getText(), gouv.getText(), sqldate, Integer.parseInt(num_tel.getText()), "", token, 0));
                User u = us.connect(email.getText(), pwd.getText());

                SmsSender sms = new SmsSender();
                sms.send("bienvenue Mr " + nom.getText() + " tocken:" + token, "b");

                Notifications notificationBuilder = Notifications.create()
                        .title("Alert").text("Bienvenue").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                        .position(Pos.CENTER_LEFT)
                        .onAction(new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent event) {
                                System.out.println("clicked ON ");
                            }
                        });
                notificationBuilder.darkStyle();
                notificationBuilder.show();

                FXMLLoader Loader = new FXMLLoader();
                Loader.setLocation(getClass().getResource("Acceuil.fxml"));
                Parent parent = Loader.load();
                AcceuilController c = Loader.getController();

                c.setData(u);
                Scene scene = new Scene(parent);
                Stage window = (Stage) emailSignin.getScene().getWindow();
                window.setScene(scene);
                window.show();
            }

        }
    }

}
