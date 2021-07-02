package org.dfpl.chronograph.traversal;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.dfpl.chronograph.common.Step;
import org.dfpl.chronograph.common.Tokens.NC;

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

	public TraversalEngine(Graph graph, Object starts, int loopCount,  Class<?> elementClass, boolean isParallel) {
		super(graph, starts, elementClass, isParallel);
		this.loopCount = loopCount;
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
			stream = stream.map(e -> {
				Vertex v = g.getVertex((String) e);
				return v;
			}).filter(e -> e != null);

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

		Step step = new Step(this.getClass().getName(), "outE", args, (Object[]) labels);
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

		Step step = new Step(this.getClass().getName(), "inE", args, (Object[]) labels);
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

		Step step = new Step(this.getClass().getName(), "out", args, (Object[]) labels);
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

		Step step = new Step(this.getClass().getName(), "in", args, (Object[]) labels);
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
		if (elementClass.equals(List.class) || elementClass.equals(Collection.class)
				|| elementClass.equals(Set.class)) {
			stream = stream.flatMap(list -> {
				if (isParallel)
					return ((Collection) list).parallelStream();
				else
					return ((Collection) list).stream();
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
	public <I, C> GremlinFluentPipeline transform(Function<I, C> function, Class<?> elementClass,
			Class<?> collectionClass, boolean setUnboxing) {
		if (setUnboxing) {
			stream = stream.flatMap(entry -> {
				if (isParallel)
					return ((Collection<C>) function.apply((I) entry)).parallelStream();
				else
					return ((Collection<C>) function.apply((I) entry)).stream();
			});

		} else {
			stream = stream.map(entry -> {
				return (C) function.apply((I) entry);
			});
		}

		// Set the class of element and collection
		this.elementClass = elementClass;
		this.collectionClass = collectionClass;

		// Step Update
		Class[] args = { Function.class, Class.class, Class.class, boolean.class };
		Step step = new Step(this.getClass().getName(), "transform", args, function, elementClass, collectionClass,
				setUnboxing);
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
	public GremlinFluentPipeline limit(Long maxSize) {
		stream = stream.limit(maxSize);

		// Step Update
		Class[] args = { Long.class };
		Step step = new Step(this.getClass().getName(), "limit", args, maxSize);
		stepList.add(step);

		return this;
	}

	// ------------------- Side Effect ----------------------

	@Override
	public <E> GremlinFluentPipeline sideEffect(Collection<E> collection) {

		stream = stream.map(e -> {
			collection.add((E) e);
			return e;
		});

		// Step Update
		Class[] args = { Collection.class };
		Step step = new Step(this.getClass().getName(), "sideEffect", args, collection);
		stepList.add(step);

		return this;
	}

	@Override
	public <E> GremlinFluentPipeline sideEffect(Function<E, E> function) {
		stream = stream.map(e -> {
			function.apply((E) e);
			return e;
		});

		// Step Update
		Class[] args = { Function.class };
		Step step = new Step(this.getClass().getName(), "sideEffect", args, function);
		stepList.add(step);

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
		int lastStepIndex = stepList.size();

		// Check if the pointer is present in the step list or there is no step between the loop and the named step
		Integer backStepIdx = stepIndex.get(pointer);
		if (backStepIdx == null || lastStepIndex  <=  backStepIdx + 1  )
			return this;

		List<Step> subStepList = stepList.subList(backStepIdx + 1, lastStepIndex);
		stream = this.innerLoop(subStepList, whilePredicate).toList().stream();

		Class[] args = { String.class, Predicate.class };
		Step step = new Step(this.getClass().getName(), "loop", args, pointer, whilePredicate);
		stepList.add(step);

		return this;
	}

	/**
	 * Recursively apply the substeps to the stream while the whilePredicate is true
	 *
	 * @param subStepList consists of the named step to the last step in the stepList
	 * @param whilePredicate the predicate to continue the while loop
	 *
	 * @return the pipeline
	 * */
	private <E> GremlinFluentPipeline innerLoop(List<Step> subStepList, Predicate<LoopBundle<E>> whilePredicate){
		stream = stream.flatMap( intermediate ->{
			LoopBundle<E> loopBundle = new LoopBundle<>((E) intermediate, null, this.loopCount);
			if (whilePredicate.test(loopBundle)){
				TraversalEngine innerPipeline = new TraversalEngine(g, intermediate, this.loopCount + 1, intermediate.getClass(), this.isParallel);

				for (Step step : subStepList){
					step.setInstance(innerPipeline);
					step.invoke();
				}

				innerPipeline.innerLoop(subStepList, whilePredicate);
				return innerPipeline.toList().parallelStream();
			}

			return makeStream(intermediate);
		});
		return this;
	}

	private Stream makeStream(Object e) {
		if (e instanceof Stream)
			return (Stream) e;
		else if (e instanceof Collection)
			return ((Collection) e).parallelStream();
		else {
			return Collections.singletonList(e).parallelStream();
		}
	}

	// ------------------- Aggregation ----------------------
	@Override
	public <I, T> Map<T, List<I>> groupBy(Function<I, T> classifier) {
		Map<Object, ?> groupedEntries = stream.collect(Collectors.groupingBy(i -> classifier.apply((I) i)));

		Class[] args = { Function.class };
		Step step = new Step(this.getClass().getName(), "groupBy", args, classifier);
		stepList.add(step);

		return (Map<T, List<I>>) groupedEntries;
	}

	@Override
	public <I, T> Map<T, Long> groupCount(Function<I, T> classifier) {
		Map<T, Long> groupedEntries = stream
				.collect(Collectors.groupingBy(entry -> classifier.apply((I) entry), Collectors.counting()));

		Class[] args = { Function.class };
		Step step = new Step(this.getClass().getName(), "groupCount", args, classifier);
		stepList.add(step);

		return groupedEntries;
	}

	@Override
	public Optional reduce(BinaryOperator reducer) {

		Optional reduced = stream.reduce(reducer);

		Class[] args = { BinaryOperator.class };
		Step step = new Step(this.getClass().getName(), "reduce", args, reducer);
		stepList.add(step);

		return reduced;
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

}
