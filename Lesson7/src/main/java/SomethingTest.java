import Interfaces.AfterSuite;
import Interfaces.BeforeSuite;
import Interfaces.Test;

public class SomethingTest {

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Before Suite");
    }

    @Test(priority = 3)
    public void test3A() {
        System.out.println("Test 3A");
    }

    @Test(priority = 1)
    public void test1() {
        System.out.println("Test1");
    }

    @Test(priority = 2)
    public void test2() {
        System.out.println("Test 2");
    }

    @Test(priority = 3)
    public void test3B() {
        System.out.println("Test 3B");
    }

    @Test(priority = 4)
    public void test4() {
        System.out.println("Test 4");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("After Suite");
    }
}
