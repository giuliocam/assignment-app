package um.edu.mt;

import java.util.ArrayList;

public class CompoundTransaction extends Transaction{





    public CompoundTransaction(ArrayList<Transaction> list) {

    }

    public CompoundTransaction getCompoundTransaction() {
        return this;
    }

    public boolean process() {
        return false;
    }
}
