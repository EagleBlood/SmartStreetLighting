import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Dot{
    private static final Color DOT_COLOR = Color.RED;
    private static final int DOT_SIZE = 12;
    private final Point2D.Double position; // Current position of the dot;
    private double currentDistance = 0.0;
    private final List<Drawable> drawables;
    private Drawable currentDrawable;
    private Drawable previousDrawable;
    private List<Object> nextDrawable;
    private boolean isReversing = false;
    private boolean shouldGetNextDrawable = false;
    private Random random = new Random();
    private double step = 1;
    private double tolerance = DOT_SIZE / 2.0;


    public Dot(Point2D.Double position, List<Drawable> drawables) {
        this.position = position;
        this.drawables = drawables;
        this.currentDrawable = getFirstDrawable();
    }

    private Drawable getFirstDrawable() {
        if (drawables == null || drawables.isEmpty()) {
            // No drawables to follow, exit.
            return null;
        }

        // Generate a random index
        //int index = random.nextInt(drawables.size());

        // Return the Drawable at the generated index
        return drawables.get(0);
    }

    public void draw(Graphics g) {
        g.setColor(DOT_COLOR);

        // Draw the dot from its center
        int drawX = (int) position.getX() - DOT_SIZE / 2;
        int drawY = (int) position.getY() - DOT_SIZE / 2;
        g.fillOval(drawX, drawY, DOT_SIZE, DOT_SIZE);

        // Move the dot on the path
        moveDot();

        ///chyba tutaj trzeba dodać sprawdzanie lampy
    }

    public void moveDot() {
        if (currentDrawable == null) {
            System.out.println("CurrentDrawable is null");
            return;
        }
    
        Double currentPosition = currentDrawable.getPosition(currentDistance);
        Double nextPosition;
    
        for (Lamp lamp : currentDrawable.getLamps()) {
            lamp.activate(this);
        }
    

        Double endPosition = isReversing ? currentDrawable.getPosition(0) : currentDrawable.getPosition(currentDrawable.getLength());
    
        if (Math.abs(currentPosition.getX() - endPosition.getX()) <= tolerance && Math.abs(currentPosition.getY() - endPosition.getY()) <= tolerance) {
            shouldGetNextDrawable = true;
        }
    
        if(shouldGetNextDrawable) {
            nextDrawable = getNextDrawable();
            if(nextDrawable != null && !nextDrawable.isEmpty()){
                previousDrawable = currentDrawable;
                currentDrawable = (Drawable) nextDrawable.get(0);
                isReversing = (Boolean) nextDrawable.get(1);
                currentDistance = isReversing ? currentDrawable.getLength() : 0.0;
                shouldGetNextDrawable = false;
            }
        }
    
        if (isReversing) {
            nextPosition = currentDrawable.getPosition(currentDistance - step);
            //System.out.println("Reversing");
        } else {
            nextPosition = currentDrawable.getPosition(currentDistance + step);
            //System.out.println("Forward");
        }
    
        if (nextPosition != null) {
            double dx = nextPosition.getX() - currentPosition.getX();
            double dy = nextPosition.getY() - currentPosition.getY();
            position.setLocation(currentPosition.getX() + dx, currentPosition.getY() + dy);
    
            if (isReversing) {
                currentDistance -= step;
            } else {
                currentDistance += step;
            }
        }
    }


    //TODO W miare działa ale trzeba ogarnąc czemu ciągle się ciągle cofa i jak zrobić żeby umiejętnie wybiełar cofanie gdy nie ma połaczeń
    public List<Object> getNextDrawable() {
        List<Drawable> potentialNextDrawables = new ArrayList<>();
        List<Boolean> shouldReverse = new ArrayList<>();
    
        for(Drawable drawable : drawables){
            //Causes endless loop when it wont find any drawable
            if (drawable == previousDrawable) {
                continue;
            }
    
            System.out.println("Drawable " + drawable + " entry point: " + drawable.getEntryPoint());
            System.out.println("Drawable " + drawable + " exit point: " + drawable.getExitPoint());
            if(drawable.getEntryPoint().equals(currentDrawable.getExitPoint())){
                potentialNextDrawables.add(drawable);
                shouldReverse.add(false);
                System.out.println("Found drawable and not shouldReverse: " + potentialNextDrawables);
            }
            else if(drawable.getExitPoint().equals(currentDrawable.getExitPoint())){
                potentialNextDrawables.add(drawable);
                shouldReverse.add(true);
                System.out.println("Found drawable and shouldReverse: " + potentialNextDrawables);
            }
        }
    
        if (!potentialNextDrawables.isEmpty()) {
            int index = potentialNextDrawables.size() == 1 ? 0 : random.nextInt(potentialNextDrawables.size());
            List<Object> result = new ArrayList<>();
            result.add(potentialNextDrawables.get(index));
            result.add(shouldReverse.get(index));
            System.out.println("Result send");
            return result;
        }
        else{
            return null;
        }
    }



    public Point2D.Double getPosition() {
        return position;
    }

    public Drawable[] getDrawables() {
        return drawables.toArray(new Drawable[0]);
    }

}
