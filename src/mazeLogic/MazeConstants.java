package mazeLogic;

import java.awt.Color;

public  class MazeConstants {
    public static final char Wall = 'X';
    public static final char Path = ' ';
    public static final char Start = 'P';
    public static final char Solution = 'S';
    public static final char End = 'K';
    public static final char TemporaryPoint = 'T';

    public static Color getConstantColor(char symbol) {
        switch (symbol) {
            case MazeConstants.Start:
                return Color.GREEN; // Początkowa pozycja
            case MazeConstants.End:
                return Color.RED; // Końcowa pozycja
            case MazeConstants.Wall:
                return Color.BLACK; // Ściana
            case MazeConstants.Solution:
                return Color.blue; // Rozwiązanie
            case MazeConstants.TemporaryPoint:
                return Color.CYAN; // Tymczasowy kliknięty punkt
            case MazeConstants.Path:
                return Color.WHITE; // Tymczasowy kliknięty punkt
            default:
                return Color.ORANGE; // Error
        }
    }
}
