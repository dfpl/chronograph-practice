package com.tinkerpop.gremlin;

import java.util.Collection;
import java.util.stream.Stream;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 * @author Jaewook Byun, Ph.D., Assistant Professor, Department of Software,
 *         Sejong University (slightly modify interface)
 */
@SuppressWarnings({ "rawtypes" })
public class GremlinPipeline<S, E> {

	protected Graph g;
	protected Stream stream;
	protected Class elementClass;

	/**
	 * Initialize TraversalEngine
	 * 
	 * @param g
	 * @param starts       of single Element (i.e., Graph, Vertex, Edge,
	 *                     VertexEvent, EdgeEvent) or Collection<Vertex> or
	 *                     Collection<Edge>
	 * @param elementClass either of Graph.class, Vertex.class, Edge.class
	 */
	public GremlinPipeline(Graph graph, Object starts, Class elementClass) {
		if (starts instanceof Graph || starts instanceof Vertex || starts instanceof Edge) {
			stream = Stream.of(starts);
		} else if (starts instanceof Collection) {
			stream = ((Collection) starts).stream();
		} else {
			throw new IllegalArgumentException();
		}
		this.elementClass = elementClass;
	}

}
