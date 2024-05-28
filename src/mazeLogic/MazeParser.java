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
    private char[][] maze; // 2D array to store the maze layout

    public void setMaze(char[][] maze) {
        this.maze = maze;
    }

    private int startX, startY, endX, endY; // Coordinates of the start and end points
    int cols, rows;

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

    // Constructor to initialize the parser with the filename of the maze
    public MazeParser(File file) throws IOException {

        var fileParts = file.getName().split("\\.");
        if (fileParts.length > 1) {
            switch (fileParts[1]) {
                case "txt":
                    List<char[]> lines = new ArrayList<>(); // List to hold each line of the maze
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line;
                        // Read each line from the file and add it to the list
                        while ((line = reader.readLine()) != null) {
                            lines.add(line.toCharArray());
                        }
                    }
                    maze = lines.toArray(new char[0][]); // Convert the list to a 2D array
                    findStartAndEnd(); // Locate the start and end points in the maze
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

    // Method to find and store the coordinates of the start ('S') and end ('E')
    // points
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

    public void resetStart(Point newStart) {
        resetPoint(getStart(), newStart, MazeConstants.Start);
        startX = newStart.x;
        startY = newStart.y;

    }

    public void resetEnd(Point newEnd) {
        resetPoint(getEnd(), newEnd, MazeConstants.End);
        endX = newEnd.x;
        endY = newEnd.y;
    }

    private void resetPoint(Point oldPos, Point newPos, char symbol) {

        maze[(int) oldPos.getX()][(int) oldPos.getY()] = MazeConstants.Path;
        maze[(int) newPos.getX()][(int) newPos.getY()] = symbol;

    }

    // Getter method to retrieve the maze layout
    public char[][] getMaze() {
        return maze;
    }

    // Getter method to retrieve the start coordinates
    public Point getStart() {
        return new Point(startX, startY);
    }

    // Getter method to retrieve the end coordinates
    public Point getEnd() {
        return new Point(endX, endY);
    }
}
