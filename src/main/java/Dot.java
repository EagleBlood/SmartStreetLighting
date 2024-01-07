import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dot{
    private static final Color DOT_COLOR = Color.RED;
    private static final int DOT_SIZE = 12;
    private final Point2D.Double position; // Current position of the dot;
    private double currentDistance = 0.0;
    private final List<Drawable> drawables;
    private Drawable currentDrawable;
    private Drawable previousDrawable;
    private boolean isReversing = false;
    private boolean shouldGetNextDrawable = false;
    private final Random random = new Random();
    private final double tolerance = DOT_SIZE / 2.0;
    private Path previousPath;
    private final double step = 1;

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

        g.dispose();
    }

    public void moveDot() {
        if (currentDrawable == null) {
            System.out.println("CurrentDrawable is null");
            return;
        }

        Double currentPosition = currentDrawable.getPosition(currentDistance);
        Double nextPosition ;

        for (Lamp lamp : currentDrawable.getLamps()) {
            lamp.activate(this);
        }


        Double endPosition = isReversing ? currentDrawable.getPosition(0) : currentDrawable.getPosition(currentDrawable.getLength());

        if (Math.abs(currentPosition.getX() - endPosition.getX()) <= tolerance && Math.abs(currentPosition.getY() - endPosition.getY()) <= tolerance) {
            shouldGetNextDrawable = true;
        }

        if(shouldGetNextDrawable) {
            List<Object> nextDrawable = getNextDrawable();
            //System.out.println("Current drawable: " + nextDrawable);
            if(nextDrawable != null && !nextDrawable.isEmpty()){
                previousDrawable = currentDrawable;
                currentDrawable = (Drawable) nextDrawable.get(0);
                isReversing = (Boolean) nextDrawable.get(1);
                currentDistance = isReversing ? currentDrawable.getLength() : 0.0;
                shouldGetNextDrawable = false;
                System.gc(); //Garbage collector
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


    public List<Object> getNextDrawable() {
        List<Drawable> potentialNextDrawables = new ArrayList<>();
        List<Boolean> shouldReverse = new ArrayList<>();

        for(Drawable drawable : drawables){
            if (drawable == currentDrawable || drawable == previousDrawable) {
                continue;
            }

            if (!isReversing) {
                if(pointsAreClose(drawable.getEntryPoint(), currentDrawable.getExitPoint(), tolerance)){
                    potentialNextDrawables.add(drawable);
                    shouldReverse.add(false);
                }
                else if(pointsAreClose(drawable.getExitPoint(), currentDrawable.getExitPoint(), tolerance)){
                    potentialNextDrawables.add(drawable);
                    shouldReverse.add(true);
                }
            } else {
                if(pointsAreClose(drawable.getEntryPoint(), currentDrawable.getEntryPoint(), tolerance)){
                    potentialNextDrawables.add(drawable);
                    shouldReverse.add(false);
                }
                else if(pointsAreClose(drawable.getExitPoint(), currentDrawable.getEntryPoint(), tolerance)){
                    potentialNextDrawables.add(drawable);
                    shouldReverse.add(true);
                }
            }
        }

        if (!potentialNextDrawables.isEmpty()) {
            int index = potentialNextDrawables.size() == 1 ? 0 : random.nextInt(potentialNextDrawables.size());
            List<Object> result = new ArrayList<>();
            result.add(potentialNextDrawables.get(index));
            result.add(shouldReverse.get(index));
            System.out.println("Potential drawables: " + potentialNextDrawables);
            System.out.println("Should reverse: " + shouldReverse);
            return result;
        }
        else{
            List<Object> result = new ArrayList<>();
            result.add(currentDrawable);
            result.add(!isReversing);
            return result;
        }
    }




    public Point2D.Double getPosition() {
        return position;
    }

    public Drawable[] getDrawables() {
        return drawables.toArray(new Drawable[0]);
    }

    private boolean pointsAreClose(Point2D.Double p1, Point2D.Double p2, double tolerance) {
        return p1.distance(p2) < tolerance;
    }

    public void setPreviousPath(Path previousPath) {
        this.previousPath = previousPath;
    }
    
    public Path getPreviousPath() {
        return previousPath;
    }

}
