package um.edu.mt;

public class Transaction {

    AccountDatabase db;
    int sourceAccountNumber;
    int destinationAccountNumber;
    long amount;

    public Transaction(AccountDatabase db, int src, int dst, long amount) {
        this.db = db;
        sourceAccountNumber = src;
        destinationAccountNumber = dst;
        this.amount = amount;
    }

    public boolean process() {
        Account a = db.getAccount(sourceAccountNumber);
        Account b = db.getAccount(destinationAccountNumber);

        if(a == null || b == null) return false;

        if(a.getAccountBalance() < amount) return false;
        else {
            a.adjustBalance(-amount);
            b.adjustBalance(amount);
        }
        return true;
    }
}
