import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.List;

public interface Drawable {
    void draw(Graphics g);
    List<Lamp> getLamps();
    Point2D.Double getExitPoint();
    Double getEntryPoint();
    double getLength();
    Double getPosition(double d);
    double getDistanceTo(Drawable drawable);
    String getName();
}
