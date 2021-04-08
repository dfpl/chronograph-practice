package org.dfpl.chronograph.traversal.memory.jgremlin;

import java.util.List;
import java.util.stream.Collectors;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.GremlinFluentPipeline;
import com.tinkerpop.gremlin.GremlinPipeline;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class JTraversalEngine<S, E> extends GremlinPipeline<S, E> implements GremlinFluentPipeline<S, E> {

	public JTraversalEngine(Graph graph, Object starts, Class elementClass) {
		super(graph, starts, elementClass);
	}

	@Override
	public GremlinFluentPipeline<Graph, Vertex> V() {

		stream = stream.flatMap(g -> ((Graph) g).getVertices().stream());
		elementClass = Vertex.class;

		return (GremlinFluentPipeline<Graph, Vertex>) this;
	}

	@Override
	public List toList() {
		return (List) stream.collect(Collectors.toList());
	}

}
