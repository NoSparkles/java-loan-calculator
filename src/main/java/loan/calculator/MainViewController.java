package loan.calculator;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainViewController {
    @FXML private TextField loanAmount;
    @FXML private TextField termYears;
    @FXML private TextField termMonths;
    @FXML private TextField annualRate;
    @FXML private ChoiceBox<String> paymentSchedule;
    @FXML private Button calculateButton;
    @FXML private Label monthlyPayment;

    private CalculatorController calculatorController;

    @FXML
    public void initialize() {
        paymentSchedule.setItems(FXCollections.observableArrayList("Anuiteto", "Linijinis"));
        paymentSchedule.setValue("Anuiteto");
    }

    @FXML
    public void loanAmountOnTyped() {
        if (this.loanAmount.getText().length() == 0) {
            return;
        }
        try {
            int amount = Integer.parseInt(this.loanAmount.getText());
            if (amount <= 0 || amount >= 1000000) {
                this.loanAmount.setText("1000000");
            }
            else if (this.loanAmount.getText().length() != 1){
                this.loanAmount.setText(this.loanAmount.getText().replaceFirst("^0+", ""));
            }
        } 
        catch (NumberFormatException exception) {
            this.loanAmount.setText("0");
        }
    }

    @FXML
    public void termYearsOnTyped() {
        if (this.termYears.getText().length() == 0) {
            return;
        }
        try {
            int years = Integer.parseInt(this.termYears.getText());
            if (years < 0) {
                this.termYears.setText("0");
            }
            else if (years > 30) {
                this.termYears.setText("30");
            }
            else if (this.termYears.getText().length() != 1) {
                this.termYears.setText(this.termYears.getText().replaceFirst("^0+", ""));
            }
        } 
        catch (NumberFormatException exception) {
            this.termYears.setText("0");
        }
    }

    @FXML
    public void termMonthsOnTyped() {
        if (this.termMonths.getText().length() == 0) {
            return;
        }
        try {
            int months = Integer.parseInt(this.termMonths.getText());
            if (months < 0) {
                this.termMonths.setText("0");
            }
            else if (months >= 12) {
                this.termMonths.setText("11");
            }
            else if (this.termMonths.getText().length() != 1) {
                this.termMonths.setText(this.termMonths.getText().replaceFirst("^0+", ""));
            }
        } 
        catch (NumberFormatException exception) {
            this.termMonths.setText("0");
        }
    }

    @FXML
    public void annualRateOnTyped() {
        if (this.annualRate.getText().length() == 0) {
            return;
        }
        try {
            int rate = Integer.parseInt(this.annualRate.getText());
            if (rate <= 0) {
                this.annualRate.setText("0");
            }
            else if (rate > 100) {
                this.annualRate.setText("100");
            }
            else if (this.annualRate.getText().length() != 1) {
                this.annualRate.setText(this.annualRate.getText().replaceFirst("^0+", ""));
            }
        } 
        catch (NumberFormatException exception) {
            this.annualRate.setText("0");
        }
    }

    @FXML
    public void calculate() {
        double amount = Double.parseDouble(loanAmount.getText());
        int years = Integer.parseInt(termYears.getText());
        int months = Integer.parseInt(termMonths.getText());
        double rate = Double.parseDouble(annualRate.getText());
        String scheduleType = paymentSchedule.getValue();

        Loan loan = new Loan(amount, years, months, rate);
        this.calculatorController = new CalculatorController(loan);
        calculatorController.calculate();

        monthlyPayment.setText(String.format("%.2f", loan.getMonthlyPayment()));
    }
}
