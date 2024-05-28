package mazeLogic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class MazeService implements IMazeService {

    @Override
    public List<Point> getSolvePoints(MazeParser parser) {
        MazeGraph graph = new MazeGraph(parser.getMaze());
        Dijkstra dijkstra = new Dijkstra(graph.getGraph(), parser.getStart(), parser.getEnd());
        return dijkstra.solve();
    }

    public void readBinary() {

    }

    public void saveMaze(File file, List<Point> solvePoints, BufferedImage image) {

        switch (getFileExtension(file)) {
            case "txt":
                saveSolveStepsToFile(file, solvePoints);
                break;
            case "bin":
                break;
            case "png":
                String pngFileName = file.getAbsolutePath();
                if (!pngFileName.toLowerCase().endsWith(".png")) {
                    pngFileName += ".png";
                }
                File pngFile = new File(pngFileName);
                saveMazeAsPNG(pngFile, image);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Wrong extension type. Available types: .bin, .txt, .png");
                break;
        }
    }

    private void saveAsBinary(File file, List<Point> solvePoints) {

    }

    private void saveSolveStepsToFile(File file, List<Point> solvePoints) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            if (solvePoints != null) {
                Point current = null;
                String currentAction = null;
                int count = 0;

                for (int i = 0; i < solvePoints.size(); i++) {
                    Point step = solvePoints.get(i);
                    String action = describeAction(i, step, solvePoints);

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

    private void saveMazeAsPNG(File file, BufferedImage image) {

        try {

            ImageIO.write(image, "png", file);
            JOptionPane.showMessageDialog(null, "Maze saved as PNG successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while saving maze as PNG!");
        }
    }

    private String getFileExtension(File file) {
        if (file == null) {
            return ""; // No file provided
        }

        String fileName = file.getName();
        int lastIndexOfDot = fileName.lastIndexOf('.');

        if (lastIndexOfDot == -1 || lastIndexOfDot == 0) {
            return ""; // No extension found or dot is at the beginning of the file name
        }

        return fileName.substring(lastIndexOfDot + 1);
    }

}
