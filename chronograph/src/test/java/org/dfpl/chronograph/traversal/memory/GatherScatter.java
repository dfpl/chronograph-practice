package org.dfpl.chronograph.traversal.memory;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.dfpl.chronograph.traversal.TraversalEngine;
import org.junit.Test;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.GremlinFluentPipeline;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

public class GatherScatter {

	@SuppressWarnings("unused")
	@Test
	public void gather() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		TraversalEngine engine = new TraversalEngine(graph, graph.getVertices(), Vertex.class, false);

		GremlinFluentPipeline vertices = engine.gather();
		assertThat(vertices.toList(), containsInAnyOrder("A", "B", "C"));
	}

	@SuppressWarnings("unused")
	@Test
	public void scatter() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		TraversalEngine engine = new TraversalEngine(graph, graph.getVertices(), Vertex.class, false);

		assertThat(engine.scatter().toList(), containsInAnyOrder("A", "B", "C"));
	}

	@SuppressWarnings("unused")
	@Test
	public void transform() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		Edge abLikes = graph.addEdge(a, b, "likes");
		Edge acLikes = graph.addEdge(a, c, "likes");
		graph.addEdge(c, c, "loves");

		TraversalEngine boxEngine = new TraversalEngine(graph, abLikes, Edge.class, false);

		List<Vertex> test = boxEngine.transform(new Function<Edge, Vertex>() {

			@Override
			public Vertex apply(Edge t) {
				return t.getVertex(Direction.IN);
			}

		}, false).toList();
		assertThat(test, containsInAnyOrder("B"));

		TraversalEngine unboxEngine = new TraversalEngine(graph, a, Vertex.class, false);

		GremlinFluentPipeline unboxPipeline = unboxEngine.transform(new Function<Vertex, Collection<Edge>>() {

			@Override
			public Collection<Edge> apply(Vertex t) {
				return t.getEdges(Direction.OUT, "likes");
			}

		}, true);

		assertThat(unboxPipeline.toList(), containsInAnyOrder(abLikes, acLikes));
	}

}
