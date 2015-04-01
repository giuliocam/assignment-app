package um.edu.mt;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void uniqueIDTest() {

        AccountDatabase aDB = new AccountDatabase();
        Account a = new Account(1, "xyz", 1000);
        Account b = new Account(2, "xyz", 2000);
        Account c = new Account(1, "xyz", 3000);

        Assert.assertTrue(aDB.addAccount(a));
        Assert.assertTrue(aDB.addAccount(b));
        Assert.assertFalse(aDB.addAccount(c));
    }

    @Test
    public void balanceTest() {

        AccountDatabase aDB = new AccountDatabase();
        TransactionManager tm = new TransactionManager(aDB);

        Account a = new Account(1, "xyz", 1000);
        //Account b = new Account(2, "xyzz", 2000);
        Account c = new Account(3, "xyzzz", 3000);

        aDB.addAccount(a);
        aDB.addAccount(c);

        Assert.assertFalse(tm.processTransaction(1, 3, 2000)); //.


    }


}
