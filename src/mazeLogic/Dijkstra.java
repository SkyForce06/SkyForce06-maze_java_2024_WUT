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
    private final Map<Point, List<Point>> graph; // Reprezentacja grafu labiryntu
    private final Point start, end; // Punkty startowy i końcowy

    // Konstruktor inicjalizujący solver z grafem, punktami startowym i końcowym
    public Dijkstra(Map<Point, List<Point>> graph, Point start, Point end) {
        this.graph = graph;
        this.start = start;
        this.end = end;
    }

    // Metoda rozwiązująca labirynt za pomocą algorytmu Dijkstry
    public List<Point> solve() {
        Map<Point, Point> prev = new HashMap<>(); // Mapa do przechowywania poprzedniego punktu na optymalnej ścieżce
        Map<Point, Integer> distances = new HashMap<>(); // Mapa do przechowywania odległości od punktu startowego
        PriorityQueue<Point> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get)); // Kolejka priorytetowa
                                                                                                // do algorytmu Dijkstry

        // Inicjalizacja odległości jako nieskończoność
        for (Point node : graph.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(start, 0); // Odległość od startu do startu to 0
        pq.add(start); // Dodaj punkt startowy do kolejki priorytetowej

        // Pętla do przeszukiwania grafu
        while (!pq.isEmpty()) {
            Point current = pq.poll(); // Pobierz punkt z najmniejszą odległością

            if (current.equals(end)) {
                return reconstructPath(prev); // Znaleziono ścieżkę, odtwórz ją
            }

            // Przetwarzaj sąsiadów bieżącego punktu
            for (Point neighbor : graph.get(current)) {
                int newDist = distances.get(current) + 1; // Zakładając jednolity koszt każdego kroku
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist); // Aktualizuj odległość
                    prev.put(neighbor, current); // Aktualizuj poprzedni punkt
                    pq.add(neighbor); // Dodaj sąsiada do kolejki priorytetowej
                }
            }
        }

        return Collections.emptyList(); // Nie znaleziono ścieżki
    }

    // Metoda do odtworzenia ścieżki od końca do początku
    private List<Point> reconstructPath(Map<Point, Point> prev) {
        List<Point> path = new ArrayList<>();
        for (Point at = end; at != null; at = prev.get(at)) {
            path.add(at); // Dodaj każdy punkt na ścieżce do listy
        }
        Collections.reverse(path); // Odwróć listę, aby uzyskać ścieżkę od początku do końca
        return path;
    }
}
