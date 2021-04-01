/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Event;
import models.Participation;
import models.User;
import services.ServiceEvent;
import services.ServiceParticipation;

/**
 * FXML Controller class
 *
 * @author Sadok Laouissi
 */
public class UserEventsController implements Initializable {

    @FXML
    private TableView tableEvents;
    @FXML
    private TableColumn nom;
    @FXML
    private TableColumn prix;
    @FXML
    private TableColumn capacite1;
    @FXML
    private TableColumn date_events;
    @FXML
    private TableColumn localisation;
    @FXML
    private TableColumn description;
    @FXML
    private Button supprimerEvents;
    @FXML
    private TextField searchEvents;
    @FXML
    private TableView tableEvents1;
    @FXML
    private TableColumn nom1;
    @FXML
    private TableColumn prix1;
    @FXML
    private TableColumn capacite11;
    @FXML
    private TableColumn date_events1;
    @FXML
    private TableColumn localisation1;
    @FXML
    private TableColumn description1;
    @FXML
    private Button supprimerEvents1;
    @FXML
    private TextField searchEvents1;
    @FXML
    private Button supprimerEvents11;

    ServiceEvent serviceEvent;
    ServiceParticipation serviceParticipation;

    ObservableList<Event> allEvents;
    ObservableList<Event> myEvents;
    private int id_user = 1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        serviceEvent = new ServiceEvent();
        serviceParticipation = new ServiceParticipation();

        List<Event> list = serviceEvent.afficher();

        for (Event e : list) {
            int size = serviceParticipation.getAllPartsByEventId(e.getId()).size();
            e.setReste(e.getCapacity() - size);
        }

        List<Event> list1 = serviceEvent.getAllParticipedEventsByUserId(id_user);

        for (Event e : list1) {
            int size = serviceParticipation.getAllPartsByEventId(e.getId()).size();
            e.setReste(e.getCapacity() - size);
        }

        allEvents = FXCollections.observableArrayList(list);
        myEvents = FXCollections.observableArrayList(list1);
        nom.setCellValueFactory(
                new PropertyValueFactory<Event, String>("Name")
        );
        prix.setCellValueFactory(
                new PropertyValueFactory<Event, Float>("price")
        );
        capacite1.setCellValueFactory(
                new PropertyValueFactory<Event, Integer>("reste")
        );
        date_events.setCellValueFactory(
                new PropertyValueFactory<Event, Date>("date")
        );
        localisation.setCellValueFactory(
                new PropertyValueFactory<Event, String>("location")
        );
        description.setCellValueFactory(
                new PropertyValueFactory<Event, String>("Description")
        );
        tableEvents.setEditable(false);
        tableEvents.setItems(allEvents);

        nom1.setCellValueFactory(
                new PropertyValueFactory<Event, String>("Name")
        );
        prix1.setCellValueFactory(
                new PropertyValueFactory<Event, Float>("price")
        );
        capacite11.setCellValueFactory(
                new PropertyValueFactory<Event, Integer>("reste")
        );
        date_events1.setCellValueFactory(
                new PropertyValueFactory<Event, Date>("date")
        );
        localisation1.setCellValueFactory(
                new PropertyValueFactory<Event, String>("location")
        );
        description1.setCellValueFactory(
                new PropertyValueFactory<Event, String>("Description")
        );
        tableEvents1.setEditable(false);
        tableEvents1.setItems(myEvents);

