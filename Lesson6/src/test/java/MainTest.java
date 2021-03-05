import org.junit.Assert;
import org.junit.Test;

public class MainTest {
    Main main = new Main();

    @Test(expected = RuntimeException.class)
    public void testArrayAfterFourException(){
        int [] arr = {1,3,2,3};
        int [] expectedArr = {};
        int [] result = main.arrayAfterFour(arr);
        Assert.assertArrayEquals(expectedArr, result);
    }

    @Test
    public void TestArrayAfterFour(){
        int [] arr = {1,2,3,4,5};
        int[] expectedArr = {5};
        int[] result = main.arrayAfterFour(arr);
        Assert.assertArrayEquals(expectedArr, result);
    }

    @Test
    public void testArrayAfterFourWhenFourIsLast(){
        int[] arr = {1, 2, 3, 4};
        int[] expectedArr = {};
        int[] result = main.arrayAfterFour(arr);
        Assert.assertArrayEquals(expectedArr, result);
    }

    @Test
    public void testArrayOfOnesAndFours(){
        int[] arr = {1, 1, 1, 4};
        Assert.assertTrue(main.arrayOfOnesAndFours(arr));
    }

    @Test
    public void testArrayOfOnes(){
        int[] arr = {1, 1, 1, 1};
        Assert.assertFalse(main.arrayOfOnesAndFours(arr));
    }

    @Test
    public void testArrayOfFours(){
        int[] arr = {4, 4, 4, 4};
        Assert.assertFalse(main.arrayOfOnesAndFours(arr));
    }

    @Test
    public void testArrayOfAnyNumbers(){
        int[] arr = {2, 2354, 3, 1};
        Assert.assertFalse(main.arrayOfOnesAndFours(arr));
    }

}
