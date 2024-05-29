package mazeLogic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MazeGraph {

    private final char[][] maze; // 2D tablica przechowująca układ labiryntu
    private final int rows, cols; // Liczba wierszy i kolumn w labiryncie
    private final Map<Point, List<Point>> graph = new HashMap<>(); // Reprezentacja grafu za pomocą listy sąsiedztwa

    // Konstruktor inicjalizujący graf na podstawie układu labiryntu
    public MazeGraph(char[][] maze) {
        this.maze = maze;
        this.rows = maze.length;
        this.cols = maze[0].length;
        buildGraph(); // Zbuduj graf na podstawie układu labiryntu
    }

    // Metoda budująca graf na podstawie labiryntu
    private void buildGraph() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze[i][j] != MazeConstants.Wall) { // Uwzględniaj tylko komórki nie będące ścianami
                    Point current = new Point(i, j);
                    graph.putIfAbsent(current, new ArrayList<>()); // Inicjalizuj listę sąsiedztwa
                    for (Point neighbor : getNeighbors(i, j)) {
                        if (maze[neighbor.x][neighbor.y] != MazeConstants.Wall) {
                            graph.get(current).add(neighbor); // Dodaj sąsiadów, jeśli nie są ścianami
                        }
                    }
                }
            }
        }
    }

    // Metoda do pobierania sąsiadów (góra, dół, lewo, prawo) danej komórki
    private List<Point> getNeighbors(int x, int y) {
        List<Point> neighbors = new ArrayList<>();
        if (x > 0)
            neighbors.add(new Point(x - 1, y)); // Góra
        if (x < rows - 1)
            neighbors.add(new Point(x + 1, y)); // Dół
        if (y > 0)
            neighbors.add(new Point(x, y - 1)); // Lewo
        if (y < cols - 1)
            neighbors.add(new Point(x, y + 1)); // Prawo
        return neighbors;
    }

    // Metoda zwracająca graf
    public Map<Point, List<Point>> getGraph() {
        return graph;
    }
}
