package mazeLogic;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
    public MazeParser(String filename) throws IOException {
        List<char[]> lines = new ArrayList<>(); // List to hold each line of the maze
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
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
