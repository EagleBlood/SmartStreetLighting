import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class Lamp {
    private final Point2D.Double position;
    private Graphics2D g2d;
    private boolean active = false;
    private static  Color LAMP_COLOR = Color.YELLOW;
    private static final Color LAMP_LIT_COLOR = Color.RED;
    private static final int LAMP_SIZE = 5;
    private static final int LAMP_RADIUS = 15;
    private static final int LAMP_STROKE = 1;
    private final int radius; // Variable radius instead of a fixed constant
    private final Color color; // Variable color


    // Constructor for WidePath lamps
     public Lamp(Point2D.Double position, int radius, Color color) {
        this.position = position;
        this.radius = radius;
        this.color = color;
    }

    // Constructor for regular path lamps
    public Lamp(Point2D.Double position) {
        this(position, LAMP_RADIUS, LAMP_COLOR); // Default radius and color
    }

    public void draw(Graphics g) {
        g2d = (Graphics2D) g;

        // Draw the lamp with the instance color and size
        g2d.setColor(this.color);
        int drawX = (int) position.getX() - LAMP_SIZE; // Half of the default LAMP_SIZE
        int drawY = (int) position.getY() - LAMP_SIZE; // Half of the default LAMP_SIZE
        g2d.fill(new Ellipse2D.Double(drawX, drawY, 10, 10)); // Default LAMP_SIZE
    }

    public void activate(Dot dot) {

        if (SettingsPanel.instance != null) {
            active = dot != null && isDotInRange(dot) && isTimeInRange(SettingsPanel.getCurrentTime());

            if (active) {
                // When active, draw the effective lighting area using instance radius\
                if (SettingsPanel.getCurrentSeason().equals("Winter")) {
                    g2d.setColor(Color.BLUE);
//                g2d.draw(new Ellipse2D.Double(position.getX() - this.radius, position.getY() - this.radius, this.radius * 3, this.radius * 3));
                    //activateAdjacentLamps(dot);
                } else {
                    g2d.setColor(LAMP_LIT_COLOR); // Active color
                }

                g2d.setStroke(new BasicStroke(LAMP_STROKE)); // Default stroke for active area
                g2d.draw(new Ellipse2D.Double(position.getX() - this.radius, position.getY() - this.radius, this.radius * 2, this.radius * 2));
            }
        }

        // Activate lamps +1 and -1 during winter season

    }



    private boolean isTimeInRange(String currentTime) {
        // Convert time to int for comparison
        int currentHour = Integer.parseInt(currentTime.split(":")[0]);

        String selectedWeather = SettingsPanel.getCurrentWeather();
        String selectedSeason = SettingsPanel.getCurrentSeason();

        if (selectedWeather.equals("Sun")) {
            if (selectedSeason.equals("Spring") || selectedSeason.equals("Autumn")) {
                return (currentHour >= 18 && currentHour <= 23) || (currentHour >= 0 && currentHour < 5);
            } else if (selectedSeason.equals("Summer")) {
                return (currentHour >= 21 && currentHour <= 23) || (currentHour >= 0 && currentHour < 4);
            } else if (selectedSeason.equals("Winter")) {
                return (currentHour >= 16 && currentHour <= 23) || (currentHour >= 0 && currentHour < 7);
            }
        } else if (selectedWeather.equals("Precipitation")) {
            if (selectedSeason.equals("Spring") || selectedSeason.equals("Autumn")) {
                return (currentHour >= 17 && currentHour <= 23) || (currentHour >= 0 && currentHour < 6);
            } else if (selectedSeason.equals("Summer")) {
                return (currentHour >= 20 && currentHour <= 23) || (currentHour >= 0 && currentHour < 4);
            } else if (selectedSeason.equals("Winter")) {
                return (currentHour >= 15 && currentHour <= 23) || (currentHour >= 0 && currentHour < 7);
            }
        }

        return false;
    }

    private boolean isDotInRange(Dot dot) {
        double distance = position.distance(dot.getPosition());
        return distance <= this.radius; // Use the instance variable 'radius'
    }

    public Point2D.Double getPosition() {
        return position;
    }

}
