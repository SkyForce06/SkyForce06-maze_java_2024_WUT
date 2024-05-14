import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FunctionButton {
    public static void loadTextButton(JButton button, MazeFrame mazeFrame,ActionListener action) {

        button.addActionListener(action);
    }

}
