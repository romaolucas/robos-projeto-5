import java.util.List;
import java.util.ArrayList;

public class Vertex implements Comparable<Vertex> {

    private double posX;

    private double posY;

    private double distToSource;

    private List<Edge> adjList;

    public Vertex(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
        this.distToSource = 0.0;
    }

    public void setDistToSource(double distToSource) {
        this.distToSource = distToSource;
    }

    public double getDistToSource() {
        return distToSource;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public List<Vertex> getNeighbors(int neighborhood) {
        Vertex up = new Vertex(this.posX, this.posY - 1);
        Vertex down = new Vertex(this.posX, this.posY + 1);
        Vertex left = new Vertex(this.posX - 1, this.posY);
        Vertex right = new Vertex(this.posX + 1, this.posY);
        
        Vertex topLeft = new Vertex(this.posX - 1, this.posY - 1);
        Vertex topRight = new Vertex(this.posX + 1, this.posY - 1);
        Vertex bottomLeft = new Vertex(this.posX - 1, this.posY + 1);
        Vertex bottomRight = new Vertex(this.posX + 1, this.posY + 1);

        List<Vertex> neighbors = new ArrayList<Vertex>();
        neighbors.add(up);
        neighbors.add(down);
        neighbors.add(left);
        neighbors.add(right);
        if (neighborhood > 4) {
            neighbors.add(topLeft);
            neighbors.add(topRight);
            neighbors.add(bottomLeft);
            neighbors.add(bottomRight);
        }
        return vertexes;
    }

    public int compareTo(Vertex w) {
        if (this.distToSource < w.getDistToSource())
            return -1;
        if (this.distToSource > w.getDistToSource())
            return 1;
        return 0;
    }

    @Override
    public String toString() {
        return "(" + posX + ", " + posY + ")";
    }

}

