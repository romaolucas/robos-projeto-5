import java.util.List;
import java.util.ArrayList;    

public class Vertex implements Comparable<Vertex> {

    public double posX;

    public double posY;

    public double distToSource;

    public Vertex(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
        this.distToSource = 0.0;
    }
    
    public List<Vertex> getNeighbors(int neighborhood, int height, int width) {
        Vertex up = new Vertex(this.posX, Math.max(this.posY - 1, 0));
        Vertex down = new Vertex(this.posX, Math.min(this.posY + 1, height));
        Vertex left = new Vertex(Math.max(this.posX - 1, 0), this.posY);
        Vertex right = new Vertex(Math.min(this.posX + 1, width), this.posY);
        if (neighborhood == 4) return Arrays.asList(up, down, left, right);

        Vertex topLeft = new Vertex(Math.max(this.posX - 1, 0), Math.max(this.posY - 1, 0));
        Vertex topRight = new Vertex(Math.min(this.posX + 1, width), Math.max(this.posY - 1, 0));
        Vertex bottomLeft = new Vertex(Math.max(this.posX - 1, 0), Math.min(this.posY + 1, height));
        Vertex bottomRight = new Vertex(Math.min(this.posX + 1, width), Math.min(this.posY + 1, height));

        List<Vertex> neighbors = new ArrayList<Vertex>();
        neighbors.add(up);
        neighbors.add(down);
        neighbors.add(left);
        neighbors.add(right);
        neighbors.add(topLeft);
        neighbors.add(topRight);
        neighbors.add(bottomLeft);
        neighbors.add(bottomRight);
        
        return neighbors;
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

