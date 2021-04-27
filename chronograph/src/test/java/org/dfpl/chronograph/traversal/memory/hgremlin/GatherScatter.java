package org.dfpl.chronograph.traversal.memory.hgremlin;

import static org.junit.Assert.*;

import java.util.List;

import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.junit.Test;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.GremlinFluentPipeline;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

public class GatherScatter {

	@Test
	public void gather() {
		Graph graph = new ChronoGraph();
		
		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");
		
		HTraversalEngine<Vertex, List<Vertex>> engine = new HTraversalEngine<Vertex, List<Vertex>>(graph, graph.getVertices(), Graph.class);
		
		GremlinFluentPipeline<Object, List<Object>> vertices = engine.gather();
		
		assertThat(vertices.toList(), containsInAnyOrder("A", "B", "C"));
	}
	
	@Test
	public void scatter() {
		Graph graph = new ChronoGraph();
		
		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");
		
		HTraversalEngine<Vertex, List<Vertex>> engine = new HTraversalEngine<Vertex, List<Vertex>>(graph, graph.getVertices(), Graph.class);
		
		assertThat(engine.scatter().toList(), containsInAnyOrder("A", "B", "C"));
	}

}
