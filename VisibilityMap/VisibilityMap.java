import java.util.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class VisibilityMap {

    public static List<Line2D> polygons = new ArrayList<Line2D>() {{
       add(new Line2D.Double(164, 356, 58, 600));
       add(new Line2D.Double(58, 600, 396, 721));
       add(new Line2D.Double(396, 721, 455, 600));
       add(new Line2D.Double(455, 600, 227, 515));
       add(new Line2D.Double(227, 515, 280, 399));
       add(new Line2D.Double(280, 399, 164, 356));
        /* Triangle */
       add(new Line2D.Double(778, 526, 1079, 748));
       add(new Line2D.Double(1079, 748, 1063, 436));
       add(new Line2D.Double(1063, 436, 778, 526));
        /* Pentagon */
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

    public static Graph computeGraph() {
        Graph G = new Graph();
        for (Line2D polygonLine : polygons) {
            G.addVertex(new Vertex(polygonLine.getP1()));
            G.addVertex(new Vertex(polygonLine.getP2()));
        }
        for (Point2D point : points) {
            G.addVertex(new Vertex(point));
        }
        boolean interceptsAnyLine = false;
        for (int i = 0; i < G.getV(); i++) {
            for (int j = 0; j < G.getV(); j++) {
                if (i != j) {
                    Vertex v = G.getVertex(i);
                    Vertex u = G.getVertex(j);
                    Point2D pointV = new Point2D.Double(v.getPosX(), v.getPosY());
                    Point2D pointU = new Point2D.Double(u.getPosX(), u.getPosY());
                    Line2D lineVU = new Line2D.Double(pointV, pointU);
                    for (Line2D polygonLine : polygons) {
                        /* 
                         * dois pontos nao se ligam se:
                         * i - os dois estao dentro de um dos poligonos
                         * ii - a linha entre os dois intersepta outra linha, mas nenhum deles
                         * esta contido nela
                         * */
                        if (polygonLine.contains(pointV) && polygonLine.contains(pointU)) {
                            interceptsAnyLine = true;
                            break;
                        }
                        if (polygonLine.intersectsLine(lineVU) && (!polygonLine.contains(pointV) && !polygonLine.contains(pointU))) {
                            interceptsAnyLine = true;
                            break;
                        }
                    }
                    if (!interceptsAnyLine) {
                        G.addEdge(i, j);
                        lines.add(lineVU);
                    }
                }
            }
        }
        return G;
    }

    public static void main(String[] args) {
        Graph G = computeGraph();
    }

}
