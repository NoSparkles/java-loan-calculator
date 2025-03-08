package loan.calculator;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import loan.calculator.loans.LoanState;
import loan.calculator.loans.PaymentSchedule;

public class GraphViewController {
    @FXML private Button goBackButton;
    @FXML private LineChart<Number, Number> lineChart;

    public void initializeLineChart() {
        // Clear existing data in the chart (if any)
        lineChart.getData().clear();
                
        // Create a data series for monthly payments
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Mėnesinės įmokos");
        LoanState state = LoanState.getInstance();
        PaymentSchedule[] schedule = state.getPaymentSchedule();
    
        int fromMonth = Integer.parseInt(state.getFromMonth());
        
        // Populate the series with data from the PaymentSchedule
        for (int i = 0; i < schedule.length - 1; i++) {
            // Start numbering the months from `fromMonth`
            series.getData().add(new XYChart.Data<>(fromMonth + i, schedule[i].getTotalPayment()));
        }
        
        // Add the series to the chart
        lineChart.getData().add(series);
        
        // Customize the x-axis to reflect the correct month numbering
        NumberAxis xAxis = (NumberAxis) lineChart.getXAxis();
        xAxis.setTickUnit(1);
        xAxis.setForceZeroInRange(false);
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(fromMonth);
        xAxis.setUpperBound(fromMonth + schedule.length - 2);
    
        // Customize the y-axis if needed
        //NumberAxis yAxis = (NumberAxis) lineChart.getYAxis();
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
