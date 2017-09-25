import java.util.Comparator;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
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
        int dim = 10;
        StdDraw.setXscale(0, 1189 / dim);
        StdDraw.setYscale(0, 841 / dim);
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        Graph G = new Graph(1189, 841, dim);
        double startX = 113 / dim;
        double startY = 35 / dim;

        for (int y = 0; y < G.height; y++) {
            for (int x = 0; x < G.width; x++) {
                if (G.map[y][x] == -1) {
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.point(x, y);
                } 
            }
        }

        Queue<Vertex> q = new LinkedList<Vertex>();
        q.add(new Vertex(startX, startY));
        while (!q.isEmpty()) {
            Vertex v = q.poll();
            int vX = (int) v.posX;
            int vY = (int) v.posY;
            List<Vertex> neighbors = v.getNeighbors(4, G.width, G.height);
            for (Vertex neighbor : neighbors) {
                int x = (int) neighbor.posX;
                int y = (int) neighbor.posY;
                if (G.map[y][x] != 0) continue;
                G.map[y][x] = 1 + G.map[vY][vX];
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.point(x, y);
                q.add(neighbor);
                if (x == 9 && y == 73) {
                    q.clear();
                    break;
                }
            }
        }
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.point(9, 73);


        // int k = 0;
        // while (parent[k] != k) {
        //     System.out.println(k);
        //     k = parent[k];
        // }
        // System.out.println(k);

    }

}
