import java.util.Set;
import java.util.HashSet;
import java.awt.geom.Point2D;

public class Graph {

    private int V;
    
    private Set<Vertex> vertexes;


    public Graph() {
        V = 0;
        vertexes = new HashSet<>();
    }

    public Graph(int V) {
        this.V = V;
        vertexes = new HashSet<>();
    }

    public Graph(Set<Vertex> vertexes) {
        this.V = vertexes.size();
        this.vertexes = vertexes;
    }

    public void addVertex(Vertex v) {
        this.vertexes.add(v);
        V++;
    }

    public void addEdge(Vertex v, Vertex w) {
        double deltaX, deltaY;
        deltaX = v.getPosX() - w.getPosX();
        deltaY = v.getPosY() - w.getPosY();
        double dist = Math.sqrt(deltaX*deltaX + deltaY*deltaY);
        v.addEdge(new Edge(v, w, dist));
        w.addEdge(new Edge(w, v, dist));
    }

    public Set<Vertex> getVertexes() {
        return vertexes;
    }

    public Vertex getVertex(Point2D point) {
        for (Vertex v : vertexes) {
            if (v.is(point)) {
                return v;
            }
        }
        return null;
    }

    public int getV() {
        return V;
    }


}
