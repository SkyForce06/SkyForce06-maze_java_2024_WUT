package gui.buttons;

import gui.*;
import mazeLogic.MazeParser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class LoadButtonTXT implements ActionListener {
    private MazeFrame mazeFrame;

    public LoadButtonTXT(MazeFrame mazeFrame) {
        this.mazeFrame = mazeFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser(".");
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                MazeParser parser = new MazeParser(selectedFile.getAbsolutePath());
                mazeFrame.loadMaze(parser);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error loading maze file!");
            }
        }
    }
}
