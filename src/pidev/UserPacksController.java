/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Pack;
import models.User;
import services.ServicePack;

/**
 * FXML Controller class
 *
 * @author Sadok Laouissi
 */
public class UserPacksController implements Initializable {

    @FXML
    private TableView table;
    @FXML
    private TableColumn nom;
    @FXML
    private TableColumn prix;
    @FXML
    private TableColumn description;
    @FXML
    private TextField search;
    @FXML
    private Button add;

    private int user_id=1;
    ServicePack sp;
    /**
     * Initializes the controller class.
     */
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
        table.setItems(filterList(data, newValue)));
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
    private void AddAction(ActionEvent event) throws Exception {
        
        Pack pack = (Pack) table.getSelectionModel().getSelectedItem();
        sp.subscribe(user_id, pack.getId());
        System.out.println("added");
        User u = new User();
        //sp.SendSMS(u, "Vous venez d'achetez un pack.");
        ServicePack.sendMail("skander.bouamayed@esprit.tn");
    }
    
}
