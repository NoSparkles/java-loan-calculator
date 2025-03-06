package loan.calculator;

public class AnnuityLoan extends Loan {

    public AnnuityLoan(double amount, int termYears, int termMonths, double annualRate) {
        super(amount, termYears, termMonths, annualRate);
    }

    @Override
    public PaymentSchedule[] getPaymentSchedule() {
        double monthlyPayment = calculateMonthlyPayment();
        int totalMonths = (int) getTotalLoanTermMonths();
        PaymentSchedule[] schedule = new PaymentSchedule[totalMonths];

        double remainingBalance = amount;

        for (int i = 0; i < totalMonths; i++) {
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
        double totalMonths = getTotalLoanTermMonths();
        return (amount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -totalMonths));
    }
}
