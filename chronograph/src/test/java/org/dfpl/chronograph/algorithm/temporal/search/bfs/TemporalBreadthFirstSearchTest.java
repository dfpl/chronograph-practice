package org.dfpl.chronograph.algorithm.temporal.search.bfs;

import java.io.IOException;

import org.dfpl.chronograph.creation.GraphBuilder;
import org.junit.Test;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Time;
import com.tinkerpop.blueprints.TimeInstant;
import com.tinkerpop.blueprints.Vertex;

public class TemporalBreadthFirstSearchTest {

    @Test
    public void testSmallScaleGraph1() {
        TemporalBreadthFirstSearch tbfs = new TemporalBreadthFirstSearch();

        Graph g = GraphBuilder.createSmallTemporalGraph1();

        Vertex source = g.getVertex("A");
        Time time = new TimeInstant(3);

        tbfs.compute(source, time, "links");

        tbfs.printInfo(g);
    }

    @Test
    public void testSmallScaleGraph2() {
        TemporalBreadthFirstSearch tbfs = new TemporalBreadthFirstSearch();
        Graph g = GraphBuilder.createSmallTemporalGraph2();

        Vertex source = g.getVertex("A");
        Time time = new TimeInstant(3);

        tbfs.compute(source, time, "links");

        tbfs.printInfo(g);
    }

    @Test
    public void testSmallScaleGraph3() {
        TemporalBreadthFirstSearch tbfs = new TemporalBreadthFirstSearch();
        Graph g = GraphBuilder.createSmallTemporalGraph3();

        Vertex source = g.getVertex("A");
        Time time = new TimeInstant(1);

        tbfs.compute(source, time, "links");

        tbfs.printInfo(g);
    }

    @Test
    public void testLargeScaleGraph() throws IOException {
        TemporalBreadthFirstSearch tbfs = new TemporalBreadthFirstSearch();
        Graph g = GraphBuilder.createSNAPEmailEuCoreTemporal("d:\\emails.txt");

        Vertex source = g.getVertex("582");
        Time time = new TimeInstant(0);

        tbfs.compute(source, time, "sendEmailTo");

        tbfs.printInfo(g);
    }

}
