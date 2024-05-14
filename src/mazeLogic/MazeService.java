package mazeLogic;

import java.awt.Point;
import java.util.List;

public class MazeService implements IMazeService {

    @Override
    public List<Point> getSolvePoints(MazeParser parser) {
        MazeGraph graph = new MazeGraph(parser.getMaze());
        Dijkstra dijkstra = new Dijkstra(graph.getGraph(), parser.getStart(), parser.getEnd());
        return dijkstra.solve();
    }

}
