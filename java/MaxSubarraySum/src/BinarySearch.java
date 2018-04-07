

/**
 * https://www.topcoder.com/community/data-science/data-science-tutorials/binary-search/
 * Boa explicacao sobre o que eh lowerbound and upperbound numa binary tree
 * https://stackoverflow.com/questions/28389065/difference-between-basic-binary-search-for-upper-bound-and-lower-bound
 *
 * https://codingblocks.com/resources/binary-search-upper-lower-bound/
 * */

public class BinarySearch {

    private static int binarySearch(Comparable [] data, Comparable target) {
        int lo = 0;
        int hi = data.length - 1;
        while (lo <= hi) {
            int mi = (hi - lo) / 2 + lo;
            Comparable p = data[mi];
            if (p.compareTo(target) == 0) {
                return mi;
            }
            else if(p.compareTo(target) > 0) {
                hi = mi - 1;
            }
            else {
                lo = mi + 1;
            }
        }
        return -1;
    }

    private static int lowerBound(Comparable [] data, Comparable k) {
        int lo = 0;
        int hi = data.length - 1;
        int an = -1;
        while (lo <= hi) {
            int mi = (hi - lo) / 2 + lo;
            Comparable p = data[mi];
            /**
             * Na funcao lowerbound, estamos procurando o primeiro elemento 'P' <= a 'K'
             * Se P == K, talvez tenhamos a nossa resposta, porem o valor K pode estar repetido
             * no vetor entao reduzimos o limite de busca diminuindo o indice do vetor.
             * Existe o caso de K nao existir no vetor, entao voltamos o indice do primeiro P > K
             * que aparecer no vetor
             * */
            if (p.compareTo(k) >= 0) {
                an = mi;
                hi = mi - 1;
            }
            // Se P > K talvez a resposta esteja mais a direita, entao diminuimos o indice do vetor
            //else if (p.compareTo(k) > 0) { hi = mi - 1; }
            else { lo = mi + 1; }
        }
        return an;
    }

    private static int upperBound(Comparable [] data, Comparable k) {
        int lo = 0;
        int hi = data.length - 1;
        int an = -1;
        while (lo <= hi) {
            int mi = (hi - lo) / 2 + lo;
            Comparable p = data[mi];
            /**
             * Na funcao upper bound, se o elemento 'K' for encontrado no vetor, quer dizer que
             * o primeiro elemento maior que 'K' esta a direita do meio
             * */
            if(p.compareTo(k) == 0) {
                lo = mi + 1;
            }
            else if(p.compareTo(k) > 0) {
                an = mi;
                hi = mi - 1;
            }
            else { lo = mi + 1; }
        }
        return an;
    }


    private static void test() {
        Integer [][] matrix = {
             {1,2,3,4,5,5,5,6,7,9}
            ,{1,2,3,4,5,5,5,6,7,9}
            ,{1,2,3,4,5,5,5,6,7,9}
            ,{1,2,3,4,5,5,5,6,7,9}
            ,{10,10,10,20,20,20,30,30}
            ,{10,10,10,30,30}
            ,{1,2,3,4,5,5,5,6,7,9}
        };

        int k [] = {5, 3, 8, 1, 20, 20, 8};
        int idx = 6;

        int bs = binarySearch(matrix[idx], k[idx]);
        System.out.println(bs);
        int up = upperBound(matrix[idx], k[idx]);
        System.out.printf("Upper Bound %d\n", up);
        int lo = lowerBound(matrix[idx], k[idx]);
        System.out.printf("Lower bound: %d\n", lo);
    }


    public static void main(String[] args) {
        test();
    }
}
