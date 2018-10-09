import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Implementacao baseada no video/tutorial do Vini Godoy
 * https://github.com/ViniGodoy/huffman
 * */


public class HuffmanCodeStringCompression {

    private static class Node implements Comparable<Node> {
        private char symbol;
        private int frequency;
        private Node lf, ri;

        private static final char l = '0';
        private static final char r = '1';

        private Node(char symbol) {
            this.symbol = symbol;
        }

        private Node(Node lf, Node ri) {
            this.symbol = '+';
            this.lf = lf;
            this.ri = ri;
            this.frequency = lf.frequency + ri.frequency;
        }

        private boolean isLeaf() {
            return lf == null && ri == null;
        }

        private int getFrequency() {
            if (isLeaf())
                return frequency;
            return lf.getFrequency() + ri.getFrequency(); // a frequencia de um no (sub)raiz e a soma da frequencia de suas folhas
        }

        private char getSymbol() {
            return symbol;
        }

        private Node getLeft() {
            return lf;
        }

        private Node getRight() {
            return ri;
        }

        private void acc() {
            frequency++;
        }

        @Override
        public int compareTo(@NotNull Node o) {
            return getFrequency() - o.getFrequency();
        }

        /**
         * a codificacao basicamente transforma o texto em uma string de 0's e 1's
         *
         * o metodo abaixo percorre usando uma busca em profundidade (DFS) a arvore de huffman, a partir do nó raiz ate os nos folhas.
         * Quando encontra um no folha, adiciona ao mapa de simbolos uma chave e um valor.
         * A chave é o simbolo que esta contido no nó folha alcançado e o valor é o código gerado ao percorrer
         * a árvore fazendo a DFS,
         *
         * Quando percorremos uma aresta da esquerda adcionamos o simbolo 0 e para direita 1
         * assim o mapa vai ter chaves cujos valores serao a concatenacao de 0's e 1's, conforme
         * percorre-se a arvore
         *
         * */

        private void createSymbolCodeMap(Map<Character, String> symbolCodeMap) {
            createSymbolCodeMap(symbolCodeMap, "");
        }

        private void createSymbolCodeMap(Map<Character, String> symbolCodeMap, String code) {
            if (isLeaf()) {
                symbolCodeMap.put(getSymbol(), code);
                return;
            }
            lf.createSymbolCodeMap(symbolCodeMap, code + l);
            ri.createSymbolCodeMap(symbolCodeMap, code + r);
        }

        @Override
        public String toString() {
            return String.format("Simbolo '%s': Frequencia: %d Nó: %s"
                    , symbol == '\n' ? "\\n" : String.valueOf(symbol)
                    , frequency
                    , isLeaf() ? "Folha" : "Raiz"
            );
        }

        @Override
        public boolean equals(Object obj) {
            return symbol == ((Node) obj).getSymbol();
        }

        @Override
        public int hashCode() {
            return symbol;
        }
    }

    private static Node root;

    private static HashMap<Character, Node> getSortedFrequency(String str) {
        HashMap<Character, Node> frequency = new HashMap<>();
        for (char c : str.toCharArray()) {
            if (!frequency.containsKey(c)) {
                frequency.put(c, new Node(c));
            }
            frequency.get(c).acc();
        }
        return frequency;
    }

    private static PriorityQueue<Node> getSortedFrequency(HashMap<Character, Node> frequency) {
        return new PriorityQueue<>(frequency.values());
    }

    private static Node createTree(PriorityQueue<Node> queue) {
        while (queue.size() > 1) {
            Node a = queue.poll();
            Node b = queue.poll();
            Node parent = new Node(a, b);
            queue.add(parent);
        }
        return queue.poll();
    }

    private static String encode(String str) {
        root = createTree(getSortedFrequency(getSortedFrequency(str)));
        Map<Character, String> symbolCodeMap = new LinkedHashMap<>();
        root.createSymbolCodeMap(symbolCodeMap);
        StringBuilder code = new StringBuilder();
        /**
         * Substituir cada caracter pelo seu codigo correspondente
         * a vantagem está que cada caracter na tabela ascii utiliza
         * 8 bits. Codificando os caracteres usando 0's e 1's nos podemos
         * reduzir para N bits onde N é o numero de 0's e 1's do texto codificados
         * */
        for (char c : str.toCharArray()) {
            code.append(symbolCodeMap.get(c));
        }
        return code.toString();
    }

    private static String decode(String encode) {
        if (root != null) {
            Node current = root;
            StringBuilder rs = new StringBuilder();
            for (char c : encode.toCharArray()) {
                current = (c == Node.l) ? current.getLeft() : current.getRight();
                if (current.isLeaf()) {
                    rs.append(current.getSymbol());
                    current = root;
                }
            }
            return rs.toString();
        }
        else
            return "";
    }

