package mazeLogic;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public interface IMazeService {
    public List<Point> getSolvePoints(MazeParser parser);

    public void saveMaze(File file, List<Point> solvePoints, BufferedImage image);

}
