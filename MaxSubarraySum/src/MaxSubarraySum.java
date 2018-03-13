import java.io.*;
import java.util.StringTokenizer;

/**
 * https://www.hackerrank.com/challenges/maximum-subarray-sum/problem
 * */

public class MaxSubarraySum {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out), true);

    private static long kadane(long [] array) {
        long currentMax = 0;
        long lastMax = 0;
        for (int i = 1; i < array.length ; i++) {
            currentMax += array[i];
            if(currentMax < 0)
                currentMax = 0;
            else if( currentMax > lastMax)
                lastMax = currentMax;
        }
        return lastMax;
    }

    private static long solver(long [] array, long M) {
        long currentMax = array[0] % M;
        long lastMax = array[0] % M;
        for(int i=1; i<array.length; i++) {
            currentMax = Math.max(currentMax, (currentMax % M + array[i] % M) % M);
            lastMax = Math.max(currentMax, lastMax);
        }
        return lastMax;
    }

    private static void test() {
        long [][] matrix = {
            {3,3,9,9,5}
        };
        long [] mod = {7};
        int idx = 0;
        System.out.println(solver(matrix[idx], mod[idx]));
    }


    private static void run() {
        try {
            int queries = Integer.parseInt(reader.readLine());
            while (queries-- > 0) {
                StringTokenizer tk = new StringTokenizer(reader.readLine(), " ");
                int size = Integer.parseInt(tk.nextToken());
                int mod = Integer.parseInt(tk.nextToken());
                long array [] = new long[size];
                tk = new StringTokenizer(reader.readLine(), " ");
                for (int i = 0; tk.hasMoreTokens() ; i++) {
                    array[i] = Long.parseLong(tk.nextToken());
                }
                writer.printf("%d\n", solver(array, mod));
            }
        } catch (IOException ioex) {}
    }

    public static void main(String[] args) {
        run();
    }
}
