package gui;

import mazeLogic.IMazeService;
import mazeLogic.MazeConstants;
import mazeLogic.MazeParser;

import javax.swing.*;
import gui.buttons.ButtonTexts;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MazeFrame extends JPanel {
    private MazeParser parser; // Parser do wczytywania labiryntu
    private int cellSize; // Rozmiar pojedynczej komórki labiryntu
    private IMazeService mazeService; // Serwis do obsługi labiryntu
    private int offsetX = 0; // Przesunięcie w osi X
    private int offsetY = 0; // Przesunięcie w osi Y
    private Boolean blockClick = false; // Flaga blokująca kliknięcia
    private BufferedImage cachedImage; // Buforowany obraz labiryntu

    private Point clickedPoint; // Punkt kliknięcia
    private Point newStartPoint; // Nowy punkt startowy
    private Point newEndPoint; // Nowy punkt końcowy
    private List<Point> solveSteps; // Lista kroków rozwiązania labiryntu
    public ArrayList<JButton> buttons; // Lista przycisków interfejsu

    // Metoda ustawiająca nowy punkt startowy
    public void setNewStartPoint() {
        if (clickedPoint != null) {
            newStartPoint = clickedPoint;
            repaintOnePixel(parser.getStart(), MazeConstants.Path);
            parser.resetStart(newStartPoint);
            repaintOnePixel(parser.getStart(), MazeConstants.Start);
            setButtonEnabled(ButtonTexts.SelectStart, false);
            setButtonEnabled(ButtonTexts.SelectEnd, false);
        }
    }

    // Metoda ustawiająca nowy punkt końcowy
    public void setNewEndPoint() {
        if (clickedPoint != null) {
            newEndPoint = clickedPoint;
            repaintOnePixel(parser.getEnd(), MazeConstants.Path);
            parser.resetEnd(newEndPoint);
            repaintOnePixel(parser.getEnd(), MazeConstants.End);
            setButtonEnabled(ButtonTexts.SelectEnd, false);
            setButtonEnabled(ButtonTexts.SelectStart, false);
        }
    }

    // Konstruktor klasy MazeFrame
    public MazeFrame(IMazeService mazeService) {
        this.buttons = new ArrayList<>();
        this.mazeService = mazeService;
        setBackground(Color.WHITE);
        cellSize = 10; // Określ rozmiar komórki
        addMouseListener(new MouseAdapter() {
            // Obsługa kliknięcia myszą
            public void mouseClicked(MouseEvent e) {
                if (parser != null && !blockClick) {
                    int col = e.getX() / cellSize;
                    int row = e.getY() / cellSize;
                    var maze = parser.getMaze();

                    if (clickedPoint != null) {
                        if ((int) clickedPoint.getX() == row && (int) clickedPoint.getY() == col)
                            return;
                        repaintOnePixel(clickedPoint, maze[(int) clickedPoint.getX()][(int) clickedPoint.getY()]);
                    }
                    clickedPoint = new Point(row, col);
                    setButtonEnabled(ButtonTexts.SelectStart, true);
                    setButtonEnabled(ButtonTexts.SelectEnd, true);
                    repaintOnePixel(clickedPoint, MazeConstants.TemporaryPoint);
                }
            }
        });
    }

    // Metoda do odświeżania jednego piksela
    public void repaintOnePixel(Point p, char symbol) {
        int row = (int) p.getX();
        int col = (int) p.getY();

        var g = cachedImage.getGraphics();
        g.setColor(MazeConstants.getConstantColor(symbol));
        g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
        g.setColor(Color.BLACK);
        g.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);
        g.dispose();
        repaint();
    }

    // Metoda do ładowania labiryntu
    public void loadMaze(MazeParser parser) {
        this.parser = parser;
        cachedImage = createCachedImage();
        setPreferredSize(new Dimension(parser.getCols() * cellSize, parser.getRows() * cellSize));
        revalidate(); // Konieczne odświeżenie rozmiaru
        repaint();
        setButtonEnabled(ButtonTexts.FindPath, true);
    }

    // Metoda do rozwiązywania labiryntu
    public void solveMaze() {
        if (parser != null) {
            solveSteps = mazeService.getSolvePoints(parser); // Zapisanie kroków rozwiązania

            for (Point solvePoint : solveSteps) {
                if (!solvePoint.equals(parser.getStart()) && !solvePoint.equals(parser.getEnd()))
                    repaintOnePixel(solvePoint, MazeConstants.Solution);
            }
            setButtonEnabled(ButtonTexts.Export, true);
            setButtonEnabled(ButtonTexts.FindPath, false);
            blockClick = true;
        }
    }

    // Metoda do tworzenia buforowanego obrazu labiryntu
    private BufferedImage createCachedImage() {
        BufferedImage image = new BufferedImage(parser.getCols() * cellSize, parser.getRows() * cellSize,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        if (parser != null) {
            int rows = parser.getRows();
            int cols = parser.getCols();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    g.setColor(MazeConstants.getConstantColor(parser.getMaze()[i][j]));
                    g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                    g.setColor(Color.BLACK);
                    g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
                }
            }
        }
        g.dispose();
        return image;
    }

    // Metoda do włączania i wyłączania przycisków
    private void setButtonEnabled(String text, boolean isEnabled) {
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).getText().equals(text)) {
                buttons.get(i).setEnabled(isEnabled);
            }
        }
    }

    // Metoda do rysowania komponentu
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (cachedImage != null) {
            g.drawImage(cachedImage, -offsetX, -offsetY, null);
        }
    }

    // Metoda do przewijania widocznego obszaru
    @Override
    public void scrollRectToVisible(Rectangle aRect) {
        super.scrollRectToVisible(aRect);
        offsetX = aRect.x;
        offsetY = aRect.y;
        repaint();
    }

    // Zwraca listę kroków rozwiązania labiryntu
    public List<Point> getSolveSteps() {
        return solveSteps;
    }

    // Zwraca parser labiryntu
    public MazeParser getParser() {
        return parser;
    }

    // Zwraca rozmiar komórki labiryntu
    public int getCellSize() {
        return cellSize;
    }

    // Zwraca buforowany obraz labiryntu
    public BufferedImage getCachedImage() {
        return cachedImage;
    }
}
