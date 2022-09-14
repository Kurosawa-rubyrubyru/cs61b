package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Solver {

    private class Boarditerable implements Iterable<WorldState> {
        private int id = anslist.size();

        public Iterator<WorldState> iterator() {
            return new Iterator<WorldState>() {
                public boolean hasNext() {
                    return (id != 0);
                }

                public WorldState next() {
                    id -= 1;
                    return anslist.get(id).board;
                }
            };
        }
    }

    private class SearchNode {
        WorldState board;
        int times;
        SearchNode prenode;

        public SearchNode(WorldState board, int times, SearchNode prenode) {
            this.board = board;
            this.times = times;
            this.prenode = prenode;
        }
    }

    private MinPQ<SearchNode> pq;

    private SearchNode ansnode;

    private ArrayList<SearchNode> anslist;

    private Map<String, Integer> boardmap = new HashMap<>();

    public Solver(WorldState initial) {
        pq = new MinPQ<SearchNode>(new Comparator<SearchNode>() {
            private int s1ans;
            private int s2ans;

            public int compare(SearchNode s1, SearchNode s2) {
                String s1s = s1.board.toString();
                if (boardmap.containsKey(s1s)) {
                    s1ans = boardmap.get(s1s);
                } else {
                    s1ans = s1.board.estimatedDistanceToGoal();
                    boardmap.put(s1s, s1ans);
                }
                String s2s = s2.board.toString();
                if (boardmap.containsKey(s2s)) {
                    s2ans = boardmap.get(s2s);
                } else {
                    s2ans = s2.board.estimatedDistanceToGoal();
                    boardmap.put(s2s, s2ans);

                }
                return s1.times + s1ans - s2.times - s2ans;
            }
        });
        SearchNode initialnode = new SearchNode(initial, 0, null);
        if (initialnode.board.isGoal()) {
            ansnode = initialnode;
        } else {
            pq.insert(initialnode);
            solverhelper();
        }
    }

    public int moves() {
        return ansnode.times;
    }

    public Iterable<WorldState> solution() {
        anslist = new ArrayList<>();
        anslist.add(ansnode);
        SearchNode newnode = ansnode;
        while (newnode.prenode != null) {
            anslist.add(newnode.prenode);
            newnode = newnode.prenode;
        }
        Boarditerable iter = new Boarditerable();
        return iter;
    }

    private SearchNode solverhelper() {
        while (true) {
            SearchNode node = pq.delMin();
            if (node.board.isGoal()) {
                ansnode = node;
                return ansnode;
            } else {
                Iterator<WorldState> ns = node.board.neighbors().iterator();
                while (ns.hasNext()) {
                    WorldState next = ns.next();
                    if (node.prenode == null || !(node.prenode.board.equals(next))) {
                        pq.insert(new SearchNode(next, node.times + 1, node));
                    }
                }
            }
        }
    }
}
