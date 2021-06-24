package org.dfpl.chronograph.traversal.traversalengine;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.dfpl.chronograph.traversal.TraversalEngine;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

public class SideEffect {

    @Test
    public void sideEffectWithFunction() {
        Graph graph = new ChronoGraph();

        Vertex a = graph.addVertex("A");
        Vertex b = graph.addVertex("B");
        Vertex c = graph.addVertex("C");

        TraversalEngine engine = new TraversalEngine(graph, graph.getVertices(), Vertex.class, false);

        Set<Vertex> vSet = new HashSet<>();

        List<Vertex> vertices = engine.sideEffect((Function<Vertex, Vertex>) t -> {
            vSet.add(t);
            return t;
        }).toList();

        assertThat(vSet, containsInAnyOrder(a, b, c));
        assertThat(vertices, containsInAnyOrder(a, b, c));
    }

    @Test
    public void sideEffectWithCollection() {
        Graph graph = new ChronoGraph();

        Vertex a = graph.addVertex("A");
        Vertex b = graph.addVertex("B");
        Vertex c = graph.addVertex("C");

        TraversalEngine engine = new TraversalEngine(graph, graph.getVertices(), Vertex.class, false);

        Set<Vertex> vertices = new HashSet<>();

        List<Vertex> engineResult = engine.sideEffect(vertices).toList();

        assertThat(vertices, containsInAnyOrder(a, b, c));
        assertThat(engineResult, containsInAnyOrder(a, b, c));
    }

}
