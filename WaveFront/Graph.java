import java.util.List;
import java.util.ArrayList;
import java.awt.geom.Line2D; 
import java.awt.geom.Point2D;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;

public class Graph {

    public double[][] map;
    public int width;
    public int height;
    public int dim;
    public Line2D.Double[] lines;

    public Graph(int width, int height, int dim, int radius, Line2D.Double[] lines) {
        this.width = width;
        this.height = height;
        this.dim = dim;
        this.map = new double[height][width];
        for (int i = 0; i < height; i ++) {
            for (int j = 0; j < width; j++) {
                this.map[i][j] = 0;
            }
        }

        this.lines = lines;
        fillMapWithObstacles(lines);
        discretizeMap(dim);  
        if (radius > 0) {
           thickenLines(radius);
        }
    }

    public void fillMapWithObstacles(Line2D.Double[] lines) {
        for (Line2D.Double line : lines) {
            double startX = Math.min(line.getX1(), line.getX2());
            double finalX = Math.max(line.getX1(), line.getX2());
            double m = (line.getY2() - line.getY1()) / (line.getX2() - line.getX1()); 
            for (int x = (int) startX; x <= (int) finalX; x++) {
                int y = (int) (m * (x - line.getX1()) + line.getY1());
                map[y][x] = -1;
            }
        }
    }

    public void thickenLines(int radius) {
        double newMap[][] = new double[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (map[y][x] == -1) {
                    Vertex v = new Vertex(x, y);
                    newMap[y][x] = -1;
                    List<Vertex> neighbors = v.getNeighbors(4, width, height);
                    for (int k = Math.max(0, x - radius); k < Math.min(width - 1, x + radius); k++) {
                        for (int l = Math.max(0, y - radius); l < Math.min(height - 1, y + radius); l++) {
                            newMap[l][k] = -1;
                        }
                    }
                }
            }
        }

        map = newMap;

    }

    public void discretizeMap(int dim) {
        int newHeight = height / dim;
        int newWidth = width / dim;
        double newMap[][] = new double[newHeight][newWidth];
        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                newMap[y][x] = 0;
            }
        }


        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                for (int y1 = y * dim; y1 < (y * dim) + dim; y1++) {
                    for (int x1 = x * dim; x1 < (x * dim) + dim; x1++) {
                        if (map[y1][x1] == -1) newMap[y][x] = -1;
                    }
                }
            }
        }

        height = newHeight;
        width = newWidth;
        map = newMap;
    }

    public Queue<Vertex> linearizePath(Stack<Vertex> path) {
        System.out.println("Caminho antes de linearizar com tamanho: " + path.size());
        Queue<Vertex> finalPath = new LinkedList<Vertex>();
        boolean intersectsAnyLine = false;
        Vertex v = path.pop();
        Vertex lastNonIntersectingVertex = v;
        Vertex u;
        int i = 0;
        finalPath.add(v);
        while (!path.isEmpty()) {
            u = path.pop();
            Point2D pointV = new Point2D.Double(v.posX * dim, v.posY * dim);
            Point2D pointU = new Point2D.Double(u.posX * dim, u.posY * dim);
            // System.out.println(" pontos: " + pointV + " e " + pointU);
            Line2D pathLine = new Line2D.Double(pointV, pointU);

            for (Line2D line : lines) {
                if (line.intersectsLine(pathLine)) {
                    intersectsAnyLine = true;
                    break;
                }
            }
            if (!intersectsAnyLine) {
                i++;
                lastNonIntersectingVertex = u;
            }
            else {
                i = 0;
                // System.out.println(" nunca aqui?");
                finalPath.add(lastNonIntersectingVertex);
                v = lastNonIntersectingVertex;
            }
            intersectsAnyLine = false;
        }
        finalPath.add(lastNonIntersectingVertex);
        System.out.println("Caminho depois de linearizar com tamanho: " + finalPath.size());;

        return finalPath;
    }

}
