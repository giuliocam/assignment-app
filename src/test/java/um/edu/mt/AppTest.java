package um.edu.mt;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    //test
    Account test = new Account();
    public void initialTest() {
        App.main();

        System.out.println("...");

    }

    @Test
    public void adjustBalanceTest(){
        Assert.assertEquals(true,test.adjustBalance(3000));

    }

}
