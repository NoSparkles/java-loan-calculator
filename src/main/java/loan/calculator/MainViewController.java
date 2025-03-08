package loan.calculator;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainViewController {
    @FXML private TextField loanAmount;
    @FXML private TextField termYears;
    @FXML private TextField termMonths;
    @FXML private TextField annualRate;
    @FXML private ChoiceBox<String> scheduleType;
    @FXML private TextField delay;
    @FXML private TextField fromMonth;
    @FXML private TextField toMonth;
    @FXML private Button showGraphButton;

    @FXML private TableView<PaymentSchedule> scheduleTable;
    @FXML private TableColumn<PaymentSchedule, Integer> monthColumn;
    @FXML private TableColumn<PaymentSchedule, Double> interestColumn;
    @FXML private TableColumn<PaymentSchedule, Double> paymentColumn;
    @FXML private TableColumn<PaymentSchedule, Double> balanceColumn;

    PaymentSchedule[] schedule = null;

    @FXML
    public void initialize() {
        scheduleType.setItems(FXCollections.observableArrayList("Anuitetas", "Linijinis"));
        scheduleType.setValue("Anuitetas");

        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        interestColumn.setCellValueFactory(new PropertyValueFactory<>("interestPayment"));
        paymentColumn.setCellValueFactory(new PropertyValueFactory<>("totalPayment"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("remainingBalance"));
    }

    public void restoreState(LoanState state) {
        this.loanAmount.setText(state.getLoanAmount());
        this.termYears.setText(state.getTermYears());
        this.termMonths.setText(state.getTermMonths());
        this.annualRate.setText(state.getAnnualRate());
        this.delay.setText(state.getDelay());
        this.fromMonth.setText(state.getFromMonth());
        this.toMonth.setText(state.getToMonth());
        this.scheduleType.setValue(state.getScheduleType());
        this.schedule = state.getPaymentSchedule();
    }

    @FXML
    public void setSchedule(PaymentSchedule[] schedule) {
        this.schedule = schedule;
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
            if (rate < 0) {
                this.annualRate.setText("0");
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
            int months = Integer.parseInt(this.delay.getText());
            if (months < 0) {
                this.delay.setText("0");
            }
            else if (months > 96) {
                this.delay.setText("96");
            }
        } 
        catch (NumberFormatException exception) {
            this.delay.setText("");
        }
    }

    @FXML
    public void fromMonthOnTyped() {
        if (this.fromMonth.getText().length() == 0) {
            return;
        }
        try {
            int months = Integer.parseInt(this.fromMonth.getText());
            if (months <= 0) {
                this.fromMonth.setText("0");
            }
        } 
        catch (NumberFormatException exception) {
            this.fromMonth.setText("");
        }
    }

    @FXML
    public void toMonthOnTyped() {
        if (this.toMonth.getText().length() == 0) {
            return;
        }
        try {
            int months = Integer.parseInt(this.toMonth.getText());
            if (months <= 0) {
                this.toMonth.setText("0");
            }
        } 
        catch (NumberFormatException exception) {
            this.fromMonth.setText("");
        }
    }

    @FXML
    public void calculate() {
        if (this.loanAmount.getText().length() == 0) {
            this.loanAmount.setText("0");
        }
        if (this.termYears.getText().length() == 0) {
            this.termYears.setText("0");
        }
        if (this.termMonths.getText().length() == 0) {
            this.termMonths.setText("0");
        }
        if (this.termYears.getText().equals("0") && this.termMonths.getText().equals("0")) {
            this.termMonths.setText("1");
        }
        if (this.annualRate.getText().length() == 0) {
            this.annualRate.setText("0");
        }
        if (this.delay.getText().length() == 0) {
            this.delay.setText("0");
        }
        if (this.fromMonth.getText().length() == 0) {
            this.fromMonth.setText("0");
        }
        if (Integer.parseInt(this.fromMonth.getText()) > Integer.parseInt(this.termYears.getText()) + Integer.parseInt(this.termMonths.getText())) {
            this.fromMonth.setText(String.valueOf(Integer.parseInt(this.termYears.getText()) + Integer.parseInt(this.termMonths.getText())));
        }
        if (this.toMonth.getText().length() == 0) {
            this.toMonth.setText("0");
        }
        if (Integer.parseInt(this.toMonth.getText()) < Integer.parseInt(this.fromMonth.getText())) {
            this.toMonth.setText(String.valueOf(Integer.parseInt(this.fromMonth.getText())));
        }
        if (Integer.parseInt(this.toMonth.getText()) > Integer.parseInt(this.termYears.getText()) + Integer.parseInt(this.termMonths.getText())) {
            this.toMonth.setText(String.valueOf(Integer.parseInt(this.termYears.getText()) + Integer.parseInt(this.termMonths.getText())));
        }

        try {
            double amount = Double.parseDouble(this.loanAmount.getText());
            int years = Integer.parseInt(this.termYears.getText());
            int months = Integer.parseInt(this.termMonths.getText());
            double rate = Double.parseDouble(this.annualRate.getText());
            String scheduleType = this.scheduleType.getValue();
            int delay = Integer.parseInt(this.delay.getText());
            int fromMonth = Integer.parseInt(this.fromMonth.getText());
            int toMonth = Integer.parseInt(this.fromMonth.getText());

            ObservableList<PaymentSchedule> data = FXCollections.observableArrayList();

            Loan loan;
            if ("Anuitetas".equals(scheduleType)) {
                loan = new AnnuityLoan(amount, years, months, rate, delay);
            }
            else {
                loan = new LinearLoan(amount, years, months, rate, delay);
            }
            this.schedule = loan.getPaymentSchedule();
            data.addAll(this.schedule);

            scheduleTable.getItems().clear();
            scheduleTable.setItems(data);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid input: " + ex.getMessage());
        }
    }

    @FXML
    public void showGraph() throws IOException {
        if (this.schedule == null) {
            return;
        }

        LoanState state = LoanState.getInstance();
        state.setLoanAmount(loanAmount.getText());
        state.setTermYears(termYears.getText());
        state.setTermMonths(termMonths.getText());
        state.setAnnualRate(annualRate.getText());
        state.setDelay(delay.getText());
        state.setScheduleType(scheduleType.getValue());
        state.setPaymentSchedule(this.schedule);

        // Load the GraphView scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GraphView.fxml"));
        Parent root = loader.load();

        GraphViewController controller = loader.getController();
        controller.initializeLineChart();

        // Set the new scene
        Stage stage = (Stage) showGraphButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
