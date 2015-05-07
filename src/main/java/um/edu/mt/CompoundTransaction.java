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

    public boolean process() {

        try {
            if(t.isEmpty()) throw new Exception("No Transactions Found");
            for (Transaction x : t) {
                if (!x.process()) {
                    throw new Exception("Error in transaction " + x.getInfo());
                }
            }
        }catch (Exception e){
            System.out.println(e.toString());
            return false;
        }

        return true;
    }

    public void setRisk(String s) {
        for(Transaction x : t) {
            x.setRisk(s);
        }
    }
}
