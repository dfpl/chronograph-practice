package org.dfpl.chronograph.traversal.memory.hgremlin;

import static org.junit.Assert.*;

import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.junit.Test;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

public class VertexToVertex {

	@Test
	public void getOutVertex() {
		Graph graph = new ChronoGraph();
		
		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		
		graph.addEdge(a, b, "likes");
		graph.addEdge(a, a, "loves");
		
		HTraversalEngine<Vertex, Vertex> engine = new HTraversalEngine<Vertex, Vertex>(graph, a, Vertex.class);
		
		assertThat(engine.out("likes", "loves").toList(), containsInAnyOrder(a,b));
	}
	
	@Test
	public void getInVertex() {
		Graph graph = new ChronoGraph();
		
		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		
		graph.addEdge(a, b, "likes");
		graph.addEdge(a, a, "loves");
		
		HTraversalEngine<Vertex, Vertex> engine = new HTraversalEngine<Vertex, Vertex>(graph, a, Vertex.class);
		
		assertThat(engine.in("likes", "loves").toList(), containsInAnyOrder(a));
	}

}
