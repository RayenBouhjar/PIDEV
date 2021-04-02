/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilisateur;

import Entities.Avis;
import Entities.Type;
import Entities.User;
import Services.UserService;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.Notifications;

public class UtilisateursController implements Initializable {

    UserService us = new UserService();

    ObservableList<User> UserList = FXCollections.observableArrayList();
    private int id_user = 0;
    User u = new User();
    
    

    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User, Integer> id;
    @FXML
    private TableColumn<User, String> nom;
    @FXML
    private TableColumn<User, String> prenom;
    @FXML
    private TableColumn<User, String> email;
    @FXML
    private TableColumn<User, Date> daten;
    @FXML
    private TableColumn<User, String> gouv;
    @FXML
    private TableColumn<User, Integer> num_tel;
    @FXML
    private TableColumn<User, Type> Type;
    @FXML
    private TextField recherche;
    @FXML
    private TableColumn<User, Integer> block;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            showuser();
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateursController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        if (event.getClickCount() == 1) {
            table.setItems(UserList);
            ObservableList<User> allUsers, r;
            allUsers = table.getItems();
            r = table.getSelectionModel().getSelectedItems();

            id_user = r.get(0).getId();
            u.setId(id_user);
            u.setBlock(r.get(0).getBlock());
        }
    }

    public void showuser() throws SQLException {
        List<User> liste = new ArrayList<User>();
        liste = us.readAll();

        for (User aux : liste) {
            UserList.add(new User(aux.getId(), aux.getNom(), aux.getPrenom(), aux.getPassword(), aux.getType(), aux.getEmail(), aux.getGouvernorat(), aux.getDate_naissance(), aux.getNum_tel(), aux.getBlock(), aux.getPhoto(), aux.getToken(), aux.getStatus()));
            table.setItems(UserList);
        }

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        daten.setCellValueFactory(new PropertyValueFactory<>("date_naissance"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        num_tel.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
        gouv.setCellValueFactory(new PropertyValueFactory<>("gouvernorat"));
        block.setCellValueFactory(new PropertyValueFactory<>("block"));

        table.setItems(UserList);

    }

    @FXML
    private void search(KeyEvent event) throws SQLException {

        UserList.clear();
        List<User> liste = new ArrayList<User>();
        liste = us.search(recherche.getText());

        for (User aux : liste) {
            UserList.add(new User(aux.getId(), aux.getNom(), aux.getPrenom(), aux.getPassword(), aux.getType(), aux.getEmail(), aux.getGouvernorat(), aux.getDate_naissance(), aux.getNum_tel(), aux.getBlock(), aux.getPhoto(), aux.getToken(), aux.getStatus()));
            table.setItems(UserList);
        }

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        daten.setCellValueFactory(new PropertyValueFactory<>("date_naissance"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        num_tel.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
        gouv.setCellValueFactory(new PropertyValueFactory<>("gouvernorat"));
        block.setCellValueFactory(new PropertyValueFactory<>("block"));

        table.setItems(UserList);

    }

    @FXML
    private void blocker(ActionEvent event) throws SQLException {
        if (id_user != 0) {
            if (u.getBlock() == 0) {
                u.setBlock(1);
                us.updateState(u);
                Notifications notificationBuilder = Notifications.create()
                        .title("Alert").text("utilisateur bloqué ").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
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
                        .title("Alert").text("utilisateur deja bloqué").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
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
                    .title("Alert").text("selectionnez un utilisateur").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                    .position(Pos.CENTER_LEFT)
                    .onAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            System.out.println("clicked ON ");
                        }
                    });
            notificationBuilder.darkStyle();
            notificationBuilder.show();
        }
        UserList.clear();
        showuser();
    }
    
    

    @FXML
    private void deblocker(ActionEvent event) throws SQLException {
        if (id_user != 0) {
            if (u.getBlock() == 1) {
                u.setBlock(0);
                us.updateState(u);
                Notifications notificationBuilder = Notifications.create()
                        .title("Alert").text("utilisateur débloqué ").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
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
                        .title("Alert").text("utilisateur deja débloqué").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
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
                    .title("Alert").text("selectionnez un utilisateur").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                    .position(Pos.CENTER_LEFT)
                    .onAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            System.out.println("clicked ON ");
                        }
                    });
            notificationBuilder.darkStyle();
            notificationBuilder.show();
        }
        UserList.clear();
        showuser();
    }

    
}
