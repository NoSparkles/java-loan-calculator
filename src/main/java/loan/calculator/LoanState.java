package loan.calculator;

public class LoanState {
    private final static LoanState instance = new LoanState();

    private String loanAmount;
    private String termYears;
    private String termMonths;
    private String annualRate;
    private String delay;
    private String fromMonth;
    private String toMonth;
    private String scheduleType;
    private PaymentSchedule[] schedule;

    private LoanState() {}

    public static LoanState getInstance() {
        return LoanState.instance;
    }

    // Add getters and setters for your fields
    public String getLoanAmount() { return this.loanAmount; }
    public void setLoanAmount(String loanAmount) { this.loanAmount = loanAmount; }
    public String getTermYears() { return this.termYears; }
    public void setTermYears(String termYears) { this.termYears = termYears; }
    public String getTermMonths() { return this.termMonths; }
    public void setTermMonths(String termMonths) { this.termMonths = termMonths; }
    public String getAnnualRate() { return this.annualRate; }
    public void setAnnualRate(String annualRate) { this.annualRate = annualRate; }
    public String getDelay() { return this.delay; }
    public void setDelay(String delay) { this.delay = delay; }
    public String getFromMonth() { return this.fromMonth; }
    public void setFromMonth(String month) { this.fromMonth = month; }
    public String getToMonth() { return this.toMonth; }
    public void setToMonth(String month) { this.toMonth = month; }
    public String getScheduleType() { return this.scheduleType; }
    public void setScheduleType(String scheduleType) { this.scheduleType = scheduleType; }
    public PaymentSchedule[] getPaymentSchedule() { return this.schedule; }
    public void setPaymentSchedule(PaymentSchedule[] schedule) { this.schedule = schedule; }
}