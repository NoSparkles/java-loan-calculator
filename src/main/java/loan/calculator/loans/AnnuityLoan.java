package loan.calculator.loans;

public class AnnuityLoan extends Loan {

    public AnnuityLoan(double amount, int termYears, int termMonths, double annualRate, int fromDelay, int delay) {
        super(amount, termYears, termMonths, annualRate, fromDelay, delay);
    }

    
    @Override
    public PaymentSchedule[] getPaymentSchedule(int fromMonth, int toMonth) {
        double monthlyPayment = calculateMonthlyPayment(); // Standard annuity payment calculation
        int totalMonths = getTotalLoanTermMonths();
        PaymentSchedule[] schedule = new PaymentSchedule[totalMonths + 1]; // +1 for the summary row

        double remainingBalance = this.amount;
        double totalInterestPayment = 0.0;

        // Regular payments before the deferment period
        for (int i = 0; i < fromDelay; i++) {
            double interestPayment = (this.annualRate / 100 / 12) * remainingBalance;
            double principalPayment = monthlyPayment - interestPayment; // Deduct interest from monthly payment
            totalInterestPayment += interestPayment;

            schedule[i] = new PaymentSchedule(
                Integer.toString(i + 1),
                roundToTwoDecimals(principalPayment), // Regular principal payment
                roundToTwoDecimals(interestPayment),
                roundToTwoDecimals(monthlyPayment),
                roundToTwoDecimals(remainingBalance)
            );

            remainingBalance -= principalPayment; // Reduce balance by principal payment
        }

        // Interest-only payments during the deferment period
        for (int i = fromDelay; i < fromDelay + delay; i++) {
            double interestPayment = (this.annualRate / 100 / 12) * remainingBalance;
            totalInterestPayment += interestPayment;

            schedule[i] = new PaymentSchedule(
                Integer.toString(i + 1),
                0.0, // No principal payment during deferment
                roundToTwoDecimals(interestPayment),
                roundToTwoDecimals(interestPayment), // Monthly payment is interest-only
                roundToTwoDecimals(remainingBalance)
            );
        }

        // Regular annuity payments after the deferment period
        for (int i = fromDelay + delay; i < totalMonths; i++) {
            double interestPayment = (this.annualRate / 100 / 12) * remainingBalance;
            double principalPayment = monthlyPayment - interestPayment;
            totalInterestPayment += interestPayment;

            schedule[i] = new PaymentSchedule(
                Integer.toString(i + 1),
                roundToTwoDecimals(principalPayment),
                roundToTwoDecimals(interestPayment),
                roundToTwoDecimals(monthlyPayment),
                roundToTwoDecimals(remainingBalance)
            );

            remainingBalance -= principalPayment; // Reduce balance by principal payment
        }

        // Add the summary row
        schedule[totalMonths] = new PaymentSchedule(
            "Iš viso:",
            roundToTwoDecimals(this.amount),
            roundToTwoDecimals(totalInterestPayment),
            roundToTwoDecimals(this.amount + totalInterestPayment),
            0.00
        );

        return this.getFilteredPaymentSchedule(schedule, fromMonth, toMonth);
    }

    public double calculateMonthlyPayment() {
        double monthlyRate = this.annualRate / 100 / 12;
        double totalMonths = getTotalLoanTermMonths() - delay;

        if (monthlyRate == 0) { // Handle zero-interest case
            return this.amount / totalMonths;
        }

        return (this.amount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -totalMonths));
    }
}
