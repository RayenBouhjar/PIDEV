/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achref;

import Entities.Piste;
import Entities.Playlist;
import Services.PlaylistService;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.Notifications;

public class MyPlaylistController implements Initializable {

    PlaylistService ps = new PlaylistService();

    ObservableList<Piste> pisteList = FXCollections.observableArrayList();

    Piste pist = new Piste();

    ObservableList<Playlist> playListObs = FXCollections.observableArrayList();

    Playlist p = new Playlist();
    Piste piste = new Piste();

    @FXML
    private TableView<Playlist> table;
    @FXML
    private TableColumn<Playlist, Integer> id;
    @FXML
    private TableColumn<Playlist, String> nom;
    @FXML
    private TableColumn<Playlist, ImageView> photo;
    @FXML
    private TableColumn<Playlist, String> description;
    @FXML
    private TableColumn<Playlist, String> album;
    @FXML
    private TableView<Piste> table1;
    @FXML
    private TableColumn<Piste, Integer> id1;
    @FXML
    private TableColumn<Piste, String> nom1;
    @FXML
    private TableColumn<Piste, String> urlpiste;
    @FXML
    private TableColumn<Piste, ImageView> image;
    @FXML
    private TableColumn<Piste, String> description1;
    @FXML
    private TableColumn<Piste, String> album1;
    @FXML
    private TableColumn<Piste, String> photo1;
    @FXML
    private TableColumn<Piste, String> artiste;
    @FXML
    private ComboBox<String> albumcombo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            showData();
            albumcombo.setItems(FXCollections.observableArrayList(getData()));
        } catch (SQLException ex) {
            Logger.getLogger(MyPlaylistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showData() throws SQLException {
        List<Playlist> liste = new ArrayList<Playlist>();
        int id_user = 1;
        liste = ps.readByUser(id_user);

        for (Playlist aux : liste) {
            Playlist pis = new Playlist(aux.getId(), aux.getId_user(), aux.getId_piste(), aux.getDate());
            pis.setNom_album(aux.getNom_album());
            pis.setNom_piste(aux.getNom_piste());
            pis.setImage(aux.getImage());
            pis.setDescription(aux.getDescription());
            pis.setDate(aux.getDate());
            pis.setMedia(aux.getMedia());

            playListObs.add(pis);
            table.setItems(playListObs);
        }

        id.setCellValueFactory(new PropertyValueFactory<Playlist, Integer>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<Playlist, String>("nom_piste"));
        description.setCellValueFactory(new PropertyValueFactory<Playlist, String>("description"));
        photo.setCellValueFactory(new PropertyValueFactory<Playlist, ImageView>("image"));
        album.setCellValueFactory(new PropertyValueFactory<Playlist, String>("nom_album"));

        table.setItems(playListObs);
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        if (event.getClickCount() == 1) {
            table.setItems(playListObs);
            ObservableList<Playlist> allPlaylistes, r;
            allPlaylistes = table.getItems();
            r = table.getSelectionModel().getSelectedItems();
            p = r.get(0);
            System.out.println("test");
        }
    }

    @FXML
    private void play(ActionEvent event) {
        p.getMedia().play();
    }

    @FXML
    private void stop(ActionEvent event) {
        p.getMedia().stop();
    }

    @FXML
    private void getAlbum(ActionEvent event) throws SQLException {
        pisteList.clear();
        List<Piste> liste = new ArrayList<Piste>();
        liste = ps.readByAlbumNom(albumcombo.getValue());

        for (Piste aux : liste) {
            Piste pis = new Piste(aux.getId(), aux.getNom(), aux.getUrl_piste(), aux.getPhoto(), aux.getDescription(), aux.getId_album());
            pis.setImage(aux.getImage());
            pis.setMedia(aux.getMedia());
            pis.setAlbum(aux.getAlbum());
            System.out.println(pis.toString());
            pisteList.add(pis);
            table1.setItems(pisteList);
        }

        id1.setCellValueFactory(new PropertyValueFactory<Piste, Integer>("id"));
        nom1.setCellValueFactory(new PropertyValueFactory<Piste, String>("nom"));
        description1.setCellValueFactory(new PropertyValueFactory<Piste, String>("description"));
        image.setCellValueFactory(new PropertyValueFactory<Piste, ImageView>("image"));
        photo1.setCellValueFactory(new PropertyValueFactory<Piste, String>("photo"));
        album1.setCellValueFactory(new PropertyValueFactory<Piste, String>("album"));
        urlpiste.setCellValueFactory(new PropertyValueFactory<Piste, String>("url_piste"));

        table1.setItems(pisteList);
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

    @FXML
    private void handleMouseAction1(MouseEvent event) {
        if (event.getClickCount() == 1) {
            table1.setItems(pisteList);
            ObservableList<Piste> allPlaylistes, r;
            allPlaylistes = table1.getItems();
            r = table1.getSelectionModel().getSelectedItems();
            piste = r.get(0);
        }
    }

    @FXML
    private void ajouter(ActionEvent event) throws SQLException {
        if (piste.getId() != 0) {
            if (!ps.Verify(piste.getId())) {
                int id_user = 1;
                LocalDate currentDate = LocalDate.now();
                java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate);
                ps.add(new Playlist(id_user, piste.getId(), sqlDate));

                Notifications notificationBuilder = Notifications.create()
                        .title("Alert").text("Piste ajoutée a votre playListe").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                        .position(Pos.CENTER)
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
                    .title("Alert").text("Selectionnez une piste !!").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
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
        showData();
    }

    @FXML
    private void supprimer(ActionEvent event) throws SQLException {
        if (p.getId() != 0) {
            ps.delete(p.getId());
            Notifications notificationBuilder = Notifications.create()
                    .title("Alert").text("Piste supprimée de votre playliste!!").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
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
                    .title("Alert").text("Selectionnez une piste !!").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
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
        showData();
    }

}
