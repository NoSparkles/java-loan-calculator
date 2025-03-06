package loan.calculator;

public class LinearLoan extends Loan {

    public LinearLoan(double amount, int termYears, int termMonths, double annualRate, int delay) {
        super(amount, termYears, termMonths, annualRate, delay);
    }

    @Override
    public PaymentSchedule[] getPaymentSchedule() {
        int totalMonths = getTotalLoanTermMonths();
        PaymentSchedule[] schedule = new PaymentSchedule[totalMonths + 1]; // +1 for the summary row

        double remainingBalance = amount;
        double principalPayment = roundToTwoDecimals(amount / (totalMonths - delay)); // Equal principal payments after delay
        double totalInterestPayment = 0.0; // To calculate the sum of all interest payments

        // Interest-only payments during the delay period
        for (int i = 0; i < delay; i++) {
            double interestPayment = roundToTwoDecimals((annualRate / 100 / 12) * remainingBalance);
            totalInterestPayment += interestPayment; // Accumulate interest payment
            schedule[i] = new PaymentSchedule(
                Integer.toString(i + 1),
                0.0, // No principal payment
                interestPayment, // Interest payment
                interestPayment, // Total payment equals interest
                remainingBalance // Balance remains unchanged
            );
        }

        // Linear payments after the delay period
        for (int i = delay; i < totalMonths; i++) {
            double interestPayment = roundToTwoDecimals((annualRate / 100 / 12) * remainingBalance);
            totalInterestPayment += interestPayment; // Accumulate interest payment
            double totalPayment = roundToTwoDecimals(principalPayment + interestPayment);

            schedule[i] = new PaymentSchedule(
                Integer.toString(i + 1),
                principalPayment, // Fixed principal payment
                interestPayment, 
                totalPayment, 
                remainingBalance
            );

            remainingBalance = roundToTwoDecimals(remainingBalance - principalPayment); // Reduce balance by principal payment
        }

        // Add the summary row
        schedule[totalMonths] = new PaymentSchedule(
            "Iš viso:", // Month label for the summary
            roundToTwoDecimals(amount), // Total principal paid
            roundToTwoDecimals(totalInterestPayment), // Total interest paid
            roundToTwoDecimals(amount + totalInterestPayment), // Total paid (principal + interest)
            0.00 // Final balance
        );

        return schedule;
    }
}
