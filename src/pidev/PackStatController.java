/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.Pack;
import services.ServicePack;

/**
 * FXML Controller class
 *
 * @author Sadok Laouissi
 */
public class PackStatController implements Initializable {

    @FXML
    private BarChart<String, Integer> barchart;
    @FXML
    private Button dismiss;
    @FXML
    private CategoryAxis xAxis;

    private ObservableList<String> packPrices = FXCollections.observableArrayList();
    @FXML
    private NumberAxis yAxis;
    ServicePack sp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        sp = new ServicePack();
        packPrices.addAll(sp.getAllPrices());
        xAxis.setCategories(packPrices);
        setData();
    }

    public void setData() {

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        List<String> list = sp.getAllPrices();

        for (String s : list) {
            int number = sp.getPackNumberByPrice(Float.valueOf(s));
            series.getData().add(new XYChart.Data<>(s, number));
        }

        barchart.getData().add(series);
    }

    @FXML
    private void Cancel(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) barchart.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("ListAll.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
