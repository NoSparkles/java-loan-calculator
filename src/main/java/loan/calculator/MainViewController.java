package loan.calculator;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainViewController {
    @FXML private TextField loanAmount;
    @FXML private TextField termYears;
    @FXML private TextField termMonths;
    @FXML private TextField annualRate;
    @FXML private ChoiceBox<String> paymentSchedule;
    @FXML private Label monthlyPayment;

    private CalculatorController calculatorController;

    @FXML
    public void initialize() {
        // Initialize controller here if needed
    }

    @FXML
    public void calculate() {
        double amount = Double.parseDouble(loanAmount.getText());
        int years = Integer.parseInt(termYears.getText());
        int months = Integer.parseInt(termMonths.getText());
        double rate = Double.parseDouble(annualRate.getText());
        String scheduleType = paymentSchedule.getValue();

        Loan loan = new Loan(amount, years, months, rate);
        calculatorController = new CalculatorController(loan);
        calculatorController.calculate();

        monthlyPayment.setText(String.format("%.2f", loan.getMonthlyPayment()));
    }
}
