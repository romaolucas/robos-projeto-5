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

    public static void init(Graph G) {
        G.addVertex(new Vertex(9.4, 73.7));
        G.addVertex(new Vertex(42.8, 80.0));
        G.addVertex(new Vertex(114.7, 81.1));
        G.addVertex(new Vertex(112.7, 35.5));
        G.addVertex(new Vertex(83.1, 43.0));
        G.addVertex(new Vertex(69.3, 49.4));
        G.addVertex(new Vertex(44.9, 51.5));
        G.addVertex(new Vertex(26.0, 27.1));
        G.addVertex(new Vertex(53.4, 30.3));
        G.addVertex(new Vertex(99.4, 8.8));
        G.addVertex(new Vertex(48.9, 1.8));
        G.addEdge(0, 1);
        G.addEdge(0, 2);
        G.addEdge(1, 2);
        G.addEdge(1, 5);
        G.addEdge(2, 6);
        G.addEdge(2, 3);
        G.addEdge(3, 4);
        G.addEdge(3, 9);
        G.addEdge(3, 5);
        G.addEdge(4, 5);
        G.addEdge(4, 6);
        G.addEdge(4, 9);
        G.addEdge(5, 6);
        G.addEdge(6, 7);
        G.addEdge(7, 10);
        G.addEdge(9, 10);
    }

    public static void initDistances(Graph G) {
        for (int i = 1; i < G.getV(); i++) {
            G.getVertex(i).setDistToSource(Double.MAX_VALUE);            
        }
    }


    public static void main(String[] args) {
        Graph G = new Graph(11);
        int[] parent = new int[11];
        Queue<Vertex> pq = new PriorityQueue<>(11, vertexComparator);
        init(G);
        initDistances(G);
        parent[0] = 0;
        pq.add(G.getVertex(0));
        while (!pq.isEmpty()) {
            Vertex v = pq.poll();
            System.out.println("To olhando vertice " + v);
            for (int i = 0; i < v.inDegree(); i++) {
                Edge vu = v.getAdjList().get(i);
                System.out.println("Olhando a aresta: " + vu);
                Vertex u = vu.getDestination();
                if (v.getDistToSource() + vu.getWeight() < u.getDistToSource()) {
                    u.setDistToSource(v.getDistToSource() + vu.getWeight());
                    int idx = G.getVertexes().indexOf(u);
                    parent[idx] = G.getVertexes().indexOf(v);
                    if (pq.contains(u)) {
                        pq.remove(u);
                        pq.add(u);
                    } else {
                        pq.add(u);
                    }
                }
            }
        }

        for (int i = 0; i < 11; i++) {
            Vertex v = G.getVertex(i);
            System.out.println("Vertice: " + v + " distancia do inicio: " + v.getDistToSource());
            System.out.println("Pai do vertice: " + G.getVertex(parent[i]));
        }

    }

}