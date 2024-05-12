import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class MazeFrame extends JPanel {
    private char[][] maze;
    private int cellSize;

    public MazeFrame() {
        setBackground(Color.WHITE);
        cellSize = 7; // Określ rozmiar komórki

    }


    public void loadMazeFromFile(File file) {
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            int rows = lines.size();
            int cols = lines.get(0).length();
            maze = new char[rows][cols];
            for (int i = 0; i < rows; i++) {
                String line = lines.get(i);
                for (int j = 0; j < cols; j++) {
                    maze[i][j] = line.charAt(j);
                }
            }
            setPreferredSize(new Dimension(cols * cellSize, rows * cellSize)); // Ustaw preferowany rozmiar
            revalidate(); // Konieczne odświeżenie rozmiaru
            repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (maze != null) {
            int rows = maze.length;
            int cols = maze[0].length;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    switch (maze[i][j]) {
                        case 'P':
                            g.setColor(Color.GREEN); // Start position
                            break;
                        case 'K':
                            g.setColor(Color.RED); // End position
                            break;
                        case 'X':
                            g.setColor(Color.BLACK); // Wall
                            break;
                        default:
                            g.setColor(Color.WHITE); // Path
                            break;
                    }
                    g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                    g.setColor(Color.BLACK);
                    g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
                }
            }
        }
    }
}

