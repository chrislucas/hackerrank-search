package impl;

/**
 * https://www.geeksforgeeks.org/segment-tree-set-1-range-minimum-query/
 *
 * */

public class SegmentTree {

    private static int [] tree;

    public static double log(long lgn, long base) {
        return Math.log(lgn) / Math.log(base);
    }

    private static double log2(long lgn) {
        return Math.log(lgn)/Math.log(2);
    }

    private static void build(int [] data) {
        int n = data.length;
        // altura da arvore = log2(n)
        int heightTree = (int) Math.ceil(log2(n));
        // memoria necessario = 2 * (2 ^ log2(n)) - 1
        int maxSize = (int) (2 * Math.pow(2, heightTree) - 1);
        tree = new int[maxSize];
        build(data, 0, n-1, 0);
    }

    private static int getLeft(int idx) {
        return 2 * idx + 1;
    }

    private static int getRight(int idx) {
        return getLeft(idx) + 1;
    }

    private static int getParent(int idx) {
        return (idx & 1) ==  0 ? idx / 2 - 1 : idx / 2;
    }

    /**
     * Constroi recursivamente a arvore de segmento no intervalor l ... r
     * a variavel ith eh o indice do i-esimo valor na arvore de segmento
     *
     * A forma de montar uma arvore de segmento varia conforme o problema que se quer resolver.
     * No exemplo implmentando queremos criar uma arvore que guarda a soma parcial de um array
     * dentro de um intervalor 0 <= l, r <= n (n sendo o tamanho do array)
     *
     * */
    private static int build(int data [], int l, int r, int ith) {
        if (l == r) {
            tree[ith] = data[l];        // preencher valor da folha
            return data[l];             // retornar o valor que foi adicionado a folha - esquerda ou direita
        }
        int middle = getMiddle(l, r);
        int valueLeft = build(data, l, middle, getLeft(ith));
        int valueRight = build(data, middle+1, r, getRight(ith));
        // adicionar ao no pai a soma dos filhis
        tree[ith] = valueLeft + valueRight; // preecher valor raiz = soma da folha da esq + a dir
        return tree[ith];
    }

    private static int getMiddle(int s, int e) {
        return (e - s) / 2 + s;
    }

    /**
     * Devolver a soma no intervalo l ... r dum array de tamanho n
     * */
    private static int getIntervalSum(int l, int r, int n) {
        if(l < 0 || l > n-1 || r < 0 || r > n-1 || l > r)
            return -1;
        return getIntervalSum(0, n-1, l, r, 0);
    }

    /**
     * funcao auxiliar para recuperar a soma de um intervalo l ... r
     * num array de intervalor s ... e
     * */
    private static int getIntervalSum(int s, int e, int l, int r, int ith) {
        // se um dos limites do intervalo de busca estiver fora do intervalo do array, nao da para recuperar a soma
        if (l > e || r < s)
            return 0;
        else if ( l <= s && r >= e)
            return tree[ith];
        int middle = getMiddle(s, e);
        int valueLeft = getIntervalSum(s, middle, l, r, 2 * ith + 1);
        int valueRight = getIntervalSum(middle + 1, e, l, r, 2 * ith + 2);
        return valueLeft + valueRight;
    }

    /**
     * Atualizar o valor do array na i-esima posicao e atualizar o arvore de segment em logn
     * */
    private static void update(int [] data, int size, int idxUpdate, int value) {
        // indice que deve ser atualizado esta fora dos limites da estrutura de dados
        if (idxUpdate < 0 || idxUpdate > size - 1)
            return;
        int diff = value - data[idxUpdate];
        data[idxUpdate] = value;
        update(0, size-1, idxUpdate, diff, 0);
    }

    /**
     * s = indice de onde comeca o intervalo da arvore de segmento
     * e = indice de onde termina
     * idxUpdate = indice que deve ser atualizado no array
     * diff = diferenca entre o valor antigo e o novo
     * ith = i-esimo elemento na arvore de segmento
     * */
    private static void update(int s, int e, int idxUpdate, int diff, int ith) {
        if(idxUpdate < s || idxUpdate > e)
            return;
        // atualizando a soma dos segmentos com a diferenca entre o valor antigo do array e o valor novo
        tree[ith] += diff;
        if(s != e) {
            int middle = getMiddle(s, e);
            update(s, middle, idxUpdate, diff, 2 * ith + 1);
            update(middle+1, e, idxUpdate, diff, 2 * ith  + 2);
        }
    }

    private static void test() {
        int [][] matrix = {
             {1, 3, 5, 7, 9, 11}
            ,{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
            ,{1, 2, 3, 4}
            ,{31, -2, 3, 14, 17, -1}
        };
        int [] length = {matrix[0].length
            , matrix[1].length
            , matrix[2].length
        };
        int idx = 0;
        build(matrix[idx]);
        System.out.println(getIntervalSum(0, 2, length[idx]));
        System.out.println(getIntervalSum(0, 9, length[idx]));
        update(matrix[idx], length[idx], 0, 3);
        System.out.println(getIntervalSum(0, 2, length[idx]));
        System.out.println(getIntervalSum(0, 0, length[idx]));
        System.out.println(getIntervalSum(2, 2, length[idx]));
        System.out.println(getIntervalSum(2, 5, length[idx]));
    }

    public static void main(String[] args) {
        test();
    }

}
