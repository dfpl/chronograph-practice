package org.dfpl.chronograph.traversal;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.dfpl.chronograph.crud.memory.ChronoGraph;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.common.Tokens.NC;
import com.tinkerpop.gremlin.GremlinFluentPipeline;
import com.tinkerpop.gremlin.GremlinPipeline;
import com.tinkerpop.gremlin.LoopBundle;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class TraversalEngine<S, E> extends GremlinPipeline<S, E> implements GremlinFluentPipeline<S, E> {

	public TraversalEngine(Graph graph, Object starts, Class<S> elementClass, boolean isParallel) {
		super(graph, starts, elementClass, isParallel);
	}

	// -------------------Transform: Graph to Element----------------------

	@Override
	public GremlinFluentPipeline<Graph, Vertex> V() {
		// Check the type of input
		// checkInputElementClass(Graph.class);

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
		// checkInputElementClass(Graph.class);

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
		// checkInputElementClass(Graph.class);

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
		// checkInputElementClass(Graph.class);

		// Modify stream
		stream = stream.flatMap(g -> ((Graph) g).getEdges(key, value).stream());

		// Set the class of element
		elementClass = Edge.class;

		// return the extended stream
		return (GremlinFluentPipeline<Graph, Edge>) this;
	}

	// -------------------Transform: Element <- -> String ----------------------

	@Override
	public GremlinFluentPipeline<Element, String> id() {
		// Check the type of input
		// checkInputElementClass(Vertex.class, Edge.class);

		// Modify stream
		stream = stream.map(e -> e.toString());

		// Set the class of element
		elementClass = String.class;

		// return the extended stream
		return (GremlinFluentPipeline<Element, String>) this;
	}

	@Override
	public GremlinFluentPipeline<String, ? extends Element> element(Class<? extends Element> elementClass) {
		// Check the type of input
		// if (!elementClass.equals(Vertex.class) || !elementClass.equals(Edge.class))
		// throw new IllegalArgumentException("The argument should be one of
		// Vertex.class or Edge.class");

		if (elementClass.equals(Vertex.class)) {
			// Modify stream
			stream = stream.map(e -> g.getVertex((String) e)).filter(e -> e != null);

			// Set the class of element
			this.elementClass = Vertex.class;

			// return the extended stream
			return (GremlinFluentPipeline<String, Vertex>) this;
		} else {
			// Modify stream
			stream = stream.map(e -> g.getEdge((String) e)).filter(e -> e != null);

			// Set the class of element
			this.elementClass = Edge.class;

			// return the extended stream
			return (GremlinFluentPipeline<String, Edge>) this;
		}
	}

	// -------------------Transform: Vertex to Edge ----------------------

	@Override
	public GremlinFluentPipeline<Vertex, Edge> outE(String... labels) {
		// Check the type of input
		// checkInputElementClass(Vertex.class, Edge.class);

		// Modify stream
		stream = stream.flatMap(v -> ((Vertex) v).getEdges(Direction.OUT, labels).stream());

		// Set the class of element
		elementClass = Edge.class;

		// return the extended stream
		return (GremlinFluentPipeline<Vertex, Edge>) this;
	}

	@Override
	public GremlinFluentPipeline<Vertex, Edge> inE(String... labels) {
		// Check the type of input
		// checkInputElementClass(Vertex.class, Edge.class);

		// Modify stream
		stream = stream.flatMap(v -> ((Vertex) v).getEdges(Direction.IN, labels).stream());

		// Set the class of element
		elementClass = Edge.class;

		// return the extended stream
		return (GremlinFluentPipeline<Vertex, Edge>) this;
	}

	// -------------------Transform: Edge to Vertex ----------------------

	@Override
	public GremlinFluentPipeline<Edge, Vertex> outV() {
		// Check the type of input
		// checkInputElementClass(Vertex.class, Edge.class);

		// Modify stream
		stream = stream.map(e -> ((Edge) e).getVertex(Direction.OUT));

		// Set the class of element
		elementClass = Vertex.class;

		// return the extended stream
		return (GremlinFluentPipeline<Edge, Vertex>) this;
	}

	@Override
	public GremlinFluentPipeline<Edge, Vertex> inV() {
		// Check the type of input
		// checkInputElementClass(Vertex.class, Edge.class);

		// Modify stream
		stream = stream.map(e -> ((Edge) e).getVertex(Direction.IN));

		// Set the class of element
		elementClass = Vertex.class;

		// return the extended stream
		return (GremlinFluentPipeline<Edge, Vertex>) this;
	}

	// -------------------Transform: Vertex to Vertex ----------------------

	@Override
	public GremlinFluentPipeline<Vertex, Vertex> out(String... labels) {
		// Check the type of input
		// checkInputElementClass(Vertex.class, Edge.class);

		// Modify stream
		stream = stream.flatMap(v -> ((Vertex) v).getVertices(Direction.OUT, labels).stream());

		// Set the class of element
		elementClass = Edge.class;

		// return the extended stream
		return (GremlinFluentPipeline<Vertex, Vertex>) this;
	}

	@Override
	public GremlinFluentPipeline<Vertex, Vertex> in(String... labels) {
		// Check the type of input
		// checkInputElementClass(Vertex.class, Edge.class);

		// Modify stream
		stream = stream.flatMap(v -> ((Vertex) v).getVertices(Direction.IN, labels).stream());

		// Set the class of element
		elementClass = Edge.class;

		// return the extended stream
		return (GremlinFluentPipeline<Vertex, Vertex>) this;
	}

	// -------------------Transform: Gather / Scatter ----------------------

	@SuppressWarnings("unused")
	@Override
	public <O> GremlinFluentPipeline<O, List<O>> gather() {
		// Check the type of input
		// checkInputElementClass(Vertex.class, Edge.class);

		// Modify stream
		List intermediate = stream.collect(Collectors.toList());

		// Set the class of element
		collectionClass = elementClass;
		elementClass = List.class;

		// return the extended stream
		return (GremlinFluentPipeline<O, List<O>>) this;
	}

	@Override
	public <I, O> GremlinFluentPipeline<I, O> scatter() {
		// Check the type of input
		// checkInputElementClass(Vertex.class, Edge.class);

		// Modify stream
		if (elementClass.equals(List.class)) {
			stream = stream.flatMap(list -> ((List) list).stream());
			elementClass = collectionClass;
			collectionClass = null;
			return (GremlinFluentPipeline<I, O>) this;
		} else {
			return (GremlinFluentPipeline<I, O>) this;
		}
	}

	@Override
	public <I, C, O> GremlinFluentPipeline<I, O> transform(Function<I, C> function, boolean setUnboxing) {
		// TODO Auto-generated method stub
		return null;
	}

	// -------------------Filter/Sort/Limit ----------------------

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
	public GremlinFluentPipeline<? extends Element, ? extends Element> has(String key) {
		// Check the type of input
		// checkInputElementClass(Vertex.class, Edge.class);

		// Modify stream
		stream = stream.filter(element -> {
			return ((Element) element).getPropertyKeys().contains(key);
		});

		// Set the class of element: no change of element class

		// return the extended stream
		return (GremlinFluentPipeline<? extends Element, ? extends Element>) this;
	}

	@Override
	public GremlinFluentPipeline<? extends Element, ? extends Element> has(String key, Object value) {
		// Check the type of input
		// checkInputElementClass(Vertex.class, Edge.class);

		// Modify stream
		stream = stream.filter(element -> {
			Object val = ((Element) element).getProperty(key);
			if (val != null && val.equals(value))
				return true;
			return false;
		});

		// Set the class of element: no change of element class

		// return the extended stream
		return (GremlinFluentPipeline<? extends Element, ? extends Element>) this;
	}

	@Override
	public GremlinFluentPipeline<? extends Element, ? extends Element> has(String key, NC t, Object value) {
		// Check the type of input
		// checkInputElementClass(Vertex.class, Edge.class);

		// Modify stream
		stream = stream.filter(element -> {
			Object val = ((Element) element).getProperty(key);
			if (val == null)
				return false;
			try {
				if (val instanceof Comparable) {
					int compare = ((Comparable) val).compareTo(value);
					if (compare == 0 && (t.equals(NC.$eq) || t.equals(NC.$gte) || t.equals(NC.$lte)))
						return true;
					else if (compare < 0 && (t.equals(NC.$lt) || t.equals(NC.$lte) || t.equals(NC.$ne)))
						return true;
					else if (compare > 0 && (t.equals(NC.$gt) || t.equals(NC.$gte) || t.equals(NC.$ne)))
						return true;
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
		return (GremlinFluentPipeline<? extends Element, ? extends Element>) this;
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

	// ------------------- Side Effect ----------------------

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

	// ------------------- Branch ----------------------

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

	// ------------------- Aggregation ----------------------

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
	public List toList() {
		return (List) stream.collect(Collectors.toList());
	}

	@SuppressWarnings("unused")
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

	public static void main(String[] args) {
		ChronoGraph g = new ChronoGraph();
		Vertex v1 = g.addVertex("1");
		Vertex v2 = g.addVertex("2");
		Vertex v3 = g.addVertex("3");

		g.addEdge(v1, v2, "l");
		g.addEdge(v1, v3, "l");

		System.out.println(new TraversalEngine<Graph, Edge>(g, g, Graph.class, false).V().outE("l").toList());

		java.util.Set<Integer> s1 = java.util.Set.of(1, 2, 3);
		java.util.Set<Integer> s2 = java.util.Set.of(2, 3, 4);

		// flatMap of set.streams has redundancy~ it is ok
		HashMap<Integer, Set<Integer>> map = new HashMap<Integer, Set<Integer>>();
		map.put(1, s1);
		map.put(2, s2);
		System.out.println(map.entrySet().stream().flatMap(m -> m.getValue().stream()).collect(Collectors.toList()));
	}
}
