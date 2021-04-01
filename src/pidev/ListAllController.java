/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev;

import java.io.IOException;
import static java.lang.Float.parseFloat;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;
import models.Pack;
import services.ServicePack;

/**
 * FXML Controller class
 *
 * @author Rednaks
 */
public class ListAllController implements Initializable {

    /**
     * Initializes the controller class.
     */
    ServicePack sp;
    @FXML
    private TableColumn nom, prix;
    @FXML
    private TableView table;
    @FXML
    private TextField search;
    @FXML
    private Button add, supprimer, modifier;
    @FXML
    private TableColumn description;
    @FXML
    private Button stat;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        sp = new ServicePack();
        ObservableList<Pack> data = FXCollections.observableArrayList(sp.afficher());

        nom.setCellValueFactory(
                new PropertyValueFactory<Pack, String>("nom")
        );
        prix.setCellValueFactory(
                new PropertyValueFactory<Pack, Float>("prix")
        );
        description.setCellValueFactory(
                new PropertyValueFactory<Pack, String>("description")
        );
        table.setEditable(false);
        table.setItems(data);
        
        search.textProperty().addListener((observable, oldValue, newValue) ->
        table.setItems(filterList(data, newValue))
);
    }

    @FXML
    private void AddAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) add.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("AddPack.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        //.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void DelAction(ActionEvent event) throws IOException {
        Pack pack = (Pack) table.getSelectionModel().getSelectedItem();
        System.out.println(pack);
        sp.supprimer(pack);
        Stage stage;
        Parent root;
        stage = (Stage) add.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("ListAll.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void EditAction(ActionEvent event) throws IOException {
       
        Pack pack = (Pack) table.getSelectionModel().getSelectedItem();
        // Step 2
        Stage stage;
        Parent root;
        stage = (Stage) add.getScene().getWindow();
        //.getSelectionModel().getSelectedItem();
      
        try {
            // Step 4
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditPack.fxml"));
             root = loader.load();
           // Parent root = FXMLLoader.load(getClass().getResource("EditPack.fxml"));
            // Step 5
            EditPackController controller =  loader.<EditPackController>getController();
            controller.setData(pack);
            // Step 6
            Scene scene = new Scene(root);
            stage.setScene(scene);
            // Step 7
            stage.show();
        } catch (IOException e) {
            System.err.println(String.format("Error: %s", e.getMessage()));
        }
    }
    
    private boolean searchFindsPack(Pack pack, String searchText){
    return (pack.getNom().toLowerCase().contains(searchText.toLowerCase())) ||
            (String.valueOf(pack.getPrix()).contains(searchText.toLowerCase())) 
            ||
            (pack.getDescription().toLowerCase().contains(searchText.toLowerCase()));
}
    
    private ObservableList<Pack> filterList(List<Pack> list, String searchText){
    List<Pack> filteredList = new ArrayList<>();
    for (Pack pack : list){
        if(searchFindsPack(pack, searchText)) filteredList.add(pack);
    }
    return FXCollections.observableList(filteredList);
}

    @FXML
    private void GoToStat(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) add.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("PackStat.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
