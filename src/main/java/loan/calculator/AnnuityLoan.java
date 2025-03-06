package loan.calculator;

public class AnnuityLoan extends Loan {

    public AnnuityLoan(double amount, int termYears, int termMonths, double annualRate, int delay) {
        super(amount, termYears, termMonths, annualRate, delay);
    }

    @Override
    public PaymentSchedule[] getPaymentSchedule() {
        double monthlyPayment = calculateMonthlyPayment();
        int totalMonths = getTotalLoanTermMonths();
        PaymentSchedule[] schedule = new PaymentSchedule[totalMonths];

        double remainingBalance = amount;

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

        // Regular annuity payments after the delay period
        for (int i = delay; i < totalMonths; i++) {
            double interestPayment = (annualRate / 100 / 12) * remainingBalance;
            double principalPayment = monthlyPayment - interestPayment;
            schedule[i] = new PaymentSchedule(
                i + 1, 
                principalPayment, 
                interestPayment, 
                monthlyPayment, 
                remainingBalance
            );
            remainingBalance -= principalPayment;
        }

        return schedule;
    }

    public double calculateMonthlyPayment() {
        double monthlyRate = annualRate / 100 / 12;
        double totalMonths = getTotalLoanTermMonths() - delay; // Adjusted for the delay
        return (amount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -totalMonths));
    }
}
