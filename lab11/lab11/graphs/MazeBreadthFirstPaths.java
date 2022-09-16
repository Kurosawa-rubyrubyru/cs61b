package lab11.graphs;

import java.util.ArrayDeque;
import java.util.Iterator;

/**
 * @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private Iterator<Integer> iter;
    private int newpos;


    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    private void bfs(int v) {
        //v是起始点id
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        marked[v] = true;
        announce();
        deque.add(v);

        while (!deque.isEmpty()) {
            v = deque.removeFirst();
            if (v == t) {
                return;
            }
            iter = maze.adj(v).iterator();
            while (iter.hasNext()) {
                newpos = iter.next();
                if (!marked[newpos]) {
                    deque.add(newpos);
                    marked[newpos] = true;
                    distTo[newpos] = distTo[v] + 1;
                    edgeTo[newpos] = v;
                }
            }
            announce();
        }


//        if (v == t) {
//            targetFound = true;
//        }
//
//        if (targetFound) {
//            return;
//        }
//
//        for (int w : maze.adj(v)) {
//            if (!marked[w]) {
//                edgeTo[w] = v;
//                announce();
//                distTo[w] = distTo[v] + 1;
//                dfs(w);
//                if (targetFound) {
//                    return;
//                }
//            }
//        }
    }

    @Override
    public void solve() {
        dfs(s);
    }
}

