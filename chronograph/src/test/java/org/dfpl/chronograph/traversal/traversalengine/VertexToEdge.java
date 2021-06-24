package org.dfpl.chronograph.traversal.traversalengine;

import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.dfpl.chronograph.traversal.TraversalEngine;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

public class VertexToEdge {

    Graph graph;
    Vertex a;
    Vertex b;
    Vertex c;
    Edge abLikes;
    Edge acLikes;
    Edge abLoves;
    Edge ccLoves;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        graph = new ChronoGraph();

        a = graph.addVertex("A");
        b = graph.addVertex("B");
        c = graph.addVertex("C");

        abLikes = graph.addEdge(a, b, "likes");
        acLikes = graph.addEdge(a, c, "likes");
        abLoves = graph.addEdge(a, b, "loves");
        ccLoves = graph.addEdge(c, c, "loves");
    }

    @Test
    public void testOutE() {
        TraversalEngine engine = new TraversalEngine(graph, a, Vertex.class, false);

        assertThat(engine.outE("likes").toList(), containsInAnyOrder("A|likes|B", "A|likes|C"));
    }

    @Test
    public void testInE() {
        TraversalEngine engine = new TraversalEngine(graph, c, Vertex.class, false);

        assertThat(engine.inE("likes", "loves").toList(), containsInAnyOrder("A|likes|C", "C|loves|C"));
    }

}
