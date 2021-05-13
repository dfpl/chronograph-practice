package org.dfpl.chronograph.traversal.memory;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.dfpl.chronograph.traversal.TraversalEngine;
import org.junit.Test;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.Matchers.*;

public class GatherScatter {

	@Test
	public void gather() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		TraversalEngine engine = new TraversalEngine(graph, graph.getVertices(), Vertex.class, false);

		List<List<Vertex>> vertices = engine.gather().toList();

		assertThat(vertices.size(), equalTo(1));
		assertThat(vertices.get(0), contains(a, b, c));
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

	@Test
	public void transformWithUnboxing() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		Edge abLikes = graph.addEdge(a, b, "likes");
		Edge acLikes = graph.addEdge(a, c, "likes");
		graph.addEdge(c, c, "loves");

		TraversalEngine unboxEngine = new TraversalEngine(graph, a, Vertex.class, false);

		List<Edge> edges = unboxEngine.transform(new Function<Vertex, Collection<Edge>>() {

			@Override
			public Collection<Edge> apply(Vertex t) {
				return t.getEdges(Direction.OUT, "likes");
			}

		}, Edge.class, null, true).toList();

		assertThat(edges, containsInAnyOrder(abLikes, acLikes));
	}

	@Test
	public void transformWithoutUnboxing() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		Edge abLikes = graph.addEdge(a, b, "likes");
		Edge acLikes = graph.addEdge(a, c, "likes");
		graph.addEdge(c, c, "loves");

		TraversalEngine engine = new TraversalEngine(graph, a, Vertex.class, false);

		List<Set<Edge>> edges = engine.transform(new Function<Vertex, Collection<Edge>>() {

			@Override
			public Collection<Edge> apply(Vertex t) {
				return t.getEdges(Direction.OUT, "likes");
			}

		}, Collection.class, Edge.class, false).toList();

		assertThat(edges.size(), equalTo(1));
		assertThat(edges.get(0), containsInAnyOrder(abLikes, acLikes));
	}

	@Test
	public void transformWithoutUnboxingWithScatter() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		Edge abLikes = graph.addEdge(a, b, "likes");
		Edge acLikes = graph.addEdge(a, c, "likes");
		graph.addEdge(c, c, "loves");

		TraversalEngine engine = new TraversalEngine(graph, a, Vertex.class, false);

		List<Edge> edges = engine.transform(new Function<Vertex, Collection<Edge>>() {

			@Override
			public Collection<Edge> apply(Vertex t) {
				return t.getEdges(Direction.OUT, "likes");
			}

		}, Collection.class, Edge.class, false).scatter().toList();

		System.out.println(edges);

		assertThat(edges.size(), equalTo(2));
		assertThat(edges, containsInAnyOrder(abLikes, acLikes));
	}

}
