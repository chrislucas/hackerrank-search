public class KadaneAlgorithm {

    private static long kadane1d(long [] array) {
        long current,last;
        current = last = array[0];
        for (int i = 1; i < array.length; i++) {
            current = Math.max(current,current + array[i]);
            last = Math.max(current,last);
        }
        return last;
    }

    private static long [] kadane1dv2(long [] array) {
        long currentSum = 0,maxValue = Integer.MIN_VALUE;
        int  idx = 0,currentIdx = 0;
        long last [] = new long[3];
        last[1] = -1;
        for (int i = 0; i < array.length; i++) {
            currentSum += array[i];
            if(currentSum < 0) {
                if(currentSum > maxValue) {
                    maxValue = currentSum;
                    idx = i;
                }
                currentIdx = i+1;
                currentSum = 0;
            }
            // > || >= eis a questao
            else if(currentSum >= last[2]) {
                last[0] = currentIdx;
                last[1] = i;
                last[2] = currentSum;
            }
        }
        if(last[1] == -1) {
            // todos os valores do array sao negativos
            last[1] = last[0] = idx;
            last[2] = maxValue;
        }
        return last;
    }


    private static long [] kadane2d(long [][] matrix) {
        long answer [] = new long [5];
        answer[4] = Integer.MIN_VALUE;
        int l = matrix.length;
        for (int i = 0; i <l; i++) {
            long [] prefixSum = new long[l];
            int c = matrix[i].length;
            for (int j = i; j < c; j++) {
                /**
                 * O loop abaixo cria um array com o somatorio
                 * das j colunas da matriz, onde j aumenta deslocando o
                 * somatorio o intervalo das colunas para esquerda.
                 * o indice k no array 'prefixSum' guarda o somatorio das (j)colunas
                 * e (k)linhas.
                 * O algoritmo comeca executando o somatorio da coluna j ate a N, onde
                 * j = 0 N = numero de linhas. A cada passo do algoritmo j aumenta em 1,
                 * o que desloca o somatorio para a coluna da direita ate a ultima coluna
                 * da matriz.
                 * */
                for (int k = 0; k < l; k++)
                    prefixSum[k] += matrix[k][j];
                /**
                 * prefixSum eh um array cujo indice k guarda
                 * o somatorio dos numeros das  j colunas na k-esima linha
                 * onde 'j' vai de 'i' ate o numero de colunas na matriz
                 * e i vai de 0 ate o numero de linhas da matriz
                 * */
                // retorna o intervalor i,j da soma maxima e a soma maxima [0],[1],[2]
                long partial [] = kadane1dv2(prefixSum);
                /**
                 * intervalo(partial[0], partial[1]) com a soma maxima partial[2]
                 * partial[0] - top: O inicio do intervalo guarda o indice da linha
                 * onde comecamos  a submatriz de soma maxima
                 * partial[1] - bottom: O final do intervalo guarda o indice da coluna
                 * onde encerramos a submatriz de soma maxima
                 * O par (partial[0], i) corresponde a esquerda superior da submatriz
                 * e o par (partial[1], j) corresponde a direita inferior da submatriz
                 * */
                if (answer[4] < partial[2]) {
                    // top, left
                    answer[0] = partial[0]; // top
                    answer[1] = i;  // left
                    // bottom right
                    answer[2] = partial[1]; // bottom
                    answer[3] = j; //right
                    answer[4] = partial[2]; // max sum
                }
            }
        }
        return answer;
    }

    private static void test() {
        /**
         * as matrizes 1, 2 e 3 sao casos interessantes para
         * para se entender pq o algoritmo kadane2d funciona
         * A matriz 4 nos da o mesmo resultado que a 2
         * */
        long [][][] matrix = {
             {{1,2,-1,-4,-20},{-8,-3,4,2,1},{3,8,10,1,3},{-4,-1,1,7,-6}}
            ,{{1,2,-1,-4},{-8,-3,4,2},{3,8,10,1},{-4,-1,1,7}}
            ,{
                  {1,2,-1,-4}
                 ,{-8,-3,4,2}
                 ,{3,0,10,1}
                 ,{-4,-1,1,7}
            }
            ,{
                 {1,2,-1,-4}
                ,{-8,-3,4,2}
                ,{3,8,10,1}
                ,{-4,-1,1,7}
            }
            ,{
                 {1,2,-1,-4,-20}
                ,{-8,-3,4,2,1}
                ,{3,0,10,1,3}
                ,{-4,-1,1,7,-6}
            }
            ,{
                 {-1,-2,-1,-4}
                ,{-8,-3,-4,-2}
                ,{3,8,10,1}
                ,{-4,-1,-1,-7}
            }
            ,{{-1,-2,-1},{-8,-3,-4},{-3,-12,-10},{-4,-1,1}}
            ,{{1,1,1},{1,1,1},{1,1,1}}
            ,{{1,-1,1},{1,-1,1},{1,-1,1}}
            ,{{1,-1,-1},{1,-1,-1},{1,-1,-1}}
            ,{{-1,-1,1},{-1,-1,1},{-1,-1,1}}
            ,{{-1,1,-1},{-1,1,-1},{-1,1,-1}}
            ,{{1,2},{8,3},{3,8},{4,1}}
            ,{{-1,-2},{8,3},{3,8},{-4,-1}}
        };
        int idx = 13;
        long [] ans = kadane2d(matrix[idx]);
        System.out.printf("LT(%d %d) BR(%d %d) %d\n",ans[0],ans[1],ans[2],ans[3],ans[4]);
    }

    private static void test2() {
        long [][] array = {
             {-4,-1,-11,-7,-6}
            ,{-4,-1,-11,7,-6}
            ,{-4,-1,11,7,-6}
            ,{2,-3,0,-6}
            ,{-2,-3,-1,-6}
        };
        int idx = 4;
        long ans [] = kadane1dv2(array[idx]);
        System.out.printf("(%d %d) %d\n",ans[0],ans[1],ans[2]);
    }

    public static void main(String[] args) {
        test();
    }
}
