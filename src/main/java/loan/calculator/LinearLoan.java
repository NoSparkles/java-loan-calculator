package loan.calculator;

public class LinearLoan extends Loan {

    public LinearLoan(double amount, int termYears, int termMonths, double annualRate, int delay) {
        super(amount, termYears, termMonths, annualRate, delay);
    }

    @Override
    public PaymentSchedule[] getPaymentSchedule() {
        int totalMonths = getTotalLoanTermMonths();
        PaymentSchedule[] schedule = new PaymentSchedule[totalMonths];

        double remainingBalance = amount;
        double principalPayment = amount / (totalMonths - delay); // Equal principal payments after delay

        // Interest-only payments during the delay period
        for (int i = 0; i < delay; i++) {
            double interestPayment = (annualRate / 100 / 12) * remainingBalance;
            schedule[i] = new PaymentSchedule(
                i + 1,
                0.0, // No principal payment
                interestPayment, // Interest payment
                interestPayment, // Total payment equals interest
                remainingBalance // Balance remains unchanged
            );
        }

        // Linear payments after the delay period
        for (int i = delay; i < totalMonths; i++) {
            double interestPayment = (annualRate / 100 / 12) * remainingBalance;
            double totalPayment = principalPayment + interestPayment;

            schedule[i] = new PaymentSchedule(
                i + 1,
                principalPayment, // Fixed principal payment
                interestPayment, 
                totalPayment, 
                remainingBalance
            );

            remainingBalance -= principalPayment; // Reduce balance by principal payment
        }

        return schedule;
    }
}