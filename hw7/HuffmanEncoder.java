import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {

    private static BinaryTrie trie;

    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        int[] frequence = new int[256];
        for (int i = 0; i < inputSymbols.length; i += 1) {
            frequence[inputSymbols[i]] += 1;
        }
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 1; i < frequence.length; i += 1) {
            if (frequence[i] > 0) {
                map.put((char) i, frequence[i]);
            }
        }
        return map;
    }

    public static void main(String[] args) {
        char[] file = FileUtils.readFile(args[0]);
        Map<Character, Integer> frequencyTable = buildFrequencyTable(file);
        trie = new BinaryTrie(frequencyTable);
        Map<Character, BitSequence> yourTable = trie.buildLookupTable();
        ObjectWriter writer = new ObjectWriter("trie.huf");
        ArrayList<Integer> integerList = new ArrayList<>();
        ArrayList<Character> charactersList = new ArrayList<>();
        for (Character c : yourTable.keySet()) {
            integerList.add(frequencyTable.get(c));
            charactersList.add(c);
        }
        writer.writeObject(integerList);
        writer.writeObject(charactersList);
        BitSequence bitsequence = new BitSequence();
        List<BitSequence> sequences = new ArrayList<>();
        for (int i = 0; i < file.length; i += 1) {
            sequences.add(yourTable.get(file[i]));
        }
        writer = new ObjectWriter(args[0] + ".huf");
        writer.writeObject(bitsequence.assemble(sequences));
    }

    public BinaryTrie gettree() {
        return trie;
    }
}
