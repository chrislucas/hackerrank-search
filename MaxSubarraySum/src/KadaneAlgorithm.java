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
            else if(currentSum > last[2]) {
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
                 * j = 0 N = numero de linhas. A cada passo do algoritmo j aumenta em 1
                 * o que desloca o somatorio para a coluna da direita ate a ultima coluna
                 * da matriz
                 * */
                for (int k = 0; k < l; k++)
                    prefixSum[k] += matrix[k][j];

                /**
                 * Com o somatorio dos k elementos das (j-esima) coluna
                 * usamos o algoritmo de kadane
                 * */

                // retorna o intervalor i,j da soma maxima e a soma maxima [0],[1],[2]
                long partial [] = kadane1dv2(prefixSum);
                if (answer[4] < partial[2]) {
                    // top, left
                    answer[0] = partial[0];
                    answer[1] = i;  // left
                    // bottom right
                    answer[2] = partial[1];
                    answer[3] = j; //right
                    answer[4] = partial[2];
                }
            }
        }
        return answer;
    }

    private static long [] test2d(long [][] matrix) {
        long last [] = new long [5],currentMax = 0;
        last[0] = -1;
        last[1] = -1;
        last[2] = -1;
        last[3] = -1;
        last[4] = Integer.MIN_VALUE;
        int currentI = 0,currentJ = 0;
        int l = matrix.length;
        for (int i = 0; i < matrix.length; i++) {
            int c = matrix[i].length;
            for (int j = 0; j<c; j++) {
                currentMax += matrix[i][j];
                if (currentMax < 0) {
                    currentMax = 0;
                    currentI = i;
                    currentJ = j;
                }
                else if (currentMax > last[4]) {
                    last[0] = i;
                    last[1] = currentJ;
                    last[2] = currentI;
                    last[3] = j;
                    last[4] = currentMax;
                }
            }

        }

        return last;
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
            ,{{-1,-2,-1},{-8,-3,-4},{-3,-12,-10},{-4,-1,1}}
            ,{{1,1,1},{1,1,1},{1,1,1}}
            ,{{1,-1,1},{1,-1,1},{1,-1,1}}
            ,{{1,-1,-1},{1,-1,-1},{1,-1,-1}}
            ,{{-1,-1,1},{-1,-1,1},{-1,-1,1}}
            ,{{-1,1,-1},{-1,1,-1},{-1,1,-1}}
        };
        int idx = 1;
        long [] ans = test2d(matrix[idx]);
        System.out.printf("(%d %d) (%d %d) %d\n",ans[0],ans[1],ans[2],ans[3],ans[4]);
        ans = kadane2d(matrix[idx]);
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
