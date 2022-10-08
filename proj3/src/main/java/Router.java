import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     *
     * @param g       The graph to use.
     * @param stlon   The longitude of the start location.
     * @param stlat   The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    protected static HashMap<Long, Integer> index;
    protected static Double[] dist;

    private static class SearchNode {
        double gn;
        double hn;
        SearchNode pre;
        Long id;

        public SearchNode(GraphDB g, Long end, SearchNode pre, Long id) {
            this.pre = pre;
            this.id = id;
            this.hn = g.distance(end, id);
        }
    }

    private static class comparator implements Comparator<SearchNode> {
        public int compare(SearchNode o1, SearchNode o2) {
            if ((dist[index.get(o1.id)] + o1.hn) <= (dist[index.get(o2.id)] + o2.hn)) {
                return -1;
            } else if ((dist[index.get(o1.id)] + o1.hn) > (dist[index.get(o2.id)] + o2.hn)) {
                return 1;
            } else {
                return 0;
            }
        }
    }


    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat, double destlon, double destlat) {
        ArrayList<Long> vertices = (ArrayList<Long>) g.vertices();
        HashSet<Long> vis = new HashSet<>();
        index = new HashMap<>();
        for (int i = 0; i < vertices.size(); i += 1) {
            index.put(vertices.get(i), i);
        }
        dist = new Double[vertices.size()];
//        Long[] pre = new Long[vertices.size()];
        Long startpath = g.closest(stlon, stlat);
        for (int i = 0; i < dist.length; i += 1) {
            dist[i] = 99999999.0;
        }
        dist[index.get(startpath)] = 0.0;
        Long endpath = g.closest(destlon, destlat);
        Queue<SearchNode> searchqueue = new PriorityQueue(new comparator());
        searchqueue.add(new SearchNode(g, endpath, null, startpath));
        vis.add(startpath);
        SearchNode nowNode = null;
        ArrayList<Long> neighbors, otherneighbors;
        SearchNode s;
        Iterator<SearchNode> it;
        while (!searchqueue.isEmpty()) {
            nowNode = searchqueue.remove();
            if (!nowNode.id.equals(endpath)) {
                neighbors = (ArrayList<Long>) (g.adjacent(nowNode.id));
                for (Long id : neighbors) {
                    if (!(vis.contains(id))) {
                        vis.add(id);
                        otherneighbors = (ArrayList<Long>) (g.adjacent(id));
                        for (Long otherid : otherneighbors) {
                            if (vis.contains(otherid)) {
                                if (dist[index.get(id)] > dist[index.get(otherid)] + g.distance(id, otherid)) {
                                    dist[index.get(id)] = dist[index.get(otherid)] + g.distance(id, otherid);
//                                    System.out.println('?');
//                                    it = searchqueue.iterator();
//                                    while (it.hasNext()) {
//                                        s = it.next();
//                                        if (s.id.equals(id)) {
//                                            it.remove();
//                                            searchqueue.remove(s);
//                                            searchqueue.add(s);
//                                            break;
//
//                                        }
//                                    }
                                }
                            }
                        }
                        searchqueue.add(new SearchNode(g, endpath, nowNode, id));
                    }
                }
            } else {
                break;
            }
        }
        vis = new HashSet<>();
        searchqueue = new PriorityQueue(new comparator());
        searchqueue.add(new SearchNode(g, endpath, null, startpath));
        vis.add(startpath);

        while (!searchqueue.isEmpty()) {
            nowNode = searchqueue.remove();
            if (!nowNode.id.equals(endpath)) {
                neighbors = (ArrayList<Long>) (g.adjacent(nowNode.id));
                for (Long id : neighbors) {
                    if (!(vis.contains(id))) {
                        vis.add(id);
                        otherneighbors = (ArrayList<Long>) (g.adjacent(id));
                        for (Long otherid : otherneighbors) {
                            if (vis.contains(otherid)) {
                                if (dist[index.get(id)] > dist[index.get(otherid)] + g.distance(id, otherid)) {
                                    dist[index.get(id)] = dist[index.get(otherid)] + g.distance(id, otherid);
                                }
                            }
                        }
                        searchqueue.add(new SearchNode(g, endpath, nowNode, id));
                    }
                }
            } else {
                break;
            }
        }

//        while ()
        /*不能直接爆算，要用A*启发式算法*/
//        for (int i = 0; i < dist.length; i += 1) {
//            dist[i] = 99999999.0;
//            pre[i] = Long.parseLong(((Integer) (-1)).toString());
//            pre[i] = null;
//
//            index.put(vertices.get(i), i);
//        }
//        Long nowindex;
//        pre[index.get(startpath)] = Long.parseLong(((Integer) (-1)).toString());
//        dist[index.get(startpath)] = 0.0;
//        Iterator startit = g.adjacent(startpath).iterator();
//
//        while (startit.hasNext()) {
//            nowindex = (Long) startit.next();
//            pre[index.get(nowindex)] = startpath;
//            dist[index.get(nowindex)] = g.distance(startpath, nowindex);
//        }
//        for (int t = 1; t < dist.length; t++) {
//            double minn = 99999999;
//            int pos = -1;
//            int posi = -1;
//            for (int i = 0; i < dist.length; i++) {
//                if (pre[i] != null) {
//                    for (Long other : g.adjacent(vertices.get(i))) {
//                        if (g.distance(vertices.get(i), other) + g.distance(other, endpath)
//                        < minn && pre[index.get(other)] == null) {
//                            minn = g.distance(vertices.get(i), other) + g.distance(other, endpath);
//                            pos = index.get(other);
//                            posi = i;
//                        }
//                    }
//                }
//            }
//            if (pos == -1) {
//                break;
//            }
//            pre[pos] = vertices.get(posi);
//
//            for (Long i : g.adjacent(vertices.get(pos))) {
//                if (pre[index.get(i)] != null && g.distance(i, vertices.get(pos)) + dist[pos] < dist[index.get(i)]) {
//                    dist[pos] = g.distance(i, vertices.get(pos)) + dist[index.get(i)];
//                    pre[pos] = i;
//
//                }
//            }
//        }
        ArrayList<Long> ans = new ArrayList<>();
        ans.add(endpath);

        while (true) {
            ans.add(nowNode.pre.id);
            nowNode = nowNode.pre;
            if (nowNode.id.equals(startpath)) {
                break;
            }
        }

        ArrayList<Long> ansfinal = new ArrayList<>();
        for (int i = ans.size() - 1; i >= 0; i -= 1) {
            ansfinal.add(ans.get(i));
        }
        return ansfinal;
    }

    /**
     * Create the list of directions corresponding to a route on the graph.
     *
     * @param g     The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigatiionDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        return null; // FIXME
    }


    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /**
         * Integer constants representing directions.
         */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /**
         * Number of directions supported.
         */
        public static final int NUM_DIRECTIONS = 8;

        /**
         * A mapping of integer values to directions.
         */
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /**
         * Default name for an unknown way.
         */
        public static final String UNKNOWN_ROAD = "unknown road";

        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /**
         * The direction a given NavigationDirection represents.
         */
        int direction;
        /**
         * The name of the way I represent.
         */
        String way;
        /**
         * The distance along this way I represent.
         */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.", DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         *
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction && way.equals(((NavigationDirection) o).way) && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }
    }
}
