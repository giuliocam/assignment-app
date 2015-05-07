package um.edu.mt;

import java.util.ArrayList;

public class TransactionManager {
    private int numTransactionsProcessed;
    private AccountDatabase db;

    public TransactionManager(AccountDatabase aDB){
        db = aDB;
    }

    public boolean processTransaction(int src, int dst, int amount) {
        Transaction t = new Transaction(db, src, dst, amount);

        boolean check = t.process();
        if (check){numTransactionsProcessed++;}
        return check;
    }

    public boolean processTransaction(CompoundTransaction t) {
        boolean check = t.process();
        if (check){numTransactionsProcessed++;}
        return check;
    }


}
