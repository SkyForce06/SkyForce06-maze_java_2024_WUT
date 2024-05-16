import javax.swing.*;
import java.awt.event.ActionListener;

public class FunctionButton {
    public static void loadTextButton(JButton button, MazeFrame mazeFrame, ActionListener action) {
        button.addActionListener(action);
    }

    public static void saveButton(JButton button, MazeFrame mazeFrame, ActionListener action) {
        button.addActionListener(action);
    }
}