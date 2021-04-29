/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahmedartiste;

import Entities.Album;
import Entities.Categorie;
import Entities.Piste;
import Services.AlbumService;
import Services.PisteServices;
import Services.VuesService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.controlsfx.control.Notifications;

public class MesAlbumsController implements Initializable {

    AlbumService as = new AlbumService();
    
    PisteServices ps = new PisteServices();
    
    VuesService vs = new VuesService();

    ObservableList<Album> AlbumList = FXCollections.observableArrayList();

    Album a = new Album();

    @FXML
    private TableView<Album> table;
    @FXML
    private TableColumn<Album, Integer> id;
    @FXML
    private TableColumn<Album, String> nom;
    @FXML
    private TableColumn<Album, Categorie> categorie;
    @FXML
    private TableColumn<Album, String> description;
    @FXML
    private TableColumn<Album, ImageView> image;
    @FXML
    private TableColumn<Album, String> photo;
    @FXML
    private TextField nom1;
    @FXML
    private TextArea des1;
    @FXML
    private ComboBox<String> categorie1;
    @FXML
    private Text url;
    @FXML
    private BarChart<?, ?> barc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            showAlbums();
        } catch (SQLException ex) {
            Logger.getLogger(MesAlbumsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<String> categories = new ArrayList<>();
        categories.add(String.valueOf(Categorie.Blues));
        categories.add(String.valueOf(Categorie.Classique));
        categories.add(String.valueOf(Categorie.House));
        categorie1.setItems(FXCollections.observableArrayList(categories));

    }

    @FXML
    private void handleMouseAction(MouseEvent event) throws SQLException {
        if (event.getClickCount() == 1) {
            table.setItems(AlbumList);
            ObservableList<Album> allAlbums, r;
            allAlbums = table.getItems();
            r = table.getSelectionModel().getSelectedItems();
            a = r.get(0);
            nom1.setText(a.getNom());
            des1.setText(a.getDescription());
            url.setText(a.getPhoto());
            categorie1.setValue(String.valueOf(a.getCategorie()));

            List<Piste> liste=new ArrayList<>();
            liste=ps.readByAlbumId(a.getId());
            
            XYChart.Series dataSeries1 = new XYChart.Series();
            dataSeries1.setName("Nombre des vue par pistes");
            for(Piste pis:liste){
                dataSeries1.getData().add(new XYChart.Data(pis.getNom(), vs.CalculVue(pis.getId())));
            }

            barc.getData().add(dataSeries1);
        }
    }

    @FXML
    private void modifier(ActionEvent event) throws IOException, SQLException {
        if ((nom1.getText().length() == 0) || (des1.getText().length() == 0)) {
            Notifications notificationBuilder = Notifications.create()
                    .title("Alert").text("Input !!").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                    .position(Pos.CENTER)
                    .onAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            System.out.println("clicked ON ");
                        }
                    });
            notificationBuilder.darkStyle();
            notificationBuilder.show();
        } else {

            a.setCategorie(Categorie.valueOf(categorie1.getValue()));
            a.setDescription(des1.getText());
            if (url.getText().length() != 0) {
                File f = new File(url.getText());
                Files.copy(Paths.get(url.getText()), Paths.get("uploads/" + f.getName()), REPLACE_EXISTING);
                a.setPhoto("uploads/" + f.getName());
            }

            as.update(a);
            a.setId(0);

            Notifications notificationBuilder = Notifications.create()
                    .title("Alert").text("Album modifié ").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                    .position(Pos.CENTER)
                    .onAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            System.out.println("clicked ON ");
                        }
                    });
            notificationBuilder.darkStyle();
            notificationBuilder.show();
            AlbumList.clear();
            showAlbums();
        }
    }

    @FXML
    private void ajouter(ActionEvent event) throws IOException, SQLException {
        if ((nom1.getText().length() == 0) || (des1.getText().length() == 0)) {
            Notifications notificationBuilder = Notifications.create()
                    .title("Alert").text("Input !!").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                    .position(Pos.CENTER)
                    .onAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            System.out.println("clicked ON ");
                        }
                    });
            notificationBuilder.darkStyle();
            notificationBuilder.show();
        } else {
            File f = new File(url.getText());

            Files.copy(Paths.get(url.getText()), Paths.get("uploads/" + f.getName()), REPLACE_EXISTING);

            as.add(new Album(nom1.getText(), Categorie.valueOf(categorie1.getValue()), "uploads/" + f.getName(), des1.getText(), 1));

            Notifications notificationBuilder = Notifications.create()
                    .title("Alert").text("Album ajouté ").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                    .position(Pos.CENTER)
                    .onAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            System.out.println("clicked ON ");
                        }
                    });
            notificationBuilder.darkStyle();
            notificationBuilder.show();
            AlbumList.clear();
            showAlbums();
        }

        nom1.setText("");
        des1.setText("");
        url.setText("");
        categorie1.setValue("");
    }

    @FXML
    private void supprimer(ActionEvent event) throws SQLException {
        if (a.getId() != 0) {
            as.delete(a.getId());
            AlbumList.clear();
            showAlbums();
            Notifications notificationBuilder = Notifications.create()
                    .title("Alert").text("Album supprimé").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                    .position(Pos.CENTER)
                    .onAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            System.out.println("clicked ON ");
                        }
                    });
            notificationBuilder.darkStyle();
            notificationBuilder.show();
        } else {
            Notifications notificationBuilder = Notifications.create()
                    .title("Alert").text("Selectionner un album").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                    .position(Pos.CENTER)
                    .onAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            System.out.println("clicked ON ");
                        }
                    });
            notificationBuilder.darkStyle();
            notificationBuilder.show();
        }
        a.setId(0);
        nom1.setText("");
        des1.setText("");
        url.setText("");
        categorie1.setValue("");
    }

    public void showAlbums() throws SQLException {
        List<Album> liste = new ArrayList<Album>();
        liste = as.readAll();

        for (Album aux : liste) {
            Album al = new Album(aux.getId(), aux.getNom(), aux.getCategorie(), aux.getPhoto(), aux.getDescription(), aux.getId_artiste());
            al.setImage(aux.getImage());
            AlbumList.add(al);
            table.setItems(AlbumList);
        }

        id.setCellValueFactory(new PropertyValueFactory<Album, Integer>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<Album, String>("nom"));
        categorie.setCellValueFactory(new PropertyValueFactory<Album, Categorie>("categorie"));
        description.setCellValueFactory(new PropertyValueFactory<Album, String>("description"));
        image.setCellValueFactory(new PropertyValueFactory<Album, ImageView>("image"));
        photo.setCellValueFactory(new PropertyValueFactory<Album, String>("photo"));

        table.setItems(AlbumList);

    }

    @FXML
    private void upload(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File file;
        file = fc.showOpenDialog(null);
        String path = file.getPath();
        url.setText(path);
    }
}
