package um.edu.mt;

import java.util.ArrayList;

public class CompoundTransaction extends Transaction{

    public CompoundTransaction(ArrayList<Transaction> list) {

    }

    public boolean process() {
        return false;
    }
}
