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
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.stage.FileChooser;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author skand
 */
public class ParametreController implements Initializable {

    User u = new User();
    UserService us = new UserService();

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
    private TextField email;
    @FXML
    private PasswordField pwd;
    @FXML
    private PasswordField pwd1;
    @FXML
    private ImageView imageprofile;
    @FXML
    private Label url;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void Modifier(ActionEvent event) throws SQLException {
        if ((nom.getText().length() == 0) || (prenom.getText().length() == 0) || (email.getText().length() == 0) || (gouv.getText().length() == 0) || (num_tel.getText().length() == 0)) {
            Notifications notificationBuilder = Notifications.create()
                    .title("Alert").text("input !!").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                    .position(Pos.CENTER_LEFT)
                    .onAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            System.out.println("clicked ON ");
                        }
                    });
            notificationBuilder.darkStyle();
            notificationBuilder.show();
        } else {
            u.setNom(nom.getText());
            u.setPrenom(prenom.getText());

            DatePicker tmpdate = (DatePicker) dateN;
            String date = (String) tmpdate.getValue().toString();
            date = date.substring(0, 4) + '/' + date.substring(5, 7) + '/' + date.substring(8);
            java.util.Date myDate = new java.util.Date(date);
            java.sql.Date sqldate = new java.sql.Date(myDate.getTime());

            u.setDate_naissance(sqldate);
            u.setEmail(email.getText());
            u.setNum_tel(Integer.parseInt(num_tel.getText()));

            us.update(u);

            Notifications notificationBuilder = Notifications.create()
                    .title("Alert").text("modification avec succée").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
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

    @FXML
    private void Modifierpwd(ActionEvent event) throws SQLException {
        if ((pwd.getText().length() == 0) || (pwd1.getText().length() == 0)) {
            System.out.println("input!!");
        } else {
            if (u.getPassword().equals(pwd.getText())) {
                u.setPassword(pwd1.getText());

                us.update(u);
                Notifications notificationBuilder = Notifications.create()
                        .title("Alert").text("mot de passe modifier").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                        .position(Pos.CENTER_LEFT)
                        .onAction(new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent event) {
                                System.out.println("clicked ON ");
                            }
                        });
                notificationBuilder.darkStyle();
                notificationBuilder.show();
            } else {
                Notifications notificationBuilder = Notifications.create()
                        .title("Alert").text("mot de passe erreuné !").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                        .position(Pos.CENTER_LEFT)
                        .onAction(new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent event) {
                                System.out.println("clicked ON ");
                            }
                        });
                notificationBuilder.darkStyle();
                notificationBuilder.show();
                pwd.setText("");
                pwd1.setText("");
            }
        }
    }

    void setData(User user) {
        u = user;

        nom.setText(u.getNom());
        prenom.setText(u.getPrenom());
        gouv.setText(u.getGouvernorat());
        num_tel.setText(String.valueOf(u.getNum_tel()));
        java.util.Date utilDate = new java.util.Date(u.getDate_naissance().getTime());
        LocalDate date = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dateN.setValue(date);
        email.setText(u.getEmail());
        if (u.getPhoto().length() == 0) {
            File file;
            file = new File("uploads/user.png");
            imageprofile.setImage(new Image(file.toURI().toString()));
            imageprofile.setCache(true);
            imageprofile.setFitHeight(100);
            imageprofile.setFitWidth(100);
        } else {
            File file;
            file = new File(u.getPhoto());
            imageprofile.setImage(new Image(file.toURI().toString()));
            imageprofile.setCache(true);
            imageprofile.setFitHeight(100);
            imageprofile.setFitWidth(100);
        }

    }

    @FXML
    private void upload(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File file;
        file = fc.showOpenDialog(null);
        String path = file.getPath();
        url.setText(path);
    }

    @FXML
    private void changerImage(ActionEvent event) throws SQLException, IOException {
        if (url.getText().length() == 0) {
            Notifications notificationBuilder = Notifications.create()
                    .title("Alert").text("Choisissez photo!").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                    .position(Pos.CENTER_LEFT)
                    .onAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            System.out.println("clicked ON ");
                        }
                    });
            notificationBuilder.darkStyle();
            notificationBuilder.show();
        } else {
            File f=new File(url.getText());
            Files.copy(Paths.get(url.getText()),Paths.get("uploads/"+f.getName()),REPLACE_EXISTING);
            u.setPhoto("uploads/"+f.getName());
            us.update(u);
            File file;
            file = new File(u.getPhoto());
            imageprofile.setImage(new Image(file.toURI().toString()));
            imageprofile.setCache(true);
            imageprofile.setFitHeight(100);
            imageprofile.setFitWidth(100);

            Notifications notificationBuilder = Notifications.create()
                    .title("Alert").text("photo changé").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
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
