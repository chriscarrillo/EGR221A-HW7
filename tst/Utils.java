import java.util.Random;

/**
 * Created by mhan on 2/2/2017.
 */
public class Utils {
    // This method constructs and returns an array of random int values of
    // length n from 0 to max where 0 is inclusive and max is exclusive
    public static int[] randomArray(int n, int max) {
        int[] numbers = new int[n];
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            numbers[i] = r.nextInt() % max;
        }
        return numbers;
    }
}
