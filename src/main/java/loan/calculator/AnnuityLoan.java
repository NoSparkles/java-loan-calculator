package loan.calculator;

public class AnnuityLoan extends Loan {

    public AnnuityLoan(double amount, int termYears, int termMonths, double annualRate, int delay) {
        super(amount, termYears, termMonths, annualRate, delay);
    }

    @Override
    public PaymentSchedule[] getPaymentSchedule() {
        double monthlyPayment = roundToTwoDecimals(calculateMonthlyPayment());
        int totalMonths = getTotalLoanTermMonths();
        PaymentSchedule[] schedule = new PaymentSchedule[totalMonths + 1]; // +1 for the summary row

        double remainingBalance = amount;
        double totalInterestPayment = 0.0; // To accumulate the sum of interest payments

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

        // Regular annuity payments after the delay period
        for (int i = delay; i < totalMonths; i++) {
            double interestPayment = roundToTwoDecimals((annualRate / 100 / 12) * remainingBalance);
            double principalPayment = roundToTwoDecimals(monthlyPayment - interestPayment);
            totalInterestPayment += interestPayment; // Accumulate interest payment
            schedule[i] = new PaymentSchedule(
                Integer.toString(i + 1), 
                principalPayment, 
                interestPayment, 
                monthlyPayment, 
                remainingBalance
            );
            remainingBalance = roundToTwoDecimals(remainingBalance - principalPayment);
        }

        // Add the summary row
        schedule[totalMonths] = new PaymentSchedule(
            "Iš viso:", // Month label for the summary
            roundToTwoDecimals(amount), // Total principal paid
            roundToTwoDecimals(totalInterestPayment), // Total interest paid
            roundToTwoDecimals(amount + totalInterestPayment), // Total payment (principal + interest)
            0.00 // Final balance
        );

        return schedule;
    }

    public double calculateMonthlyPayment() {
        double monthlyRate = annualRate / 100 / 12;
        double totalMonths = getTotalLoanTermMonths() - delay; // Adjusted for the delay
        return (amount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -totalMonths));
    }
}
