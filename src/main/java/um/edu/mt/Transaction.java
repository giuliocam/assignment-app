package um.edu.mt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

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

        if (a == null || b == null) return false;

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String strDate = sdfDate.format(now); //string containing current time
        Date aDate = new Date();
        Date bDate = new Date();
        boolean checkA,checkB;
        long secondsA = 0,secondsB = 0;

        try {
            now = sdfDate.parse(strDate);
            checkA = a.getTransactionOccured();
            checkB = b.getTransactionOccured();
            if (checkA == true)  { //transaction has occured before
                aDate = sdfDate.parse(a.getLastTransaction());
                secondsA = (now.getTime()-aDate.getTime())/1000;
            }
            else secondsA = 16;
            if (checkB == true)  { //transaction has occured before
                bDate = sdfDate.parse(b.getLastTransaction());
                secondsB = (now.getTime()-bDate.getTime())/1000;
            }
            else secondsB =16;
        }catch(ParseException pe){
            pe.printStackTrace();
        }

        if (secondsA < 15 || secondsB < 15) return false;
        if (a.getAccountBalance() < amount) return false;
        else {
            a.adjustBalance(-amount);
            b.adjustBalance(amount);
            a.setLastTransaction(strDate); //add 15 seconds?
            b.setLastTransaction(strDate); //add 15 seconds?
            a.setTransactionOccured(true);
            b.setTransactionOccured(true);
        }
        return true;
    }
}
