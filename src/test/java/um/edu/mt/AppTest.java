package um.edu.mt;

//import junit.framework.Assert;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    Account test;
    @Before
    public void init() {

        test = new Account();
    }
    @Test
    public void initialTest() {
        App.main();

        System.out.println("...");

    }
    @Test
    public void adjustBalanceTest(){
        long originalBalance = test.getAccountBalance();
        test.adjustBalance(-1);
        Assert.assertEquals(originalBalance-1,test.getAccountBalance());

    }

    @Test
    public void adjustBalanceTest2() {
        long originalBalance = test.getAccountBalance();
        test.adjustBalance(1);
        Assert.assertEquals(originalBalance+1,test.getAccountBalance());

    }

    
    public void uniqueIDTest() {

        AccountDatabase aDB = new AccountDatabase();
        Account a = new Account(1, "xyz", 1000);
        Account b = new Account(2, "xyz", 2000);
        Account c = new Account(1, "xyz", 3000);

        Assert.assertEquals(true,aDB.addAccount(a));
        Assert.assertEquals(true,aDB.addAccount(b));
        Assert.assertEquals(false,aDB.addAccount(c));
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

        Assert.assertEquals(false,tm.processTransaction(1, 3, 2000)); //.

    }




}
