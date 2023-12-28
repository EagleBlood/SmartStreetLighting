import javax.swing.*;


public class ButtonAction {
    private Timer timerCloak;
    private Timer timerDot;


    public ButtonAction(Timer timerDot, Timer timerCloak) {
        this.timerDot = timerDot;
        this.timerCloak = timerCloak;
    }

    public void startCanvas() {
        if (!timerDot.isRunning() && !timerCloak.isRunning()) {
            timerDot.start();

            if (timerCloak != null && !timerCloak.isRunning()) {
                timerCloak.start();
            }
        }
    }

    public void stopCanvas() {
        if (timerDot.isRunning() && timerCloak.isRunning()) {
            timerDot.stop();
            timerCloak.stop();
        }
    }

}
