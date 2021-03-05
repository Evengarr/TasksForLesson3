import java.util.Arrays;
public class Main {
    public int[] arrayAfterFour(int[] arr){
        for (int i = arr.length; i >= 0 ; i--) {
            if (arr[i] == 4){
                return Arrays.copyOfRange(arr, i + 1, arr.length);
            }
        }
        throw new RuntimeException();
    }

    public boolean arrayOfOnesAndFours(int[]arr){
        boolean isOneIn = false;
        boolean isFourIn = false;

        for (int value : arr) {
            if (value != 1 && value != 4){
                return false;
            }
            if (value == 1){
                isOneIn = true;
            } else {
                isFourIn = true;
            }
        }
    return isOneIn && isFourIn;
    }
}
