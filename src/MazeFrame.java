import mazeLogic.IMazeService;
import mazeLogic.MazeConstants;
import mazeLogic.MazeParser;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MazeFrame extends JPanel {
    private MazeParser parser;
    private int cellSize;
    private IMazeService mazeService;
    private int offsetX = 0;
    private int offsetY = 0;
    private Image cachedImage;

    public MazeFrame(IMazeService mazeService) {
        this.mazeService = mazeService;
        setBackground(Color.WHITE);
        cellSize = 6; // Określ rozmiar komórki
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        // addMouseListener(new MouseAdapter() {

        // public void mouseClicked(MouseEvent e) {
        // // Calculate which cell was clicked
        // if (maze != null) {
        // int col = e.getX() / cellSize;
        // int row = e.getY() / cellSize;

        // // Ensure the click is within the bounds of the maze
        // if (row >= 0 && row < maze.length && col >= 0 && col < maze[0].length) {
        // char cell = maze[row][col];
        // // Perform an action based on the cell that was clicked
        // handleCellClick(row, col, cell);
        // }
        // }
        // }
        // });
    }

    public void loadMazeFromFile(File file) {
        try {
            parser = new MazeParser(file.getAbsolutePath());
            cachedImage = createCachedImage();
            setPreferredSize(new Dimension(parser.getCols() * cellSize, parser.getRows() * cellSize));
            revalidate(); // Konieczne odświeżenie rozmiaru
            repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void solveMaze() {
        if (parser != null) {
            var solvePoints = mazeService.getSolvePoints(parser);
            var maze = parser.getMaze();
            for (Point solvePoint : solvePoints) {
                maze[(int) solvePoint.getX()][(int) solvePoint.getY()] = MazeConstants.Solution;
            }
            cachedImage = createCachedImage();
            repaint();
        }
    }

    private void handleCellClick(int row, int col, char cell) {
        // Example action: display a message with the cell's coordinates and value
        JOptionPane.showMessageDialog(this, "Clicked cell at (" + row + ", " + col + ") with value: " + cell);
    }
    private Image createCachedImage() {
        Image image = new BufferedImage(parser.getCols() * cellSize, parser.getRows() * cellSize, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        if (parser != null) {
            int rows = parser.getRows();
            int cols = parser.getCols();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    switch (parser.getMaze()[i][j]) {
                        case MazeConstants.Start:
                            g.setColor(Color.GREEN); // Początkowa pozycja
                            break;
                        case MazeConstants.End:
                            g.setColor(Color.RED); // Końcowa pozycja
                            break;
                        case MazeConstants.Wall:
                            g.setColor(Color.BLACK); // Ściana
                            break;
                        case MazeConstants.Solution:
                            g.setColor(Color.CYAN); // Ścieżka
                            break;
                        default:
                            g.setColor(Color.WHITE); // Ścieżka
                            break;
                    }
                    g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                    g.setColor(Color.BLACK);
                    g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
                }
            }
        }
        g.dispose();
        return image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (cachedImage != null) {
            g.drawImage(cachedImage, -offsetX, -offsetY, null);
        }
    }

    @Override
    public void scrollRectToVisible(Rectangle aRect) {
        super.scrollRectToVisible(aRect);
        offsetX = aRect.x;
        offsetY = aRect.y;
        repaint();
    }
}
