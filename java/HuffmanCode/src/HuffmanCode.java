import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.PriorityQueue;

public class HuffmanCode {

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

        private void add() { frequency++; }

        @Override
        public int compareTo(@NotNull Node that) {
            return frequency - that.frequency;
        }

        private void mapping(LinkedHashMap<Character, String> symbolMap
                , LinkedHashMap<String, Character> reverseMap, String code) {
            if (isLeaf()) {
                symbolMap.put(symbol, code);
                reverseMap.put(code, symbol);
                return;
            }
            lf.mapping(symbolMap, reverseMap, code + l);
            ri.mapping(symbolMap, reverseMap, code + r);
        }
    }


    private static Node root;
    private static LinkedHashMap<Character, String> symbolMap = new LinkedHashMap<>();
    private static LinkedHashMap<String, Character> reverseMap = new LinkedHashMap<>();

    private static LinkedHashMap<Character, Node> getFrequency(String str) {
        LinkedHashMap<Character, Node> frequency = new LinkedHashMap<>();
        for(Character c : str.toCharArray()) {
            if (!frequency.containsKey(c)) {
                frequency.put(c, new Node(c));
            }
            frequency.get(c).add();
        }
        return frequency;
    }

    private static PriorityQueue<Node> heapify(LinkedHashMap<Character, Node> frequency) {
        return new PriorityQueue<>(frequency.values());
    }


    private static Node buildTree(PriorityQueue<Node> heap) {
        while (heap.size() > 1) {
            Node l = heap.poll();
            Node r = heap.poll();
            heap.add(new Node(l, r));
        }
        return heap.poll();
    }

    private static String encode(String str) {
        Node root = buildTree(heapify(getFrequency(str)));
        root.mapping(symbolMap, reverseMap, "");
        StringBuilder sb = new StringBuilder();
        for (Character symbol : str.toCharArray())
            sb.append(symbolMap.get(symbol));
        return sb.toString();
    }

    private static String decode(String encoded) {
        StringBuilder sb = new StringBuilder();
        StringBuilder ans = new StringBuilder();
        for (Character code : encoded.toCharArray()) {
            sb.append(code);
            if (reverseMap.containsKey(sb.toString())) {
                ans.append(reverseMap.get(sb.toString()));
                sb = new StringBuilder();
            }
        }
        return ans.toString();
    }


    public static void main(String[] args) {
        String str [] = {
                "Ana ama sua nana, sua mana e banana"
                ,"AAAAAABBBBBCCCCDDDEEF"
                ,"christoffer"
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
        };
        int idx = 0;
        String encode = encode(str[idx]);
        System.out.printf("%d %d\n", encode.length(), str[idx].length());
        System.out.println(encode);
        System.out.println(str[idx]);

    }
}
