package org.dfpl.chronograph.algorithm.general.search.bfs;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import org.junit.Test;

import java.util.ArrayList;

import static org.dfpl.chronograph.creation.GraphBuilder.createSmallStaticGraph3;

public class BreadthFirstSearchBlueprintsTest {

    @Test
    public void testSmallStaticGraph3() {

        BreadthFirstSearchBlueprints bfs = new BreadthFirstSearchBlueprints();
        Graph g = createSmallStaticGraph3();
        ArrayList<ArrayList<ArrayList<Vertex>>> result = bfs.BFS(g, Direction.OUT, "c");
        System.out.println(result);
    }
}
