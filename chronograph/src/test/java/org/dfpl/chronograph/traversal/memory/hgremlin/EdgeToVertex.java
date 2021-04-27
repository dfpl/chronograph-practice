package org.dfpl.chronograph.traversal.memory.hgremlin;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import org.dfpl.chronograph.crud.memory.ChronoGraph;
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

		HTraversalEngine<Edge, Vertex> engine = new HTraversalEngine<Edge, Vertex>(graph, abLikes, Edge.class);

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

		HTraversalEngine<Edge, Vertex> engine = new HTraversalEngine<Edge, Vertex>(graph, abLikes, Edge.class);

		assertThat(engine.outV().toList(), containsInAnyOrder("A"));
	}
}
