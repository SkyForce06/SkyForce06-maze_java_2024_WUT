package gui.buttons;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import gui.MazeFrame;

public class SetEndBtn extends JButton {
    private MazeFrame frame;

    public SetEndBtn(MazeFrame frame,String text) {
        super(text);
        this.frame = frame;
    }

    @Override
    protected void fireActionPerformed(ActionEvent event) {
        // Call the custom click method
        frame.setNewEndPoint();

        // Call super to ensure standard behavior like notifying listeners
        super.fireActionPerformed(event);
    }
}
