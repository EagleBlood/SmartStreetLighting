public class Lamp {

    int x;
    private static final int LAMP_RADIUS = 60;

    Lamp(int x) {
        this.x = x;
    }

    boolean isActive() {
        for (Main.Point dot : dots) {
            if (checkActivation(dot)) {
                return true;
            }
        }
        return false;
    }

    boolean checkActivation(Main.Point dot) {
        if (Math.abs(dot.x - x) <= LAMP_RADIUS) {
            dot.inLampRange = true;
            return true;
        }
        dot.inLampRange = false;
        return false;
    }
}
