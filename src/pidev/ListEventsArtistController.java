/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Artist;
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
public class ListEventsArtistController implements Initializable {
    private int selectedValue;
    @FXML
    private TableColumn nom;
    @FXML
    private TableColumn prix;
    @FXML
    private TableView tableEvents;
    @FXML
    private TableColumn capacite;
    @FXML
    private TableColumn date_events;
    @FXML
    private TableColumn localisation;
    @FXML
    private TableColumn description;
    @FXML
    private Button modifierEvents;
    @FXML
    private Button supprimerEvents;
    @FXML
    private TextField searchEvents;
    @FXML
    private TextField titre;
    @FXML
    private TextField descriptionadd;
    @FXML
    private DatePicker dateAdd;
    @FXML
    private TextField capaciteAdd;
    @FXML
    private TextField prixAdd;
    @FXML
    private TextField locationAdd;
    @FXML
    private TableView tablePart;
    @FXML
    private TableColumn<Participation,String> NomPart;
    @FXML
    private TableColumn<Participation,String> PrenomPart;
    @FXML
    private TableColumn DatePart;
    @FXML
    private Button Notify;
    @FXML
    private TextField searchPart;
    @FXML
    private Button ExportExcel;
    @FXML
    private ComboBox eventCombo;
    @FXML
    private Text capacitePart;
    @FXML
    private Text restePart;
    @FXML
    private Text totalPart;

    ServiceEvent serviceEvent;
    ServiceParticipation serviceParticipation;
    @FXML
    private Button Add;
    ObservableList<Event> artistEvents;
    ObservableList<Participation> eventParticipations;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        selectedValue = -1;
        serviceEvent = new ServiceEvent();
        serviceParticipation = new ServiceParticipation();

        // Setup  My Events
        artistEvents = FXCollections.observableArrayList(serviceEvent.getAllByArtistId(1));

        nom.setCellValueFactory(
                new PropertyValueFactory<Event, String>("Name")
        );
        prix.setCellValueFactory(
                new PropertyValueFactory<Event, Float>("price")
        );
        capacite.setCellValueFactory(
                new PropertyValueFactory<Event, Integer>("capacity")
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
        tableEvents.setItems(artistEvents);
        //Filter My events
        searchEvents.textProperty().addListener((observable, oldValue, newValue)
                -> tableEvents.setItems(filterList(artistEvents, newValue)));

        //Combo box fill
        ComboBoxFill();

        //Setup Participation Table
        NomPart.setCellValueFactory(
                
               cellData -> 
        new SimpleStringProperty(cellData.getValue().getUser().getNom()));
        

        PrenomPart.setCellValueFactory(
                
             cellData -> 
        new SimpleStringProperty(cellData.getValue().getUser().getPrenom()));

        DatePart.setCellValueFactory(
                new PropertyValueFactory<Participation, String>("date")
        );
        tablePart.setEditable(false);

    }

    @FXML
    private void EditAction(ActionEvent event) throws IOException {
        Event eve = (Event) tableEvents.getSelectionModel().getSelectedItem();
        Stage stage;
        Parent root;
        stage = (Stage) tableEvents.getScene().getWindow();
        try {
            // Step 4
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditEvent.fxml"));
             root = loader.load();
           // Parent root = FXMLLoader.load(getClass().getResource("EditPack.fxml"));
            // Step 5
            EditEventController controller =  loader.<EditEventController>getController();
            controller.setData(eve);
            // Step 6
            Scene scene = new Scene(root);
            stage.setScene(scene);
            // Step 7
            stage.show();
        } catch (IOException e) {
            System.err.println(String.format("Error: %s", e.getMessage()));
        }
    }

    // Filter My Events
    private boolean searchFindsEvent(Event event, String searchText) {
        return (event.getName().toLowerCase().contains(searchText.toLowerCase()))
                || (String.valueOf(event.getPrice()).contains(searchText.toLowerCase()))
                || (String.valueOf(event.getCapacity()).contains(searchText.toLowerCase()))
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

    // Delete My Events
    @FXML
    private void DelEventAction(ActionEvent event) {
        Event e = (Event) tableEvents.getSelectionModel().getSelectedItem();

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
            serviceEvent.supprimer(e);
            UpdateAll();
        }

    }

    // Add Event
    @FXML
    private void AddAction(ActionEvent event) {

        if (titre.getText().isEmpty()
                || descriptionadd.getText().isEmpty()
                || capaciteAdd.getText().isEmpty()
                || prixAdd.getText().isEmpty()
                || locationAdd.getText().isEmpty()
                || dateAdd.getValue() == null) {
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
            /*User Required*/
            Artist u = new Artist();
            u.setId(1);
            /*User Required*/
            Event e = new Event();
            e.setArtist(u);
            e.setCapacity(Integer.valueOf(capaciteAdd.getText()));
            e.setDate(java.sql.Date.valueOf(dateAdd.getValue()));
            e.setDescription(descriptionadd.getText());
            e.setLocation(locationAdd.getText());
            e.setPrice(Float.valueOf(prixAdd.getText()));
            e.setName(titre.getText());

            System.out.println(e);

            serviceEvent.ajouter(e);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succes");
            alert.setHeaderText("Ajout Avec Succes !");
            //Customize the buttons in the confirmation dialog
            ButtonType buttonTypeYes = new ButtonType("D'accord");

            //Set buttons onto the confirmation window
            alert.getButtonTypes().setAll(buttonTypeYes);

            //Get the user's answer on whether deleting or not
            Optional<ButtonType> result = alert.showAndWait();
            UpdateAll();
            clearAll();
        }

    }

