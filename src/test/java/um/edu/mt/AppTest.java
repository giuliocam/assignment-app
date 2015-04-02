package um.edu.mt;

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
        Assert.assertEquals(originalBalance - 1, test.getAccountBalance());

    }

    @Test
    public void adjustBalanceTest2() {
        long originalBalance = test.getAccountBalance();
        test.adjustBalance(1);
        Assert.assertEquals(originalBalance + 1, test.getAccountBalance());
    }

    @Test
    public void dbGetSize() {
        aDB.addAccount(b);
        aDB.addAccount(c);

        Assert.assertEquals(2, aDB.getSize());
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
        Assert.assertEquals(true, aDB.addAccount(test));
        Assert.assertEquals(false,aDB.addAccount(c));
    }

    @Test
    public void balanceTest() {
        aDB.addAccount(test);
        aDB.addAccount(b);

        Assert.assertEquals(false,tm.processTransaction(1, 2, 2000));
    }
    @Test
    public void balanceTest2() {
        aDB.addAccount(test);
        aDB.addAccount(b);
        Assert.assertEquals(true,tm.processTransaction(1, 2, 20));
    }

    @Test
    public void transactionIntervalTest1(){
        aDB.addAccount(test);
        aDB.addAccount(b);

        boolean transaction = tm.processTransaction(1,2,10);
        Assert.assertEquals(false, tm.processTransaction(2,1,10));

    }
    @Test
    public void transactionIntervalTest2(){
        aDB.addAccount(test);
        aDB.addAccount(b);

        boolean transaction = tm.processTransaction(1,2,500);
        try{
            Thread.sleep(16000);
        }catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }
        Assert.assertEquals(true,tm.processTransaction(2,1,500));
    }

    @Test
    public void AccConstGetTest(){
        Account acc = new Account(3,"d",10);
        Assert.assertEquals("d",acc.getAccountName());
    }
    @Test
    public void AccSetTest(){
        Account acc = new Account(3,"d",10);
        acc.setAccountName("e");
        Assert.assertEquals("e",acc.getAccountName());
    }
}
