package gui.buttons;

import gui.*;
import mazeLogic.*;
import javax.swing.*;
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

    // Konstruktor SaveButton, który przyjmuje obiekt klasy MazeFrame.
    public SaveButton(MazeFrame mazeFrame) {
        this.mazeFrame = mazeFrame;
    }

    // Metoda obsługująca zdarzenie naciśnięcia przycisku.
    @Override
    public void actionPerformed(ActionEvent e) {
        String[] options = {"SaveTXT", "SaveBin", "SavePNG"};
        int choice = JOptionPane.showOptionDialog(null, "Choose save option", "Save",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice != -1) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                switch (options[choice]) {
                    case "SaveTXT":
                        saveSolveStepsToFile(selectedFile);
                        break;
                    case "SaveBin":
                        break;
                    case "SavePNG":
                        String pngFileName = selectedFile.getAbsolutePath();
                        if (!pngFileName.toLowerCase().endsWith(".png")) {
                            pngFileName += ".png";
                        }
                        File pngFile = new File(pngFileName);
                        boolean withPath = mazeFrame.getSolveSteps() != null;
                        saveMazeAsPNG(pngFile, withPath);
                        break;
//                      boolean withPath = mazeFrame.getSolveSteps() != null;
//                      saveMazeAsPNG(selectedFile, withPath);
//                      break;
                }
            }
        }
    }

    // Metoda zapisująca kroki rozwiązania labiryntu do pliku TXT.
    private void saveSolveStepsToFile(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            List<Point> solveSteps = mazeFrame.getSolveSteps();
            if (solveSteps != null) {
                Point current = null;
                String currentAction = null;
                int count = 0;

                for (int i = 0; i < solveSteps.size(); i++) {
                    Point step = solveSteps.get(i);
                    String action = describeAction(i, step, solveSteps);

                    if (current == null || !action.equals(currentAction)) {
                        if (current != null) {
                            writer.write(describeSequence(count, currentAction));
                            writer.newLine();
                        }
                        current = step;
                        currentAction = action;
                        count = 1;
                    } else {
                        count++;
                    }
                }
                if (current != null) {
                    writer.write(describeSequence(count, currentAction));
                    writer.newLine();
                }

                JOptionPane.showMessageDialog(null, "Solve steps saved successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No maze solved to save steps!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while saving solve steps!");
        }
    }

    // Metoda opisująca akcję podjętą przez użytkownika w labiryncie.
    private String describeAction(int index, Point current, List<Point> steps) {
        if (index == 0) {
            return "Starting from point";
        }
        Point previous = steps.get(index - 1);
        int dx = current.x - previous.x;
        int dy = current.y - previous.y;

        if (dx == 0 && dy == 0) {
            return "No movement from point";
        } else if (dx != 0) {
            return describeDirection(dx, "GO DOWN", "GO UP");
        } else {
            return describeDirection(dy, "GO RIGHT", "GO LEFT");
        }
    }

    // Metoda opisująca kierunek ruchu w labiryncie.
    private String describeDirection(int steps, String positive, String negative) {
        String direction = steps > 0 ? positive : negative;
        steps = Math.abs(steps);
        return steps == 1 ? direction : steps + " steps " + direction;
    }

    // Metoda opisująca sekwencję ruchów w labiryncie.
    private String describeSequence(int count, String action) {
        return count + " " + action;
    }

    // Metoda zapisująca labirynt jako plik PNG.
    private void saveMazeAsPNG(File file, boolean withPath) {
        MazeParser parser = mazeFrame.getParser();
        int cellSize = mazeFrame.getCellSize();

        try {
            BufferedImage image = new BufferedImage(parser.getCols() * cellSize, parser.getRows() * cellSize, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();

            if (parser != null) {
                int rows = parser.getRows();
                int cols = parser.getCols();
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        if (withPath && parser.getMaze()[i][j] == MazeConstants.Solution) {
                            g.setColor(Color.CYAN); // Path color
                        } else {
                            switch (parser.getMaze()[i][j]) {
                                case MazeConstants.Start:
                                    g.setColor(Color.GREEN); // Start position
                                    break;
                                case MazeConstants.End:
                                    g.setColor(Color.RED); // End position
                                    break;
                                case MazeConstants.Wall:
                                    g.setColor(Color.BLACK); // Wall
                                    break;
                                default:
                                    g.setColor(Color.WHITE); // Path
                                    break;
                            }
                        }
                        g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                        g.setColor(Color.BLACK);
                        g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
                    }
                }
            }
            g.dispose();
            ImageIO.write(image, "png", file);
            JOptionPane.showMessageDialog(null, "Maze saved as PNG successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while saving maze as PNG!");
        }
    }

}