    public void clearAll() {
        titre.setText("");
        descriptionadd.setText("");
        capaciteAdd.setText("");
        prixAdd.setText("");
        locationAdd.setText("");

    }

    public void ComboBoxFill() {

        ObservableList<String> items = FXCollections.observableArrayList();
        List<Event> ls = serviceEvent.getAllByArtistId(1);
        for (Event l : ls) {
            items.add(l.getName());
        }
        eventCombo.setItems(items);
    }

    public void UpdateAll() {
        ComboBoxFill();
        ObservableList<Event> artistEvents = FXCollections.observableArrayList(serviceEvent.getAllByArtistId(1));
        tableEvents.setItems(artistEvents);
        tableEvents.refresh();
    }

    @FXML
    private void OnItemSelect(ActionEvent event) {
        if ((eventCombo.getSelectionModel().getSelectedIndex() != -1)|| selectedValue!=eventCombo.getSelectionModel().getSelectedIndex()) {
            tablePart.getItems().clear();
            selectedValue = eventCombo.getSelectionModel().getSelectedIndex();
            System.out.println(selectedValue);
            Event e = artistEvents.get(eventCombo.getSelectionModel().getSelectedIndex());
            eventParticipations = FXCollections.observableArrayList(serviceParticipation.getAllPartsByEventId(e.getId()));
            capacitePart.setText(String.valueOf(e.getCapacity()));
            totalPart.setText(String.valueOf(eventParticipations.size()));
            restePart.setText(String.valueOf((e.getCapacity()-eventParticipations.size())));
           tablePart.setItems(eventParticipations);
           searchPart.textProperty().addListener((observable, oldValue, newValue)
                -> tablePart.setItems(filterListParticipants(eventParticipations, newValue)));
        }
    }
    
    // Filter Participants
    private boolean searchFindsParticipants(Participation participation, String searchText) {
        return (participation.getUser().getNom().toLowerCase().contains(searchText.toLowerCase()))
                || (participation.getUser().getPrenom().toLowerCase().contains(searchText.toLowerCase()))
                || (String.valueOf(participation.getDate()).contains(searchText.toLowerCase()));
    }

    private ObservableList<Participation> filterListParticipants(List<Participation> list, String searchText) {
        List<Participation> filteredList = new ArrayList<>();
        for (Participation event : list) {
            if (searchFindsParticipants(event, searchText)) {
                filteredList.add(event);
            }
        }
        return FXCollections.observableList(filteredList);
    }

    @FXML
    private void NotifyPart(ActionEvent event) {
         if (selectedValue!=-1){
            Event e = artistEvents.get(eventCombo.getSelectionModel().getSelectedIndex());
            eventParticipations = FXCollections.observableArrayList(serviceParticipation.getAllPartsByEventId(e.getId()));
            String m = "Notification D'evenement "+e.getName()+" le "+e.getDate().toString()+" à "+e.getLocation();
          if(serviceParticipation.notifyAllParticipants(eventParticipations,m))  {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succes");
            alert.setHeaderText("Notifié Avec Succes !");
            //Customize the buttons in the confirmation dialog
            ButtonType buttonTypeYes = new ButtonType("D'accord");

            //Set buttons onto the confirmation window
            alert.getButtonTypes().setAll(buttonTypeYes);

            //Get the user's answer on whether deleting or not
            Optional<ButtonType> result = alert.showAndWait();
          }else{
              Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Erreur d'envoi veuillez essayer encore !");
            //Customize the buttons in the confirmation dialog
            ButtonType buttonTypeYes = new ButtonType("D'accord");

            //Set buttons onto the confirmation window
            alert.getButtonTypes().setAll(buttonTypeYes);

            //Get the user's answer on whether deleting or not
            Optional<ButtonType> result = alert.showAndWait();
          }
        }else{
              Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Veuillez selectionner un evenement !");
            //Customize the buttons in the confirmation dialog
            ButtonType buttonTypeYes = new ButtonType("D'accord");

            //Set buttons onto the confirmation window
            alert.getButtonTypes().setAll(buttonTypeYes);

            //Get the user's answer on whether deleting or not
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    @FXML
    private void ExportExcell(ActionEvent event) {
        if (selectedValue!=-1){
            Event e = artistEvents.get(eventCombo.getSelectionModel().getSelectedIndex());
            eventParticipations = FXCollections.observableArrayList(serviceParticipation.getAllPartsByEventId(e.getId()));
          if(serviceParticipation.exportParticipantsToExcell(eventParticipations))  {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succes");
            alert.setHeaderText("Exportation Avec Succes !");
            //Customize the buttons in the confirmation dialog
            ButtonType buttonTypeYes = new ButtonType("D'accord");

            //Set buttons onto the confirmation window
            alert.getButtonTypes().setAll(buttonTypeYes);

            //Get the user's answer on whether deleting or not
            Optional<ButtonType> result = alert.showAndWait();
          }else{
              Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Erreur d'exportation  !");
            //Customize the buttons in the confirmation dialog
            ButtonType buttonTypeYes = new ButtonType("D'accord");

            //Set buttons onto the confirmation window
            alert.getButtonTypes().setAll(buttonTypeYes);

            //Get the user's answer on whether deleting or not
            Optional<ButtonType> result = alert.showAndWait();
          }
        }else{
              Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Veuillez selectionner un evenement !");
            //Customize the buttons in the confirmation dialog
            ButtonType buttonTypeYes = new ButtonType("D'accord");

            //Set buttons onto the confirmation window
            alert.getButtonTypes().setAll(buttonTypeYes);

            //Get the user's answer on whether deleting or not
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    
}
