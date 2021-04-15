package com.tinkerpop.gremlin;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.common.Tokens;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 * @author Jaewook Byun, Ph.D., Assistant Professor, Department of Software,
 *         Sejong University (slightly modify interface)
 * @param <S> a type of start traverser
 * @param <E> a type of end traverser
 */
public interface GremlinFluentPipeline<S, E> {

	// -------------------Transform: Graph to Element----------------------

	/**
	 * Move traverser from a graph to all the vertices
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

	// -------------------Transform: Element <- -> String ----------------------

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
	public GremlinFluentPipeline<Element, String> id();

	/**
	 * Move traversers from IDs of graph elements to the elements
	 *
	 * Type: transform
	 * 
	 * Lazy Evaluation: true
	 * 
	 * Terminal Step: false
	 * 
	 * @return the extended Pipeline
	 */
	public GremlinFluentPipeline<String, ? extends Element> element(Class<? extends Element> elementClass);

	// -------------------Transform: Vertex to Edge ----------------------

	/**
	 * Move traverser from each vertex to out-going edges
	 * 
	 * Type: transform
	 * 
	 * Lazy Evaluation: true
	 * 
	 * Terminal Step: false
	 * 
	 * @return the extended Pipeline
	 */
	public GremlinFluentPipeline<Vertex, Edge> outE(String... labels);

	/**
	 * Move traverser from each vertex to in-going edges
	 * 
	 * Type: transform
	 * 
	 * Lazy Evaluation: true
	 * 
	 * Terminal Step: false
	 * 
	 * @return the extended Pipeline
	 */
	public GremlinFluentPipeline<Vertex, Edge> inE(String... labels);

	// -------------------Transform: Edge to Vertex ----------------------

	/**
	 * Move traverser from each edge to out vertex
	 * 
	 * Type: transform
	 * 
	 * Lazy Evaluation: true
	 * 
	 * Terminal Step: false
	 * 
	 * @return the extended Pipeline
	 */
	public GremlinFluentPipeline<Edge, Vertex> outV();

	/**
	 * Move traverser from each edge to in vertex
	 * 
	 * Type: transform
	 * 
	 * Lazy Evaluation: true
	 * 
	 * Terminal Step: false
	 * 
	 * @return the extended Pipeline
	 */
	public GremlinFluentPipeline<Edge, Vertex> inV();

	// -------------------Transform: Vertex to Vertex ----------------------
	/**
	 * Move traverser from each vertex to out-going vertices
	 * 
	 * Type: transform
	 * 
	 * Lazy Evaluation: true
	 * 
	 * Terminal Step: false
	 * 
	 * @return the extended Pipeline
	 */
	public GremlinFluentPipeline<Vertex, Vertex> out(String... labels);

	/**
	 * Move traverser from each vertex to out-going vertices
	 * 
	 * Type: transform
	 * 
	 * Lazy Evaluation: true
	 * 
	 * Terminal Step: false
	 * 
	 * @return the extended Pipeline
	 */
	public GremlinFluentPipeline<Vertex, Vertex> in(String... labels);

	// -------------------Transform: Gather / Scatter ----------------------

	/**
	 * All the objects previous to this step are aggregated in a greedy fashion and
	 * emitted as a List (boxing).
	 * 
	 * Type: transform
	 * 
	 * Lazy Evaluation: false
	 * 
	 * Terminal Step: false
	 * 
	 * @return the extended Pipeline
	 */
	public <O> GremlinFluentPipeline<O, List<O>> gather();

	/**
	 * Any input extending Collection is unboxed. If the input is not extending
	 * Collection, the input is emitted as it is.
	 * 
	 * Type: transform
	 * 
	 * Lazy Evaluation: true
	 * 
	 * Terminal Step: false
	 * 
	 * @return the extended Pipeline
	 */
	public <I, O> GremlinFluentPipeline<I, O> scatter();

	/**
	 * Given an input, the provided function is computed on the input and the output
	 * of that function is emitted.
	 * 
	 * Type: transform
	 * 
	 * Lazy Evaluation: true
	 * 
	 * Terminal Step: false
	 *
	 * @param function     the customer transform function
	 * @param setUnboxing: if E is extending of Collection, it would be unboxed and
	 *                     emitted.
	 * @return the extended Pipeline
	 */
	public <I, C, O> GremlinFluentPipeline<I, O> transform(Function<I, C> function, boolean setUnboxing);

	// -------------------Filter/Sort/Limit ----------------------

	/**
	 * Deduplicate the traversers
	 * 
	 * Type: filter
	 * 
	 * Lazy Evaluation: true
	 * 
	 * Terminal Step: false
	 * 
	 * @param predicate the custom filter function
	 * @return the extended Pipeline
	 */
	public <I> GremlinFluentPipeline<I, I> dedup();

