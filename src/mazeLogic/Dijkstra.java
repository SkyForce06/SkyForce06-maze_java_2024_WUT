package mazeLogic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Dijkstra {
    private final Map<Point, List<Point>> graph; // Graph representation of the maze
    private final Point start, end; // Start and end points

    // Constructor to initialize the solver with the graph, start, and end points
    public Dijkstra(Map<Point, List<Point>> graph, Point start, Point end) {
        this.graph = graph;
        this.start = start;
        this.end = end;
    }

    // Method to solve the maze using Dijkstra's algorithm
    public List<Point> solve() {
        Map<Point, Point> prev = new HashMap<>(); // Map to store the previous point in the optimal path
        Map<Point, Integer> distances = new HashMap<>(); // Map to store the distance from the start point
        PriorityQueue<Point> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get)); // Priority queue for
                                                                                                // Dijkstra's algorithm

        // Initialize distances to infinity
        for (Point node : graph.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(start, 0); // Distance from start to start is 0
        pq.add(start); // Add the start point to the priority queue

        while (!pq.isEmpty()) {
            Point current = pq.poll(); // Get the point with the smallest distance

            if (current.equals(end)) {
                return reconstructPath(prev); // Path found, reconstruct it
            }

            for (Point neighbor : graph.get(current)) {
                int newDist = distances.get(current) + 1; // Assuming uniform cost for each step
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist); // Update the distance
                    prev.put(neighbor, current); // Update the previous point
                    pq.add(neighbor); // Add the neighbor to the priority queue
                }
            }
        }

        return Collections.emptyList(); // No path found
    }

    // Method to reconstruct the path from end to start
    private List<Point> reconstructPath(Map<Point, Point> prev) {
        List<Point> path = new ArrayList<>();
        for (Point at = end; at != null; at = prev.get(at)) {
            path.add(at); // Add each point in the path to the list
        }
        Collections.reverse(path); // Reverse the list to get the path from start to end
        return path;
    }
}
