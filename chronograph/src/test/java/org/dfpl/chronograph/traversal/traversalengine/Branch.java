package org.dfpl.chronograph.traversal.traversalengine;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import com.tinkerpop.gremlin.LoopBundle;
import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.dfpl.chronograph.traversal.TraversalEngine;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

public class Branch {
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

        a.setProperty("isOdd", true);
        a.setProperty("value", 5);
        a.setProperty("direction", "north");

        b.setProperty("isOdd", false);
        b.setProperty("value", 10);
        b.setProperty("direction", "south");

        c.setProperty("isOdd", true);
        c.setProperty("value", 6);
        c.setProperty("direction", "west");

        abLikes = graph.addEdge(a, b, "likes");
        acLikes = graph.addEdge(a, c, "likes");
        abLoves = graph.addEdge(a, b, "loves");
        ccLoves = graph.addEdge(c, c, "loves");

        abLikes.setProperty("isOdd", true);
        abLoves.setProperty("isOdd", true);
        acLikes.setProperty("isOdd", false);
        ccLoves.setProperty("weight", 50);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testIfThenElse_WithoutUnboxing() {
        TraversalEngine engine = new TraversalEngine(graph, graph.getVertices(), Vertex.class, false);

        List<Object> vertices = engine.ifThenElse(
            t -> t.getProperty("isOdd"),
            t -> t.getProperty("direction"),
            (Function<Vertex, Object>) t -> t.getProperty("value"),
            false,
            false).toList();

        assertThat(vertices, containsInAnyOrder("north", 10, "west"));
    }

    @SuppressWarnings("unused")
    @Test
    public void testIfThenElse_WithUnboxing() {
        TraversalEngine engine = new TraversalEngine(graph, graph.getVertices(), Vertex.class, false);

        List<Object> vertices = engine.ifThenElse(
            t -> t.getProperty("isOdd"),
            t -> t.getVertices(Direction.OUT, "likes"),
            (Function<Vertex, Collection<Vertex>>) t -> t.getVertices(Direction.OUT, "likes"),
            true,
            true).toList();

        fail("Unimplemented");
//		assertThat(vertices, containsInAnyOrder("north", 10, "west"));
    }

    @Test
    public void testLoop_VertexProperty() {
        for (Vertex v : graph.getVertices()) {
            v.setProperty("loopValue", 0);
        }

        TraversalEngine engine = new TraversalEngine(graph, graph.getVertices(), Vertex.class, false);

        engine
            .as("first")
            .sideEffect(v -> {
                int currentValue = ((Vertex) v).getProperty("loopValue");
                ((Vertex) v).setProperty("loopValue", currentValue + 1);
                return v;
            })
            .sideEffect(v -> {
                System.out.println(((Vertex) v).getId() + " " + ((Vertex) v).getProperty("loopValue").toString());
                return v;
            })
            .loop("first", (Predicate<LoopBundle<Vertex>>) loopBundle -> {
                Vertex v = loopBundle.getTraverser();
                return (int) v.getProperty("loopValue") < 5;
            });

        List<Vertex> vertices =  engine.toList();

        vertices.forEach(v -> {
            assertEquals(5, (int) v.getProperty("loopValue"));
        });
    }

    @Test
    public void testLoop_OutVertex() {
        graph.removeEdge(acLikes);
        graph.addEdge(b, c, "likes");

        TraversalEngine engine = new TraversalEngine(graph, graph.getVertex("A"), Vertex.class, false);

        engine
            .as("first")
            .out("likes")
            .loop("first", (Predicate<LoopBundle<Vertex>>) loopBundle -> {
                Vertex v = loopBundle.getTraverser();
                return v.getVertices(Direction.OUT, "likes").size() != 0;
            });

        List<Vertex> vertices =  engine.toList();
        List<Vertex> expectedVertices = new LinkedList<>(List.of(a, b));
        assertTrue(vertices.containsAll(expectedVertices));
    }

}
