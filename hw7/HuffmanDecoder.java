import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HuffmanDecoder {
    private static BinaryTrie trie;

    public static void main(String[] args) {
        ObjectReader reader = new ObjectReader(args[0]);
        BitSequence b = new BitSequence(reader.readObject().toString());
        ObjectReader or = new ObjectReader("trie.huf");
        Object x = (ArrayList<Integer>) or.readObject();
        Object y = (ArrayList<Character>) or.readObject();
        ArrayList<Integer> integerList = (ArrayList<Integer>) x;
        ArrayList<Character> charactersList = (ArrayList<Character>) y;
        Map<Character, Integer> frequencyTable = new HashMap<>();
        for (int i = 0; i < integerList.size(); i += 1) {
            frequencyTable.put(charactersList.get(i), integerList.get(i));
        }
        trie = new BinaryTrie(frequencyTable);
        int start = 0;
        int end = 0;
        Match m;
        ArrayList<Character> ans = new ArrayList<>();
        while (b.length() > 0) {
            m = trie.longestPrefixMatch(b);
            ans.add(m.getSymbol());
            b = b.lastNBits(b.length() - m.getSequence().length());
        }
        char[] ansfile = new char[ans.size()];
        for (int i = 0; i < ans.size(); i += 1) {
            ansfile[i] = ans.get(i);
        }
        FileUtils.writeCharArray(args[1], ansfile);
    }
}
