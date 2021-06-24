package org.dfpl.chronograph.traversal.traversalengine;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import org.dfpl.chronograph.common.Tokens.NC;
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

public class FilterSortLimit {
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
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testHas_WithKeyAndValue() {
        a.setProperty("isOdd", true); // included
        b.setProperty("isOdd", false);
        c.setProperty("weight", 7);

        TraversalEngine engine = new TraversalEngine(graph, graph.getVertices(), Vertex.class, false);

        assertThat(engine.has("isOdd", true).toList(), containsInAnyOrder(a));
    }

    @Test
    public void testHas_WithKey() {
        a.setProperty("isOdd", true); // included
        b.setProperty("isOdd", false); // included
        c.setProperty("weight", 7);

        TraversalEngine engine = new TraversalEngine(graph, graph.getVertices(), Vertex.class, false);

        assertThat(engine.has("isOdd").toList(), containsInAnyOrder(a, b));
    }

    @Test
    public void testHas_WithToken() {
        a.setProperty("isOdd", true); // included
        b.setProperty("isOdd", false);
        c.setProperty("weight", 7);

        TraversalEngine engine = new TraversalEngine(graph, graph.getVertices(), Vertex.class, false);

        assertThat(engine.has("weight", NC.$eq, 7).toList(), containsInAnyOrder(c));
    }

    @Test
    public void testDedup() {
        Vertex aDup = graph.addVertex("A");

        Collection<Vertex> dupVertices = Arrays.asList(a, b, aDup);
        TraversalEngine engine = new TraversalEngine(graph, dupVertices, Vertex.class, false);

        assertThat(engine.dedup().toList(), containsInAnyOrder(a, b));
    }

    @Test
    public void testFilter() {
        TraversalEngine engine = new TraversalEngine(graph, graph.getVertices(), Vertex.class, false);

        List<Vertex> vertices = engine.filter((Predicate<Vertex>) t -> t.getId().equals("A")).toList();

        assertThat(vertices, containsInAnyOrder(a));
    }

}
