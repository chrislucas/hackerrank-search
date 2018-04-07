import java.util.TreeSet;

public class PrefixSum {

    private static long [] prefixSum(long [] array) {
        long prefix [] = new long[array.length];
        prefix [0] = array[0];
        for(int i=1; i<array.length; i++)
            prefix[i] = array[i] + prefix[i-1];
        return prefix;
    }

    // acc = (array[i] % m + acc) % m; prefix[i] = acc;
    // e igual a ((array[i]%m)+(prefix[i-1]%m))%m;
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

    private static int lowerBound(Long [] array, long target) {
        int lo = 0;
        int hi = array.length - 1;
        int an = -1;
        while (lo <= hi) {
            int mi = (hi - lo) / 2 + lo;
            if(array[mi] >= target) {
                an = mi;
                hi = mi-1;
            }
           // else if (array[mi] > target) { hi = mi - 1; }
            else {
                lo = mi + 1;
            }
        }
        return an;
    }

    private static long maxSubsetSum(long [] array, long m) {
        TreeSet<Long> treeSet = new TreeSet<>();
        long max = 0, prefix = 0;
        for(int i = 0; i < array.length; i++) {
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

    /**
     * O array prefixModularSum armazena em cada indice 'i' a soma
     * de 0 a i modulo m
     * Assim:
     * Para i = 0 -> prefix[0] = array[0] % m
     * Para i = 1 -> prefix[1] = (array[0] + array[1]) % m
     * Para i = 2 -> prefix[1] = (array[0] + array[1] + array[2]) % m
     * O array prefix pode ser montado usando uma variacao do algoritmo
     * de prefix sum usando porem aritmetica modular.
     *
     * f : (prefixModularSum[i]-prefixModularSum[j]+m)%m
     * */
    private static long maxSubUsingPrefixSum(long [] prefixModularSum, long m) {
        long _max = 0;
        for (int i=0; i<prefixModularSum.length; i++) {
            for (int j=i-1; j>=0; j--) {
                /**
                 *
                 * */
                long d = prefixModularSum[i]-prefixModularSum[j];
                long cur = (d+m)%m;
                _max = Math.max(_max, cur);
            }
            /**
             * Aqui verificamos se s[0] + ... + s[i] % m > s[0] + ... + s[i-1]
             * para i == 0 comparamos s[0] > 0
             * */
            _max = Math.max(_max, prefixModularSum[i]);
        }
        return _max;
    }

    private static void test() {
        long [][] matrix = {
             {3,3,9,9,5}
            ,{7,1,3,1,4,5,1,3,6}
            ,{6,6,11,15,12,1}
            ,{10,7,18}
        };
        long [] mod = {7, 7, 13, 13};
        int idx = 1;
        // System.out.println(lowerBound(new Long [] {0L,1L,1L,2L,3L,5L}, 4));
        //long [] prefix = modularPrefixSum(matrix[idx], mod[idx]);
        //long max1 = maxSumCalculatingPrefixSum(matrix[idx], mod[idx]);
        long max2 = maxSubUsingPrefixSum(modularPrefixSum(matrix[idx], mod[idx]), mod[idx]);
        long max3 = maxSubsetSum(matrix[idx], mod[idx]);
        System.out.printf("%d %d\n", max2, max3);
    }

    public static void main(String[] args) {
        test();
    }

    /**
     * Retornar os ultimos 'p' numeros num range entre (m,1)
     * a partir de um inteiro 'q' q <= m
     * */
    private static int [] modularTest(int quantity, int limit, int from) throws IllegalArgumentException {
        if(from > limit)
            throw new IllegalArgumentException(String.format("Valor Q: %d maior que limite máximo M: %d", from, limit));
        int [] array = new int[quantity];
        int p = (from-1);
        array[0] =  p == 0 ? limit : p;
        for (int j = 1; j < quantity; j++) {
            p = array[j-1]-1;
            array[j] =  p == 0 ? limit : p;
        }
        return array;
    }
}
