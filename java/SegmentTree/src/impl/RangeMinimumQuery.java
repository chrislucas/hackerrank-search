package impl;

/**
 * https://www.geeksforgeeks.org/segment-tree-set-1-range-minimum-query/
 *
 * */

import com.sun.org.apache.regexp.internal.RE;

import static  java.lang.Math.ceil;
import static  java.lang.Math.log;
import static  java.lang.Math.pow;

public class RangeMinimumQuery {

    private int [] segmentTree, data;
    private int n;
    private static class Result {
        private int answer;
        private boolean valid;
        public Result(int answer, boolean valid) {
            this.answer = answer;
            this.valid = valid;
        }
        public int getAnswer() {  return answer; }

        public boolean isValid() { return valid; }

        @Override
        public String toString() { return String.format("%s %d", valid, answer); }
    }

    public RangeMinimumQuery(int [] data) {
        this.data = data;
        this.n = data.length;
        int heightTree = (int) ceil(log(n)/log(2));
        int maxSize = 2 * (int) pow(2, heightTree) - 1;
        this.segmentTree = new int[maxSize];
        build(0, n, 0);
    }

    private int getMiddle(int s, int e) {
        return (e - s) / 2 + s;
    }

    private int getLeft(int idx) {
        return 2 * idx + 1;
    }

    private int getRight(int idx) {
        return getLeft(idx) + 1;
    }

    private int build(int startIdx, int endIdx, int curIdx) {
        if(startIdx == endIdx) {
            segmentTree[curIdx] = data[startIdx];
            return data[startIdx];
        }
        int middle = getMiddle(startIdx, endIdx);
        int l = build(startIdx, middle, getLeft(curIdx));
        int r = build(middle+1, endIdx, getRight(curIdx));
        // adicionar ao no pai o valor minimo entre os nos filhos da esq. ou dir.
        segmentTree[curIdx] = Math.min(l, r);
        return segmentTree[curIdx];
    }


    public Result rqm(int startIdxQuery, int endIdxQuery) {
        if (startIdxQuery < 0 || endIdxQuery > n || (startIdxQuery > endIdxQuery)) {
            return new Result(-1, false);
        }
        return rqm(0, n, startIdxQuery, endIdxQuery, 0);
    }

    private Result rqm(int startIdx, int endIdx, int startIdxQuery, int endIdxQuery, int curIdx) {
        if (startIdxQuery <= startIdx && endIdxQuery >= endIdx) {
            return new Result(segmentTree[curIdx], true);
        }

        else if(endIdx < startIdxQuery || startIdx > endIdxQuery) {
            return new Result(-1, false);
        }

        int middle = getMiddle(startIdx, endIdx);

        Result left = rqm(startIdx, middle, startIdxQuery, endIdxQuery, getLeft(curIdx));
        Result right = rqm(middle+1, endIdx, startIdxQuery, endIdxQuery, getRight(curIdx));
        return left.getAnswer() < right.getAnswer() ? left : right;
    }


    private static void test1() {
        int [][] data = {
            {1, 3, 2, 7, 9, 11}
        };
        RangeMinimumQuery st = new RangeMinimumQuery(data[0]);
        Result results [] = {st.rqm(0, 3), st.rqm(0, 6), st.rqm(0, 1)};
        for (Result result : results) {
            System.out.println(result);
        }
    }

    public static void main(String[] args) {
        test1();
    }
}
