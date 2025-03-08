package loan.calculator;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GraphViewController {
    @FXML private Button goBackButton;
    @FXML private LineChart<Number, Number> lineChart;

    private PaymentSchedule[] schedule;

    public void setSchedule(PaymentSchedule[] schedule) {
        this.schedule = schedule;
    }

    public void initializeLineChart() {
        // Clear existing data in the chart (if any)
        lineChart.getData().clear();
        
        // // Create a data series for monthly payments
        // XYChart.Series<Number, Number> series = new XYChart.Series<>();
        // series.setName("Monthly Payments");

        // // Populate the series with data from the PaymentSchedule
        // for (int i = 0; i < schedule.length; i++) {
        //     series.getData().add(new XYChart.Data<>(i + 1, schedule[i].getTotalPayment()));
        // }

        // // Add the series to the chart
        // lineChart.getData().add(series);

        // // Customize the chart axes labels
        // NumberAxis xAxis = (NumberAxis) lineChart.getXAxis();
        // xAxis.setLabel("Months");

        // NumberAxis yAxis = (NumberAxis) lineChart.getYAxis();
        // yAxis.setLabel("Monthly Payment");
    }

    @FXML
    public void goBack()  throws IOException {

        // Load MainView
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        Parent root = loader.load();

        // Get the controller
        MainViewController mainController = loader.getController();
        // Restore the state
        LoanState state = LoanState.getInstance();
        mainController.restoreState(state);
        mainController.calculate();

        // Set the scene
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
