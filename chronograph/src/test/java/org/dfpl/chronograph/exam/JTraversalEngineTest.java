package org.dfpl.chronograph.exam;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import kr.ac.adv.exam.Gremlin.JTraversalEngine;
import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.junit.Before;
import org.junit.Test;

public class JTraversalEngineTest {

    ChronoGraph chronoGraph;
    Vertex v1, v2, v3, v4, v5, v6;
    Edge e1, e2, e3, e4, e5, e6, e7;

    @Before
    public void init() {
        chronoGraph = new ChronoGraph();
        v1 = chronoGraph.addVertex("1");
        v1.setProperty("property", "property");
        v2 = chronoGraph.addVertex("2");
        v3 = chronoGraph.addVertex("3");
        v4 = chronoGraph.addVertex("4");
        v5 = chronoGraph.addVertex("5");
        v6 = chronoGraph.addVertex("6");
        e1 = chronoGraph.addEdge(v1, v2, "love");
        e2 = chronoGraph.addEdge(v2, v4, "hate");
        e3 = chronoGraph.addEdge(v1, v3, "like");
        e4 = chronoGraph.addEdge(v5, v6, "love");
        e5 = chronoGraph.addEdge(v3, v2, "love");
        e6 = chronoGraph.addEdge(v6, v1, "hate");
        e7 = chronoGraph.addEdge(v5, v4, "like");
        chronoGraph.getEdges().forEach(e -> {
            e.setProperty("weight", 1);
        });


    }

    @Test
    public void VTest() {
        JTraversalEngine jTraversalEngine = new JTraversalEngine(chronoGraph,
            chronoGraph, ChronoGraph.class, false);
        Collection<Vertex> vertices = chronoGraph.getVertices();
//        System.out.println(jTraversalEngine.V().toList());
//        System.out.println(vertices.stream().toList());
        assertEquals(jTraversalEngine.V().toList().size(), 6);
    }

    @Test
    public void idTest() {
        JTraversalEngine jTraversalEngine = new JTraversalEngine(chronoGraph,
            chronoGraph.getVertices(), Vertex.class, false);
        assertThat(jTraversalEngine.id().toList(),
            containsInAnyOrder("1", "2", "3", "4", "5", "6"));


    }

    @Test
    public void elementTest() {
        JTraversalEngine jTraversalEngine = new JTraversalEngine(chronoGraph,
            chronoGraph, Graph.class, false);
        //System.out.println(jTraversalEngine.V().id().element(Vertex.class).toList());
        assertThat(jTraversalEngine.V().id().element(Vertex.class).toList(),
            containsInAnyOrder("1", "2", "3", "4", "5", "6"));
    }

    @Test
    public void outETest() {
        JTraversalEngine jTraversalEngine = new JTraversalEngine(
            chronoGraph, chronoGraph.getVertex("1"), Vertex.class, false);
        assertThat(jTraversalEngine.outE("love").toList()
            , containsInAnyOrder("1|love|2"));
    }

    @Test
    public void inVTest() {
        JTraversalEngine jTraversalEngine = new JTraversalEngine(
            chronoGraph, e1, Edge.class, false);
        assertThat(jTraversalEngine.inV().toList(), containsInAnyOrder("2"));

    }

    @Test
    public void outTest() {
        JTraversalEngine jTraversalEngine = new JTraversalEngine(
            chronoGraph, chronoGraph.getVertex("3"), Vertex.class, false);
        assertThat(jTraversalEngine.out("love").toList()
            , containsInAnyOrder("3|love|2"));

    }

    @Test
    public void gatherTest() {
        JTraversalEngine jTraversalEngine = new JTraversalEngine(
            chronoGraph, chronoGraph, Graph.class, false);
        List<List<Vertex>> vertices = jTraversalEngine.V().gather().toList();
        assertEquals(vertices.size(), 1);
        assertThat(vertices.get(0), containsInAnyOrder("1", "2", "3", "4", "5", "6"));
    }

    @Test
    public void scatterTest() {
        JTraversalEngine jTraversalEngine = new JTraversalEngine(
            chronoGraph, chronoGraph, Graph.class, false);
        List<Vertex> vertices = jTraversalEngine.V().scatter().toList();
        assertEquals(vertices.size(), 6);
        assertThat(vertices, containsInAnyOrder("1", "2", "3", "4", "5", "6"));

    }

    @Test
    public void transformTest() {
        JTraversalEngine jTraversalEngine = new JTraversalEngine(
            chronoGraph, chronoGraph, Graph.class, false);
        List<Set<Edge>> edges = jTraversalEngine.V().transform((Function<Vertex, Collection<Edge>>)
            t -> t.getEdges(Direction.OUT, "love"), Collection.class, Edge.class, false).toList();
        System.out.println(edges);
        assertThat(edges.get(0), containsInAnyOrder("1|love|2"));
    }

    @Test
    public void dedupTest() {
        Vertex v = chronoGraph.addVertex("1");
        Collection<Vertex> vertices = Arrays.asList(v1, v2, v);
        JTraversalEngine jTraversalEngine = new JTraversalEngine(chronoGraph, vertices,
            Vertex.class, false);
        assertThat(jTraversalEngine.dedup().toList(), containsInAnyOrder("1", "2"));
    }

    @Test
    public void filterTest() {
        JTraversalEngine jTraversalEngine = new JTraversalEngine(chronoGraph, chronoGraph,
            Graph.class, false);
        List<Edge> edges = jTraversalEngine.E().filter(new Predicate<Edge>() {
            @Override
            public boolean test(Edge edge) {
                if (edge.getLabel().equals("love")) {
                    return true;
                }
                return false;
            }
        }).toList();
        assertThat(edges, containsInAnyOrder("5|love|6", "1|love|2", "3|love|2"));
    }

    @Test
    public void sideEffectTest() {
        JTraversalEngine jTraversalEngine = new JTraversalEngine(chronoGraph, chronoGraph,
            Graph.class, false);
        Set<Edge> edgeHashSet = new HashSet<>();
        List<Edge> edges = jTraversalEngine.E().sideEffect((Function<Edge, Edge>) e -> {
            edgeHashSet.add(e);
            return e;
        }).toList();
        System.out.println(edges);
        System.out.println(edgeHashSet);

    }

    @Test
    public void ifThenElseTest() {
        JTraversalEngine jTraversalEngine = new JTraversalEngine(chronoGraph, chronoGraph,
            Graph.class, false);
        List<Object> objects = jTraversalEngine.V().ifThenElse(
            v -> {
                if (v.getId().equals("1")) {
                    return true;
                }
                return false;
            }
            , v -> v.getProperty("property")
            , (Function<Vertex, Object>) v -> v.getVertices(Direction.OUT, "love")
            , false, false).toList();

        System.out.println("objects = " + objects);

    }

    @Test
    public void asTest() {
        //could not implement
    }

    @Test
    public void loopTest() {
//could not implement
    }

}
