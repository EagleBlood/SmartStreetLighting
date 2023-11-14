import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.List;

public class Path implements Drawable{
    private static final Color PATH_COLOR = Color.GRAY;
    private static final int PATH_THICKNESS = 20;
    private static final int LAMP_ACTIVE_STROKE = 1;
    private final List<Lamp> lamps = new ArrayList<>();
    private final Path2D.Float path2D;
    private double lastX;
    private double lastY;
    private final Point2D.Double exitPoint;



    public Path(Path2D.Float path2D) {
        this.path2D = path2D;
        Point2D endPoint2D = getEndPoint();
        this.exitPoint = new Point2D.Double(endPoint2D.getX(), endPoint2D.getY());
        updateLamps();
    }

    void updateLamps() {
        lamps.clear();
        double totalPathLength = getLength();
        double distance = 0.0;
        double interval = 100.0; // Interval between lamps

        while (distance < totalPathLength) {
            double[] pos = getPointAtLength(distance);
            lamps.add(new Lamp(new Point2D.Double(pos[0], pos[1])));
            distance += interval;
        }
    }



    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Set the color and thickness for the path
        g2d.setColor(PATH_COLOR);
        g2d.setStroke(new BasicStroke(PATH_THICKNESS, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        // Draw the path
        g2d.draw(path2D);

        // Draw the lamps
        for (Lamp lamp : lamps) {
            g2d.setStroke(new BasicStroke(LAMP_ACTIVE_STROKE));
            lamp.draw(g2d);
        }
    }


    //For lamps generation
    public double getLength() {
        PathIterator pathIterator = path2D.getPathIterator(null);
        double[] coords = new double[6];
        double length = 0;
        while (!pathIterator.isDone()) {
            int segmentType = pathIterator.currentSegment(coords);
            switch (segmentType) {
                case PathIterator.SEG_MOVETO:
                    lastX = coords[0];
                    lastY = coords[1];
                    break;
                case PathIterator.SEG_LINETO:
                    double x = coords[0];
                    double y = coords[1];
                    length += Math.sqrt((x - lastX) * (x - lastX) + (y - lastY) * (y - lastY));
                    lastX = x;
                    lastY = y;
                    break;
                case PathIterator.SEG_QUADTO:
                    double x1 = coords[0];
                    double y1 = coords[1];
                    double x2 = coords[2];
                    double y2 = coords[3];
                    length += Math.sqrt((x1 - lastX) * (x1 - lastX) + (y1 - lastY) * (y1 - lastY));
                    length += Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
                    lastX = x2;
                    lastY = y2;
                    break;
                case PathIterator.SEG_CUBICTO:
                    double x3 = coords[0];
                    double y3 = coords[1];
                    double x4 = coords[2];
                    double y4 = coords[3];
                    double x5 = coords[4];
                    double y5 = coords[5];
                    length += Math.sqrt((x3 - lastX) * (x3 - lastX) + (y3 - lastY) * (y3 - lastY));
                    length += Math.sqrt((x4 - x3) * (x4 - x3) + (y4 - y3) * (y4 - y3));
                    length += Math.sqrt((x5 - x4) * (x5 - x4) + (y5 - y4) * (y5 - y4));
                    lastX = x5;
                    lastY = y5;
                    break;
            }
            pathIterator.next();
        }
        return (int) Math.round(length);
    }

    //For dot movement
    public double[] getPointAtLength(double distance) {
        PathIterator pathIterator = path2D.getPathIterator(null);
        double[] coords = new double[6];
        double totalLength = 0;
        double lastX = 0;
        double lastY = 0;
        while (!pathIterator.isDone()) {
            int segmentType = pathIterator.currentSegment(coords);
            switch (segmentType) {
                case PathIterator.SEG_MOVETO:
                    lastX = coords[0];
                    lastY = coords[1];
                    break;

                case PathIterator.SEG_LINETO:
                    double x = coords[0];
                    double y = coords[1];
                    double segmentLength = Math.sqrt((x - lastX) * (x - lastX) + (y - lastY) * (y - lastY));
                    if (totalLength + segmentLength >= distance) {
                        double remainingLength = distance - totalLength;
                        double ratio = remainingLength / segmentLength;
                        double[] point = new double[2];
                        point[0] = (float) (lastX + ratio * (x - lastX));
                        point[1] = (float) (lastY + ratio * (y - lastY));
                        return point;
                    }
                    totalLength += segmentLength;
                    lastX = x;
                    lastY = y;
                    break;

                    case PathIterator.SEG_QUADTO:
                        // handle quadratic curve segment
                        break;

                    case PathIterator.SEG_CUBICTO:
                        // handle cubic curve segment
                        break;

            }
            pathIterator.next();
        }
        return null;
    }


    @Override
    public Point2D.Double getExitPoint() {
        return exitPoint;
    }

    public Point2D.Double getEndPoint() {
        return new Point2D.Double(lastX, lastY);
    }

    @Override
    public Double getEntryPoint() {
        return new Point2D.Double(path2D.getBounds2D().getMinX(), path2D.getBounds2D().getMinY());
    }

    @Override
    public Double getPosition(double distance) {
        if (path2D == null || distance < 0) {
            // Return null if the path or distance is invalid
            return null;
        }

        PathIterator pathIterator = path2D.getPathIterator(null);
        double[] coords = new double[6];
        double remainingDistance = distance;
        double lastX = 0;
        double lastY = 0;

        while (!pathIterator.isDone()) {
            int segmentType = pathIterator.currentSegment(coords);

            switch (segmentType) {
                case PathIterator.SEG_MOVETO:
                    // Set the last position to the starting point of the segment
                    lastX = coords[0];
                    lastY = coords[1];
                    break;

                case PathIterator.SEG_LINETO:
                    double x = coords[0];
                    double y = coords[1];
                    double segmentLength = Math.sqrt((x - lastX) * (x - lastX) + (y - lastY) * (y - lastY));

                    // Check if the remaining distance is within the current segment
                    if (segmentLength >= remainingDistance) {
                        // Calculate the ratio of the remaining distance to the segment length
                        double ratio = remainingDistance / segmentLength;

                        // Calculate the new position based on the remaining distance and the segment ratio
                        double newX = lastX + ratio * (x - lastX);
                        double newY = lastY + ratio * (y - lastY);

                        // Return the new position
                        return new Point2D.Double(newX, newY);
                    }

                    // Update the remaining distance
                    remainingDistance -= segmentLength;

                    // Update the last position
                    lastX = x;
                    lastY = y;
                    break;

                case PathIterator.SEG_CLOSE:
                    // Handle close segment if needed
                    break;

                // Add cases for other segment types if needed
            }

            // Move to the next segment
            pathIterator.next();
        }

        return null;
    }


    @Override
    public double getDistanceTo(Drawable drawable) {
        Point2D.Double thisExitPoint = this.getExitPoint();
        Point2D.Double otherEntryPoint = drawable.getEntryPoint();

        double dx = thisExitPoint.getX() - otherEntryPoint.getX();
        double dy = thisExitPoint.getY() - otherEntryPoint.getY();

        return Math.sqrt(dx * dx + dy * dy);
    }

    public List<Lamp> getLamps() {
        return lamps;
    }
}
