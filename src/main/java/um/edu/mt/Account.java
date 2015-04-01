package um.edu.mt;

public class Account {

    private int accountNumber;
    private String accountName;
    private long accountBalance;

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
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

    public void adjustBalance(long change) {
        this.accountBalance += change;
    }

    public Account(int id, String name,long amount) {
        accountNumber = id;
        accountName = name;
        accountBalance = amount;
    }
    public Account(){

    }




}
