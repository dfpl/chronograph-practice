package com.tinkerpop.gremlin;

import java.util.List;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.common.Tokens;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 * @author Jaewook Byun, Ph.D., Assistant Professor, Department of Software,
 *         Sejong University (slightly modify interface)
 */
public interface GremlinFluentPipeline<S, E> {

	/**
	 * Move traverser from Graph to all the vertices
	 * 
	 * Type: transform
	 * 
	 * Lazy Evaluation: true
	 * 
	 * Terminal Step: false
	 *
	 * @return the extended Pipeline
	 */
	public GremlinFluentPipeline<Graph, Vertex> V();

	/**
	 * Move traverser from Graph to all the vertices matched with key-value
	 *
	 * Type: transform
	 *
	 * Lazy Evaluation: true
	 * 
	 * Terminal Step: false
	 *
	 * @param key   they key that all the emitted vertices should be checked on
	 * @param value the value that all the emitted vertices should have for the key
	 * @return the extended Pipeline
	 */
	public GremlinFluentPipeline<Graph, Vertex> V(String key, Object value);

	/**
	 * Move traverser from Graph to all the edges
	 * 
	 * Type: transform
	 * 
	 * Lazy Evaluation: true
	 * 
	 * Terminal Step: false
	 * 
	 *
	 * @return the extended Pipeline
	 */
	public GremlinFluentPipeline<Graph, Edge> E();

	/**
	 * Move traverser from Graph to all the edges matched with key-value
	 * 
	 * Type: transform
	 * 
	 * Lazy Evaluation: true
	 * 
	 * Terminal Step: false
	 * 
	 * @param key   they key that all the emitted edges should be checked on
	 * @param value the value that all the emitted edges should have for the key
	 * @return the extended Pipeline
	 */
	public GremlinFluentPipeline<Graph, Edge> E(String key, Object value);

	/**
	 * Check if the vertex or edge has a property with provided key.
	 *
	 * Type: filter
	 * 
	 * Lazy Evaluation: true
	 * 
	 * Terminal Step: false
	 *
	 * @param key the property key to check
	 * @return the extended Pipeline
	 */
	public GremlinFluentPipeline<S, S> has(String key);

	/**
	 * Check if the vertex or edge has a property with provided key-value.
	 *
	 * Type: filter
	 * 
	 * Lazy Evaluation: true
	 * 
	 * Terminal Step: false
	 *
	 * @param key   the property key to check
	 * @param value the object to filter on (in an OR manner)
	 * @return the extended Pipeline
	 */
	public GremlinFluentPipeline<S, S> has(String key, Object value);

	/**
	 * Check if the vertex or edge has a property with provided key-predicate-value.
	 *
	 * Type: filter
	 * 
	 * Lazy Evaluation: true
	 * 
	 * Terminal Step: false
	 *
	 * @param key          the property key to check
	 * @param compareToken the comparison to use
	 * @param value        the object to filter on
	 * @return the extended Pipeline
	 */
	public GremlinFluentPipeline<S, S> has(String key, Tokens.NC compareToken, Object value);

	/**
	 * Move traversers from graph elements to their IDs (String)
	 *
	 * Type: transform
	 * 
	 * Lazy Evaluation: true
	 * 
	 * Terminal Step: false
	 * 
	 * @return the extended Pipeline
	 */
	public GremlinFluentPipeline<S, String> id();

	/**
	 * 
	 * Collect the current traversers in Gremlin as List
	 * 
	 * Lazy Evaluation: false
	 * 
	 * Terminal Step: true
	 * 
	 * @return the current traversers in Gremlin as List
	 */
	public List<E> toList();
}
