import javax.swing.*;
import java.awt.event.ActionListener;


public class ButtonAction {
    private Timer timerCloak;
    private Timer timerDot;


    public ButtonAction(Timer timerDot, Timer timerCloak) {
        this.timerDot = timerDot;
        this.timerCloak = timerCloak;
    }

    public ActionListener startCanvas() {
        return e -> {
            if (!timerDot.isRunning() && !timerCloak.isRunning()) {
                timerDot.start();
                
                if (timerCloak != null && !timerCloak.isRunning()) {
                    timerCloak.start();
                }
            }
        };
    }

    public ActionListener stopCanvas() {
        return e -> {
            if (timerDot.isRunning() && timerCloak.isRunning()) {
                timerDot.stop();
                timerCloak.stop();
            }
        };
    }
}