import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Lamp {
    private final Point2D.Double position;
    private Graphics2D g2d;
    private boolean active = false;
    private static  Color LAMP_COLOR = Color.YELLOW;
    private static final Color LAMP_LIT_COLOR = Color.RED;
    private static final int LAMP_SIZE = 5;
    private static final int LAMP_STROKE = 1;
    private final int radius; // Variable radius instead of a fixed constant

    public Lamp(Point2D.Double position, char roadCategory) {
        this.position = position;
        switch (roadCategory) {
            case 'A':
                this.radius = 15;
                break;
            case 'B':
                this.radius = 30;
                break;
            default:
                this.radius = 10; // default radius
                break;
        }
    }

    public void draw(Graphics g) {
        g2d = (Graphics2D) g;

        g2d.setColor(LAMP_COLOR);
        int drawX = (int) position.getX() - LAMP_SIZE; // Half of the default LAMP_SIZE
        int drawY = (int) position.getY() - LAMP_SIZE; // Half of the default LAMP_SIZE
        g2d.fill(new Ellipse2D.Double(drawX, drawY, 10, 10)); // Default LAMP_SIZE
    }

    /*public void activate(Dot dot) {

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

    }*/

    public void activate(Dot dot) {
        if (SettingsPanel.instance != null) {
            active = dot != null && isDotInRange(dot) && isTimeInRange(SettingsPanel.getCurrentTime());
            if (active) {
                // When active, draw the effective lighting area using instance radius
                if (SettingsPanel.getCurrentSeason().equals("Winter")) {
                    g2d.setColor(Color.BLUE);
                } else {
                    g2d.setColor(LAMP_LIT_COLOR); // Active color
                }
                    
                g2d.setStroke(new BasicStroke(LAMP_STROKE)); // Default stroke for active area
                
                // Draw the area for the current lamp
                g2d.draw(new Ellipse2D.Double(position.getX() - this.radius, position.getY() - this.radius, this.radius * 2, this.radius * 2));
        
                // Draw the area for the lamp ahead
                List<Lamp> lamps = Path.getAllLamps();
                int currentIndex = lamps.indexOf(this);
                if (currentIndex < lamps.size() - 1) {
                    Lamp nextLamp = lamps.get(currentIndex + 1);
                    nextLamp.drawArea();
                }
                
                // Draw the area for the lamp behind
                if (currentIndex > 0) {
                    Lamp previousLamp = lamps.get(currentIndex - 1);
                    previousLamp.drawArea();
                }
            }
        }
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

    public boolean isActive() {
        return active;
    }

    public void drawArea() {
         if (SettingsPanel.getCurrentSeason().equals("Winter")) {
            g2d.setColor(Color.BLUE);
        } else {
            g2d.setColor(LAMP_LIT_COLOR); // Active color
        }

        g2d.setStroke(new BasicStroke(LAMP_STROKE));
        g2d.draw(new Ellipse2D.Double(position.getX() - this.radius, position.getY() - this.radius, this.radius * 2, this.radius * 2));
    }

}