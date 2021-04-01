/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Artist;
import models.Event;
import services.ServiceEvent;
import services.ServiceParticipation;

/**
 * FXML Controller class
 *
 * @author Sadok Laouissi
 */
public class EditEventController implements Initializable {

    @FXML
    private TextField titre;
    @FXML
    private TextField descriptiona;
    @FXML
    private DatePicker date;
    @FXML
    private TextField capacite;
    @FXML
    private TextField prix;
    @FXML
    private TextField location;
    @FXML
    private Button dismiss;
    @FXML
    private Button edit;

    ServiceEvent serviceEvent;
    ServiceParticipation serviceParticipation;
    private Event event;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        serviceEvent = new ServiceEvent();
        serviceParticipation = new ServiceParticipation();
    }

    public void setData(Event data) {
        event = data;
        this.titre.setText(this.event.getName());
        this.descriptiona.setText(this.event.getDescription());
        this.date.setValue(this.event.getDate().toLocalDate());
        this.capacite.setText(String.valueOf(this.event.getCapacity()));
        this.prix.setText(String.valueOf(this.event.getPrice()));
        this.location.setText(this.event.getLocation());
    }

    @FXML
    private void Dismiss(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) titre.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("ListEventsArtist.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void EditAction(ActionEvent event) throws IOException {

        if (titre.getText().isEmpty()
                || descriptiona.getText().isEmpty()
                || capacite.getText().isEmpty()
                || prix.getText().isEmpty()
                || location.getText().isEmpty()
                || date.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Données Invalide");
            alert.setHeaderText("Champs Vides !");
            alert.setContentText("Veuillez remplir tout les champs.");
            //Customize the buttons in the confirmation dialog
            ButtonType buttonTypeYes = new ButtonType("D'accord");

            //Set buttons onto the confirmation window
            alert.getButtonTypes().setAll(buttonTypeYes);
            Optional<ButtonType> result = alert.showAndWait();
        } else {

            if (serviceParticipation.getAllPartsByEventId(this.event.getId()).size() > Integer.valueOf(capacite.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("La capacité choisie est inferieure au nombre des participants inscrits !");
                //Customize the buttons in the confirmation dialog
                ButtonType buttonTypeYes = new ButtonType("D'accord");

                //Set buttons onto the confirmation window
                alert.getButtonTypes().setAll(buttonTypeYes);

                //Get the user's answer on whether deleting or not
                Optional<ButtonType> result = alert.showAndWait();
            } else {
                
                this.event.setCapacity(Integer.valueOf(capacite.getText()));
                this.event.setDate(java.sql.Date.valueOf(date.getValue()));
                this.event.setDescription(descriptiona.getText());
                this.event.setLocation(location.getText());
                this.event.setPrice(Float.valueOf(prix.getText()));
                this.event.setName(titre.getText());

                System.out.println(this.event);

                serviceEvent.modifier(this.event);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Succes");
                alert.setHeaderText("Modification Avec Succes !");
                //Customize the buttons in the confirmation dialog
                ButtonType buttonTypeYes = new ButtonType("D'accord");

                //Set buttons onto the confirmation window
                alert.getButtonTypes().setAll(buttonTypeYes);

                //Get the user's answer on whether deleting or not
                Optional<ButtonType> result = alert.showAndWait();
                Stage stage;
                Parent root;
                stage = (Stage) titre.getScene().getWindow();

                // Step 4
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ListEventsArtist.fxml"));
                root = loader.load();
                // Parent root = FXMLLoader.load(getClass().getResource("EditPack.fxml"));
                // Step 5
                ListEventsArtistController controller = loader.<ListEventsArtistController>getController();
                controller.UpdateAll();
                // Step 6
                Scene scene = new Scene(root);
                stage.setScene(scene);
                // Step 7
                stage.show();

            }

        }

    }

}
