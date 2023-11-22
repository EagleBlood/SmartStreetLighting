import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class ButtonAction {
    private Timer timer;
    private final JPanel canvasPanel;
    List<Path> userPathList = new ArrayList<>();


    public ButtonAction(Timer timer, JPanel canvasPanel) {
        this.timer = timer;
        this.canvasPanel = canvasPanel;
    }

    public ActionListener StartCanvas() {
        return e -> {
            if (!timer.isRunning()) {
                timer = new Timer(10, e1 -> {
                    canvasPanel.repaint();
                });
                timer.start();
            }

        };
    }

    public ActionListener StopCanvas() {
        return e -> {
            if (timer.isRunning()) {
                timer.stop();
            }
        };
    }
}