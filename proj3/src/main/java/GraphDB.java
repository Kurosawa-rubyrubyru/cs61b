import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     *
     * @param dbPath Path to the XML file to be parsed.
     */
    protected Map<String, Node> nodes;
    protected Map<String, Way> ways;


    static class Node {
        double lon;
        double lat;
        String id;
        String name;
        ArrayList<String> connected = new ArrayList<>();

        Node(String lon, String lat, String id) {
            this.id = id;
            this.lon = Double.parseDouble(lon);
            this.lat = Double.parseDouble(lat);
        }
    }

    static class Way {
        LinkedList<Node> nodesOfway = new LinkedList<>();
        String id;
        HashMap<String, String> extraInfo = new HashMap<>();

        Way(String id) {
            this.id = id;
        }

        public void addNode(String id, GraphDB g) {
            this.nodesOfway.add(g.nodes.get(id));
        }

        public ArrayList<String> getBesides(String id) {
            ArrayList<String> ans = new ArrayList<>();
            for (int i = 0; i < nodesOfway.size(); i += 1) {
                if (((nodesOfway.get(i)).id).equals(id)) {
                    if (i > 0) {
                        ans.add((nodesOfway.get(i - 1)).id);
                    }
                    if (i < nodesOfway.size() - 1) {
                        ans.add((nodesOfway.get(i + 1)).id);
                    }
                }
            }
            return ans;
        }

    }

    public GraphDB(String dbPath) {
        this.nodes = new HashMap<>();
        this.ways = new HashMap<>();
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
//            GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     *
     * @param s Input string.
     * @return Cleaned string.
     */

    void addNode(GraphDB g, Node c) {
        g.nodes.put(c.id, c);
    }

    void addWay(Way w) {
        this.ways.put(w.id, w);
    }

    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     * Remove nodes with no connections from the graph.
     * While this does not guarantee that any two nodes in the remaining graph are connected,
     * we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        Iterator<Map.Entry<String, Node>> it = nodes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Node> entry = it.next();
            Node val = entry.getValue();
            if (!adjacent(Long.parseLong(val.id)).iterator().hasNext()) {
                it.remove();
            }
        }
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     *
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        //YOUR CODE HERE, this currently returns only an empty list.
        ArrayList<Long> ans = new ArrayList<>();
        for (String key : this.nodes.keySet()) {
            ans.add(Long.parseLong(this.nodes.get(key).id));
        }
        return ans;
    }

    /**
     * Returns ids of all vertices adjacent to v.
     *
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        ArrayList<Long> ans = new ArrayList<>();
        ArrayList<String> ansstring = new ArrayList<>();
        for (String key : (this.nodes.get(Long.toString(v)).connected)) {
            if (GraphBuildingHandler.ALLOWED_HIGHWAY_TYPES.contains(ways.get(key).extraInfo.get("highway"))) {
                ansstring = (ways.get(key)).getBesides(Long.toString(v));
                for (String item : ansstring) {
                    ans.add(Long.parseLong(item));
                }
            }
        }
        return ans;
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     *
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     *
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     *
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        double ansmin = 99999999;
        double ans;
        String ansid = null;
        for (String key : nodes.keySet()) {
            ans = distance(lon, lat, nodes.get(key).lon, nodes.get(key).lat);
            if (ans < ansmin) {
                ansmin = Math.min(ansmin, ans);
                ansid = nodes.get(key).id;
            }
        }
        return Long.parseLong(ansid);
    }

    /**
     * Gets the longitude of a vertex.
     *
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        return nodes.get(Long.toString(v)).lon;
    }

    /**
     * Gets the latitude of a vertex.
     *
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        return nodes.get(Long.toString(v)).lat;
    }
}
