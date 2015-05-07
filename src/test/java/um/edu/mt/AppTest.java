package um.edu.mt;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class AppTest {

    ArrayList<Transaction> transactions;

    AccountDatabase aDB;
    AccountDatabase aDB2;
    TransactionManager tm;

    Account test, b, c,d,e;

    @Before
    public void init() {
        aDB = new AccountDatabase();
        aDB2 = new AccountDatabase();
        tm = new TransactionManager(aDB);
        test = new Account(1, "xyz", 1000);
        b = new Account(2, "xyz", 2000);
        c = new Account(1, "c", 5);
        d = new Account(4, "d", 50);
        e = new Account(3 , "e",100);
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
    public void transactionTest(){
        aDB.addAccount(b);
        Assert.assertEquals(false, tm.processTransaction(2, 2, 20));
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
    @Test //account does not exist in the account database, therefore transaction cannot occur
    public void transactionIntervalTest3(){
        aDB.addAccount(test);
        aDB.addAccount(b);

        Assert.assertEquals(false, tm.processTransaction(3, 1, 10));

    }
/*
    @Test
    public void transactionIntervalTest4(){
        aDB.addAccount(test);
        aDB.addAccount(b);
        aDB.addAccount(d);
        tm.processTransaction(1, 4, 10);
        Assert.assertEquals(false, tm.processTransaction(2, 1, 10));
    }
*/
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
    @Test //create a compound transaction containing 2 transactions and check whether the compound transaction is created correctly
    public void CreateSimpleCompoundTransaction(){
        AccountDatabase db = new AccountDatabase();
        db.addAccount(test);
        db.addAccount(b);
        Transaction first = new Transaction(db, 1,2 , 10);
        Transaction second = new Transaction(db, 2,1 , 10);
        transactions.add(first);
        transactions.add(second);
        CompoundTransaction ct = new CompoundTransaction(transactions);
        Assert.assertSame(transactions, ct.getCompoundTransaction());
    }
    @Test
    public void CreateComplexCompoundTransaction(){
        AccountDatabase db = new AccountDatabase();
        db.addAccount(test);
        db.addAccount(b);

        Transaction first = new Transaction(db, 1,2 , 10,"First");
        Transaction second = new Transaction(db, 2,1 , 10,"Second");

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
        Assert.assertSame(transactions2, ct.getCompoundTransaction());
    }

    @Test//create a compound transaction containing 2 transactions and process these transactions(4 different accounts used)
    public void processCompoundTransaction(){
        aDB.addAccount(test);
        aDB.addAccount(b);
        aDB.addAccount(d);
        aDB.addAccount(e);
        Transaction t1 = new Transaction(aDB,1,2,100);
        Transaction t2 = new Transaction(aDB,3,4,50);
        transactions.add(t1);
        transactions.add(t2);
        CompoundTransaction cT = new CompoundTransaction(transactions);
        Assert.assertEquals(true, tm.processTransaction(cT));
    }

    @Test//create a compound transaction containing 2 transactions and process these transactions(3 different accounts used)
    public void processCompoundTransaction2(){
        aDB.addAccount(test);
        aDB.addAccount(b);
        aDB.addAccount(d);
        Transaction t1 = new Transaction(aDB,1,2,100);
        Transaction t2 = new Transaction(aDB,4,1, 50);
        transactions.add(t1);
        transactions.add(t2);
        CompoundTransaction cT = new CompoundTransaction(transactions);
        Assert.assertEquals(true, tm.processTransaction(cT));
    }

    @Test
    public void FailProcessTransaction(){
        AccountDatabase db = new AccountDatabase();
        db.addAccount(test);
        db.addAccount(b);

        Transaction first = new Transaction(db, 1,2 , 10,"First");
        Transaction second = new Transaction(db, 2,1 , 1000000,"Second");

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
        TransactionManager tm2 = new TransactionManager(db);
        Assert.assertFalse(tm2.processTransaction(ct));
    }


    @Test
    public void checkRisk() {
        aDB.addAccount(test);
        aDB.addAccount(b);

        Transaction t1 = new Transaction(aDB,1,2,100);
        Transaction t2 = new Transaction(aDB,3,4,50);
        Transaction t3 = new Transaction(aDB,1,4,1);
        Transaction t4 = new Transaction(aDB,4,1,1);

        transactions.add(t1);
        transactions.add(t2);
        CompoundTransaction ct = new CompoundTransaction(transactions);

        ArrayList<Transaction> transactions2 = new ArrayList<Transaction>();

        transactions2.add(ct);
        transactions2.add(t3);

        CompoundTransaction ct2 = new CompoundTransaction(transactions2);

        ArrayList<Transaction> transactions3 = new ArrayList<Transaction>();

        transactions3.add(t4);

        CompoundTransaction ct3 = new CompoundTransaction(transactions3);


        ct3.setRisk("Low");


        iterateList(ct3.getCompoundTransaction());
    }
    @Test
    public void preset(){
        //list of transactions, risk , dests (to create),

        //source accounts created and added to db
        Account s1 = new Account(3123, "s1", 20000);
        //Account s2 = new Account(8665, "s2", 20000);
        Account s3 = new Account(3143, "s3", 200000);
        Account s4 = new Account(3133, "s4", 50000);
        Account s5 = new Account(6565, "s5", 80000);
        Account s6 = new Account(6588, "s6", 60000);
        aDB2.addAccount(s1);
        //aDB2.addAccount(s2);
        aDB2.addAccount(s3);
        aDB2.addAccount(s4);
        aDB2.addAccount(s5);
        aDB2.addAccount(s6);

        //create and add destination accounts
        Account d1 = new Account(4444, "d1", 20000);
        aDB2.addAccount(d1);
        aDB2.addAccount(b);
        aDB2.addAccount(c);

        ArrayList<Integer> dest = new ArrayList<Integer>();
        dest.add(2);
        dest.add(1);
        ArrayList<Long> amounts = new ArrayList<Long>();
        amounts.add((long)1000);
        amounts.add((long)500);
        //amounts.add((long)20);


        CompoundTransaction ct = new CompoundTransaction("low",4,500,dest,amounts,aDB2);

    }

    @Test
    public void EmptyTransactionTest(){
        CompoundTransaction ct = new CompoundTransaction(transactions);
        Assert.assertFalse(tm.processTransaction(ct));
    }
    @Test
    public void GetAtomicDecValue(){
        Transaction t1 = new Transaction(aDB,1,2,100);
        Transaction t2 = new Transaction(aDB,3,4,50);
        Transaction t3 = new Transaction(aDB,1,4,11);
        Transaction t4 = new Transaction(aDB,4,3,1);
        Transaction t5 = new Transaction(aDB,3,4,101);
        Transaction t6 = new Transaction(aDB,3,1,20);
        transactions.add(t1);
        transactions.add(t2);
        CompoundTransaction ct1 = new CompoundTransaction(transactions);
        transactions = new ArrayList<Transaction>();
        transactions.add(t3);
        transactions.add(t4);
        CompoundTransaction ct2= new CompoundTransaction(transactions);
        transactions = new ArrayList<Transaction>();
        transactions.add(ct1);
        transactions.add(ct2);
        transactions.add(t5);
        transactions.add(t6);
        CompoundTransaction ct3 = new CompoundTransaction(transactions);
        ArrayList<Transaction> sortedlist = new ArrayList<Transaction>();
        sortedlist.add(t5);
        sortedlist.add(t1);
        sortedlist.add(t2);
        sortedlist.add(t6);
        sortedlist.add(t3);
        sortedlist.add(t4);
        Assert.assertTrue(arrayEquals(sortedlist, ct3.sortList("Dec")));
    }
    @Test
    public void GetAtomicIncValue(){
        Transaction t1 = new Transaction(aDB,1,2,100);
        Transaction t2 = new Transaction(aDB,3,4,50);
        Transaction t3 = new Transaction(aDB,1,4,11);
        Transaction t4 = new Transaction(aDB,4,3,1);
        Transaction t5 = new Transaction(aDB,3,4,101);
        Transaction t6 = new Transaction(aDB,3,1,20);
        transactions.add(t1);
        transactions.add(t2);
        CompoundTransaction ct1 = new CompoundTransaction(transactions);
        transactions = new ArrayList<Transaction>();
        transactions.add(t3);
        transactions.add(t4);
        CompoundTransaction ct2= new CompoundTransaction(transactions);
        transactions = new ArrayList<Transaction>();
        transactions.add(ct1);
        transactions.add(ct2);
        transactions.add(t5);
        transactions.add(t6);
        CompoundTransaction ct3 = new CompoundTransaction(transactions);
        ArrayList<Transaction> sortedlist = new ArrayList<Transaction>();
        sortedlist.add(t4);
        sortedlist.add(t3);
        sortedlist.add(t6);
        sortedlist.add(t2);
        sortedlist.add(t1);
        sortedlist.add(t5);
        Assert.assertTrue(arrayEquals(sortedlist, ct3.sortList("Inc")));
    }
    @Test
    public void GetAtomicSrcAccount(){
        Transaction t1 = new Transaction(aDB,1,2,100);
        Transaction t2 = new Transaction(aDB,3,4,50);
        Transaction t3 = new Transaction(aDB,1,4,11);
        Transaction t4 = new Transaction(aDB,4,3,1);
        Transaction t5 = new Transaction(aDB,3,4,101);
        Transaction t6 = new Transaction(aDB,3,1,20);
        transactions.add(t1);
        transactions.add(t2);
        CompoundTransaction ct1 = new CompoundTransaction(transactions);
        transactions = new ArrayList<Transaction>();
        transactions.add(t3);
        transactions.add(t4);
        CompoundTransaction ct2= new CompoundTransaction(transactions);
        transactions = new ArrayList<Transaction>();
        transactions.add(ct1);
        transactions.add(ct2);
        transactions.add(t5);
        transactions.add(t6);
        CompoundTransaction ct3 = new CompoundTransaction(transactions);
        ArrayList<Transaction> sortedlist = new ArrayList<Transaction>();
        sortedlist.add(t1);
        sortedlist.add(t3);
        Assert.assertTrue(arrayEquals(sortedlist, ct3.sortList(1)));
    }
    public void iterateList(ArrayList<Transaction> list) {
        for(Transaction t : list) {

            if(t instanceof CompoundTransaction) {
                iterateList(((CompoundTransaction) t).getCompoundTransaction());
            }
            Assert.assertEquals(true, t.getRisk().equalsIgnoreCase("Low"));
        }
    }
    public boolean arrayEquals(ArrayList<Transaction> sortedlist, ArrayList<Transaction> list){
        if (sortedlist.size()!= list.size()) return false;
        for(int i = 0; i < list.size();i++){
            if(sortedlist.get(i)!= list.get(i))return false;
        }
        return true;
    }

}

