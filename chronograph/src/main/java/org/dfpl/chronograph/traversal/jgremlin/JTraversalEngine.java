package org.dfpl.chronograph.traversal.jgremlin;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.common.Tokens.NC;
import com.tinkerpop.gremlin.GremlinFluentPipeline;
import com.tinkerpop.gremlin.GremlinPipeline;
import com.tinkerpop.gremlin.LoopBundle;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class JTraversalEngine<S, E> extends GremlinPipeline<S, E> implements GremlinFluentPipeline<S, E> {

	public JTraversalEngine(Graph graph, Object starts, Class<S> elementClass, boolean isParallel) {
		super(graph, starts, elementClass, isParallel);
	}

	@Override
	public GremlinFluentPipeline<Graph, Vertex> V() {
		// Check the type of input
		checkInputElementClass(Graph.class);

		// Modify stream
		stream = stream.flatMap(g -> ((Graph) g).getVertices().stream());

		// Set the class of element
		elementClass = Vertex.class;

		// return the extended stream
		return (GremlinFluentPipeline<Graph, Vertex>) this;
	}

	@Override
	public GremlinFluentPipeline<Graph, Vertex> V(String key, Object value) {
		// Check the type of input
		checkInputElementClass(Graph.class);

		// Modify stream
		stream = stream.flatMap(g -> ((Graph) g).getVertices(key, value).stream());

		// Set the class of element
		elementClass = Vertex.class;

		// return the extended stream
		return (GremlinFluentPipeline<Graph, Vertex>) this;
	}

	@Override
	public GremlinFluentPipeline<Graph, Edge> E() {
		// Check the type of input
		checkInputElementClass(Graph.class);

		// Modify stream
		stream = stream.flatMap(g -> ((Graph) g).getEdges().stream());

		// Set the class of element
		elementClass = Edge.class;

		// return the extended stream
		return (GremlinFluentPipeline<Graph, Edge>) this;
	}

	@Override
	public GremlinFluentPipeline<Graph, Edge> E(String key, Object value) {
		// Check the type of input
		checkInputElementClass(Graph.class);

		// Modify stream
		stream = stream.flatMap(g -> ((Graph) g).getEdges(key, value).stream());

		// Set the class of element
		elementClass = Edge.class;

		// return the extended stream
		return (GremlinFluentPipeline<Graph, Edge>) this;
	}

	@Override
	public GremlinFluentPipeline<S, S> has(String key) {
		// Check the type of input
		checkInputElementClass(Vertex.class, Edge.class);

		// Modify stream
		stream = stream.filter(element -> {
			if (element instanceof Vertex) {
				return ((Vertex) element).getPropertyKeys().contains(key);
			} else {
				return ((Edge) element).getPropertyKeys().contains(key);
			}
		});

		// Set the class of element: no change of element class

		// return the extended stream
		return (GremlinFluentPipeline<S, S>) this;
	}

	@Override
	public GremlinFluentPipeline<S, S> has(String key, Object value) {
		// Check the type of input
		checkInputElementClass(Vertex.class, Edge.class);

		// Modify stream
		stream = stream.filter(element -> {
			if (element instanceof Vertex) {
				Object val = ((Vertex) element).getProperty(key);
				if (val != null && val.equals(value))
					return true;
				return false;
			} else {
				Object val = ((Edge) element).getProperty(key);
				if (val != null && val.equals(value))
					return true;
				return false;
			}
		});

		// Set the class of element: no change of element class

		// return the extended stream
		return (GremlinFluentPipeline<S, S>) this;
	}

	@Override
	public GremlinFluentPipeline<S, S> has(String key, NC t, Object value) {
		// Check the type of input
		checkInputElementClass(Vertex.class, Edge.class);

		// Modify stream
		stream = stream.filter(element -> {

			Object val = null;

			if (element instanceof Vertex) {
				val = ((Vertex) element).getProperty(key);
			} else {
				val = ((Edge) element).getProperty(key);
			}
			val = ((Vertex) element).getProperty(key);
			if (val == null)
				return false;
			try {
				if (val instanceof Comparable) {
					int compare = ((Comparable) val).compareTo(value);
					// Returns anegative integer, zero, or a positive integer
					// as this object is lessthan, equal to, or greater than the specified object.
					if (compare == 0 && (t.equals(NC.$eq) || t.equals(NC.$gte) || t.equals(NC.$lte))) {
						return true;
					} else if (compare < 0 && (t.equals(NC.$lt) || t.equals(NC.$lte) || t.equals(NC.$ne))) {
						return true;
					} else if (compare > 0 && (t.equals(NC.$gt) || t.equals(NC.$gte) || t.equals(NC.$ne))) {
						return true;
					}
					return false;
				} else {
					return false;
				}
			} catch (RuntimeException e) {
				return false;
			}
		});

		// Set the class of element: no change of element class

		// return the extended stream
		return (GremlinFluentPipeline<S, S>) this;
	}

	@Override
	public GremlinFluentPipeline<S, String> id() {
		// Check the type of input
		checkInputElementClass(Vertex.class, Edge.class);

		// Modify stream
		stream = stream.map(e -> e.toString());

		// Set the class of element
		elementClass = String.class;

		// return the extended stream
		return (GremlinFluentPipeline<S, String>) this;
	}

	@Override
	public GremlinFluentPipeline<String, E> element(Class<E> elementClass) {
		// Check the type of input
		if (!elementClass.equals(Vertex.class) || !elementClass.equals(Edge.class))
			throw new IllegalArgumentException("The argument should be one of Vertex.class or Edge.class");

		if (elementClass.equals(Vertex.class)) {
			// Modify stream
			stream = stream.map(e -> g.getVertex((String) e)).filter(e -> e != null);

			// Set the class of element
			this.elementClass = Vertex.class;

			// return the extended stream
			return (GremlinFluentPipeline<String, E>) this;
		} else {
			// Modify stream
			stream = stream.map(e -> g.getEdge((String) e)).filter(e -> e != null);

			// Set the class of element
			this.elementClass = Edge.class;

			// return the extended stream
			return (GremlinFluentPipeline<String, E>) this;
		}
	}

	@Override
	public List toList() {
		return (List) stream.collect(Collectors.toList());
	}

	private void checkInputElementClass(Class... correctClasses) {
		boolean isMatched = false;
		for (Class correct : correctClasses) {
			if (elementClass == correct) {
				isMatched = true;
				break;
			}
		}
		if (isMatched == false) {
			throw new UnsupportedOperationException(
					"Current stream element class " + elementClass + " should be one of " + correctClasses);
		}
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
	public GremlinFluentPipeline<S, List<S>> gather() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GremlinFluentPipeline<S, E> scatter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> GremlinFluentPipeline<S, T> transform(Function<S, E> function, boolean setUnboxing) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GremlinFluentPipeline<S, S> dedup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GremlinFluentPipeline<S, S> random(Double lowerBound) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GremlinFluentPipeline<S, S> filter(Predicate<S> predicate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GremlinFluentPipeline<S, S> sideEffect(Collection<S> collection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GremlinFluentPipeline<S, S> sideEffect(Function<S, S> function) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GremlinFluentPipeline<S, E> ifThenElse(Predicate<S> ifPredicate, Function<S, ?> thenFunction,
			Function<S, ?> elseFunction, boolean setThenUnboxing, boolean setElseUnboxing) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GremlinFluentPipeline<S, E> as(String pointer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GremlinFluentPipeline<S, E> loop(String pointer, Predicate<LoopBundle<S>> whilePredicate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GremlinFluentPipeline<S, S> sort(Comparator<S> comparator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GremlinFluentPipeline<S, S> limit(long maxSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Map<T, List<S>> groupBy(Function<S, T> classifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Map<T, Long> groupCount(Function<S, T> classifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T reduce(T base, BinaryOperator<T> reducer) {
		// TODO Auto-generated method stub
		return null;
	}

}
