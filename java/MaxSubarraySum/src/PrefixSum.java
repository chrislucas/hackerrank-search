import java.util.TreeSet;

public class PrefixSum {

    private static long [] prefixSum(long [] array) {
        long prefix [] = new long[array.length];
        prefix [0] = array[0];
        for(int i=1; i<array.length; i++)
            prefix[i] = array[i] + prefix[i-1];
        return prefix;
    }

    private static long [] modularPrefixSum(long [] array, long m) {
        long prefix [] = new long[array.length];
        prefix [0] = array[0];
        for(int i=1; i<array.length; i++)
            prefix[i] = ((array[i]%m)+(prefix[i-1]%m))%m;
        return prefix;
    }

    private static long maxSumCalculatingPrefixSum(long [] array, long m) {
        long _max = array[0];
        long prefix [] = new long[array.length];
        prefix [0] = array[0];
        for (int i=1; i<array.length; i++) {
            for (int j=i; j<=i; j++) {
                prefix[i] = ((array[i]%m)+(prefix[j-1]%m))%m;
                _max = Math.max(_max, prefix[i]);
            }
        }
        return _max;
    }

    private static long maxSubUsingPrefixSum(long [] prefix, long m) {
        long _max = 0;
        for (int i=0; i<prefix.length; i++) {
            for (int j=i-1; j>=0; j--) {
                long cur = (prefix[i] - prefix[j]) % m;
                _max = Math.max(_max, cur);
            }
        }
        _max = Math.max(_max, prefix[0]);
        return _max;
    }


    private static int lowerBound(Long [] array, long target) {
        int lo = 0;
        int hi = array.length - 1;
        int an = -1;
        while (lo <= hi) {
            int mi = (hi - lo) / 2 + lo;
            if(array[mi] == target) {
                an = mi;
                hi = mi -1;
            }
            else if (array[mi] > target) {
                hi = mi - 1;
            }
            else {
                lo = mi + 1;
            }
        }
        return an;
    }

    private static long maxSubsetSum(long [] array, long m) {
        TreeSet<Long> treeSet = new TreeSet<>();
        long max = 0, prefix = 0;
        treeSet.add(0L);
        for (int i = 0; i < array.length; i++) {
            prefix = ((prefix % m) + (array[i] % m)) % m;
            max = Math.max(max, prefix);

            Long [] set = new Long[treeSet.size()];
            treeSet.toArray(set);
            int idx = lowerBound(set,prefix+1);
            if(idx != -1)
                max = Math.max(max, prefix - set[idx] + m);
            treeSet.add(prefix);
        }
        return max;
    }

    private static void test() {
        long [][] matrix = {
             {3,3,9,9,5}
            ,{7, 1, 3, 1, 4, 5, 1, 3, 6}
            ,{6,6,11,15,12,1}
        };
        long [] mod = {7, 7, 13};
        int idx = 0;
        //long [] prefix = modularPrefixSum(matrix[idx], mod[idx]);
        //long max1 = maxSumCalculatingPrefixSum(matrix[idx], mod[idx]);
        //long max2 = maxSubUsingPrefixSum(prefixSum(matrix[idx]), mod[idx]);
        long max2 = maxSubsetSum(matrix[idx], mod[idx]);
        System.out.println(max2);
    }

    public static void main(String[] args) {
        test();
    }
}
