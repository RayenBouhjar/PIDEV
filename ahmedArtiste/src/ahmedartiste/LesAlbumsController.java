/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahmedartiste;

import Entities.Piste;
import Entities.Vues;
import Services.PisteServices;
import Services.VuesService;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author skand
 */
public class LesAlbumsController implements Initializable {

    PisteServices ps = new PisteServices();
    VuesService vs = new VuesService();
    
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
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        if (event.getClickCount() == 1) {
            table.setItems(pisteList);
            ObservableList<Piste> allPistes, r;
            allPistes = table.getItems();
            r = table.getSelectionModel().getSelectedItems();
            p = r.get(0);
        }
        else if(event.getClickCount() == 2){
            //todo
        }
    }

    @FXML
    private void play(ActionEvent event) throws SQLException {
        p.getMedia().play();
        int id_user =1;
        if(!vs.Verify(id_user,p.getId())){
            vs.add(new Vues(id_user, p.getId()));
        }
    }

    @FXML
    private void stop(ActionEvent event) {
        p.getMedia().stop();
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
    private void getAlbum(ActionEvent event) throws SQLException {
        List<Piste> liste = new ArrayList<Piste>();
        liste = ps.readByAlbumNom(album1.getValue());

        for (Piste aux : liste) {
            Piste pis = new Piste(aux.getId(), aux.getNom(), aux.getUrl_piste(), aux.getPhoto(), aux.getDescription(), aux.getId_album());
            pis.setImage(aux.getImage());
            pis.setMedia(aux.getMedia());
            pis.setAlbum(aux.getAlbum());
            System.out.println(pis.toString());
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

}
