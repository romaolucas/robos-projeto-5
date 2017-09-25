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
        this.adjList = new ArrayList<>();
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

    public void addEdge(Edge e) {
        adjList.add(e);
    }

    public List<Edge> getAdjList() {
        return adjList;
    }

    public int inDegree() {
        return adjList.size();
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