    public static void main(String[] args) {
        String str [] = {
            "Ana ama sua nana, sua mana e banana"
            ,"AAABBBCCC"
            ,"AAAAAABBBBBCCCCDDDEEF"
            ,"christoffer"
            ,"banana"
            ,"Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Phasellus scelerisque feugiat nunc ac vulputate. Praesent " +
                "venenatis tortor nec massa ullamcorper feugiat. Maecenas ac libero et " +
                "leo placerat sodales. Mauris a mattis diam, vestibulum ultricies dui. " +
                "Etiam eu nisi a massa tincidunt luctus sit amet aliquam mauris. " +
                "Pellentesque sed ex nec ante efficitur tincidunt vel nec augue. " +
                "Integer eget rutrum leo. Fusce faucibus eros at semper posuere. " +
                "Etiam purus diam, semper volutpat lacus in, " +
                "tincidunt sollicitudin felis. Donec rutrum nisi " +
                "sed est vestibulum, vitae tincidunt sem auctor. " +
                "Cras facilisis eleifend velit eget tempus. " +
                "Nulla vel consequat elit. Quisque et elit sit amet " +
                "purus eleifend bibendum ut ac nisi. Vivamus tincidunt laoreet " +
                "dignissim. Morbi enim risus, tempus nec consequat a, " +
                "tincidunt quis turpis. Cras a urna semper, tempor nulla sed, rhoncus odio." +
                "Donec varius tortor sed neque dignissim, " +
                "sit amet aliquet risus tempor. Integer et risus efficitur, " +
                "vestibulum elit non, consequat massa. Duis fermentum neque vitae " +
                "massa luctus ultrices. Aliquam erat volutpat. Aenean faucibus consequat quam, " +
                "sed tincidunt velit tincidunt vel. Proin eu luctus ex. Mauris convallis nisl tortor, " +
                "quis consequat justo vehicula ac. Nam id lectus sit amet eros dapibus tincidunt. " +
                "Cras porta augue quis sem scelerisque, quis congue erat pharetra. " +
                "Maecenas id risus dictum, congue sapien ut, tempor lorem." +
                "Suspendisse sagittis libero pharetra ultricies semper. Pellentesque eget maximus neque. " +
                "Sed vitae urna turpis. Vestibulum neque dolor, fringilla ac vehicula et, blandit quis lectus. " +
                "Donec viverra iaculis fermentum. Vivamus ornare urna id nibh consequat, ac interdum erat auctor. " +
                "Sed ac convallis massa, id venenatis nibh. Cras non pretium leo. Suspendisse accumsan eget nisi a ultricies. " +
                "Pellentesque posuere at nulla in rhoncus. Pellentesque convallis orci et sem scelerisque, " +
                "at malesuada odio pellentesque. Vestibulum vitae rutrum turpis." +
                "Etiam vel sollicitudin neque. Aliquam aliquam dignissim nunc non " +
                "pharetra. Morbi molestie molestie nunc quis mattis. " +
                "Nullam arcu erat, pulvinar quis venenatis vel, vulputate at risus. " +
                "Suspendisse dui mauris, condimentum a ullamcorper vel, egestas at neque. " +
                "Vivamus viverra posuere felis at pulvinar. Nam eget neque at erat sagittis tempus. " +
                "Nunc egestas felis ut vestibulum ornare. Fusce iaculis consequat elit, at molestie ipsum. " +
                "Sed arcu libero, faucibus et erat at, molestie tincidunt lectus. " +
                "Interdum et malesuada fames ac ante ipsum primis in faucibus. " +
                "Fusce nec eros non dolor auctor finibus. Ut rhoncus mi id tortor dignissim iaculis. " +
                "Suspendisse eleifend non tortor in dapibus. " +
                "Nam egestas purus nec lorem facilisis, eu pretium mi pulvinar." +
                "Quisque laoreet lorem eu placerat scelerisque. " +
                "Duis rhoncus ex sit amet vulputate cursus. Nulla quis posuere erat. " +
                "Sed a quam a tellus mattis laoreet ac sed lacus. Mauris ut felis ultrices, " +
                "rutrum lacus eget, dignissim arcu. Mauris sit amet felis et tellus ornare lacinia. " +
                "In vitae metus quis justo mollis porta. Aliquam imperdiet faucibus lacinia. " +
                "Vestibulum auctor id augue eu auctor." +
                "Proin sodales massa sit amet risus venenatis aliquet. " +
                "Nulla facilisi. Sed porta dictum nisl, eu ullamcorper diam. " +
                "Integer id magna ultrices quam sagittis congue sit amet et odio. " +
                "Vestibulum ante tellus, maximus sed magna ac, consectetur lacinia tellus. " +
                "Phasellus fermentum sollicitudin mauris, eu"
            , "In computer science and information theory, a Huffman code is a particular type\n" +
                "of optimal prefix code that is commonly used for lossless data compression. The\n" +
                "process of finding and/or using such a code proceeds by means of Huffman coding,\n" +
                "an algorithm developed by David A. Huffman while he was a Ph.D. student at MIT,\n" +
                "and published in the 1952 paper \"A Method for the Construction of\n" +
                "Minimum-Redundancy Codes\"."
        };
        int idx = 1;
        String e = encode(str[idx]);
        int sizeNormalText = str[idx].length() * 8;
        int sizeEncodedText = e.length();
        System.out.println(e);
        String message =
                "Quantidade de bits no texto original: %d.\n" +
                "Quantidade de bits no texto comprimido: %d\n" +
                "Taxa de compressao: %f\n";
        System.out.printf(message
                , sizeNormalText
                , sizeEncodedText
                , 1.0 - ((sizeEncodedText * 1.0) / (sizeNormalText * 1.0))
        );
        System.out.println(decode(e));
    }
}
