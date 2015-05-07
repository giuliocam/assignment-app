package um.edu.mt;

public class Account {
    private long lastTransaction;
    private int accountNumber;
    private String accountName;
    private long accountBalance;
    private boolean transactionOccurred;

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

    public boolean getTransactionOccurred(){return transactionOccurred;}

    public void setTransactionOccurred(boolean t){transactionOccurred = t;}

    public boolean adjustBalance(long change) {
        this.accountBalance += change;
        return true;
    }

    public Account(int id, String name,long amount) {
        accountNumber = id;
        accountName = name;
        accountBalance = amount;
        transactionOccurred = false;
    }

}
