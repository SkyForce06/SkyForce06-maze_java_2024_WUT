import mazeLogic.IMazeService;
import mazeLogic.MazeConstants;
import mazeLogic.MazeParser;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class MazeFrame extends JPanel {
    private MazeParser parser;
    private int cellSize;
    private IMazeService mazeService;
    private int offsetX = 0;
    private int offsetY = 0;
    private Image cachedImage;
    private List<Point> solveSteps;

    public MazeFrame(IMazeService mazeService) {
        this.mazeService = mazeService;
        setBackground(Color.WHITE);
        cellSize = 6; // Określ rozmiar komórki
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    public void loadMaze(MazeParser parser) {
        this.parser = parser;
        cachedImage = createCachedImage();
        setPreferredSize(new Dimension(parser.getCols() * cellSize, parser.getRows() * cellSize));
        revalidate(); // Konieczne odświeżenie rozmiaru
        repaint();
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

    public void solveMaze() {
        if (parser != null) {
            solveSteps = mazeService.getSolvePoints(parser); // Store the solve steps
            var maze = parser.getMaze();
            for (Point solvePoint : solveSteps) {
                maze[(int) solvePoint.getX()][(int) solvePoint.getY()] = MazeConstants.Solution;
            }
            cachedImage = createCachedImage();
            repaint();
        }
    }

    public List<Point> getSolveSteps() {
        return solveSteps;
    }

    public MazeParser getParser() {
        return parser;
    }
}
