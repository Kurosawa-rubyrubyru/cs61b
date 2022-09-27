import edu.princeton.cs.algs4.Queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;

public class Boggle {

    // File path of dictionary file
    static String dictPath = "words.txt";
    private static int width;
    private static int height;
    private static Character[][] b;

    /**
     * Solves a Boggle puzzle.
     *
     * @param k             The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     * The Strings are sorted in descending order of length.
     * If multiple words have the same length,
     * have them in ascending alphabetical order.
     */
    private static class Node {
        private boolean end;
        private String nowstring;
        private TreeMap<Character, Node> nexttree;

        public Node(boolean end, String nowstring) {
            this.nowstring = nowstring;
            this.end = end;
            this.nexttree = new TreeMap<>();
        }
    }

    private static class SearchNode {
        private Node nownode;
        private ArrayList<ArrayList<Integer>> positions;

        public SearchNode(Node nownode, ArrayList<ArrayList<Integer>> positions) {
            this.nownode = nownode;
            this.positions = positions;
        }
    }


    public static List<String> solve(int k, String boardFilePath) {
        //----------构建字典树---------------
        In dict = new In(dictPath);
        if (dict.isEmpty() || k <= 0) {
            throw new IllegalArgumentException("nmybb");
        }
        Node root = new Node(false, "");
        String line;
        Node now;
        Character nowchar;
        while (!dict.isEmpty()) {
            line = dict.readLine();
            now = root;
            for (int i = 0; i < line.length(); i += 1) {
                nowchar = line.charAt(i);
                if (now.nexttree.get(nowchar) == null) {
                    now.nexttree.put(nowchar, new Node((i == line.length() - 1),
                            line.substring(0, i + 1)));
                    now = now.nexttree.get(nowchar);
                } else {
                    now = now.nexttree.get(nowchar);
                }
            }
        }
        //----------构建字符矩阵---------------
        In board = new In(boardFilePath);
        line = board.readLine();
        width = line.length();
        height = 1;
        while (!board.isEmpty()) {
            line = board.readLine();
            if (line.length() != width) {
                throw new IllegalArgumentException("not rectangle!!!!!!!!!!!^_^");
            }
            height += 1;
        }
        b = new Character[height][width];
        board = new In(boardFilePath);
        int nowheight = 0;
        while (!board.isEmpty()) {
            line = board.readLine();
            for (int i = 0; i < width; i += 1) {
                b[nowheight][i] = line.charAt(i);
            }
            nowheight += 1;
        }
        //----------求解---------------
        HashSet<String> ans = new HashSet<>();
        ArrayList<SearchNode> nextnode = new ArrayList<>();
        SearchNode find;
        SearchNode nownode;
        for (int i = 0; i < height; i += 1) {
            for (int j = 0; j < width; j += 1) {
                Queue<SearchNode> ansqueue = new Queue<>();
                ArrayList<ArrayList<Integer>> position = new ArrayList<ArrayList<Integer>>();
                ArrayList<Integer> nowposition = new ArrayList<>();
                nowposition.add(i);
                nowposition.add(j);
                position.add(nowposition);
                if (root.nexttree.get(b[i][j]) != null) {
                    ansqueue.enqueue(new SearchNode(root.nexttree.get(b[i][j]), position));
                } else {
                    continue;
                }
                while (ansqueue.size() > 0) {
                    nownode = ansqueue.dequeue();
                    nextnode = getNewNode(nownode);
                    for (int r = 0; r < nextnode.size(); r += 1) {
                        find = nextnode.get(r);
                        if (find.nownode.end) {
                            ans.add(find.nownode.nowstring);
                        }
                        ansqueue.enqueue(find);
                    }
                }
            }
        }
        return findAnswer(ans, k);
    }


