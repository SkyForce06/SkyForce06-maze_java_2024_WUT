import javax.swing.*;

import mazeLogic.MazeService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainFrame extends JFrame {

    private MazeFrame mazeFrame;

    private JButton loadTextButton;
    private JButton loadBinaryButton;
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
        loadTextButton = new JButton("Load Text");
        loadBinaryButton = new JButton("Load Binary");
        saveButton = new JButton("Save");
        findPathButton = new JButton("Find Path");
        selectStartButton = new JButton("Select Start");
        selectEndButton = new JButton("Select End");
        toolPanel.add(loadTextButton);
        toolPanel.add(loadBinaryButton);
        toolPanel.add(saveButton);
        toolPanel.add(findPathButton);
        toolPanel.add(selectStartButton);
        toolPanel.add(selectEndButton);
        mainPanel.add(toolPanel, BorderLayout.NORTH);

        // Dodaj główny panel do ramki
        add(mainPanel);

        // Dodaj funkcje do przycisków używając klasy FunctionButton
        FunctionButton.loadTextButton(loadTextButton, mazeFrame, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    mazeFrame.loadMazeFromFile(selectedFile);
                }
            }
        });
        FunctionButton.loadTextButton(findPathButton, mazeFrame, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mazeFrame.solveMaze();
            }
        });

        FunctionButton.saveButton(saveButton, mazeFrame, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    mazeFrame.saveSolveStepsToFile(selectedFile);
                }
            }
        });
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
