package loan.calculator;

import java.util.List;

public class CalculatorController {
    private Loan loan;
    private List<PaymentSchedule> paymentSchedules;

    public CalculatorController(Loan loan) {
        this.loan = loan;
    }

    public void calculate() {
        // Implement the calculation logic
    }

    public List<PaymentSchedule> filterPayments(int startMonth, int endMonth) {
        // Implement the filter logic
        return null;
    }

    public void generateReport(String filePath) {
        // Implement the report generation logic
    }
}
