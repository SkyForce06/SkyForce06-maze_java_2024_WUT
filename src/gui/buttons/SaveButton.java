package gui.buttons;

import gui.*;
import mazeLogic.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

public class SaveButton implements ActionListener {
    private MazeFrame mazeFrame;

    private IMazeService mazeService;

    // Konstruktor SaveButton, który przyjmuje obiekt klasy MazeFrame.
    public SaveButton(MazeFrame mazeFrame, IMazeService service) {
        this.mazeFrame = mazeFrame;
        mazeService = service;
    }

    // Metoda obsługująca zdarzenie naciśnięcia przycisku.
    @Override
    public void actionPerformed(ActionEvent e) {

        FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Txt file (.txt)", "txt");
        FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("Photo (.png)", "png");
        FileNameExtensionFilter binFilter = new FileNameExtensionFilter("Binary file (.bin)", "bin");

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooser.addChoosableFileFilter(txtFilter);
        fileChooser.addChoosableFileFilter(pngFilter);
        fileChooser.addChoosableFileFilter(binFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        mazeService.saveMaze(fileChooser.getSelectedFile(), mazeFrame.getSolveSteps(), mazeFrame.getCachedImage());

    }

}