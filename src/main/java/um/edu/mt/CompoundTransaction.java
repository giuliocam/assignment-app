package um.edu.mt;

import java.util.ArrayList;

public class CompoundTransaction extends Transaction{

    ArrayList<Transaction>  t;

    public CompoundTransaction(ArrayList<Transaction> list) {
        t = list;
    }

    public CompoundTransaction getCompoundTransaction() {
        return this;
    }

    public boolean process() {

        for(Transaction x : t) {
            if(!x.process()) {
                System.out.println("Error in transaction " + x.getInfo());
                return false;
            }
        }

        return true;
    }
}
