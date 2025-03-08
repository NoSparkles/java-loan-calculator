package loan.calculator;

public class AnnuityLoan extends Loan {

    public AnnuityLoan(double amount, int termYears, int termMonths, double annualRate, int delay) {
        super(amount, termYears, termMonths, annualRate, delay);
    }

    @Override
    public PaymentSchedule[] getPaymentSchedule(int fromMonth, int toMonth) {
        double monthlyPayment = calculateMonthlyPayment(); // No rounding here
        int totalMonths = getTotalLoanTermMonths();
        PaymentSchedule[] schedule = new PaymentSchedule[totalMonths + 1]; // +1 for the summary row

        double remainingBalance = this.amount;
        double totalInterestPayment = 0.0;

        // Interest-only payments during the delay period
        for (int i = 0; i < this.delay; i++) {
            double interestPayment = (this.annualRate / 100 / 12) * remainingBalance;
            totalInterestPayment += interestPayment;
            schedule[i] = new PaymentSchedule(
                Integer.toString(i + 1),
                0.0,
                roundToTwoDecimals(interestPayment), // Round only at this step
                roundToTwoDecimals(interestPayment),
                roundToTwoDecimals(remainingBalance)
            );
        }

        // Regular annuity payments after the delay period
        for (int i = this.delay; i < totalMonths; i++) {
            double interestPayment = (this.annualRate / 100 / 12) * remainingBalance;
            double principalPayment = monthlyPayment - interestPayment;
            totalInterestPayment += interestPayment;

            schedule[i] = new PaymentSchedule(
                Integer.toString(i + 1),
                roundToTwoDecimals(principalPayment), // Round only at this step
                roundToTwoDecimals(interestPayment),
                roundToTwoDecimals(monthlyPayment),
                roundToTwoDecimals(remainingBalance)
            );

            remainingBalance -= principalPayment; // Use full precision here
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
