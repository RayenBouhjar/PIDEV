/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilisateur;

import Entities.Avis;
import Entities.User;
import Services.AvisService;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author skand
 */
public class AjoutAvisController implements Initializable {

    AvisService as=new AvisService();
    
    User u=new User();
    
    private int n=1;
    
    @FXML
    private Rating rating;
    @FXML
    private TextArea description;
    @FXML
    private Label note;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        rating.ratingProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number t, Number t1) {
                note.setText(t1.toString());
                n=t1.intValue();
            }
        });
        note.setVisible(false);
    }    

    @FXML
    private void envoyer(ActionEvent event) throws SQLException {
        LocalDate currentDate = LocalDate.now();
        java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate);
        as.add(new Avis(n,description.getText(),u.getId(),sqlDate));
    }
    
}
