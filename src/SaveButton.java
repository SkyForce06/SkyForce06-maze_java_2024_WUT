import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.awt.Point;

public class SaveButton implements ActionListener {
    private MazeFrame mazeFrame;

    public SaveButton(MazeFrame mazeFrame) {
        this.mazeFrame = mazeFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            saveSolveStepsToFile(selectedFile);
        }
    }

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

    private String describeDirection(int steps, String positive, String negative) {
        String direction = steps > 0 ? positive : negative;
        steps = Math.abs(steps);
        return steps == 1 ? direction : steps + " steps " + direction;
    }

    private String describeSequence(int count, String action) {
        return count + " " + action;
    }
}

