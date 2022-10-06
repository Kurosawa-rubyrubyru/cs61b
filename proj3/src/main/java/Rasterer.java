import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    public Rasterer() {

    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     * <p>
     * The grid of images must obey the following properties, where image in the
     * grid is referred to as a "tile".
     * <ul>
     *     <li>The tiles collected must cover the most longitudinal distance per pixel
     *     (LonDPP) possible, while still covering less than or equal to the amount of
     *     longitudinal distance per pixel in the query box for the user viewport size. </li>
     *     <li>Contains all tiles that intersect the query bounding box that fulfill the
     *     above condition.</li>
     *     <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     * </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     * forget to set this to true on success! <br>
     */

    /*
    {lrlon=-122.2104604264636, ullon=-122.30410170759153, w=1085.0, h=566.0, ullat=37.870213571328854, lrlat=37.8318576119893}
    这意味着用户想要由经度 -122.2104604264636 和 -122.30410170759153 和纬度 37.870213571328854 和 37.8318576119893 ，
    并且他们希望它们显示在大约 1085 x 566 像素大小的窗口中. 我们将用户在地球上想要的显示位置称为 查询框 。
     */

    /*
    有四个常量定义了世界地图的坐标，全部在 MapServer.java. 第一个是 ROOT_ULLAT，它告诉我们地图左上角的纬度。 第二个是 ROOT_ULLON，它告诉我们地图
    左上角的经度。相似地， ROOT_LRLAT和 ROOT_LRLON给出地图右下角的经纬度。 给定图块覆盖的所有坐标称为该图块的“边界框”。
    因此，例如，单个深度为 0 的图像 d0_x0_y0涵盖由给出的坐标 ROOT_ULLAT, ROOT_ULLON, ROOT_LRLAT， 和 ROOT_LRLON.
    另一个重要常数 MapServer.java是 TILE_SIZE. 这很重要，因为我们需要它来计算图像文件的 LonDPP。 对于深度 0 的瓦片，LonDPP 只是
     (ROOT_LRLON - ROOT_ULLON)/TILE_SIZE，即经度单位数除以像素数。
     */
    private boolean check(Map<String, Double> params) {
        if (params.get("lrlat") > MapServer.ROOT_ULLAT) return false;
        if (params.get("lrlon") < MapServer.ROOT_ULLON) return false;
        if (params.get("ullon") > MapServer.ROOT_LRLON) return false;
        if (params.get("ullat") < MapServer.ROOT_LRLAT) return false;
        if (params.get("ullat") < params.get("lrlat")) return false;
        if (params.get("ullon") > params.get("lrlon")) return false;
        return true;
    }

    private int getDepth(Map<String, Double> params) {
        Double goalDpp = (params.get("lrlon") - params.get("ullon")) / ((double) (params.get("w")));
        Double nowDpp = ((Double) (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON)) / (((Integer) (MapServer.TILE_SIZE)).doubleValue());
        for (int i = 0; i <= 7; i += 1) {
            if (nowDpp < goalDpp) {
                return i;
            }
            nowDpp /= 2;
        }
        return 7;
    }

    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        results.put("query_success", check(params));
        results.put("depth", getDepth(params));
        Double lonDpp = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (256.0 * Math.pow(2, (Integer) results.get("depth")));
        Double latDpp = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / (256.0 * Math.pow(2, (Integer) results.get("depth")));

        Double leftpp = (params.get("ullon") - MapServer.ROOT_ULLON) / lonDpp;
        Double rightpp = (params.get("lrlon") - MapServer.ROOT_ULLON) / lonDpp;
        Double uppp = (MapServer.ROOT_ULLAT - params.get("ullat")) / latDpp;
        Double downpp = (MapServer.ROOT_ULLAT - params.get("lrlat")) / latDpp;

        Integer leftpos = leftpp.intValue() / 256;
        Integer rightpos = rightpp.intValue() / 256;
        Integer uppos = uppp.intValue() / 256;
        Integer downpos = downpp.intValue() / 256;

        results.put("raster_ul_lon", MapServer.ROOT_ULLON + (256.0 * leftpos * lonDpp));
        results.put("raster_lr_lon", MapServer.ROOT_ULLON + (256.0 * (rightpos + 1) * lonDpp));
        results.put("raster_ul_lat", MapServer.ROOT_ULLAT - (256.0 * uppos * latDpp));
        results.put("raster_lr_lat", MapServer.ROOT_ULLAT - (256.0 * (downpos + 1) * latDpp));

        String[][] ans = new String[downpos - uppos + 1][rightpos - leftpos + 1];
        for (Integer x = leftpos; x <= rightpos; x += 1) {
            for (Integer y = uppos; y <= downpos; y += 1) {
                ans[y - uppos][x - leftpos] = ("d" + results.get("depth").toString() + "_x" + x.toString() + "_y" + y.toString() + ".png");
            }
        }
        results.put("render_grid", ans);

//        System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
//                + "your browser.");
        return results;
    }

}
