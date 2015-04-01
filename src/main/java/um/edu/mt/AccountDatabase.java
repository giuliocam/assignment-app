package um.edu.mt;

import java.util.ArrayList;

public class AccountDatabase {

    private ArrayList<Account> list = new ArrayList<Account>();

    public boolean addAccount(Account a) {

        for(Account acc: list) {
            if(a.getAccountNumber() == acc.getAccountNumber()) return false;
        }
        list.add(a);

        return true;
    }

    public Account getAccount(int accNum) {
            for(Account acc: list) {
            if(acc.getAccountNumber() == accNum) return acc;
        }
        return null;
    }

    public int getSize() {
        return list.size();
    }

}
