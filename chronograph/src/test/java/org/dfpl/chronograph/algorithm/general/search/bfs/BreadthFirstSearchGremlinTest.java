package org.dfpl.chronograph.algorithm.general.search.bfs;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Graph;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.dfpl.chronograph.creation.GraphBuilder.createSmallStaticGraph3;

public class BreadthFirstSearchGremlinTest {
    @SuppressWarnings("rawtypes")
    @Test
    public void testSmallStaticGraph3() {

        BreadthFirstSearchGremlin bfs = new BreadthFirstSearchGremlin();
        Graph g = createSmallStaticGraph3();

        ArrayList<ArrayList<List>> result = bfs.BFS(g, Direction.OUT, "c");
        System.out.println(result);
    }
}
