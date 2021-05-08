package org.dfpl.chronograph.traversal.memory;

import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.dfpl.chronograph.traversal.TraversalEngine;
import org.junit.Test;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class GraphToElement {

	@Test
	public void getVertices() {
		Graph graph = new ChronoGraph();

		graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("C");

		TraversalEngine engine = new TraversalEngine(graph, graph, Graph.class, false);

		assert (engine.V().toList().size() == 3);
	}

	@Test
	public void getEdges() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		graph.addEdge(a, b, "likes");
		graph.addEdge(a, c, "likes");
		graph.addEdge(a, b, "loves");
		graph.addEdge(c, c, "loves");

		TraversalEngine engine = new TraversalEngine(graph, graph, Graph.class, false);

		assert (engine.E().toList().size() == 4);
	}

	@Test
	public void getVerticesWithProp() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		a.setProperty("isOdd", true);
		b.setProperty("isOdd", false);
		c.setProperty("isOdd", true);

		TraversalEngine engine = new TraversalEngine(graph, graph, Graph.class, false);

		assert (engine.V("isOdd", false).toList().size() == 1);
	}

	@Test
	public void getEdgesWithProp() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		Edge abLikes = graph.addEdge(a, b, "likes");
		Edge acLikes = graph.addEdge(a, c, "likes");
		Edge abLoves = graph.addEdge(a, b, "loves");
		Edge ccLoves = graph.addEdge(c, c, "loves");

		abLikes.setProperty("isOdd", true);
		abLoves.setProperty("isOdd", true);
		acLikes.setProperty("isOdd", false);
		ccLoves.setProperty("weight", 2);

		TraversalEngine engine = new TraversalEngine(graph, graph, Graph.class, false);

		assert (engine.E("isOdd", true).toList().size() == 2);
	}
}
