package um.edu.mt;

//import junit.framework.Assert;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

public class AppTest {

    AccountDatabase aDB;
    TransactionManager tm;

    Account test, b, c;
    @Before
    public void init() {
        aDB = new AccountDatabase();
        tm = new TransactionManager(aDB);
        test = new Account(1, "xyz", 1000);
        b = new Account(2, "xyz", 2000);
        c = new Account(1, "c", 5);
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
    @Test
    public void adjustBalanceTest3() {
        long originalBalance = test.getAccountBalance();
        test.adjustBalance(1);
        Assert.assertEquals(originalBalance,test.getAccountBalance());

    }

    @Test
    public void addAccountTest() {
        Assert.assertEquals(true, aDB.addAccount(test));
    }

    @Test
    public void uniqueIDTest1() {
        Assert.assertEquals(true,aDB.addAccount(b));
    }

    @Test
    public void uniqueIDTest2() {
        Assert.assertEquals(false,aDB.addAccount(c));
    }

    @Test
    public void balanceTest() {
        Assert.assertEquals(false,tm.processTransaction(1, 2, 2000));
    }





}
