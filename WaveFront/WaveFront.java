import java.util.Comparator;
import java.util.Queue;
import java.util.LinkedList;
import java.awt.geom.Line2D;


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
    }

    public static void main(String[] args) {
        Graph G = new Graph(1189, 841, 10);
        double startX = 94 / 10;
        double startY = 737 / 10;
        Queue<Vertex> q = new LinkedList<Vertex>();
        q.add(new Vertex(startX, startY));
        while (!q.isEmpty()) {
            Vertex v = q.poll();
            int vX = v.posX;
            int vY = v.posY;
            List<Vertex> neighbors = v.getNeighbors(4, G.width, G.height);
            for (Vertex neighbor : neighbors) {
                int x = neighbor.posX;
                int y = neighbor.posY;
                if (G.map[y][x] != 0) continue;
                G.map[y][x] = 1 + G.map[vY][vX];
                q.add(neighbor);
            }
        }


        // int k = 0;
        // while (parent[k] != k) {
        //     System.out.println(k);
        //     k = parent[k];
        // }
        // System.out.println(k);

    }

}
