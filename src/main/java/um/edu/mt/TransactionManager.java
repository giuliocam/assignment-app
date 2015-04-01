package um.edu.mt;

public class TransactionManager {
    private int numTransactionsProcessed;
    private AccountDatabase db;

    public TransactionManager(AccountDatabase aDB){
        db = aDB;
    }

    public boolean processTransaction(int src, int dst, int amount) {
        Transaction t = new Transaction(db, src, dst, amount);

        return t.process();
    }

}