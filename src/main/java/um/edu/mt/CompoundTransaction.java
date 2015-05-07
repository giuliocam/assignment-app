package um.edu.mt;

import java.util.ArrayList;

public class CompoundTransaction extends Transaction{

    private ArrayList<Transaction>  t;

    public CompoundTransaction(ArrayList<Transaction> list) {
        t = list;
    }

    public ArrayList<Transaction> getCompoundTransaction() {
        return t;
    }

    // Repeated Code for transaction manager
    /*public boolean process() {

        for(Transaction x : t) {
            if(!x.process()) {
                System.out.println("Error in transaction " + x.getInfo());
                return false;
            }
        }

        return true;
    }*/
}
