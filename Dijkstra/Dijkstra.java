import java.util.*;
import java.awt.geom.*;

public class Dijkstra {

    public static Comparator<Vertex> vertexComparator = new Comparator<Vertex>() {

        @Override
        public int compare(Vertex v, Vertex w) {
            return v.compareTo(w);
        }

    };

    public void initDistances(Graph G) {
        for (Vertex v: G.getVertexes()) {
            v.setDistToSource(Double.MAX_VALUE);            
        }
    }

    public static List<Line2D> polygons = new ArrayList<Line2D>() {{
       add(new Line2D.Double(16.4, 35.6, 5.8, 60.0));
       add(new Line2D.Double(5.8, 60.0, 39.6, 72.1));
       add(new Line2D.Double(39.6, 72.1, 45.5, 60.0));
       add(new Line2D.Double(45.5, 60.0, 22.7, 51.5));
       add(new Line2D.Double(22.7, 51.5, 28.0, 39.9));
       add(new Line2D.Double(28.0, 39.9, 16.4, 35.6));
       add(new Line2D.Double(77.8, 52.6, 107.9, 74.8));
       add(new Line2D.Double(107.9, 74.8, 106.3, 43.6));
       add(new Line2D.Double(106.3, 43.6, 77.8, 52.6));
       add(new Line2D.Double(50.3, 7.6, 33.3, 26.7));
       add(new Line2D.Double(33.3, 26.7, 48.1, 45.2));
       add(new Line2D.Double(48.1, 45.2, 73.0, 40.9));
       add(new Line2D.Double(73.0, 40.9, 70.4, 15.0));
       add(new Line2D.Double(70.4, 15.0, 50.3, 7.6));
    
    }};


    public static void drawPolygons() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.005);
        for (Line2D line : polygons) {
            Point2D p1 = line.getP1();
            Point2D p2 = line.getP2();
            StdDraw.line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        }
    }

    public void calculateShortestPath(Graph G, Vertex r) {
        Queue<Vertex> pq = new PriorityQueue<>(11, vertexComparator);
        initDistances(G);
        r.setDistToSource(0);
        r.setParent(r);
        pq.add(r);
        while (!pq.isEmpty()) {
            Vertex v = pq.poll();
            for (int i = 0; i < v.inDegree(); i++) {
                Edge vu = v.getAdjList().get(i);
                Vertex u = vu.getDestination();
                if (v.getDistToSource() + vu.getWeight() < u.getDistToSource()) {
                    u.setDistToSource(v.getDistToSource() + vu.getWeight());
                    u.setParent(v);
                    if (pq.contains(u)) {
                        pq.remove(u);
                        pq.add(u);
                    } else {
                        pq.add(u);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Graph G = new Graph(11);
        int[] parent = new int[11];
        Dijkstra dijkstra = new Dijkstra();
        StdDraw.setXscale(0, 118.9);
        StdDraw.setYscale(0, 84.1);
        StdDraw.setPenRadius(0.03);
        StdDraw.setPenColor(StdDraw.BLACK);
        Vertex[] vertexes = new Vertex[] {
            new Vertex(9.4, 73.7),
            new Vertex(42.8, 80.0),
            new Vertex(114.7, 81.1),
            new Vertex(112.7, 35.5),
            new Vertex(83.1, 43.0),
            new Vertex(69.3, 49.4),
            new Vertex(44.9, 51.5),
            new Vertex(26.0, 27.1),
            new Vertex(53.4, 30.3),
            new Vertex(99.4, 8.8),
            new Vertex(48.9, 1.8)
        };
        for (int i = 0; i < vertexes.length; i++) {
            G.addVertex(vertexes[i]);
            StdDraw.point(vertexes[i].getPosX(), vertexes[i].getPosY());
        }
        G.addEdge(vertexes[0], vertexes[1]);
        G.addEdge(vertexes[0], vertexes[2]);
        G.addEdge(vertexes[1], vertexes[2]);
        G.addEdge(vertexes[1], vertexes[5]);
        G.addEdge(vertexes[2], vertexes[6]);
        G.addEdge(vertexes[2], vertexes[3]);
        G.addEdge(vertexes[3], vertexes[4]);
        G.addEdge(vertexes[3], vertexes[9]);
        G.addEdge(vertexes[3], vertexes[5]);
        G.addEdge(vertexes[4], vertexes[5]);
        G.addEdge(vertexes[4], vertexes[6]);
        G.addEdge(vertexes[4], vertexes[9]);
        G.addEdge(vertexes[5], vertexes[6]);
        G.addEdge(vertexes[6], vertexes[7]);
        G.addEdge(vertexes[7], vertexes[10]);
        G.addEdge(vertexes[9], vertexes[10]);
        dijkstra.calculateShortestPath(G, vertexes[10]);
        StdDraw.setPenRadius(0.005);
        drawPolygons();
        for (Vertex v : G.getVertexes()) {
            for (Edge e : v.getAdjList()) {
                Vertex u = e.getDestination();
                StdDraw.line(v.getPosX(), v.getPosY(), u.getPosX(), u.getPosY());
            }
        }
        Vertex v = G.getVertex(vertexes[0]);
        List<Vertex> path = new ArrayList<>();
        path.add(v);
        StdDraw.setPenColor(StdDraw.RED);
        while (!v.equals(v.getParent())) {
            Vertex u = v.getParent();
            StdDraw.line(u.getPosX(), u.getPosY(), v.getPosX(), v.getPosY());
            path.add(u);
            v = u;
        }
        System.out.println("Caminho de P11 a P1: ");
        for (int i = path.size() - 1; i >= 0; i--) {
            System.out.println(path.get(i));
        }
    }

}
