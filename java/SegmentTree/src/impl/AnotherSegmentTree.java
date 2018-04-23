package impl;


import static java.lang.Math.log;
import static java.lang.Math.ceil;

public class AnotherSegmentTree {

    private static double log2(long n) {
        return log(n) / log(2);
    }

    private static void initializer(int [] data, int [] tree) {
        int n = data.length;
        int heightTree = (int) ceil(log2(n));
        int memoryAlloc = (int) (2 * Math.pow(2, heightTree) - 1);
        tree = new int[memoryAlloc];
        builder(data, tree, 0, n-1, 0);
    }

    private static int builder(int [] data, int [] tree, int l, int r, int ithValue) {
        if (l == r) {
            tree[ithValue] = data[l];        // preenche o no folha
            return data[l];
        }
        int middle = (r - l) / 2 + l;
        int left = builder(data, tree, l, middle, 2 * ithValue + 1);
        int right = builder(data, tree, middle + 1, r, 2 * ithValue + 1);
        tree[ithValue] = left + right;  // preenche no raiz
        return tree[ithValue];
    }

    // li = limite inferior, ls = limite superior, sz = size;
    private static int getIntervalSum(int li, int ls, int sz, int [] tree) {
        if (li < 0 || li > sz - 1 || ls < 0 || ls > sz-1 || li > ls)
            return -1;
        return getIntervalSum(0, sz-1, li, ls, 0, tree);
    }

    private static int getIntervalSum(int s, int e, int l, int r, int ith, int [] tree) {
        if (l > e || r < s)
            return 0;
        else if ( l <= s && r >= e)
            return tree[ith];
        int middle = e-s/2+s;
        int valueLeft = getIntervalSum(s, middle, l, r, 2 * ith + 1, tree);
        int valueRight = getIntervalSum(middle + 1, e, l, r, 2 * ith + 2, tree);
        return valueLeft + valueRight;
    }

    private static void update(int [] data, int sz, int idxToUpdate, int value, int [] tree) {
        if (idxToUpdate < 0 || idxToUpdate > sz - 1)
            return;
        int diff = value - data[idxToUpdate];
        data[idxToUpdate] = value;
        update(0, sz-1, idxToUpdate, diff, 0, tree);
    }

    private static void update(int s, int e, int idxToUpdate, int value, int ith, int [] tree) {
        if (idxToUpdate < s || idxToUpdate > e)
            return;
        tree[ith] += value;
        if(s != e) {
            int middle = (e-s)/2+e;
            update(s, middle, idxToUpdate, value, 2 * ith + 1, tree);
            update(middle+1, e, idxToUpdate, value, 2 * ith + 2, tree);
        }
    }

    private static void test() {
        int [][] matrix = {
                {1, 3, 5, 7, 9, 11}
            ,{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
            ,{1, 2, 3, 4}
            ,{31, -2, 3, 14, 17, -1}
        };
        int [] length = {
                matrix[0].length
            , matrix[1].length
            , matrix[2].length
        };
        int idx = 2;
        int tree [] = null;
        initializer(matrix[idx], tree);
        System.out.println(getIntervalSum(0, 2, length[idx], tree));
        System.out.println(getIntervalSum(0, 9, length[idx], tree));
        update(matrix[idx], length[idx], 0, 3, tree);
        System.out.println(getIntervalSum(0, 2, length[idx], tree));
        System.out.println(getIntervalSum(0, 0, length[idx], tree));
        System.out.println(getIntervalSum(2, 2, length[idx], tree));
        System.out.println(getIntervalSum(2, 5, length[idx], tree));
    }

    public static void main(String[] args) {

    }

}
