package gui;

import javax.swing.*;

import gui.buttons.*;
import mazeLogic.MazeService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainFrame extends JFrame {

    private MazeFrame mazeFrame;

    private JButton loadTextButton;
    private JButton saveButton;
    private JButton findPathButton;
    private JButton selectStartButton;
    private JButton selectEndButton;

    public MainFrame() {
        setTitle("Maze Solver");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Główny panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Maze Frame z paskami przewijania
        mazeFrame = new MazeFrame(new MazeService());
        JScrollPane scrol = new JScrollPane(mazeFrame);
        mainPanel.add(scrol, BorderLayout.CENTER);

        // Panel narzędzi
        JPanel toolPanel = new JPanel(new FlowLayout());
        loadTextButton = new JButton(ButtonTexts.LoadT);
        saveButton = new JButton(ButtonTexts.Export);
        findPathButton = new JButton(ButtonTexts.FindPath);
        selectStartButton = new SetStartBtn(mazeFrame, ButtonTexts.SelectStart);
        selectEndButton = new SetEndBtn(mazeFrame, ButtonTexts.SelectEnd);
        selectEndButton.setEnabled(false);
        selectStartButton.setEnabled(false);
        saveButton.setEnabled(false);
        findPathButton.setEnabled(false);

        JButton[] buttons = {
                loadTextButton,
                saveButton,
                findPathButton,
                selectStartButton,
                selectEndButton
        };
        for (JButton jButton : buttons) {
            toolPanel.add(jButton);
            mazeFrame.buttons.add(jButton);
        }

        mainPanel.add(toolPanel, BorderLayout.NORTH);

        // Dodaj główny panel do ramki
        add(mainPanel);

        // Dodaj funkcje do przycisków
        loadTextButton.addActionListener(new LoadButtonTXT(mazeFrame));
        saveButton.addActionListener(new SaveButton(mazeFrame, new MazeService()));
        findPathButton.addActionListener(e -> mazeFrame.solveMaze());

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}