    private static ArrayList<SearchNode> getNewNode(SearchNode searchnode) {
        ArrayList<SearchNode> ans = new ArrayList<>();
        Node nownode = searchnode.nownode;
        ArrayList<Integer> position = searchnode.positions.get(searchnode.positions.size() - 1);
        int i = position.get(0);
        int j = position.get(1);

        if (i > 0 && j > 0 && (nownode.nexttree.get(b[i - 1][j - 1]) != null)) {
            ArrayList<ArrayList<Integer>> positions = check(searchnode.positions, i - 1, j - 1);
            if (positions.size() != searchnode.positions.size()) {
                ans.add(new SearchNode(nownode.nexttree.get(b[i - 1][j - 1]), positions));
            }
        }
        if (j > 0 && (nownode.nexttree.get(b[i][j - 1]) != null)) {
            ArrayList<ArrayList<Integer>> positions = check(searchnode.positions, i, j - 1);
            if (positions.size() != searchnode.positions.size()) {
                ans.add(new SearchNode(nownode.nexttree.get(b[i][j - 1]), positions));
            }
        }
        if (i < height - 1 && j > 0 && (nownode.nexttree.get(b[i + 1][j - 1]) != null)) {
            ArrayList<ArrayList<Integer>> positions = check(searchnode.positions, i + 1, j - 1);
            if (positions.size() != searchnode.positions.size()) {
                ans.add(new SearchNode(nownode.nexttree.get(b[i + 1][j - 1]), positions));
            }
        }
        if (i > 0 && (nownode.nexttree.get(b[i - 1][j]) != null)) {
            ArrayList<ArrayList<Integer>> positions = check(searchnode.positions, i - 1, j);
            if (positions.size() != searchnode.positions.size()) {
                ans.add(new SearchNode(nownode.nexttree.get(b[i - 1][j]), positions));
            }
        }
        if (i < height - 1 && (nownode.nexttree.get(b[i + 1][j]) != null)) {
            ArrayList<ArrayList<Integer>> positions = check(searchnode.positions, i + 1, j);
            if (positions.size() != searchnode.positions.size()) {
                ans.add(new SearchNode(nownode.nexttree.get(b[i + 1][j]), positions));
            }
        }
        if (i > 0 && j < width - 1 && (nownode.nexttree.get(b[i - 1][j + 1]) != null)) {
            ArrayList<ArrayList<Integer>> positions = check(searchnode.positions, i - 1, j + 1);
            if (positions.size() != searchnode.positions.size()) {
                ans.add(new SearchNode(nownode.nexttree.get(b[i - 1][j + 1]), positions));
            }
        }
        if (j < width - 1 && (nownode.nexttree.get(b[i][j + 1]) != null)) {
            ArrayList<ArrayList<Integer>> positions = check(searchnode.positions, i, j + 1);
            if (positions.size() != searchnode.positions.size()) {
                ans.add(new SearchNode(nownode.nexttree.get(b[i][j + 1]), positions));
            }
        }
        if (i < height - 1 && j < width - 1 && (nownode.nexttree.get(b[i + 1][j + 1]) != null)) {
            ArrayList<ArrayList<Integer>> positions = check(searchnode.positions, i + 1, j + 1);
            if (positions.size() != searchnode.positions.size()) {
                ans.add(new SearchNode(nownode.nexttree.get(b[i + 1][j + 1]), positions));
            }
        }
        return ans;
    }

    private static ArrayList<ArrayList<Integer>> check(
            ArrayList<ArrayList<Integer>> oldposition, int i, int j) {
        ArrayList<ArrayList<Integer>> newposition = new ArrayList<>();
        boolean check = true;
        for (ArrayList<Integer> a : oldposition) {
            if (i == a.get(0) && j == a.get(1)) {
                check = false;
                break;
            }
        }
        if (check) {
            ArrayList<Integer> nowposition = new ArrayList<>();
            for (ArrayList<Integer> a : oldposition) {
                newposition.add(a);
            }
            nowposition.add(i);
            nowposition.add(j);
            newposition.add(nowposition);
            return newposition;
        } else {
            return oldposition;
        }
    }

    private static List<String> findAnswer(HashSet<String> ans, int k) {
        ArrayList<String> finalans = new ArrayList<>();
        for (String s : ans) {
            finalans.add(s);
        }
        Collections.sort(finalans, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                if (s1.length() > s2.length()) {
                    return -1;
                } else if (s1.length() < s2.length()) {
                    return 1;
                } else {
                    for (int i = 0; i < s1.length(); i += 1) {
                        if (s1.charAt(i) < s2.charAt(i)) {
                            return -1;
                        }
                        if (s1.charAt(i) > s2.charAt(i)) {
                            return 1;
                        }
                    }
                    return 0;
                }
            }
        });
        if (finalans.size() > k) {
            return finalans.subList(0, k);
        } else {
            return finalans;
        }
    }

    public static void main(String[] args) {
        List<String> ans = solve(7, "exampleBoard.txt");
        System.out.println(ans);
    }
}
