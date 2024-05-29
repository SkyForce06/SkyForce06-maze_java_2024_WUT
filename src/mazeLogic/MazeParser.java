package mazeLogic;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
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

                        // Read the length of the string first
                        // var bytes = dis.readNBytes(5);
                        // var buffer = ByteBuffer.wrap(bytes);
                        var buffer=fis.readNBytes(4);
                        int id = ByteBuffer.wrap(buffer).getInt();
                        // dis.readByte();

                        // this.cols = dis.readUnsignedShort();
                        // this.rows = dis.readUnsignedShort();
                        // this.startX = dis.readUnsignedShort();
                        // this.startY = dis.readUnsignedShort();
                        // this.endX = dis.readUnsignedShort();
                        // this.endY = dis.readUnsignedShort();
                        // int tst = dis.readInt();
                        // dis.readNBytes(20);
                        maze = new char[rows][cols];
                        for (int i = 0; i < rows; i++) {
                            for (int j = 0; j < cols; j++) {
                                maze[i][j] = MazeConstants.Wall;
                            }
                        }
                        // maze[startX][startY] = MazeConstants.Start;
                        // maze[endX][endY] = MazeConstants.End;

                        // char separator = dis.readChar();
                        // char wall = dis.readChar();
                        // char path = dis.readChar();

                        // int readLines = 0;
                        // while (readLines != rows - 1) {
                        //     int readColumns = 0;
                        //     int x = 0;
                        //     while (readColumns != rows) // czytamy wiersz i sprawdzamy ile już przeczytaliśmy w tym
                        //                                 // wierszu
                        //     {
                        //         char keySep = dis.readChar();
                        //         char value = dis.readChar();
                        //         char count = dis.readChar();

                        //         int size = count + 1;
                        //         for (int i = 0; i < size; i++) {
                        //             if (path == value) {
                        //                 maze[readLines][cols] = MazeConstants.Path;
                        //             }
                        //             x++;
                        //         }
                        //         readColumns += size; // odświeżamy liczbę przeczytanych kolumn

                        //     }
                        //     readLines++; // odświeżamy igrek

                        // }

                    }
                    break;
                default:
                    throw new IOException("Not supported extension. Available extensions: .txt, .bin");
            }

        } else {
            throw new IOException("Error of reading file");
        }
    }

    // Metoda do znajdowania i przechowywania współrzędnych punktów startowego ('S') i końcowego ('E')
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
