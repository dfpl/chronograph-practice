package org.dfpl.chronograph.traversal.memory.hgremlin;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.junit.Test;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

public class SideEffect {

	@Test
	public void sideEffectWithFunction() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		HTraversalEngine engine = new HTraversalEngine(graph, graph.getVertices(), Vertex.class);

		Set<Vertex> vSet = new HashSet<Vertex>();

		List<Vertex> vertices = engine.sideEffect(new Function<Vertex, Vertex>() {

			@Override
			public Vertex apply(Vertex t) {
				vSet.add(t);
				return t;
			}
		}).toList();

		assertThat(vSet, containsInAnyOrder(a, b, c));
	}

	@Test
	public void sideEffectWithCollection() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		HTraversalEngine engine = new HTraversalEngine(graph, graph.getVertices(), Vertex.class);

		Set<Vertex> vertices = new HashSet<Vertex>();

		engine.sideEffect(vertices);

		assertThat(vertices, containsInAnyOrder(a, b, c));
	}

}
