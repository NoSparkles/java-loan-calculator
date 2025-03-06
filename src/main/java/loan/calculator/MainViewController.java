package loan.calculator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainViewController {
    @FXML private TextField loanAmount;
    @FXML private TextField termYears;
    @FXML private TextField termMonths;
    @FXML private TextField annualRate;
    @FXML private ChoiceBox<String> paymentSchedule;
    @FXML private TextField delay;
    @FXML private Button calculateButton;

    @FXML private TableView<PaymentSchedule> scheduleTable;
    @FXML private TableColumn<PaymentSchedule, Integer> monthColumn;
    @FXML private TableColumn<PaymentSchedule, Double> interestColumn;
    @FXML private TableColumn<PaymentSchedule, Double> paymentColumn;
    @FXML private TableColumn<PaymentSchedule, Double> balanceColumn;

    @FXML
    public void initialize() {
        paymentSchedule.setItems(FXCollections.observableArrayList("Anuiteto", "Linijinis"));
        paymentSchedule.setValue("Anuiteto");

        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        interestColumn.setCellValueFactory(new PropertyValueFactory<>("interestPayment"));
        paymentColumn.setCellValueFactory(new PropertyValueFactory<>("totalPayment"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("remainingBalance"));
    }

    @FXML
    public void loanAmountOnTyped() {
        if (this.loanAmount.getText().length() == 0) {
            return;
        }
        try {
            int amount = Integer.parseInt(this.loanAmount.getText());
            if (amount < 0 || amount > 1000000) {
                this.loanAmount.setText("1000000");
            }
        } 
        catch (NumberFormatException exception) {
            this.loanAmount.setText("");
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
        } 
        catch (NumberFormatException exception) {
            this.termYears.setText("");
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
            else if (months > 11) {
                this.termMonths.setText("11");
            }
        } 
        catch (NumberFormatException exception) {
            this.termMonths.setText("");
        }
    }

    @FXML
    public void annualRateOnTyped() {
        if (this.annualRate.getText().length() == 0) {
            return;
        }
        try {
            int rate = Integer.parseInt(this.annualRate.getText());
            if (rate < 1) {
                this.annualRate.setText("1");
            }
            else if (rate > 200) {
                this.annualRate.setText("200");
            }
        } 
        catch (NumberFormatException exception) {
            this.annualRate.setText("");
        }
    }

    @FXML
    public void delayOnTyped() {
        if (this.delay.getText().length() == 0) {
            return;
        }
        try {
            int delay = Integer.parseInt(this.delay.getText());
            if (delay <= 0) {
                this.annualRate.setText("0");
            }
            else if (delay > 1) {
                this.delay.setText("96");
            }
        } 
        catch (NumberFormatException exception) {
            this.delay.setText("");
        }
    }

    // !!!TODO: include delay in calculations
    @FXML
    public void calculate() {
        try {
            double amount = Double.parseDouble(loanAmount.getText());
            int years = Integer.parseInt(termYears.getText());
            int months = Integer.parseInt(termMonths.getText());
            double rate = Double.parseDouble(annualRate.getText());
            String scheduleType = paymentSchedule.getValue();

            ObservableList<PaymentSchedule> data = FXCollections.observableArrayList();

            if ("Anuiteto".equals(scheduleType)) {
                AnnuityLoan loan = new AnnuityLoan(amount, years, months, rate);
                PaymentSchedule[] schedule = loan.getPaymentSchedule();
                data.addAll(schedule); // Add all schedule entries to the data list
            } else if ("Linijinis".equals(scheduleType)) {
                LinearLoan loan = new LinearLoan(amount, years, months, rate);
                PaymentSchedule[] schedule = loan.getPaymentSchedule();
                data.addAll(schedule);
            }

            scheduleTable.getItems().clear();
            scheduleTable.setItems(data);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid input: " + ex.getMessage());
        }
    }
}
