package org.dfpl.chronograph.traversal.traversalengine;

import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.dfpl.chronograph.traversal.TraversalEngine;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

public class VertexToVertex {

    Graph graph;
    Vertex a;
    Vertex b;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        Graph graph = new ChronoGraph();

        a = graph.addVertex("A");
        b = graph.addVertex("B");

        graph.addEdge(a, b, "likes");
        graph.addEdge(a, a, "loves");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testOut() {
        TraversalEngine engine = new TraversalEngine(graph, a, Vertex.class, false);

        assertThat(engine.out("likes", "loves").toList(), containsInAnyOrder(a, b));
    }

    @Test
    public void testIn() {
        TraversalEngine engine = new TraversalEngine(graph, a, Vertex.class, false);

        assertThat(engine.in("likes", "loves").toList(), containsInAnyOrder(a));
    }

}
