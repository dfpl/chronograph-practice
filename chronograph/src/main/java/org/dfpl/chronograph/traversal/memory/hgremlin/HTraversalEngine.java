package org.dfpl.chronograph.traversal.memory.hgremlin;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.common.Tokens.NC;
import com.tinkerpop.gremlin.GremlinFluentPipeline;
import com.tinkerpop.gremlin.GremlinPipeline;
import com.tinkerpop.gremlin.LoopBundle;

public class HTraversalEngine extends GremlinPipeline implements GremlinFluentPipeline {

	@SuppressWarnings("rawtypes")
	public HTraversalEngine(Graph graph, Object starts, Class elementClass) {
		super(graph, starts, elementClass, false);
	}

	@Override
	public GremlinFluentPipeline V() {
		// ensure that the element class is a graph
		if (!this.elementClass.equals(Graph.class))
			throw new IllegalArgumentException("Start object must be a Graph class");

		// flatten vertices -> [Vertex, Vertex, ..., Vertex] of graph
		stream = stream.flatMap(g -> ((Graph) g).getVertices().stream());

		// since the pipeline is from Graph to Vertex, change stream's elementClass to
		// Vertex class
		elementClass = Vertex.class;

		// return explicit type
		return this;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<?> toList() {
		return (List) stream.collect(Collectors.toList());
	}

	@Override
	public GremlinFluentPipeline V(String key, Object value) {
		stream = stream.flatMap(g -> ((Graph) g).getVertices(key, value).stream());
		elementClass = Vertex.class;
		return this;
	}

	@Override
	public GremlinFluentPipeline E() {
		stream = stream.flatMap(g -> ((Graph) g).getEdges().stream());

		elementClass = Edge.class;

		return this;
	}

	@Override
	public GremlinFluentPipeline E(String key, Object value) {
		stream = stream.flatMap(g -> ((Graph) g).getEdges(key, value).stream());
		elementClass = Edge.class;
		return this;
	}

	@Override
	public GremlinFluentPipeline id() {
		stream = stream.map(e -> ((Element) e).getId());

		elementClass = String.class;

		return this;
	}

	@Override
	public GremlinFluentPipeline element(Class<? extends Element> elementClass) {
		if (!elementClass.equals(Vertex.class) || !elementClass.equals(Edge.class))
			throw new IllegalArgumentException("The argument should be of Vertex or Edge class.");
		
		this.elementClass = elementClass;

		if (elementClass.equals(Vertex.class)) {
			stream = stream.flatMap(g -> ((Graph) g).getVertices().stream());
		} else if (elementClass.equals(Edge.class)) {
			stream = stream.flatMap(g -> ((Graph) g).getEdges().stream());
		} 
		
		return this;
	}

	@Override
	public GremlinFluentPipeline outE(String... labels) {
		stream = stream.flatMap(v -> ((Vertex) v).getEdges(Direction.OUT, labels).stream());
		elementClass = Edge.class;
		return this;
	}

	@Override
	public GremlinFluentPipeline inE(String... labels) {
		stream = stream.flatMap(v -> ((Vertex) v).getEdges(Direction.IN, labels).stream());
		elementClass = Edge.class;
		return this;
	}

	@Override
	public GremlinFluentPipeline outV() {
		stream = stream.map(e -> ((Edge) e).getVertex(Direction.OUT));
		elementClass = Vertex.class;
		return this;
	}

	@Override
	public GremlinFluentPipeline inV() {
		stream = stream.map(e -> ((Edge) e).getVertex(Direction.IN));
		elementClass = Vertex.class;
		return this;
	}

	@Override
	public GremlinFluentPipeline out(String... labels) {
		stream = stream.flatMap(v -> ((Vertex) v).getVertices(Direction.OUT, labels).stream());
		return this;
	}

	@Override
	public GremlinFluentPipeline in(String... labels) {
		stream = stream.flatMap(v -> ((Vertex) v).getVertices(Direction.IN, labels).stream());
		return this;
	}

	@Override
	public GremlinFluentPipeline gather() {
		stream = stream.collect(Collectors.toList()).stream();
		
		collectionClass = elementClass;
		elementClass = List.class;
		return this;
	}

	@Override
	public GremlinFluentPipeline scatter() {
		if (elementClass.equals(List.class)) {
			stream = stream.flatMap(list -> ((List<?>) list).stream());
			elementClass = collectionClass;
			collectionClass = null;
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <I, C> GremlinFluentPipeline transform(Function<I, C> function, boolean setUnboxing) {
		if(setUnboxing) {
			stream = stream.flatMap(entry -> {
				Collection<C> temp = (Collection<C>) function.apply((I) entry);
				return temp.stream();
			});
			
		} else {
			stream = stream.map(entry -> {
				elementClass = entry.getClass();
				return function.apply((I) entry);
			});
		}
		
		return this;
	}

	@Override
	public GremlinFluentPipeline dedup() {
		stream = stream.distinct();
		return this;
	}

	@Override
	public GremlinFluentPipeline random(Double lowerBound) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E> GremlinFluentPipeline filter(Predicate<E> predicate) {
		stream = stream.filter(entry -> {
			if (predicate.test((E) entry)) {
				return true;
			}
			return false;
		});
		return this;
	}

	@Override
	public <E> GremlinFluentPipeline sort(Comparator<E> comparator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GremlinFluentPipeline limit(long maxSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E> GremlinFluentPipeline sideEffect(Collection<E> collection) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> GremlinFluentPipeline sideEffect(Function<E, E> function) {
		stream = stream.map(entry -> {
			function.apply((E) entry);
			return entry;
		});
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <I, C1, C2> GremlinFluentPipeline ifThenElse(Predicate<I> ifPredicate, Function<I, C1> thenFunction,
			Function<I, C2> elseFunction, boolean setThenUnboxing, boolean setElseUnboxing) {
		stream = stream.map(entry -> {
			if (ifPredicate.test((I) entry)) {
				thenFunction.apply((I) entry);
			} else {
				elseFunction.apply((I) entry);
			}
			return entry;
		});
		return this;
	}

	@Override
	public GremlinFluentPipeline as(String pointer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E> GremlinFluentPipeline loop(String pointer, Predicate<LoopBundle<E>> whilePredicate) {
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

	@Override
	public GremlinFluentPipeline has(String key, Object value) {
		stream = stream.filter(element -> {
			Object property = ((Element) element).getProperty(key);
			return property != null && property.equals(value);
		});
		return this;
	}

	@Override
	public GremlinFluentPipeline has(String key, NC compareToken, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GremlinFluentPipeline has(String key) {
		stream = stream.filter(element -> {
			return ((Element) element).getPropertyKeys().contains(key);
		});

		return this;
	}
}
