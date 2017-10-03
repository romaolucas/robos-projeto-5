import java.util.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class VisibilityMap {

    List<Line2D> polygons = new ArrayList<>() {{
       add(new Line2D.Double(164, 356, 58, 600));
       add(new Line2D.Double(58, 600, 396, 721));
       add(new Line2D.Double(396, 721, 455, 600));
       add(new Line2D.Double(455, 600, 227, 515));
       add(new Line2D.Double(227, 515, 280, 399));
       add(new Line2D.Double(280, 399, 164, 356));
        /* Triangle */
       add(new Line2D.Double(778, 526, 1079, 748));
       add(new Line2D.Double(1079, 748, 1063, 436));
       add(new Line2D.Double(1063, 436, 778, 526));
        /* Pentagon */
       add(new Line2D.Double(503, 76, 333, 267));
       add(new Line2D.Double(333, 267, 481, 452));
       add(new Line2D.Double(481, 452, 730, 409));
       add(new Line2D.Double(730, 409, 704, 150));
       add(new Line2D.Double(704, 150, 503, 76));
    
    };

    List<Point2D> points = new ArrayList<>() {{
       add(new Point2D.Double(94,737)); /* P1 */
       add(new Point2D.Double(489,18)); /* P11 */        
    }};

}
