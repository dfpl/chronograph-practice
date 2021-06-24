package org.dfpl.chronograph.traversal.traversalengine;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import org.hamcrest.collection.IsMapContaining;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.dfpl.chronograph.traversal.TraversalEngine;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class Aggregation {

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

        a.setProperty("isOdd", true);
        b.setProperty("isOdd", false);
        c.setProperty("isOdd", true);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGroupBy() {
        TraversalEngine engine = new TraversalEngine(graph, graph.getVertices(), Vertex.class, false);

        Map<Boolean, List<Vertex>> map = engine.groupBy(t -> t.getProperty("isOdd"));

        assertEquals(2, map.size());
        assertThat(map, IsMapContaining.hasEntry(true, Arrays.asList(a, c)));
        assertThat(map, IsMapContaining.hasEntry(false, Collections.singletonList(b)));
    }

    @Test
    public void testGroupCount() {
        TraversalEngine engine = new TraversalEngine(graph, graph.getVertices(), Vertex.class, false);

        Map<Boolean, Long> map = engine.groupCount((Function<Vertex, Boolean>) t -> t.getProperty("isOdd"));

        Map<Boolean, Long> expectedMap = new HashMap<>() {
            {
                put(true, 2L);
                put(false, 1L);
            }
        };

        assertEquals(2, map.size());
        assertEquals(map, expectedMap);
    }

    @Test
    public void testReduce_LastIDVertex() {
        TraversalEngine engine = new TraversalEngine(graph, graph.getVertices(), Vertex.class, false);

        Vertex v = (Vertex) engine.reduce((BinaryOperator<Vertex>) (t, u) -> {
            if (t.getId().compareTo(u.getId()) > 0)
                return t;
            else
                return u;
        }).get();

        assertEquals("C", v.getId());
    }

}
