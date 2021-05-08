package org.dfpl.chronograph.traversal;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.dfpl.chronograph.common.Step;
import org.dfpl.chronograph.common.Tokens.NC;
import org.dfpl.chronograph.crud.memory.ChronoGraph;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.GremlinFluentPipeline;
import com.tinkerpop.gremlin.GremlinPipeline;
import com.tinkerpop.gremlin.LoopBundle;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class TraversalEngine extends GremlinPipeline implements GremlinFluentPipeline {

	public TraversalEngine(Graph graph, Object starts, Class<?> elementClass, boolean isParallel) {
		super(graph, starts, elementClass, isParallel);
	}

	// -------------------Transform: Graph to Element----------------------

	@Override
	public GremlinFluentPipeline V() {
		// Check the type of input
		checkInputElementClass(Graph.class);

		// Modify stream
		stream = stream.flatMap(g -> ((Graph) g).getVertices().stream());

		// Set the class of element
		elementClass = Vertex.class;

		// Step Update
		Class[] args = {};
		Step step = new Step(this.getClass().getName(), "V", args);
		stepList.add(step);

		// return the extended stream
		return this;
	}

	@Override
	public GremlinFluentPipeline V(String key, Object value) {
		// Check the type of input
		checkInputElementClass(Graph.class);

		// Modify stream
		stream = stream.flatMap(g -> ((Graph) g).getVertices(key, value).stream());

		// Set the class of element
		elementClass = Vertex.class;

		// Step Update
		Class[] args = { String.class, Object.class };
		Step step = new Step(this.getClass().getName(), "V", args, key, value);
		stepList.add(step);

		// return the extended stream
		return this;
	}

	@Override
	public GremlinFluentPipeline E() {
		// Check the type of input
		checkInputElementClass(Graph.class);

		// Modify stream
		stream = stream.flatMap(g -> ((Graph) g).getEdges().stream());

		// Set the class of element
		elementClass = Edge.class;

		// Step Update
		Class[] args = {};
		Step step = new Step(this.getClass().getName(), "E", args);
		stepList.add(step);

		// return the extended stream
		return this;
	}

	@Override
	public GremlinFluentPipeline E(String key, Object value) {
		// Check the type of input
		checkInputElementClass(Graph.class);

		// Modify stream
		stream = stream.flatMap(g -> ((Graph) g).getEdges(key, value).stream());

		// Set the class of element
		elementClass = Edge.class;

		// Step Update
		Class[] args = { String.class, Object.class };
		Step step = new Step(this.getClass().getName(), "E", args, key, value);
		stepList.add(step);

		// return the extended stream
		return this;
	}

	// -------------------Transform: Element <- -> String ----------------------

	@Override
	public GremlinFluentPipeline id() {
		// Check the type of input
		checkInputElementClass(Vertex.class, Edge.class);

		// Modify stream
		stream = stream.map(e -> ((Element) e).getId());

		// Set the class of element
		elementClass = String.class;

		// Step Update
		Class[] args = {};
		Step step = new Step(this.getClass().getName(), "id", args);
		stepList.add(step);

		// return the extended stream
		return this;
	}

	@Override
	public GremlinFluentPipeline element(Class<? extends Element> elementClass) {
		// Check the type of input
		if (!(elementClass.equals(Vertex.class) || elementClass.equals(Edge.class)))
			throw new IllegalArgumentException("The argument should be one of Vertex.class or Edge.class");

		if (elementClass.equals(Vertex.class)) {
			// Modify stream
			stream = stream.map(e -> g.getVertex((String) e)).filter(e -> e != null);

			// Set the class of element
			this.elementClass = Vertex.class;
		} else {
			// Modify stream
			stream = stream.map(e -> g.getEdge((String) e)).filter(e -> e != null);

			// Set the class of element
			this.elementClass = Edge.class;
		}

		// Step Update
		Class[] args = { Class.class };
		Step step = new Step(this.getClass().getName(), "element", args);
		stepList.add(step);

		// return the extended stream
		return this;
	}

	// -------------------Transform: Vertex to Edge ----------------------

	@Override
	public GremlinFluentPipeline outE(String... labels) {
		// Check the type of input
		checkInputElementClass(Vertex.class);

		// Modify stream
		if (isParallel)
			stream = stream.flatMap(v -> ((Vertex) v).getEdges(Direction.OUT, labels).parallelStream());
		else
			stream = stream.flatMap(v -> ((Vertex) v).getEdges(Direction.OUT, labels).stream());

		// Set the class of element
		elementClass = Edge.class;

		// Step Update
		Class[] args = { labels.getClass() };

		// TODO: Confirm warning on parameter type
		Step step = new Step(this.getClass().getName(), "outE", args, labels);
		stepList.add(step);

		// return the extended stream
		return this;
	}

	@Override
	public GremlinFluentPipeline inE(String... labels) {
		// Check the type of input
		checkInputElementClass(Vertex.class);

		// Modify stream
		if (isParallel)
			stream = stream.flatMap(v -> ((Vertex) v).getEdges(Direction.IN, labels).parallelStream());
		else
			stream = stream.flatMap(v -> ((Vertex) v).getEdges(Direction.IN, labels).stream());

		// Set the class of element
		elementClass = Edge.class;

		// Step Update
		Class[] args = { labels.getClass() };

		// TODO: Confirm warning on parameter type
		Step step = new Step(this.getClass().getName(), "inE", args, labels);
		stepList.add(step);

		// return the extended stream
		return this;
	}

	// -------------------Transform: Edge to Vertex ----------------------

	@Override
	public GremlinFluentPipeline outV() {
		// Check the type of input
		checkInputElementClass(Edge.class);

		// Modify stream
		stream = stream.map(e -> ((Edge) e).getVertex(Direction.OUT));

		// Set the class of element
		elementClass = Vertex.class;

		// Step Update
		Class[] args = {};
		Step step = new Step(this.getClass().getName(), "outV", args);
		stepList.add(step);

		// return the extended stream
		return this;
	}

	@Override
	public GremlinFluentPipeline inV() {
		// Check the type of input
		checkInputElementClass(Edge.class);

		// Modify stream
		stream = stream.map(e -> ((Edge) e).getVertex(Direction.IN));

		// Set the class of element
		elementClass = Vertex.class;

		// Step Update
		Class[] args = {};
		Step step = new Step(this.getClass().getName(), "inV", args);
		stepList.add(step);

		// return the extended stream
		return this;
	}

	// -------------------Transform: Vertex to Vertex ----------------------

	@Override
	public GremlinFluentPipeline out(String... labels) {
		// Check the type of input
		checkInputElementClass(Vertex.class);

		// Modify stream
		if (isParallel)
			stream = stream.flatMap(v -> ((Vertex) v).getVertices(Direction.OUT, labels).parallelStream());
		else
			stream = stream.flatMap(v -> ((Vertex) v).getVertices(Direction.OUT, labels).stream());

		// Set the class of element
		elementClass = Vertex.class;

		// Step Update
		Class[] args = { labels.getClass() };
		// TODO: Confirm warning on parameter type
		Step step = new Step(this.getClass().getName(), "out", args, labels);
		stepList.add(step);

		// return the extended stream
		return this;
	}

	@Override
	public GremlinFluentPipeline in(String... labels) {
		// Check the type of input
		checkInputElementClass(Vertex.class);

		// Modify stream
		if (isParallel)
			stream = stream.flatMap(v -> ((Vertex) v).getVertices(Direction.IN, labels).parallelStream());
		else
			stream = stream.flatMap(v -> ((Vertex) v).getVertices(Direction.IN, labels).stream());

		// Set the class of element
		elementClass = Vertex.class;

		// Step Update
		Class[] args = { labels.getClass() };
		// TODO: Confirm warning on parameter type
		Step step = new Step(this.getClass().getName(), "in", args, labels);
		stepList.add(step);

		// return the extended stream
		return this;
	}

	// -------------------Transform: Gather / Scatter ----------------------

	@Override
	public GremlinFluentPipeline gather() {
		// Modify stream
		List intermediate = stream.collect(Collectors.toList());
		if (isParallel)
			stream = List.of(intermediate).parallelStream();
		else
			stream = List.of(intermediate).stream();

		// Set the class of element
		collectionClass = elementClass;
		elementClass = List.class;

		// Step Update
		Class[] args = {};
		Step step = new Step(this.getClass().getName(), "gather", args);
		stepList.add(step);

		// return the extended stream
		return this;
	}

	@Override
	public GremlinFluentPipeline scatter() {
		// Modify stream
		if (elementClass.equals(List.class)) {
			stream = stream.flatMap(list -> {
				if (isParallel)
					return ((List) list).parallelStream();
				else
					return ((List) list).stream();
			});

			// Set the class of element and collection
			elementClass = collectionClass;
			collectionClass = null;
		}

		// Step Update
		Class[] args = {};
		Step step = new Step(this.getClass().getName(), "scatter", args);
		stepList.add(step);

		return this;
	}

	@Override
	public <I, C> GremlinFluentPipeline transform(Function<I, C> function, Boolean setUnboxing) {
		if (setUnboxing) {
			stream = stream.flatMap(entry -> {
				if (isParallel)
					return ((Collection<C>) function.apply((I) entry)).parallelStream();
				else
					return ((Collection<C>) function.apply((I) entry)).stream();
			});

		} else {
			stream = stream.map(entry -> {
				elementClass = entry.getClass();
				return (C) function.apply((I) entry);
			});
		}

		// TODO: have to check
//		try {
//			Type sooper = getClass().getGenericSuperclass();
//			Type t = ((ParameterizedType) sooper).getActualTypeArguments()[0];
//			elementClass = Class.forName(t.toString());
//		} catch (ClassNotFoundException e) {
//			// Not occurred
//		}

		// Step Update
		Class[] args = { Function.class, Boolean.class };
		Step step = new Step(this.getClass().getName(), "transform", args, function, setUnboxing);
		stepList.add(step);

		return this;
	}

	// -------------------Filter/Sort/Limit ----------------------

	@Override
	public GremlinFluentPipeline dedup() {
		stream = stream.distinct();

		// Step Update
		Class[] args = {};
		Step step = new Step(this.getClass().getName(), "dedup", args);
		stepList.add(step);

		return this;
	}

	@Override
	public GremlinFluentPipeline random(Double lowerBound) {
		Random r = new Random();
		stream = stream.filter(e -> {
			double dr = r.nextDouble();
			return dr > lowerBound;
		});

		// Step Update
		Class[] args = { Double.class };
		Step step = new Step(this.getClass().getName(), "random", args, lowerBound);
		stepList.add(step);

		return this;
	}

	@Override
	public GremlinFluentPipeline has(String key) {
		// Check the type of input
		checkInputElementClass(Vertex.class, Edge.class);

		// Modify stream
		stream = stream.filter(element -> {
			return ((Element) element).getPropertyKeys().contains(key);
		});

		// Step Update
		Class[] args = { String.class };
		Step step = new Step(this.getClass().getName(), "has", args, key);
		stepList.add(step);

		// return the extended stream
		return this;
	}

	@Override
	public GremlinFluentPipeline has(String key, Object value) {
		// Check the type of input
		checkInputElementClass(Vertex.class, Edge.class);

		// Modify stream
		stream = stream.filter(element -> {
			Object val = ((Element) element).getProperty(key);
			return val != null && val.equals(value);
		});

		// Step Update
		Class[] args = { String.class, Object.class };
		Step step = new Step(this.getClass().getName(), "has", args, key, value);
		stepList.add(step);

		// return the extended stream
		return this;
	}

	@Override
	public GremlinFluentPipeline has(String key, NC t, Object value) {
		// Check the type of input
		checkInputElementClass(Vertex.class, Edge.class);

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

		// Step Update
		Class[] args = { String.class, NC.class, Object.class };
		Step step = new Step(this.getClass().getName(), "has", args, key, t, value);
		stepList.add(step);

		// return the extended stream
		return this;
	}

	@Override
	public <E> GremlinFluentPipeline filter(Predicate<E> predicate) {
		stream = stream.filter(entry -> {
			return predicate.test((E) entry);
		});

		// Step Update
		Class[] args = { Predicate.class };
		Step step = new Step(this.getClass().getName(), "filter", args, predicate);
		stepList.add(step);

		return this;
	}

	@Override
	public GremlinFluentPipeline sort(Comparator comparator) {
		stream = stream.sorted(comparator);

		// Step Update
		Class[] args = { Comparator.class };
		Step step = new Step(this.getClass().getName(), "sort", args, comparator);
		stepList.add(step);

		return this;
	}

	@Override
	public GremlinFluentPipeline limit(long maxSize) {
		stream = stream.limit(maxSize);

		return this;
	}

	// ------------------- Side Effect ----------------------

	@Override
	public <E> GremlinFluentPipeline sideEffect(Collection<E> collection) {
		stream = stream.map(e -> {
			collection.add((E) e);
			return e;
		});

		return this;
	}

	@Override
	public <E> GremlinFluentPipeline sideEffect(Function<E, E> function) {
		stream = stream.map(e -> {
			function.apply((E) e);
			return e;
		});

		return this;
	}

	// ------------------- Branch ----------------------

	@Override
	public <I, C1, C2> GremlinFluentPipeline ifThenElse(Predicate<I> ifPredicate, Function<I, C1> thenFunction,
			Function<I, C2> elseFunction, boolean setThenUnboxing, boolean setElseUnboxing) {
		// TODO: Make more elegance
		AtomicBoolean isThen = new AtomicBoolean(true);
		stream = stream.map(entry -> {
			if (ifPredicate.test((I) entry)) {
				return (C1) thenFunction.apply((I) entry);
			} else {
				isThen.set(false);
				return (C2) elseFunction.apply((I) entry);
			}
		});
		if ((isThen.get() == true && setThenUnboxing) || (isThen.get() == false && setElseUnboxing)) {
			if (isParallel)
				stream = stream.flatMap(e -> ((Collection) e).parallelStream());
			else
				stream = stream.flatMap(e -> ((Collection) e).stream());
		}
		return this;
	}

	@Override
	public GremlinFluentPipeline as(String pointer) {
		// Step Update
		Class[] args = new Class[1];
		args[0] = String.class;
		Step step = new Step(this.getClass().getName(), "as", args, pointer);
		stepList.add(step);

		this.stepIndex.put(pointer, stepList.indexOf(step));
		return this;
	}

	@Override
	public <E> GremlinFluentPipeline loop(String pointer, Predicate<LoopBundle<E>> whilePredicate) {
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

		System.out.println(new TraversalEngine(g, g, Graph.class, false).V().outE("l").toList());

		java.util.Set<Integer> s1 = java.util.Set.of(1, 2, 3);
		java.util.Set<Integer> s2 = java.util.Set.of(2, 3, 4);

		// flatMap of set.streams has redundancy~ it is ok
		HashMap<Integer, Set<Integer>> map = new HashMap<Integer, Set<Integer>>();
		map.put(1, s1);
		map.put(2, s2);
		System.out.println(map.entrySet().stream().flatMap(m -> m.getValue().stream()).collect(Collectors.toList()));
	}
}
