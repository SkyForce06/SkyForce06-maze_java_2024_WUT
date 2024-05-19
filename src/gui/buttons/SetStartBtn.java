package gui.buttons;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import gui.MazeFrame;

public class SetStartBtn extends JButton {
    private MazeFrame frame;

    public SetStartBtn(MazeFrame frame, String text) {
        super(text);
        this.frame = frame;
    }

    @Override
    protected void fireActionPerformed(ActionEvent event) {
        // Call the custom click method
        frame.setNewStartPoint();

        // Call super to ensure standard behavior like notifying listeners
        super.fireActionPerformed(event);
    }

}
