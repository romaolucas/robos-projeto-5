import java.util.Comparator;
import java.util.Queue;
import java.util.PriorityQueue;

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
        dijkstra.calculateShortestPath(G, vertexes[0]);
        for (Vertex v : G.getVertexes()) {
            System.out.println("Vertice: " + v + " distancia do inicio: " + v.getDistToSource());
            System.out.println("Pai do vertice: " + v.getParent());
        }

    }

}
