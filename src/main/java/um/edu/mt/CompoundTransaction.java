package um.edu.mt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CompoundTransaction extends Transaction{

    private ArrayList<Transaction>  t;

    public CompoundTransaction(ArrayList<Transaction> list) {
        t = list;
    }

    public CompoundTransaction() {

    }

    public boolean setPreset(String risk, int ddest, int damount, ArrayList<Integer> dests, ArrayList<Long> amounts, AccountDatabase db) {

        int dsrc , msrc , csrc , cdest ;
        int total = 0;
        int comm ;

        if(risk.equalsIgnoreCase("high")) {
            dsrc = 3123;
            msrc = 3143;
            csrc = 6565;
            cdest = 4444;
            comm = 5;
        }

        else if(risk.equalsIgnoreCase("low")) {
            dsrc = 8665;
            msrc = 3133;
            csrc = 6588;
            cdest = 4445;
            comm = 10;
        }
        else return false;

        Transaction deposit = new Transaction(db, dsrc, ddest, damount);

        ArrayList<Transaction> m = new ArrayList<Transaction>();
        for(int i = 0; i < dests.size();i++) {
            m.add(new Transaction(db, msrc, dests.get(i), amounts.get(i)));
            total += amounts.get(i);
        }
        CompoundTransaction main = new CompoundTransaction(m);

        //assuming commission is atomic
        Transaction commission = new Transaction(db, csrc, cdest, (long)(comm*0.01*total));

        t = new ArrayList<Transaction>();

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
    public ArrayList<Transaction> sortList(String type){
        ArrayList<Transaction> a = iterateList();

        if(type.equalsIgnoreCase("dec")) Collections.sort(a, new TransactionComparator2());
        if(type.equalsIgnoreCase("inc")) Collections.sort(a, new TransactionComparator());

        return a;
    }

    public ArrayList<Transaction> sortList(int src){
        ArrayList<Transaction> nt = new ArrayList<Transaction>();
        for (Transaction n: t){
            if (n instanceof CompoundTransaction){

                ArrayList<Transaction> temp = ((CompoundTransaction)n).sortList(src);
                nt.addAll(temp);
            }else {
                if(n.getSourceAccount() == src) nt.add(n);
            }
        }
        return nt;
    }
    public void setRisk(String s) {
        for(Transaction x : t) {
            x.setRisk(s);
        }
    }

    public ArrayList<Transaction> iterateList() {
        ArrayList<Transaction> a = new ArrayList<Transaction>();
        for(Transaction x : t) {
            if(x instanceof CompoundTransaction) {
                a.addAll(((CompoundTransaction) x).iterateList());
            } else {
                a.add(x);

            }
        }
        return a;
    }

    class TransactionComparator implements Comparator<Transaction> {
        @Override
        public int compare(Transaction a, Transaction b) {
            long aamount = a.getAmount();
            long bamount = b.getAmount();

            return aamount < bamount ? -1 : bamount == aamount ? 0 : 1;
        }

    }
    class TransactionComparator2 implements Comparator<Transaction> {
        @Override
        public int compare(Transaction a, Transaction b) {
            long aamount = a.getAmount();
            long bamount = b.getAmount();

            return aamount > bamount ? -1 : bamount == aamount ? 0 : 1;
        }

    }
}
