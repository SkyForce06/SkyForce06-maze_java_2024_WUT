package mazeLogic;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class MazeParser {
    private char[][] maze; // 2D tablica przechowująca układ labiryntu

    public void setMaze(char[][] maze) {
        this.maze = maze;
    }

    private int startX, startY, endX, endY; // Współrzędne punktów startowego i końcowego
    int cols, rows; // Liczba kolumn i wierszy w labiryncie

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    // Konstruktor inicjalizujący parser z nazwą pliku labiryntu
    public MazeParser(File file) throws IOException {
        var fileParts = file.getName().split("\\.");
        if (fileParts.length > 1) {
            switch (fileParts[1]) {
                case "txt":
                    List<char[]> lines = new ArrayList<>(); // Lista do przechowywania każdej linii labiryntu
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line;
                        // Odczytuj każdą linię z pliku i dodaj ją do listy
                        while ((line = reader.readLine()) != null) {
                            lines.add(line.toCharArray());
                        }
                    }
                    maze = lines.toArray(new char[0][]); // Konwertuj listę na 2D tablicę
                    findStartAndEnd(); // Znajdź punkty startowy i końcowy w labiryncie
                    rows = maze.length;
                    cols = maze[0].length;
                    break;
                case "bin":
                    try (FileInputStream fis = new FileInputStream(file)) {

                        var header = fis.readNBytes(40);
                        ByteBuffer buffer = ByteBuffer.wrap(header).order(ByteOrder.LITTLE_ENDIAN);
                        buffer.getInt();
                        buffer.get();

                        this.cols = buffer.getShort();
                        this.rows = buffer.getShort();
                        this.startY = buffer.getShort() - 1;
                        this.startX = buffer.getShort() - 1;
                        this.endY = buffer.getShort() - 1;
                        this.endX = buffer.getShort() - 1;
                        buffer.position(buffer.position() + 12);
                        int counter = buffer.getInt();
                        buffer.getInt();
                        char separator = (char) buffer.get();
                        char wall = (char) buffer.get();
                        char path = (char) buffer.get();

                        int tempCol = 0;
                        int tempRow = 0;
                        maze = new char[rows][cols];

                        for (int i = 0; i < counter; i++) {
                            byte sep = (byte) fis.read();
                            byte value = (byte) fis.read();
                            byte count = (byte) fis.read();
                            int actualCount = (count & 0xFF) + 1;

                            for (int j = 0; j < actualCount; j++) {
                                if (tempCol == cols) {
                                    tempCol = 0;
                                    tempRow++;
                                }
                                if ((char) value == wall) {
                                    maze[tempRow][tempCol] = MazeConstants.Wall;
                                } else if ((char) value == path) {
                                    maze[tempRow][tempCol] = MazeConstants.Path;
                                }
                                tempCol++;
                            }
                        }

                        maze[startX][startY] = MazeConstants.Start;
                        maze[endX][endY] = MazeConstants.End;
                    }
                    break;
                default:
                    throw new IOException("Not supported extension. Available extensions: .txt, .bin");
            }
        } else {
            throw new IOException("Error of reading file");
        }
    }

    // Metoda do znajdowania i przechowywania współrzędnych punktów startowego ('S')
    // i końcowego ('E')
    private void findStartAndEnd() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == MazeConstants.Start) {
                    startX = i;
                    startY = j;
                } else if (maze[i][j] == MazeConstants.End) {
                    endX = i;
                    endY = j;
                }
            }
        }
    }

    // Metoda do resetowania punktu startowego
    public void resetStart(Point newStart) {
        resetPoint(getStart(), newStart, MazeConstants.Start);
        startX = newStart.x;
        startY = newStart.y;
    }

    // Metoda do resetowania punktu końcowego
    public void resetEnd(Point newEnd) {
        resetPoint(getEnd(), newEnd, MazeConstants.End);
        endX = newEnd.x;
        endY = newEnd.y;
    }

    // Metoda pomocnicza do resetowania punktu
    private void resetPoint(Point oldPos, Point newPos, char symbol) {
        maze[(int) oldPos.getX()][(int) oldPos.getY()] = MazeConstants.Path;
        maze[(int) newPos.getX()][(int) newPos.getY()] = symbol;
    }

    // Metoda zwracająca układ labiryntu
    public char[][] getMaze() {
        return maze;
    }

    // Metoda zwracająca współrzędne punktu startowego
    public Point getStart() {
        return new Point(startX, startY);
    }

    // Metoda zwracająca współrzędne punktu końcowego
    public Point getEnd() {
        return new Point(endX, endY);
    }
}
