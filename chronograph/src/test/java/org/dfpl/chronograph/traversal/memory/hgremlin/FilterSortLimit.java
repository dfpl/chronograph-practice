package org.dfpl.chronograph.traversal.memory.hgremlin;


import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.junit.Test;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class FilterSortLimit {

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
