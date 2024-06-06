package mazeLogic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class MazeService implements IMazeService {

    // Metoda do uzyskania punktów rozwiązania labiryntu
    @Override
    public List<Point> getSolvePoints(MazeParser parser) {
        MazeGraph graph = new MazeGraph(parser.getMaze()); // Tworzenie grafu z labiryntu
        Dijkstra dijkstra = new Dijkstra(graph.getGraph(), parser.getStart(), parser.getEnd()); // Inicjalizacja
                                                                                                // algorytmu Dijkstry
        return dijkstra.solve(); // Rozwiązanie labiryntu
    }

    // Metoda do zapisywania labiryntu do pliku
    public void saveMaze(File file, List<Point> solvePoints, BufferedImage image) {
        switch (getFileExtension(file)) { // Sprawdzanie rozszerzenia pliku
            case "txt":
                saveSolveStepsToFile(file, solvePoints); // Zapisanie kroków rozwiązania do pliku tekstowego
                break;
            case "bin":
                File binFile = new File(file.getAbsolutePath());
                saveAsBinary(binFile, solvePoints);
                break;
            case "png":
                String pngFileName = file.getAbsolutePath();
                if (!pngFileName.toLowerCase().endsWith(".png")) {
                    pngFileName += ".png";
                }
                File pngFile = new File(pngFileName);
                saveMazeAsPNG(pngFile, image); // Zapisanie labiryntu jako PNG
                break;
            case "":
                break;
            default:
                JOptionPane.showMessageDialog(null, "Wrong extension type. Available types: .bin, .txt, .png");
                break;
        }
    }

    // Metoda do zapisu danych binarnych
    private void saveAsBinary(File file, List<Point> solvePoints) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            DataOutputStream dos = new DataOutputStream(fos);
            Random rand = new Random();
            dos.writeInt(rand.nextInt(100000));
            int countOfSteps = 0;
            // dos.writeByte(solvePoints.size());
            ByteBuffer buffer = ByteBuffer.allocate(solvePoints.size() * 2);
            if (solvePoints != null) {
                Point current = null;
                String currentAction = null;
                int count = 0;

                for (int i = 0; i < solvePoints.size(); i++) {
                    Point step = solvePoints.get(i);
                    String action = describeAction(i, step, solvePoints);

                    if (current == null || !action.equals(currentAction)) {
                        if (current != null) {

                            var seq = describeSequence(count, action);
                            var identificator = getActionIdentificator(seq);
                            if (currentAction != "Starting from point") {
                                buffer.putInt(count);
                                buffer.putChar(identificator);
                                countOfSteps++;
                            }

                        }
                        current = step;
                        currentAction = action;
                        count = 1;
                    } else {
                        count++;
                    }
                }
                if (current != null) {
                    var seq = describeSequence(count, currentAction);
                    var identificator = getActionIdentificator(seq);
                    buffer.putInt(count);
                    buffer.putChar(identificator);
                    countOfSteps++;
                }
                dos.writeInt(countOfSteps);
                dos.write(buffer.array());
                JOptionPane.showMessageDialog(null, "Solve steps saved successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No maze solved to save steps!");
            }
        } catch (IOException e) {
            file.delete();
            e.printStackTrace();
        }
    }

    // Metoda do zapisywania kroków rozwiązania do pliku tekstowego
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

    // Metoda opisująca akcję podjętą przez użytkownika w labiryncie
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
            return describeDirection(dx, "GO SOUTH", "GO NORTH");
        } else {
            return describeDirection(dy, "GO EAST", "GO WEST");
        }
    }

    // Metoda opisująca kierunek ruchu w labiryncie
    private String describeDirection(int steps, String positive, String negative) {
        String direction = steps > 0 ? positive : negative;
        steps = Math.abs(steps);
        return steps == 1 ? direction : steps + " steps " + direction;
    }

    // Metoda opisująca sekwencję ruchów w labiryncie
    private String describeSequence(int count, String action) {
        return count + " " + action;
    }

    // Metoda do zapisywania labiryntu jako PNG
    private void saveMazeAsPNG(File file, BufferedImage image) {
        try {
            ImageIO.write(image, "png", file);
            JOptionPane.showMessageDialog(null, "Maze saved as PNG successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while saving maze as PNG!");
        }
    }

    public static char getActionIdentificator(String sentence) {
        String upperCaseSentence = sentence.toUpperCase();
        int index = upperCaseSentence.indexOf("GO");

        if (index != -1 && index + 2 < upperCaseSentence.length()) {
            for (int i = index + 2; i < upperCaseSentence.length(); i++) {
                if (Character.isLetter(upperCaseSentence.charAt(i))) {
                    return upperCaseSentence.charAt(i);
                }
            }
        }
        return '\0';
    }

    // Metoda do uzyskania rozszerzenia pliku
    private String getFileExtension(File file) {
        if (file == null) {
            return ""; // Brak pliku
        }

        String fileName = file.getName();
        int lastIndexOfDot = fileName.lastIndexOf('.');

        if (lastIndexOfDot == -1 || lastIndexOfDot == 0) {
            return ""; // Brak rozszerzenia lub kropka na początku nazwy pliku
        }

        return fileName.substring(lastIndexOfDot + 1);
    }
}
