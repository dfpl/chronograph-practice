package org.dfpl.chronograph.traversal.memory.hgremlin;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.common.Tokens.NC;
import com.tinkerpop.gremlin.GremlinFluentPipeline;
import com.tinkerpop.gremlin.GremlinPipeline;
import com.tinkerpop.gremlin.LoopBundle;

public class HTraversalEngine<S, E> extends GremlinPipeline<S, E> implements GremlinFluentPipeline<S, E> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HTraversalEngine(Graph graph, Object starts, Class elementClass) {
		super(graph, starts, elementClass, false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public GremlinFluentPipeline<Graph, Vertex> V() {
		// ensure that the element class is a graph
		if (!this.elementClass.equals(Graph.class))
			throw new IllegalArgumentException("Start object must be a Graph class");

		// flatten vertices -> [Vertex, Vertex, ..., Vertex] of graph
		stream = stream.flatMap(g -> ((Graph) g).getVertices().stream());

		// since the pipeline is from Graph to Vertex, change stream's elementClass to
		// Vertex class
		elementClass = Vertex.class;

		// return explicit type
		return (GremlinFluentPipeline<Graph, Vertex>) this;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<E> toList() {
		return (List) stream.collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public GremlinFluentPipeline<Graph, Vertex> V(String key, Object value) {
		stream = stream.flatMap(g -> ((Graph) g).getVertices(key, value).stream());
		elementClass = Vertex.class;

		return (GremlinFluentPipeline<Graph, Vertex>) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GremlinFluentPipeline<Graph, Edge> E() {
		stream = stream.flatMap(g -> ((Graph) g).getEdges().stream());

		elementClass = Edge.class;

		return (GremlinFluentPipeline<Graph, Edge>) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GremlinFluentPipeline<Graph, Edge> E(String key, Object value) {
		stream = stream.flatMap(g -> ((Graph) g).getEdges(key, value).stream());
		elementClass = Edge.class;
		return (GremlinFluentPipeline<Graph, Edge>) this;
	}

	@Override
	public GremlinFluentPipeline<Element, String> id() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GremlinFluentPipeline<String, ? extends Element> element(Class<? extends Element> elementClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GremlinFluentPipeline<Vertex, Edge> outE(String... labels) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GremlinFluentPipeline<Vertex, Edge> inE(String... labels) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GremlinFluentPipeline<Edge, Vertex> outV() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GremlinFluentPipeline<Edge, Vertex> inV() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GremlinFluentPipeline<Vertex, Vertex> out(String... labels) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GremlinFluentPipeline<Vertex, Vertex> in(String... labels) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <O> GremlinFluentPipeline<O, List<O>> gather() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <I, O> GremlinFluentPipeline<I, O> scatter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <I, C, O> GremlinFluentPipeline<I, O> transform(Function<I, C> function, boolean setUnboxing) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <I> GremlinFluentPipeline<I, I> dedup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <I> GremlinFluentPipeline<I, I> random(Double lowerBound) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <I> GremlinFluentPipeline<I, I> filter(Predicate<S> predicate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <I> GremlinFluentPipeline<I, I> sort(Comparator<S> comparator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <I> GremlinFluentPipeline<I, I> limit(long maxSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <I> GremlinFluentPipeline<I, I> sideEffect(Collection<I> collection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <I> GremlinFluentPipeline<I, I> sideEffect(Function<I, I> function) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <I, C1, C2, O> GremlinFluentPipeline<I, O> ifThenElse(Predicate<I> ifPredicate, Function<I, C1> thenFunction,
			Function<I, C2> elseFunction, boolean setThenUnboxing, boolean setElseUnboxing) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <I> GremlinFluentPipeline<I, I> as(String pointer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <I, O> GremlinFluentPipeline<I, O> loop(String pointer, Predicate<LoopBundle<I>> whilePredicate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <I, T> Map<T, List<I>> groupBy(Function<I, T> classifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <I, T> Map<T, Long> groupCount(Function<I, T> classifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T reduce(T base, BinaryOperator<T> reducer) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GremlinFluentPipeline<? extends Element, ? extends Element> has(String key, Object value) {
		stream = stream.filter(element -> {
			return ((Element) element).getProperty(key).equals(value);
		});
		
		return (GremlinFluentPipeline<? extends Element, ? extends Element>) this;
	}

	@Override
	public GremlinFluentPipeline<? extends Element, ? extends Element> has(String key, NC compareToken, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GremlinFluentPipeline<? extends Element, ? extends Element> has(String key) {
		stream = stream.filter(element -> {
			return ((Element) element).getPropertyKeys().contains(key);
		});
		
		return (GremlinFluentPipeline<? extends Element, ? extends Element>) this;
	}
}
