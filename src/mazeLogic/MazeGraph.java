package mazeLogic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MazeGraph {
    
    private final char[][] maze; // 2D array to store the maze layout
    private final int rows, cols; // Number of rows and columns in the maze
    private final Map<Point, List<Point>> graph = new HashMap<>(); // Graph representation using adjacency list

    // Constructor to initialize the graph with the maze layout
    public MazeGraph(char[][] maze) {
        this.maze = maze;
        this.rows = maze.length;
        this.cols = maze[0].length;
        buildGraph(); // Build the graph from the maze layout
    }

    // Method to build the graph from the maze
    private void buildGraph() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze[i][j] != MazeConstants.Wall) { // Only consider non-wall cells
                    Point current = new Point(i, j);
                    graph.putIfAbsent(current, new ArrayList<>()); // Initialize adjacency list
                    for (Point neighbor : getNeighbors(i, j)) {
                        if (maze[neighbor.x][neighbor.y] != MazeConstants.Wall) {
                            graph.get(current).add(neighbor); // Add neighbors if not walls
                        }
                    }
                }
            }
        }
    }

    // Method to get the valid neighbors (up, down, left, right) of a cell
    private List<Point> getNeighbors(int x, int y) {
        List<Point> neighbors = new ArrayList<>();
        if (x > 0)
            neighbors.add(new Point(x - 1, y)); // Up
        if (x < rows - 1)
            neighbors.add(new Point(x + 1, y)); // Down
        if (y > 0)
            neighbors.add(new Point(x, y - 1)); // Left
        if (y < cols - 1)
            neighbors.add(new Point(x, y + 1)); // Right
        return neighbors;
    }

    // Getter method to retrieve the graph
    public Map<Point, List<Point>> getGraph() {
        return graph;
    }
}
