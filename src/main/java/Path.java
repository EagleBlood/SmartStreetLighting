import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Path implements Drawable{
    private static final Color MAIN_COLOR = Color.BLACK;
    private static final Color SUBMAIN_COLOR = Color.GRAY;
    private static final Color PATH_COLOR = Color.RED;


    public static final int PATH_THICKNESS = 20;
    public final List<Lamp> lamps = new ArrayList<>();
    public static List<List<Lamp>> allLamps = new ArrayList<>();
    public final Path2D.Float path2D;
    private double lastX;
    private double lastY;
    private final Point2D.Double exitPoint;
    public List<Drawable> connectedDrawables;
    protected boolean shouldDrawLamps = true;
    char roadCategory;
    int lampInterval;
    int lampCount;
    String streetName;

    public Path(Path2D.Float path2D, int lampInterval, int lampCount, char roadCategory, String streetName) {
        this.path2D = path2D;
        Point2D endPoint2D = getEndPoint();
        this.exitPoint = new Point2D.Double(endPoint2D.getX(), endPoint2D.getY());
        this.lampInterval = lampInterval;
        this.lampCount = lampCount;
        this.roadCategory = roadCategory;
        this.streetName = streetName;
    }

    public void updateLamps() {
        lamps.clear();
        double pathLength = getLength();

        if (lampCount == 1) {
            // If there's only one lamp, place it at the center of the path
            Point2D.Double lampPosition = getPosition(pathLength / 2);
            if (lampPosition != null) {
                lamps.add(new Lamp(lampPosition, roadCategory));
            }
        } else {
            // If there are more than one lamps, place them at intervals along the path
            double totalIntervalLength = (lampCount - 1) * lampInterval;
            double remainingLength = pathLength - totalIntervalLength;
            double startDistance = remainingLength / 2;

            for (int i = 0; i < lampCount; i++) {
                double lampPositionDistance = startDistance + i * lampInterval;
                Point2D.Double lampPosition = getPosition(lampPositionDistance);
                if (lampPosition != null) {
                    lamps.add(new Lamp(lampPosition, roadCategory));
                }
            }
        }

        allLamps.add(lamps);
    }

    @Override
    public void draw(Graphics g) {
        // Draw the name in the center of the drawable
        Graphics2D g2d = (Graphics2D) g;
        FontMetrics fm = g2d.getFontMetrics();
        double textWidth = fm.getStringBounds(this.getName(), g2d).getWidth();
        
        // Calculate the center of the path
        double centerX = (this.getEntryPoint().getX() + this.getEndPoint().getX()) / 2;
        double centerY = (this.getEntryPoint().getY() + this.getEndPoint().getY()) / 2;

        // Calculate the angle of the path
        double angle = Math.atan2(this.getEndPoint().getY() - this.getEntryPoint().getY(), this.getEndPoint().getX() - this.getEntryPoint().getX());

        // Calculate the offset
        double offset = 25; // You can adjust this value as needed
        double offsetX = offset * Math.cos(angle + Math.PI / 2);
        double offsetY = offset * Math.sin(angle + Math.PI / 2);

        // Adjust the center of the path
        centerX += offsetX;
        centerY += offsetY;

        // Rotate the Graphics2D object
        g2d.rotate(angle, centerX, centerY);

        // Draw the name
        g2d.setColor(Color.BLACK);
        g2d.drawString(this.getName(), (int) (centerX - textWidth / 2), (int) centerY);

        // Rotate the Graphics2D object back
        g2d.rotate(-angle, centerX, centerY);
        
        switch(roadCategory)
        {
            case 'A':
            {
                g2d.setColor(MAIN_COLOR);
                g2d.setStroke(new BasicStroke(PATH_THICKNESS, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                break;
            }
            case 'B':
            {
                g2d.setColor(SUBMAIN_COLOR);
                g2d.setStroke(new BasicStroke(PATH_THICKNESS, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                break;
            }
            default:
            {
                g2d.setColor(PATH_COLOR);
                g2d.setStroke(new BasicStroke(PATH_THICKNESS, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                break;
            }
        }
        
        
        g2d.draw(path2D); // Draw the path itself

        for (Lamp lamp : this.getLamps()) {
            lamp.draw(g); // Draw the lamps
        }
    }
        protected boolean shouldDrawLampAtPosition(Point2D.Double position) {
            return true; // By default, a lamp should be drawn at every position
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
        PathIterator pathIterator = path2D.getPathIterator(null);
        double[] coords = new double[6];
        double lastX = 0;
        double lastY = 0;
        while (!pathIterator.isDone()) {
            int segmentType = pathIterator.currentSegment(coords);
            if (segmentType == PathIterator.SEG_MOVETO || segmentType == PathIterator.SEG_LINETO) {
                lastX = coords[0];
                lastY = coords[1];
            }
            pathIterator.next();
        }
        return new Point2D.Double(lastX, lastY);
    }

    @Override
    public Double getEntryPoint() {
        PathIterator pathIterator = path2D.getPathIterator(null);
        double[] coords = new double[6];
        if (!pathIterator.isDone()) {
            pathIterator.currentSegment(coords);
            return new Point2D.Double(coords[0], coords[1]);
        }
        return null;
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

        // If distance exceeds the path length, return the position at the end of the path
        return new Point2D.Double(lastX, lastY);
    }

    public void setConnectedDrawables(List<Drawable> connectedDrawables) {
        this.connectedDrawables = connectedDrawables;
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

    public void initializeAfterSettingDrawables() {
        updateLamps();
    }

    public static List<Lamp> getAllLamps() {
        List<Lamp> allLampsFlat = new ArrayList<>();
        for (List<Lamp> lampList : allLamps) {
            allLampsFlat.addAll(lampList);
        }
        return allLampsFlat;
    }

    @Override
    public String getName() {
        return this.streetName;
    }
}

