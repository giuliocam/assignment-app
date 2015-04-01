package um.edu.mt;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {


    //test
    Account test = new Account();
    @Test
    public void initialTest() {
        App.main();

        System.out.println("...");

    }
    @Test
    public void adjustBalanceTest(){
        Assert.assertEquals(false,test.adjustBalance(-1));

    }

    @Test
    public void adjustBalanceTest2() {
        Assert.assertEquals(true, test.adjustBalance(3000));

    }
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