        searchEvents1.textProperty().addListener((observable, oldValue, newValue)
                -> tableEvents1.setItems(filterList(myEvents, newValue)));
        searchEvents.textProperty().addListener((observable, oldValue, newValue)
                -> tableEvents.setItems(filterList(allEvents, newValue)));
    }

    private boolean searchFindsEvent(Event event, String searchText) {
        return (event.getName().toLowerCase().contains(searchText.toLowerCase()))
                || (String.valueOf(event.getPrice()).contains(searchText.toLowerCase()))
                || (String.valueOf(event.getReste()).contains(searchText.toLowerCase()))
                || (String.valueOf(event.getDate()).contains(searchText.toLowerCase()))
                || (String.valueOf(event.getLocation()).contains(searchText.toLowerCase()))
                || (String.valueOf(event.getDescription()).contains(searchText.toLowerCase()));
    }

    private ObservableList<Event> filterList(List<Event> list, String searchText) {
        List<Event> filteredList = new ArrayList<>();
        for (Event event : list) {
            if (searchFindsEvent(event, searchText)) {
                filteredList.add(event);
            }
        }
        return FXCollections.observableList(filteredList);
    }

    @FXML
    private void DelEventAction(ActionEvent event) {
        Event e = (Event) tableEvents1.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Dialogue de Confirmation");
        alert.setHeaderText("Supprimer l'evenement");
        alert.setContentText("Etes-vous sur de supprimer cet evenement?");
        //Customize the buttons in the confirmation dialog
        ButtonType buttonTypeYes = new ButtonType("Oui");
        ButtonType buttonTypeNo = new ButtonType("Non");
        //Set buttons onto the confirmation window
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        //Get the user's answer on whether deleting or not
        Optional<ButtonType> result = alert.showAndWait();

        //If the user wants to delete the planning, call the function that deletes the planing. Otherwise, close the window
        if (result.get() == buttonTypeYes) {
            System.out.println(e);
            this.serviceParticipation.supprimerByIdUserAndEvent(id_user, e.getId());
            UpdateAll();
        }

    }

    public void UpdateAll() {

        List<Event> list = serviceEvent.afficher();

        for (Event e : list) {
            int size = serviceParticipation.getAllPartsByEventId(e.getId()).size();
            e.setReste(e.getCapacity() - size);
        }

        List<Event> list1 = serviceEvent.getAllParticipedEventsByUserId(id_user);

        for (Event e : list1) {
            int size = serviceParticipation.getAllPartsByEventId(e.getId()).size();
            e.setReste(e.getCapacity() - size);
        }

        allEvents = FXCollections.observableArrayList(list);
        myEvents = FXCollections.observableArrayList(list1);
        tableEvents.setItems(allEvents);
        tableEvents1.setItems(myEvents);
    }

    @FXML
    private void ParticipateAction(ActionEvent event) {
        boolean exist = false;
        if (tableEvents.getSelectionModel().getSelectedIndex() == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Dialogue d'Erreur");
            alert.setHeaderText("Veuillez selectionner un evenement");
            //Customize the buttons in the confirmation dialog
            ButtonType buttonTypeYes = new ButtonType("D'accord");
            //Set buttons onto the confirmation window
            alert.getButtonTypes().setAll(buttonTypeYes);

            //Get the user's answer on whether deleting or not
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            Event e = (Event) tableEvents.getSelectionModel().getSelectedItem();

            List<Participation> listP = serviceParticipation.getAllPartsByEventId(e.getId());

            for (Participation ev : listP) {
                if (ev.getUser().getId() == id_user) {
                    exist = true;
                }
            }
            if (exist) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Dialogue d'Erreur");
                alert.setHeaderText("Vous etes déja inscrit");
                alert.setContentText("Vous avez déja participé à cet evenement");
                //Customize the buttons in the confirmation dialog
                ButtonType buttonTypeYes = new ButtonType("D'accord");
                //Set buttons onto the confirmation window
                alert.getButtonTypes().setAll(buttonTypeYes);

                //Get the user's answer on whether deleting or not
                Optional<ButtonType> result = alert.showAndWait();
            } else if(e.getReste()<=0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Dialogue d'Erreur");
                alert.setHeaderText("Cet evenement est complet");
                alert.setContentText("Vous ne pouvez pas participer à cet evenement");
                //Customize the buttons in the confirmation dialog
                ButtonType buttonTypeYes = new ButtonType("D'accord");
                //Set buttons onto the confirmation window
                alert.getButtonTypes().setAll(buttonTypeYes);

                //Get the user's answer on whether deleting or not
                Optional<ButtonType> result = alert.showAndWait();
                
            }else{
                Participation pr = new Participation();
                LocalDate locald = LocalDate.now();
                Date date = Date.valueOf(locald);
                pr.setDate(date);
                User u = new User();
                u.setId(id_user);
                pr.setUser(u);
                pr.setEvent(e);
                
                serviceParticipation.ajouter(pr);
                
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Dialogue de succes");
                alert.setHeaderText("Participation aves succes");
                alert.setContentText("Vous étes maintenant inscrit à cet evenement");
                //Customize the buttons in the confirmation dialog
                ButtonType buttonTypeYes = new ButtonType("D'accord");
                //Set buttons onto the confirmation window
                alert.getButtonTypes().setAll(buttonTypeYes);

                //Get the user's answer on whether deleting or not
                Optional<ButtonType> result = alert.showAndWait();
                UpdateAll();
            }
        }

    }

    @FXML
    private void ExportPDF(ActionEvent event) {
      if (tableEvents1.getSelectionModel().getSelectedIndex() == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Dialogue d'Erreur");
            alert.setHeaderText("Veuillez selectionner un evenement");
            //Customize the buttons in the confirmation dialog
            ButtonType buttonTypeYes = new ButtonType("D'accord");
            //Set buttons onto the confirmation window
            alert.getButtonTypes().setAll(buttonTypeYes);

            //Get the user's answer on whether deleting or not
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            Event e = (Event) tableEvents1.getSelectionModel().getSelectedItem();
            Participation per = serviceParticipation.getPartByEventAndUserId(e.getId(), id_user);
            System.out.println(per);
            serviceParticipation.ParticipationToPDF(per);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Dialogue de succes");
                alert.setHeaderText("Exportation aves succes");
                //Customize the buttons in the confirmation dialog
                ButtonType buttonTypeYes = new ButtonType("D'accord");
                //Set buttons onto the confirmation window
                alert.getButtonTypes().setAll(buttonTypeYes);

                //Get the user's answer on whether deleting or not
                Optional<ButtonType> result = alert.showAndWait();
      }
    }

}
