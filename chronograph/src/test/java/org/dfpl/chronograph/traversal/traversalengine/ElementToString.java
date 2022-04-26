package org.dfpl.chronograph.traversal.traversalengine;

import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.dfpl.chronograph.traversal.TraversalEngine;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

public class ElementToString {
    Graph graph;
    Vertex a;
    Vertex b;
    Vertex c;

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

        graph.addEdge(a, b, "likes");
        graph.addEdge(a, c, "likes");
        graph.addEdge(c, c, "loves");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testId_Vertices() {
        TraversalEngine engine = new TraversalEngine(graph, graph.getVertices(), Vertex.class, false);

        assertThat(engine.id().toList(), containsInAnyOrder("A", "B", "C"));
    }

    @Test
    public void testId_Edges() {
        TraversalEngine engine = new TraversalEngine(graph, graph.getEdges(), Edge.class, false);

        assertThat(engine.id().toList(), containsInAnyOrder("A|likes|B", "A|likes|C", "C|loves|C"));
    }

    @Test
    public void testElement() {
        TraversalEngine vEngine = new TraversalEngine(graph, graph, Graph.class, false);
        // id to its graph element (vertex, edge)
        assertThat(vEngine.V().id().element(Vertex.class).toList(), containsInAnyOrder("A", "B", "C"));

        TraversalEngine eEngine = new TraversalEngine(graph, graph, Graph.class, false);
        System.out.println(eEngine.E().id().element(Edge.class).toList());
//        assertThat(eEngine.E().id().element(Edge.class).toList(),
 //           containsInAnyOrder("A|likes|B", "A|likes|C", "C|loves|C"));
    }

}
