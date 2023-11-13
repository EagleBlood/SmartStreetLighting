import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public interface Drawable {
    void draw(Graphics g);
    void update();
    Point2D.Double getExitPoint();
    Double getEntryPoint();
    double getLength();
    Double getPosition(double d);
    boolean isEntryPoint(Double position);
    boolean isExitPoint(Double position);
    double getDistanceTo(Drawable drawable);
}