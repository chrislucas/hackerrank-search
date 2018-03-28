import java.io.*;
import java.util.*;

/**
 * https://www.hackerrank.com/challenges/missing-numbers/problem
 * DONE
 * */

public class MissingNumbers {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out), true);


    public static void solver() {
        try {
            int sizeA = Integer.parseInt(reader.readLine());
            StringTokenizer tk = new StringTokenizer(reader.readLine(), " ");
            TreeMap<String, Integer> freqA = new TreeMap<>();
            while (tk.hasMoreTokens()) {
                String token = tk.nextToken();
                if(freqA.containsKey(token)) {
                    freqA.put(token, freqA.get(token) + 1);
                }
                else
                    freqA.put(token, 1);
            }
            int sizeB = Integer.parseInt(reader.readLine());
            tk = new StringTokenizer(reader.readLine(), " ");
            TreeMap<String, Integer> freqB = new TreeMap<>();
            while (tk.hasMoreTokens()) {
                String token = tk.nextToken();
                if(freqB.containsKey(token)) {
                    freqB.put(token, freqB.get(token) + 1);
                }
                else
                    freqB.put(token, 1);
            }
            boolean flag = true;
            for(Map.Entry<String, Integer> entry : freqA.entrySet()) {
                String key = entry.getKey();
                int val = entry.getValue();
                if(freqB.containsKey(key) && freqB.get(key) == val)
                    continue;
                else {
                    writer.printf(flag ? "%s" :" %s", key);
                    flag = false;
                }
            }

        } catch (IOException ioex) {}
    }

    public static void main(String[] args) {
        solver();
    }
}
