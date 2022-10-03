import edu.princeton.cs.algs4.MinPQ;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class BinaryTrie implements Serializable {
    Node root;
    Map<Character, BitSequence> map;

    private class Node {
        Node subleft;
        Node subright;
        int value;
        boolean leaf;
        Character alphabet;

        public Node(Node n1, Node n2) {
            if (n1.value >= n2.value) {
                this.subleft = n2;
                this.subright = n1;
            } else {
                this.subleft = n1;
                this.subright = n2;
            }
            this.value = n1.value + n2.value;
            this.alphabet = null;
            this.leaf = false;
        }

        public Node(int value, Character alphabet) {
            this.leaf = true;
            this.value = value;
            this.alphabet = alphabet;
            this.subleft = null;
            this.subright = null;
        }
    }


    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        MinPQ<Node> huffmanqueue = new MinPQ<Node>(new Comparator<Node>() {
            public int compare(Node o1, Node o2) {
                return o1.value - o2.value;
            }
        });
        for (Character c : frequencyTable.keySet()) {
            huffmanqueue.insert(new Node(frequencyTable.get(c), c));
        }
        Node n1, n2, n3;
        n3 = null;
        if (huffmanqueue.size() < 2) {
            n3 = huffmanqueue.delMin();
        }
        while (huffmanqueue.size() >= 2) {
            n1 = huffmanqueue.delMin();
            n2 = huffmanqueue.delMin();
            n3 = new Node(n1, n2);
            huffmanqueue.insert(n3);
        }
        root = n3;
    }

    public Match longestPrefixMatch(BitSequence querySequence) {
        int nowbit;
        Node nownode = root;
        for (int i = 0; i < querySequence.length(); i += 1) {
            nowbit = querySequence.bitAt(i);
            if (nowbit == 1) {
                nownode = nownode.subright;
                if (nownode.leaf) {
                    return new Match(querySequence.firstNBits(i + 1), nownode.alphabet);
                }
            } else {
                nownode = nownode.subleft;
                if (nownode.leaf) {
                    return new Match(querySequence.firstNBits(i + 1), nownode.alphabet);
                }
            }
        }
        throw new IllegalArgumentException("nmybb");
    }

    public Map<Character, BitSequence> buildLookupTable() {
        map = new HashMap<>();
        vaebuildLookupTableHelper(root, new BitSequence());
        return map;
    }

    private void vaebuildLookupTableHelper(Node nownode, BitSequence bitSequence) {
        if (nownode.leaf) {
            map.put(nownode.alphabet, bitSequence);
        } else {
            vaebuildLookupTableHelper(nownode.subleft, new BitSequence(bitSequence).appended(0));
            vaebuildLookupTableHelper(nownode.subright, new BitSequence(bitSequence).appended(1));
        }
    }
}
