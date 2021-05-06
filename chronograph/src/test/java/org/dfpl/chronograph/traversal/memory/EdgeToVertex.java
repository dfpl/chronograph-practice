package org.dfpl.chronograph.traversal.memory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.dfpl.chronograph.traversal.TraversalEngine;
import org.junit.Test;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class EdgeToVertex {

	@Test
	public void getInVertex() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		Edge abLikes = graph.addEdge(a, b, "likes");
		graph.addEdge(a, c, "likes");
		graph.addEdge(a, b, "loves");
		graph.addEdge(c, c, "loves");

		TraversalEngine engine = new TraversalEngine(graph, abLikes, Edge.class, false);

		assertThat(engine.inV().toList(), containsInAnyOrder("B"));
	}

	@Test
	public void getOutVertex() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		Edge abLikes = graph.addEdge(a, b, "likes");
		graph.addEdge(a, c, "likes");
		graph.addEdge(a, b, "loves");
		graph.addEdge(c, c, "loves");

		TraversalEngine engine = new TraversalEngine(graph, abLikes, Edge.class, false);

		assertThat(engine.outV().toList(), containsInAnyOrder("A"));
	}
}
