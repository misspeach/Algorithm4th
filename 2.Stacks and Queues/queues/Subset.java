import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by shizhan on 16/11/8.
 */
public class Subset {
    public static void main(String[] args) {
        int k = Integer.valueOf(args[0]);
//        int k = 8;
        int n = 0;
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            randomizedQueue.enqueue(s);
        }
        for (String item : randomizedQueue) {
            if (n == k)
                break;
            else {
                StdOut.println(item);
                n++;
            }
        }
    }
}
