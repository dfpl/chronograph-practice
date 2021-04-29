package org.dfpl.chronograph.traversal.memory.hgremlin;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.tinkerpop.blueprints.Direction;
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

	@SuppressWarnings("unchecked")
	@Override
	public GremlinFluentPipeline<Element, String> id() {
		stream = stream.map(e -> ((Element) e).getId());

		elementClass = String.class;

		return (GremlinFluentPipeline<Element, String>) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GremlinFluentPipeline<String, ? extends Element> element(Class<? extends Element> elementClass) {
		this.elementClass = elementClass;

		if (elementClass.equals(Vertex.class)) {
			stream = stream.flatMap(g -> ((Graph) g).getVertices().stream());
		} else if (elementClass.equals(Edge.class)) {
			stream = stream.flatMap(g -> ((Graph) g).getEdges().stream());
		} else {
			stream = null;
		}
		return (GremlinFluentPipeline<String, ? extends Element>) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GremlinFluentPipeline<Vertex, Edge> outE(String... labels) {
		stream = stream.flatMap(v -> ((Vertex) v).getEdges(Direction.OUT, labels).stream());
		elementClass = Edge.class;
		return (GremlinFluentPipeline<Vertex, Edge>) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GremlinFluentPipeline<Vertex, Edge> inE(String... labels) {
		stream = stream.flatMap(v -> ((Vertex) v).getEdges(Direction.IN, labels).stream());
		elementClass = Edge.class;
		return (GremlinFluentPipeline<Vertex, Edge>) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GremlinFluentPipeline<Edge, Vertex> outV() {
		stream = stream.map(e -> ((Edge) e).getVertex(Direction.OUT));
		elementClass = Vertex.class;
		return (GremlinFluentPipeline<Edge, Vertex>) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GremlinFluentPipeline<Edge, Vertex> inV() {
		stream = stream.map(e -> ((Edge) e).getVertex(Direction.IN));
		elementClass = Vertex.class;
		return (GremlinFluentPipeline<Edge, Vertex>) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GremlinFluentPipeline<Vertex, Vertex> out(String... labels) {
		stream = stream.flatMap(v -> ((Vertex) v).getVertices(Direction.OUT, labels).stream());
		return (GremlinFluentPipeline<Vertex, Vertex>) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GremlinFluentPipeline<Vertex, Vertex> in(String... labels) {
		stream = stream.flatMap(v -> ((Vertex) v).getVertices(Direction.IN, labels).stream());

		return (GremlinFluentPipeline<Vertex, Vertex>) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <O> GremlinFluentPipeline<O, List<O>> gather() {
		stream = (Stream<List<O>>) stream.collect(Collectors.toList()).stream();

		collectionClass = elementClass;
		elementClass = List.class;
		return (GremlinFluentPipeline<O, List<O>>) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <I, O> GremlinFluentPipeline<I, O> scatter() {
		if (elementClass.equals(List.class)) {
			stream = stream.flatMap(list -> ((List) list).stream());
			elementClass = collectionClass;
			collectionClass = null;
		}
		return (GremlinFluentPipeline<I, O>) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <I, C, O> GremlinFluentPipeline<I, O> transform(Function<I, C> function, boolean setUnboxing) {
		stream = stream.map(entry -> {
			function.apply((I) entry);
			return entry;
		});

		return (GremlinFluentPipeline<I, O>) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <I> GremlinFluentPipeline<I, I> dedup() {
		stream = stream.distinct();
		return (GremlinFluentPipeline<I, I>) this;
	}

	@Override
	public <I> GremlinFluentPipeline<I, I> random(Double lowerBound) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <I> GremlinFluentPipeline<I, I> filter(Predicate<S> predicate) {
		stream = stream.filter(entry -> {
			if (predicate.equals(entry)) {
				return true;
			}
			return false;
		});
		return (GremlinFluentPipeline<I, I>) this;
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
		collection = (Collection<I>) stream.collect(Collectors.toList());
		return (GremlinFluentPipeline<I, I>) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <I> GremlinFluentPipeline<I, I> sideEffect(Function<I, I> function) {
		stream = stream.flatMap(entry -> {
			function.apply((I) entry);
			return (Stream<? extends Object>) entry;
		});
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <I, C1, C2, O> GremlinFluentPipeline<I, O> ifThenElse(Predicate<I> ifPredicate, Function<I, C1> thenFunction,
			Function<I, C2> elseFunction, boolean setThenUnboxing, boolean setElseUnboxing) {
		stream = stream.flatMap(entry -> {
			if (ifPredicate.test((I) entry)) {
				thenFunction.apply((I) entry);
			} else {
				elseFunction.apply((I) entry);
			}
			return (Stream<? extends Object>) entry;
		});
		return (GremlinFluentPipeline<I, O>) this;
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
			Object property = ((Element) element).getProperty(key);
			return property != null && property.equals(value);
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
