import java.util.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class VisibilityMap {

    public static List<Line2D> polygonLines = new ArrayList<Line2D>();

    public static List<Line2D> rectangles = new ArrayList<Line2D>() {{
       add(new Line2D.Double(16.4, 35.6, 5.8, 60.0));
       add(new Line2D.Double(5.8, 60.0, 39.6, 72.1));
       add(new Line2D.Double(39.6, 72.1, 45.5, 60.0));
       add(new Line2D.Double(45.5, 60.0, 22.7, 51.5));
       add(new Line2D.Double(22.7, 51.5, 28.0, 39.9));
       add(new Line2D.Double(28.0, 39.9, 16.4, 35.6));
    }};

    public static List<Line2D> triangle = new ArrayList<Line2D>() {{
       add(new Line2D.Double(77.8, 52.6, 107.9, 74.8));
       add(new Line2D.Double(107.9, 74.8, 106.3, 43.6));
       add(new Line2D.Double(106.3, 43.6, 77.8, 52.6));
    }};

    public static List<Line2D> pentagon = new ArrayList<Line2D>() {{
       add(new Line2D.Double(50.3, 7.6, 33.3, 26.7));
       add(new Line2D.Double(33.3, 26.7, 48.1, 45.2));
       add(new Line2D.Double(48.1, 45.2, 73.0, 40.9));
       add(new Line2D.Double(73.0, 40.9, 70.4, 15.0));
       add(new Line2D.Double(70.4, 15.0, 50.3, 7.6));
    
    }};

    public static List<Point2D> points = new ArrayList<Point2D>() {{
       add(new Point2D.Double(9.4,73.7)); /* P1 */
       add(new Point2D.Double(99.4, 8.8)); /* P10 */
       add(new Point2D.Double(48.9,1.8)); /* P11 */        
    }};

    public static List<Line2D> lines = new ArrayList<Line2D>();

    public static void addVertexesFrom(List<Line2D> polygon, Set<Point2D> points, Graph G) {
        for (Line2D polygonLine : polygon) {
            Point2D point1 = polygonLine.getP1();
            Point2D point2 = polygonLine.getP2();
            G.addVertex(new Vertex(point1));
            G.addVertex(new Vertex(point2));
            points.add(point1);
            points.add(point2);
            polygonLines.add(polygonLine);
        }
    }

    public static Graph computeGraph() {
        Graph G = new Graph();
        Set<Point2D> rectanglesPoints = new HashSet<Point2D>();
        Set<Point2D> trianglePoints = new HashSet<Point2D>();
        Set<Point2D> pentagonPoints = new HashSet<Point2D>();
        addVertexesFrom(rectangles, rectanglesPoints, G);
        addVertexesFrom(triangle, trianglePoints, G);
        addVertexesFrom(pentagon, pentagonPoints, G);
        for (Point2D point : points) {
            G.addVertex(new Vertex(point));
        }
        boolean interceptsAnyLine = false;
        for (Vertex v : G.getVertexes()) {
            for (Vertex u : G.getVertexes()) {
                if (!v.equals(u)) {
                    Point2D pointV = new Point2D.Double(v.getPosX(), v.getPosY());
                    Point2D pointU = new Point2D.Double(u.getPosX(), u.getPosY());
                    Line2D lineVU = new Line2D.Double(pointV, pointU);
                    for (Line2D polygonLine : polygonLines) {
                        /* 
                         * dois pontos nao se ligam se:
                         * i - os dois estao dentro de um dos poligonos
                         * ii - a linha entre os dois intersepta outra linha, mas nenhum deles
                         * esta contido nela
                         * */
                        boolean vIsInTheLine = polygonLine.getP1().equals(pointV) || polygonLine.getP2().equals(pointV);
                        boolean uIsInTheLine = polygonLine.getP1().equals(pointU) || polygonLine.getP2().equals(pointU);
                        if (vIsInTheLine && uIsInTheLine) {
                            interceptsAnyLine = true;
                            break;
                        }
                        if (polygonLine.intersectsLine(lineVU) && (!vIsInTheLine && !uIsInTheLine)) {
                            interceptsAnyLine = true;
                            break;
                        }
                    }
                    boolean isInRectangles = rectanglesPoints.contains(pointV) && rectanglesPoints.contains(pointU);
                    boolean isInTriangle = trianglePoints.contains(pointV) && trianglePoints.contains(pointU);
                    boolean isInPentagon = pentagonPoints.contains(pointV) && pentagonPoints.contains(pointU);
                    if (!interceptsAnyLine && (!isInRectangles && !isInTriangle && !isInPentagon)) {
                        G.addEdge(v, u);
                        lines.add(lineVU);
                    }
                    interceptsAnyLine = false;
                }
            }
        }
        return G;
    }

    public static void drawVisibilityMap() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.005);
        for (Line2D polygonLine : polygonLines) {
            Point2D point1 = polygonLine.getP1();
            Point2D point2 = polygonLine.getP2();
            StdDraw.line(point1.getX(), point1.getY(),point2.getX(), point2.getY());
        }
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.03);
        for (Point2D point : points) {
            StdDraw.point(point.getX(), point.getY());
        }
        StdDraw.setPenRadius(0.002);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (Line2D line : lines) {
            Point2D point1 = line.getP1();
            Point2D point2 = line.getP2();
            StdDraw.line(point1.getX(), point1.getY(), point2.getX(), point2.getY());
        }
    }

    public static void main(String[] args) {
        Graph G = computeGraph();
        Dijkstra dijkstra = new Dijkstra();
        dijkstra.calculateShortestPath(G, G.getVertex(points.get(2)));
        StdDraw.setXscale(0, 118.9);
        StdDraw.setYscale(0, 84.1);
        drawVisibilityMap();
        Vertex v = G.getVertex(points.get(0));
        List<Vertex> path = new ArrayList<>();
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.ORANGE);
        path.add(v);
        while (!v.equals(v.getParent())) {
            Vertex u = v.getParent();
            StdDraw.line(u.getPosX(), u.getPosY(), v.getPosX(), v.getPosY());
            path.add(u);
            v = u;
        }
        System.out.println("Caminho de P11 a P1:");
        for (int i = path.size() - 1; i >= 0; i--) {
            System.out.println(path.get(i));
        }
    }

}
