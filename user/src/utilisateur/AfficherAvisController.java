/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilisateur;

import Entities.Avis;
import Entities.User;
import Services.AvisService;
import Services.UserService;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.Notifications;

public class AfficherAvisController implements Initializable {

    AvisService as = new AvisService();

    ObservableList<Avis> AvisList = FXCollections.observableArrayList();
    Avis a = new Avis();

    private int id_avis = 0;

    @FXML
    private TableView<Avis> table;
    @FXML
    private TableColumn<Avis, Integer> id;
    @FXML
    private TableColumn<Avis, Integer> note;
    @FXML
    private TableColumn<Avis, String> nom;
    @FXML
    private TableColumn<Avis, String> description;
    @FXML
    private PieChart pie;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            showAvis();
        } catch (SQLException ex) {
            Logger.getLogger(AfficherAvisController.class.getName()).log(Level.SEVERE, null, ex);
        }
        showStats();
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        if (event.getClickCount() == 1) {
            table.setItems(AvisList);
            ObservableList<Avis> allClubs, r;
            allClubs = table.getItems();
            r = table.getSelectionModel().getSelectedItems();

            id_avis = r.get(0).getId();
            a = r.get(0);
        }
    }

    @FXML
    private void archiver(ActionEvent event) throws SQLException {
        as.Archiver(a);
        AvisList.clear();
        showAvis();
        showStats();
    }

    @FXML
    private void supprimer(ActionEvent event) throws SQLException {
        if (id_avis != 0) {
            as.delete(id_avis);
            AvisList.clear();
            showAvis();
            Notifications notificationBuilder = Notifications.create()
                    .title("Alert").text("Avis supprimé").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                    .position(Pos.CENTER_LEFT)
                    .onAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            System.out.println("clicked ON ");
                        }
                    });
            notificationBuilder.darkStyle();
            notificationBuilder.show();
            id_avis = 0;
        } else {
            Notifications notificationBuilder = Notifications.create()
                    .title("Alert").text("Clickez sur un avis").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                    .position(Pos.CENTER_LEFT)
                    .onAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            System.out.println("clicked ON ");
                        }
                    });
            notificationBuilder.darkStyle();
            notificationBuilder.show();
        }

        showStats();
    }

    public void showAvis() throws SQLException {
        List<Avis> liste = new ArrayList<Avis>();
        liste = as.readAll();

        for (Avis aux : liste) {
            AvisList.add(new Avis(aux.getId(), aux.getNote(), aux.getDescription(), aux.getId_user(), aux.getDate_creation(), aux.getNom()));
            table.setItems(AvisList);
        }

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        note.setCellValueFactory(new PropertyValueFactory<>("note"));

        table.setItems(AvisList);

    }

    void showStats() {
        ObservableList<PieChart.Data> pieChartData;
        try {
            pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("note 1", as.CalculeNote(1)),
                    new PieChart.Data("note 2", as.CalculeNote(2)),
                    new PieChart.Data("note 3", as.CalculeNote(3)),
                    new PieChart.Data("note 4", as.CalculeNote(4)),
                    new PieChart.Data("note 5", as.CalculeNote(5)));
            pie.setTitle("Stats des avis");
            pie.setData(pieChartData);

        } catch (SQLException ex) {
            Logger.getLogger(AfficherAvisController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
