package com.tinkerpop.gremlin;

import java.util.List;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 * @author Jaewook Byun, Ph.D., Assistant Professor, Department of Software,
 *         Sejong University (slightly modify interface)
 */
public interface GremlinFluentPipeline<S, E> {

	/**
	 * Add a GraphQueryPipe to the end of the Pipeline. If optimizations are
	 * enabled, then the the next steps can fold into a GraphQueryPipe compilation.
	 *
	 * @return the extended Pipeline
	 */
	public GremlinFluentPipeline<Graph, Vertex> V();

	public List<E> toList();
}
