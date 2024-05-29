package mazeLogic;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

// Interfejs IMazeService definiuje operacje związane z rozwiązywaniem labiryntów i zapisywaniem wyników.
public interface IMazeService {
    // Metoda getSolvePoints zwraca listę punktów reprezentujących rozwiązanie labiryntu na podstawie danych zawartych w obiekcie parser.
    public List<Point> getSolvePoints(MazeParser parser);

    // Metoda saveMaze zapisuje labirynt w określonym pliku, uwzględniając podane punkty rozwiązania i obraz.
    public void saveMaze(File file, List<Point> solvePoints, BufferedImage image);
}
