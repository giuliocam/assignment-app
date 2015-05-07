package um.edu.mt;

public class Transaction { //Atomic Transaction

    private AccountDatabase db;
    private int sourceAccountNumber;
    private int destinationAccountNumber;
    private long amount;
    private String note = "";
    private String risk = "";

    public Transaction() {

    }

    public Transaction(AccountDatabase db, int src, int dst, long amount) {
        this.db = db;
        sourceAccountNumber = src;
        destinationAccountNumber = dst;
        this.amount = amount;
    }

    public Transaction(AccountDatabase db, int src, int dst, long amount, String desc) {
        this(db, src, dst, amount);
        note = desc;
    }

    public boolean process() {
        Account a = db.getAccount(sourceAccountNumber);
        Account b = db.getAccount(destinationAccountNumber);

        if (a == null || b == null|| sourceAccountNumber == destinationAccountNumber) return false;
        long now = System.currentTimeMillis();

        boolean checkA,checkB;
        long milliSecondsA ,milliSecondsB ;

        checkA = a.getTransactionOccurred();
        checkB = b.getTransactionOccurred();
        if (checkA)  { //transaction has occurred before
            milliSecondsA = (now-a.getLastTransaction());
        }
        else milliSecondsA = 16000;
        if (checkB)  { //transaction has occurred before
            milliSecondsB = (now-b.getLastTransaction());
        }
        else milliSecondsB = 16000;

        try{
            if (milliSecondsA < 15000){//check if 15 seconds passed, if not wait, for the amount of seconds that remain
                Thread.sleep(15000-(milliSecondsA));
            }
            if(milliSecondsB < 15000){
                Thread.sleep(15000-(milliSecondsB));
            }
        }catch(InterruptedException ex){
            Thread.currentThread().interrupt();
            return false;
        }

        if (a.getAccountBalance() < amount) return false;
        else {
            a.adjustBalance(-amount);
            b.adjustBalance(amount);
            a.setLastTransaction(now);
            b.setLastTransaction(now);
            a.setTransactionOccurred(true);
            b.setTransactionOccurred(true);
        }
        return true;
    }

    public String getInfo() {
        return sourceAccountNumber + "->" + destinationAccountNumber + " " + note;
    }

    public void setRisk(String s) {
        risk = s;
    }
    public int getSourceAccount() {
        return sourceAccountNumber;
    }
    public long getAmount() {
        return amount;
    }

    public String getRisk() {
        return risk;
    }

}
