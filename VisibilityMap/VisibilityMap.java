import java.util.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class VisibilityMap {

    public static List<Line2D> polygonLines = new ArrayList<Line2D>();

    public static List<Line2D> rectangles = new ArrayList<Line2D>() {{
       add(new Line2D.Double(164, 356, 58, 600));
       add(new Line2D.Double(58, 600, 396, 721));
       add(new Line2D.Double(396, 721, 455, 600));
       add(new Line2D.Double(455, 600, 227, 515));
       add(new Line2D.Double(227, 515, 280, 399));
       add(new Line2D.Double(280, 399, 164, 356));
    }};

    public static List<Line2D> triangle = new ArrayList<Line2D>() {{
       add(new Line2D.Double(778, 526, 1079, 748));
       add(new Line2D.Double(1079, 748, 1063, 436));
       add(new Line2D.Double(1063, 436, 778, 526));
    }};

    public static List<Line2D> pentagon = new ArrayList<Line2D>() {{
       add(new Line2D.Double(503, 76, 333, 267));
       add(new Line2D.Double(333, 267, 481, 452));
       add(new Line2D.Double(481, 452, 730, 409));
       add(new Line2D.Double(730, 409, 704, 150));
       add(new Line2D.Double(704, 150, 503, 76));
    
    }};

    public static List<Point2D> points = new ArrayList<Point2D>() {{
       add(new Point2D.Double(94,737)); /* P1 */
       add(new Point2D.Double(489,18)); /* P11 */        
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
        System.out.println("Pontos no retangulo: " + rectanglesPoints.size());
        System.out.println("Pontos no triangulo: " + trianglePoints.size());
        System.out.println("Pontos no pentagono: " + pentagonPoints.size());
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

    public static void main(String[] args) {
        Graph G = computeGraph();
        StdDraw.setXscale(0, 1189);
        StdDraw.setYscale(0, 841);
        StdDraw.setPenRadius(0.001);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (Line2D polygonLine : polygonLines) {
            Point2D point1 = polygonLine.getP1();
            Point2D point2 = polygonLine.getP2();
            StdDraw.line(point1.getX(), point1.getY(),point2.getX(), point2.getY());
        }
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.005);
        for (Point2D point : points) {
            StdDraw.point(point.getX(), point.getY());
        }
        StdDraw.setPenRadius(0.001);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (Line2D line : lines) {
            Point2D point1 = line.getP1();
            Point2D point2 = line.getP2();
            StdDraw.line(point1.getX(), point1.getY(), point2.getX(), point2.getY());
        }
    }

}
