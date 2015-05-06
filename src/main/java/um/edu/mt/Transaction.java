package um.edu.mt;

public class Transaction {

    private AccountDatabase db;
    private int sourceAccountNumber;
    private int destinationAccountNumber;
    private long amount;
    private String note = "";

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

        if (a == null || b == null) return false;
        long now = System.currentTimeMillis();

        boolean checkA,checkB;
        long milliSecondsA = 0,milliSecondsB = 0;

        checkA = a.getTransactionOccured();
        checkB = b.getTransactionOccured();
        if (checkA)  { //transaction has occured before
            milliSecondsA = (now- a.getLastTransaction());
        }
        else milliSecondsA = 16;
        if (checkB)  { //transaction has occured before
            milliSecondsB = (now-b.getLastTransaction());
        }
        else milliSecondsB =16;

        if (milliSecondsA < 15 || milliSecondsB < 15) return false;
        if (a.getAccountBalance() < amount) return false;
        else {
            a.adjustBalance(-amount);
            b.adjustBalance(amount);
            a.setLastTransaction(now);
            b.setLastTransaction(now);
            a.setTransactionOccured(true);
            b.setTransactionOccured(true);
        }
        return true;
    }

    public String getInfo() {
        return sourceAccountNumber + "->" + destinationAccountNumber + " " + note;
    }

}
