package um.edu.mt;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class AppTest {

    ArrayList<Transaction> transactions;

    AccountDatabase aDB;
    TransactionManager tm;

    Account test, b, c,d;

    @Before
    public void init() {
        aDB = new AccountDatabase();
        tm = new TransactionManager(aDB);
        test = new Account(1, "xyz", 1000);
        b = new Account(2, "xyz", 2000);
        c = new Account(1, "c", 5);
        d = new Account(4, "d", 50);

        transactions = new ArrayList<Transaction>();
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
    public void transactionIntervalTest3(){
        aDB.addAccount(test);
        aDB.addAccount(b);

        Assert.assertEquals(false, tm.processTransaction(3,1,10));

    }

    @Test
    public void transactionIntervalTest4(){
        aDB.addAccount(test);
        aDB.addAccount(b);
        aDB.addAccount(d);
        tm.processTransaction(1, 4, 10);
        Assert.assertEquals(false, tm.processTransaction(2,1,10));
    }

    @Test
    public void AccGetTest(){
        Account acc = new Account(3,"d",10);
        Assert.assertEquals("d",acc.getAccountName());

    }
    @Test
    public void AccSetTest(){
        Account acc = new Account(3,"d",10);
        acc.setAccountName("e");
        Assert.assertEquals("e",acc.getAccountName());
    }
    @Test
    public void AccDbGetTest(){
        AccountDatabase db = new AccountDatabase();
        Assert.assertEquals(null,db.getAccount(4));
    }
    @Test
    public void CreateSimpleCompoundTransaction(){
        AccountDatabase db = new AccountDatabase();
        db.addAccount(test);
        db.addAccount(b);
        Transaction first = new Transaction(db, 1,2 , 10);
        Transaction second = new Transaction(db, 2,1 , 10);
        transactions.add(first);
        transactions.add(second);
        CompoundTransaction ct = new CompoundTransaction(transactions);
        Assert.assertEquals(ct, ct.getCompoundTransaction() );
    }
    @Test
    public void CreateComplexCompoundTransaction(){
        AccountDatabase db = new AccountDatabase();
        db.addAccount(test);
        db.addAccount(b);

        Transaction first = new Transaction(db, 1,2 , 10);
        Transaction second = new Transaction(db, 2,1 , 10);

        transactions.add(first);
        transactions.add(second);
        CompoundTransaction lct = new CompoundTransaction(transactions);

        ArrayList<Transaction> transactions2 = new ArrayList<Transaction>();
        Transaction third = new Transaction(db, 1,2 , 100);
        Transaction forth = new Transaction(db, 2,1 , 20);
        transactions2.add(third);
        transactions2.add(forth);
        transactions2.add(lct);
        CompoundTransaction ct = new CompoundTransaction(transactions2);
        Assert.assertEquals(ct, ct.getCompoundTransaction() );
    }


}

