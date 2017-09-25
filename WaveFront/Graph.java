import java.util.List;
import java.util.ArrayList;
import java.awt.geom.Line2D; 

public class Graph {

    public [][]double map;
    public int width;
    public int height;
    public int dim;

    public Graph(int height, int width, int dim) {
        this.width = width;
        this.height = height;
        this.dim = dim;
        this.map = new double[height][width];
        Line2D[] lines = {
          /* L-shape polygon */
          new Line2D(164,356,58,600),
          new Line2D(58,600,396,721),
          new Line2D(396,721,455,600),
          new Line2D(455,600,227,515),
          new Line2D(227,515,280,399),
          new Line2D(280,399,164,356),
          /* Triangle */
          new Line2D(778,526,1079,748),
          new Line2D(1079,748,1063,436),
          new Line2D(1063,436,778,526),
          /* Pentagon */
          new Line2D(503,76,333,267),
          new Line2D(333,267,481,452),
          new Line2D(481,452,730,409),
          new Line2D(730,409,704,150),
          new Line2D(704,150,503,76)
        };
        fillMapWithObstacles(lines);
        thickenLines(1);
        discretizeMap(dim);  
    }

    public void fillMapWithObstacles(Line2D[] lines) {
        for (Line2D line : lines) {
            double startX = Math.min(line.getX1(), line.getX2());
            double finalX = Math.max(line.getX1(), line.getX2());
            double m = (line.getY2() - line.getY1()) / (line.getX2() - line.getX1()); 
            for (int x = (int) startX; x <= (int) finalX; x++) {
                int y = (int) m * (x - line.getX1()) + line.getY1();
                map[y][x] = -1;
            }
        }
    }

    public void thickenLines(int radius) {
        double [][]newMap = new double[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (map[y][x] == -1) {
                    Vertex v = new Vertex(x, y);
                    newMap[y][x] = -1;
                    List<Vertex> neighbors = v.getNeighbors(4, width, height);
                    for (Vertex neighbor : neighbors) {
                        int neighborX = (int) neighbor.posX;
                        int neighborY = (int) neighbor.posY;
                        newMap[neighborY][neighborX] = -1;
                    }
                }
            }
        }

        map = newMap;

    }

    public void discretizeMap(int dim) {
        int newHeight = height / dim;
        int newWidth = width / dim;
        double [][]newMap = new double[newHeight][newWidth];
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

    public List<Vertex> linearizePath(List<Vertex> path) {

    }

}
