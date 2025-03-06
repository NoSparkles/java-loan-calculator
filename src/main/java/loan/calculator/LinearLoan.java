package loan.calculator;

public class LinearLoan extends Loan {

    public LinearLoan(double amount, int termYears, int termMonths, double annualRate) {
        super(amount, termYears, termMonths, annualRate);
    }

    @Override
    public PaymentSchedule[] getPaymentSchedule() {
        int totalMonths = this.getTotalLoanTermMonths();
        PaymentSchedule[] schedule = new PaymentSchedule[totalMonths];
        double principalPayment = amount / totalMonths;

        double remainingBalance = amount;
        for (int i = 0; i < totalMonths; i++) {
            double interestPayment = (annualRate / 100 / 12) * remainingBalance;
            double totalPayment = principalPayment + interestPayment;

            schedule[i] = new PaymentSchedule(i + 1, principalPayment, interestPayment, totalPayment, remainingBalance);

            remainingBalance -= principalPayment;
        }
        return schedule;
    }
}