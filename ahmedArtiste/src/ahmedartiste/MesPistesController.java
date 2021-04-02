/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahmedartiste;

import Entities.Album;
import Entities.Categorie;
import Entities.Piste;
import Services.PisteServices;
import java.io.File;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author skand
 */
public class MesPistesController implements Initializable {

    PisteServices ps = new PisteServices();
    ObservableList<Piste> pisteList = FXCollections.observableArrayList();

    Piste p = new Piste();

    @FXML
    private TableView<Piste> table;
    @FXML
    private TableColumn<Piste, Integer> id;
    @FXML
    private TableColumn<Piste, String> nom;
    @FXML
    private TableColumn<Piste, String> description;
    @FXML
    private TableColumn<Piste, String> album;
    @FXML
    private TableColumn<Piste, String> urlpiste;
    @FXML
    private TableColumn<Piste, ImageView> image;
    @FXML
    private TableColumn<Piste, String> photo;
    @FXML
    private TextField nom1;
    @FXML
    private TextArea des1;
    @FXML
    private ComboBox<String> album1;
    @FXML
    private Text url;
    @FXML
    private Text url_media;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        album1.setItems(FXCollections.observableArrayList(getData()));
        try {
            showAlbums();
        } catch (SQLException ex) {
            Logger.getLogger(MesPistesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        if (event.getClickCount() == 1) {
            table.setItems(pisteList);
            ObservableList<Piste> allPistes, r;
            allPistes = table.getItems();
            r = table.getSelectionModel().getSelectedItems();
            p = r.get(0);
            nom1.setText(p.getNom());
            des1.setText(p.getDescription());
            url.setText(p.getPhoto());
            album1.setValue(String.valueOf(p.getAlbum()));
            urlpiste.setText(p.getUrl_piste());
        }
    }

    @FXML
    private void modifier(ActionEvent event) throws SQLException {
        if ((url.getText().length() == 0) || (urlpiste.getText().length() == 0) || (nom1.getText().length() == 0) || (des1.getText().length() == 0)) {
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
            p.setId_album(0);
            p.setDescription(des1.getText());
            p.setNom(nom1.getText());
            p.setPhoto(url.getText());
            p.setUrl_piste(urlpiste.getText());
            ps.update(p);
            Notifications notificationBuilder = Notifications.create()
                    .title("Alert").text("Piste ajouté !!").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                    .position(Pos.CENTER)
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
    private void ajouter(ActionEvent event) throws SQLException {
        if ((url.getText().length() == 0) || (urlpiste.getText().length() == 0) || (nom1.getText().length() == 0) || (des1.getText().length() == 0)) {
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
            Piste pi = new Piste(nom1.getText(), url_media.getText(), url.getText(), des1.getText(), ps.getAlbumByNom(album1.getValue()));
            ps.add(pi);
            Notifications notificationBuilder = Notifications.create()
                    .title("Alert").text("Piste ajouté !!").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                    .position(Pos.CENTER)
                    .onAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            System.out.println("clicked ON ");
                        }
                    });
            notificationBuilder.darkStyle();
            notificationBuilder.show();
        }
        pisteList.clear();
        showAlbums();
    }

    @FXML
    private void supprimer(ActionEvent event) throws SQLException {
        if (p.getId() != 0) {
            ps.delete(p.getId());
            pisteList.clear();
            showAlbums();
            Notifications notificationBuilder = Notifications.create()
                    .title("Alert").text("Piste supprimé").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
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
                    .title("Alert").text("Selectionner une piste").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                    .position(Pos.CENTER)
                    .onAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            System.out.println("clicked ON ");
                        }
                    });
            notificationBuilder.darkStyle();
            notificationBuilder.show();
        }
        p.setId(0);
        nom1.setText("");
        des1.setText("");
        url.setText("");
        album1.setValue("");
    }

    @FXML
    private void upload(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File file;
        file = fc.showOpenDialog(null);
        String path = file.getPath();
        url.setText(path);
    }

    private List<String> getData() {
        List<String> strings = new ArrayList<>();
        try {
            strings = ps.getAlbums();
            return strings;
        } catch (SQLException ex) {
            return null;
        }
    }

    public void showAlbums() throws SQLException {
        List<Piste> liste = new ArrayList<Piste>();
        liste = ps.readAll();

        for (Piste aux : liste) {
            Piste pis = new Piste(aux.getId(), aux.getNom(), aux.getUrl_piste(), aux.getPhoto(), aux.getDescription(), aux.getId_album());
            pis.setImage(aux.getImage());
            pis.setMedia(aux.getMedia());

            pisteList.add(pis);
            table.setItems(pisteList);
        }

        id.setCellValueFactory(new PropertyValueFactory<Piste, Integer>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<Piste, String>("nom"));
        description.setCellValueFactory(new PropertyValueFactory<Piste, String>("description"));
        image.setCellValueFactory(new PropertyValueFactory<Piste, ImageView>("image"));
        photo.setCellValueFactory(new PropertyValueFactory<Piste, String>("photo"));
        album.setCellValueFactory(new PropertyValueFactory<Piste, String>("album"));
        urlpiste.setCellValueFactory(new PropertyValueFactory<Piste, String>("url_piste"));

        table.setItems(pisteList);
    }

    @FXML
    private void uploadpiste(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File file;
        file = fc.showOpenDialog(null);
        String path = file.getPath();
        url_media.setText(path);
    }

    @FXML
    private void play(ActionEvent event) {
        p.getMedia().play();
    }

    @FXML
    private void stop(ActionEvent event) {
        p.getMedia().stop();
    }

}
