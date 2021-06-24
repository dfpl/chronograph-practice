package org.dfpl.chronograph.algorithm.temporal.search.dfs;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Time;
import com.tinkerpop.blueprints.TimeInstant;
import com.tinkerpop.blueprints.Vertex;
import org.dfpl.chronograph.creation.GraphBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.dfpl.chronograph.creation.GraphBuilder.*;

public class TemporalDepthFirstSearchTest {

    static TemporalDepthFirstSearch tdfs;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        tdfs = new TemporalDepthFirstSearch();
    }

    @Test
    public void testSmallScaleGraph1() {
        Graph g = createSmallTemporalGraph1();

        Vertex source = g.getVertex("A");
        Time time = new TimeInstant(3);

        tdfs.compute(g, source, time, "links");

        tdfs.printInfo(g);
    }

    @Test
    public void testSmallScaleGraph2() {
        Graph g = createSmallTemporalGraph2();
        Vertex source = g.getVertex("A");
        Time time = new TimeInstant(1);

        tdfs.compute(g, source, time, "links");

        tdfs.printInfo(g);
    }

    @Test
    public void testSmallScaleGraph3() {
        Graph g = createSmallTemporalGraph3();
        Vertex source = g.getVertex("A");
        Time time = new TimeInstant(3);

        tdfs.compute(g, source, time, "links");

        tdfs.printInfo(g);
    }

    @Test
    public void testLargeScaleGraph() throws IOException {
        Graph g = GraphBuilder.createSNAPEmailEuCoreTemporal("d:\\emails.txt");

        Vertex source = g.getVertex("582");
        Time time = new TimeInstant(0);

        tdfs.compute(g, source, time, "sendEmailTo");
        tdfs.printInfo(g);
    }
}
