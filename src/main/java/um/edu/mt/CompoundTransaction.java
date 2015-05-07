package um.edu.mt;

import java.util.ArrayList;

public class CompoundTransaction extends Transaction{

    private ArrayList<Transaction>  t;

    public CompoundTransaction(ArrayList<Transaction> list) {
        t = list;
    }

    public CompoundTransaction() {

    }

    public boolean setPreset(String risk, int ddest, int damount, ArrayList<Integer> dests, ArrayList<Long> amounts, AccountDatabase db) {

        int dsrc = 0, msrc = 0, csrc = 0, cdest = 0;
        int total = 0;
        int comm = 0;

        if(risk.equalsIgnoreCase("low")) {
            dsrc = 3123;
            msrc = 3143;
            csrc = 6565;
            cdest = 4444;
            comm = 5;
        }

        if(risk.equalsIgnoreCase("high")) {
            dsrc = 8665;
            msrc = 3133;
            csrc = 6588;
            cdest = 5555;
            comm = 10;
        }

        Transaction deposit = new Transaction(db, dsrc, ddest, damount);

        ArrayList<Transaction> m = new ArrayList<Transaction>();
        for(int i = 0; i < dests.size();i++) {
            m.add(new Transaction(db, msrc, dests.get(i), amounts.get(i)));
            total += amounts.get(i);
        }
        CompoundTransaction main = new CompoundTransaction(m);

        //assuming commission is atomic
        Transaction commission = new Transaction(db, csrc, cdest, (long)(comm*0.01*total));

        t.add(deposit);
        t.add(main);
        t.add(commission);

        setRisk(risk);

        return true;
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
