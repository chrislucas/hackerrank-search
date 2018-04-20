import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class MinMaxHeap {

    private static final Comparator<Comparable> MIN = new Comparator<Comparable>() {
        @Override
        public int compare(Comparable a, Comparable b) {
            return a.compareTo(b);
        }
    };

    private static final Comparator<Comparable> MAX = new Comparator<Comparable>() {
        @Override
        public int compare(Comparable a, Comparable b) {
            return b.compareTo(a);
        }
    };

    public static <T extends Comparable>  PriorityQueue<T> minHeap(int minSize) {
        return new PriorityQueue<>(minSize, MIN);
    }

    public static  PriorityQueue<Comparable> maxHeap(int minSize) {
        return new PriorityQueue<>(minSize, MAX);
    }


    public static void main(String[] args) {
        PriorityQueue<Comparable> pq = minHeap(10);
        int [] data = {10,8,9,3,11};
        for (int x : data)
            pq.add(x);

        for (Comparable cp : pq)
            System.out.printf("%s ", cp);
        pq.add(2);
        System.out.println("");

        for (Comparable cp : pq)
            System.out.printf("%s ", cp);
        System.out.println("");
    }

}
