package org.dfpl.chronograph.traversal.memory.hgremlin;

import static org.junit.Assert.*;

import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.junit.Test;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class TestFluentPipeline {

	@Test
	public void getVertices() {
		Graph graph = new ChronoGraph();

		graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("C");

		HTraversalEngine<Graph, Vertex> engine = new HTraversalEngine<Graph, Vertex>(graph, graph, Graph.class);

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

		HTraversalEngine<Graph, Edge> engine = new HTraversalEngine<Graph, Edge>(graph, graph, Graph.class);

		assert (engine.E().toList().size() == 4);
	}

	@Test
	public void getVerticesWithProp() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		a.setProperty("test", true);
		b.setProperty("test", false);
		c.setProperty("not", true);

		HTraversalEngine<Graph, Vertex> engine = new HTraversalEngine<Graph, Vertex>(graph, graph, Graph.class);

		assert (engine.V("test", true).toList().size() == 1);
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

		abLikes.setProperty("test", true);
		abLoves.setProperty("test", true);
		acLikes.setProperty("test", false);
		ccLoves.setProperty("notest", "a");

		HTraversalEngine<Graph, Edge> engine = new HTraversalEngine<Graph, Edge>(graph, graph, Graph.class);

		assert (engine.E("test", true).toList().size() == 2);
	}

	@Test
	public void getElementsWithPropKey() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		a.setProperty("test", true);
		b.setProperty("test", false);
		c.setProperty("not", true);

		Edge abLikes = graph.addEdge(a, b, "likes");
		Edge acLikes = graph.addEdge(a, c, "likes");
		Edge abLoves = graph.addEdge(a, b, "loves");
		Edge ccLoves = graph.addEdge(c, c, "loves");

		abLikes.setProperty("test", true); // included
		abLoves.setProperty("test", true); // included
		acLikes.setProperty("test", false); // included
		ccLoves.setProperty("notest", "a");

		HTraversalEngine<Edge, Edge> engine = new HTraversalEngine<Edge, Edge>(graph, graph.getEdges(), Edge.class);

		assert (engine.has("test").toList().size() == 3);
	}

	@Test
	public void getElementsWithPropKeyAndValue() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		a.setProperty("test", true); // included
		b.setProperty("test", false);
		c.setProperty("not", true);

		Edge abLikes = graph.addEdge(a, b, "likes");
		Edge acLikes = graph.addEdge(a, c, "likes");
		Edge abLoves = graph.addEdge(a, b, "loves");
		Edge ccLoves = graph.addEdge(c, c, "loves");

		abLikes.setProperty("test", true);
		abLoves.setProperty("test", true);
		acLikes.setProperty("test", false);
		ccLoves.setProperty("notest", "a");

		HTraversalEngine<Vertex, Vertex> engine = new HTraversalEngine<Vertex, Vertex>(graph, graph.getVertices(), Vertex.class);

		assert (engine.has("test", true).toList().size() == 1);
	}

}