	/**
	 * A biased coin toss determines if the object is emitted or not.
	 * 
	 * @param lowerBound if Random.getDouble() is larger than lowerBound, retained,
	 *                   else filtered. (lowerBound is between 0 to 1)
	 * @return the extended Pipeline
	 * 
	 */
	public <I> GremlinFluentPipeline<I, I> random(Double lowerBound);

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
	public GremlinFluentPipeline<? extends Element, ? extends Element> has(String key);

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
	public GremlinFluentPipeline<? extends Element, ? extends Element> has(String key, Object value);

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
	public GremlinFluentPipeline<? extends Element, ? extends Element> has(String key, Tokens.NC compareToken,
			Object value);

	/**
	 * Given an input, the provided predicate evaluates the input and only inputs
	 * where predicate for an input is true are emitted.
	 * 
	 * Type: filter
	 * 
	 * Lazy Evaluation: true
	 * 
	 * Terminal Step: false
	 * 
	 * @param predicate the custom filter function
	 * @return the extended Pipeline
	 */
	public <I> GremlinFluentPipeline<I, I> filter(Predicate<S> predicate);

	/**
	 * Sort the traversers based on comparator
	 * 
	 * Type: filter
	 * 
	 * Lazy Evaluation: true
	 * 
	 * Terminal Step: false
	 * 
	 * @param comparator
	 * @return the extended Pipeline
	 */
	public <I> GremlinFluentPipeline<I, I> sort(Comparator<S> comparator);

	/**
	 * Limit the number of traversers
	 * 
	 * Type: filter
	 * 
	 * Lazy Evaluation: true
	 * 
	 * Terminal Step: false
	 * 
	 * @param maxSize
	 * @return the extended Pipeline
	 */
	public <I> GremlinFluentPipeline<I, I> limit(long maxSize);

	// ------------------- Side Effect ----------------------

	/**
	 * 
	 * Store traversers into a collection
	 *
	 * Type: sideEffect
	 * 
	 * Lazy Evaluation: true
	 * 
	 * Terminal Step: false
	 * 
	 * @param collection to store traversers
	 * @return
	 */
	public <I> GremlinFluentPipeline<I, I> sideEffect(Collection<I> collection);

	/**
	 * The provided function is invoked while the incoming object is just emitted to
	 * the outgoing object.
	 * 
	 * Type: sideEffect
	 * 
	 * Lazy Evaluation: true
	 * 
	 * Terminal Step: false
	 * 
	 * @param function the function should return the intact argument
	 * @return the extended Pipeline
	 */
	public <I> GremlinFluentPipeline<I, I> sideEffect(Function<I, I> function);

	// ------------------- Branch ----------------------

	/**
	 * If the ifPredicate for an input is true, thenFunction will be applied Else,
	 * elseFunction will be applied
	 * 
	 * @param ifPredicate
	 * @param thenFunction
	 * @param elseFunction
	 * @param setThenUnboxing refer to {@link #transform(Function, boolean)}
	 * @param setElseUnboxing refer to {@link #transform(Function, boolean)}
	 * @return the extended Pipeline
	 */
	public <I, C1, C2, O> GremlinFluentPipeline<I, O> ifThenElse(Predicate<I> ifPredicate, Function<I, C1> thenFunction,
			Function<I, C2> elseFunction, boolean setThenUnboxing, boolean setElseUnboxing);

	/**
	 * Useful for naming steps and is used in conjunction with loop
	 * 
	 * @param pointer for the loop
	 * @return the extended Pipeline
	 */
	public <I> GremlinFluentPipeline<I, I> as(String pointer);

	/**
	 * loop repeats a part of a pipeline between as('name') to loop('name',
	 * whilePredicate);
	 * 
	 * @param pointer        within a pipeline
	 * @param whilePredicate if true, go to the pointer
	 * @return the extended Pipeline
	 */
	public <I, O> GremlinFluentPipeline<I, O> loop(String pointer, Predicate<LoopBundle<I>> whilePredicate);

	// ------------------- Aggregation ----------------------

	/**
	 * Group the traversers based on results of classifier
	 * 
	 * Type: transform
	 * 
	 * Lazy Evaluation: false
	 * 
	 * Terminal Step: true
	 * 
	 * @param classifier
	 * @return the extended Pipeline
	 */
	public <I, T> Map<T, List<I>> groupBy(Function<I, T> classifier);

	/**
	 * Counting the traversers grouped by results of classifier
	 * 
	 * Type: transform
	 * 
	 * Lazy Evaluation: false
	 * 
	 * Terminal Step: true
	 * 
	 * @param classifier
	 * @return the extended Pipeline
	 */
	public <I, T> Map<T, Long> groupCount(Function<I, T> classifier);

	/**
	 * Reduce the traversers based on reducer
	 * 
	 * Type: transform
	 * 
	 * Lazy Evaluation: false
	 * 
	 * Terminal Step: true
	 * 
	 * @param classifier
	 * @return the extended Pipeline
	 */
	public <T> T reduce(T base, BinaryOperator<T> reducer);

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
	public <I> List<I> toList();
}
