public class PrefixSum {

    public static long [] prefixSum(long [] array) {
        long prefix [] = new long[array.length];
        prefix [0] = array[0];
        for(int i=1; i<array.length; i++)
            prefix[i] = array[i] + prefix[i-1];

        return prefix;
    }

    public static long [] modularPrefixSum(long [] array, long m) {
        long prefix [] = new long[array.length];
        prefix [0] = array[0];
        for(int i=1; i<array.length; i++)
            prefix[i] = ((array[i] % m)+(prefix[i-1]%m))% m;

        return prefix;
    }

    private static long maxSubArrayAllSets(long [] array, long m) {
        long _max = array[0];
        long prefix [] = new long[array.length];
        prefix [0] = array[0];
        for (int i=1; i<array.length-1; i++) {
            for (int j=i; j<=i; j++) {
                prefix[i] = ((array[i] % m)+(prefix[j-1]%m))% m;
                _max = Math.max(_max, prefix[i]);
            }
        }
        int i = array.length-1;
        prefix[i] = ((array[i] % m)+(prefix[i-1]%m))% m;
        _max = Math.max(_max, prefix[i]);
        return _max;
    }


    private static long maxSubArrayPrefixSum(long [] prefix, long m) {
        long _max = 0;
        for (int i=0; i<prefix.length; i++) {
            for (int j=i-1; j>=0; j--) {
                _max = Math.max(_max, (prefix[i] - prefix[j] + m) % m);
            }
        }
        _max = Math.max(_max, prefix[0]);
        return _max;
    }
    

    public static void test() {
        long [][] matrix = {{3,3,9,9,5}};
        long [] mod = {7};
        int idx = 0;
        long [] prefix = modularPrefixSum(matrix[idx], mod[idx]);
        for (int i=0; i<prefix.length; i++) {
            System.out.printf("%d ", prefix[i]);
        }

        //long max = maxSubArrayAllSets(matrix[idx], mod[idx]);
        long max = maxSubArrayPrefixSum(prefix, mod[idx]);
        System.out.println("");
    }

    public static void main(String[] args) {
        test();
    }
}
