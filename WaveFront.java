import java.util.Comparator;
import java.util.Queue;
import java.util.LinkedList;

public class WaveFront {

    public static Comparator<Vertex> vertexComparator = new Comparator<Vertex>() {

        @Override
        public int compare(Vertex v, Vertex w) {
            return v.compareTo(w);
        }

    };

    public static void init(Graph G, double cellSize, int w, int h) {
        int width = w;
        int height = h;

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

        for (double i = cellSize / 2; i < width; i += cellSize) {
            for (double j = cellSize / 2; j < height; j += cellSize) {
                G.addVertex(new Vertex(i, j));
            }
        }
    }

    public static void initDistances(Graph G) {
        for (int i = 0; i < G.getV() - 1; i++) {
            G.getVertex(i).setDistToSource(Double.MAX_VALUE);            
        }
    }


    public static void main(String[] args) {
        Graph G = new Graph(11);
        int[] parent = new int[11];
        for (int i = 0; i < 11; i++) parent[i] = -1;
        Queue<Vertex> q = new LinkedList<Vertex>();
        init(G, 5.0, 30, 30);
        initDistances(G);
        parent[10] = 10;
        q.add(G.getVertex(10));
        while (!q.isEmpty()) {
            Vertex v = q.poll();
            for (int i = 0; i < v.inDegree(); i++) {
                Edge vu = v.getAdjList().get(i);
                Vertex u = vu.getDestination();
                int idx = G.getVertexes().indexOf(u);
                if (parent[idx] != -1) continue;
                u.setDistToSource(v.getDistToSource() + vu.getWeight());
                parent[idx] = G.getVertexes().indexOf(v);
                q.add(u);
            }
        }
        int k = 0;
        while (parent[k] != k) {
            System.out.println(k);
            k = parent[k];
        }
        System.out.println(k);

    }

}
