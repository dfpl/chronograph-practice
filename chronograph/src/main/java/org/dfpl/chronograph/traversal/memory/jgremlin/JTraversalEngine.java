package org.dfpl.chronograph.traversal.memory.jgremlin;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.GremlinFluentPipeline;

@SuppressWarnings("rawtypes")
public class JTraversalEngine<S, E> implements GremlinFluentPipeline<S, E> {

	private Stream stream;
	@SuppressWarnings({ "unused" })
	private Class elementClass;

	public JTraversalEngine(Object starts, Class elementClass) {
		if (starts instanceof Graph) {
			stream = Stream.of(starts);
			this.elementClass = elementClass;
		} else if (starts instanceof Vertex) {
			stream = Stream.of(starts);
			this.elementClass = elementClass;
		} else if (starts instanceof Edge) {
			stream = Stream.of(starts);
			this.elementClass = elementClass;
		} else if (starts instanceof Collection) {
			stream = ((Collection) starts).stream();
			this.elementClass = elementClass;
		} else {
			throw new IllegalArgumentException();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public GremlinFluentPipeline<Graph, Vertex> V() {

		stream = stream.flatMap(g -> ((Graph) g).getVertices().stream());
		elementClass = Vertex.class;

		return (GremlinFluentPipeline<Graph, Vertex>) this;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List toList() {
		return (List) stream.collect(Collectors.toList());
	}

}
