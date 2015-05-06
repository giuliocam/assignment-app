package um.edu.mt;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Account {
    private long lastTransaction;
    private int accountNumber;
    private String accountName;
    private long accountBalance;
    private boolean transactionOccured;

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public long getAccountBalance() {
        return accountBalance;
    }

    public long getLastTransaction(){ return lastTransaction;}

    public void setLastTransaction(long lt){this.lastTransaction = lt;}

    public boolean getTransactionOccured(){return transactionOccured;}

    public void setTransactionOccured(boolean t){transactionOccured = t;}

    public boolean adjustBalance(long change) {
        this.accountBalance += change;
        return true;
    }

    public Account(int id, String name,long amount) {
        accountNumber = id;
        accountName = name;
        accountBalance = amount;
        transactionOccured = false;
    }

}
