package cerberus.controllers;

import cerberus.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

public class ChartsController implements Initializable {

    public static ChartsController instance;

    public PieChart pieChart;
    public BarChart<?, ?> barChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        setCharts();
    }

    public void setCharts() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList(
                new PieChart.Data("Birthday", Main.database.getBirthdays().find().size()),
                new PieChart.Data("Celebration", Main.database.getCelebrations().find().size()),
                new PieChart.Data("Farewell", Main.database.getFarewells().find().size()),
                new PieChart.Data("Wedding", Main.database.getWeddings().find().size())
        );

        pieChart.setData(data);

        XYChart.Series series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data("Birthday", Main.database.getBirthdays().find().size()));
        series.getData().add(new XYChart.Data("Celebration", Main.database.getCelebrations().find().size()));
        series.getData().add(new XYChart.Data("Farewell", Main.database.getFarewells().find().size()));
        series.getData().add(new XYChart.Data("Wedding", Main.database.getWeddings().find().size()));
        barChart.getData().add(series);
    }
}
