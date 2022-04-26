package org.dfpl.chronograph.traversal;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.tinkerpop.blueprints.*;
import org.dfpl.chronograph.common.Step;
import org.dfpl.chronograph.common.Tokens.NC;

import com.tinkerpop.gremlin.GremlinFluentPipeline;
import com.tinkerpop.gremlin.GremlinPipeline;
import com.tinkerpop.gremlin.LoopBundle;

public class JTraversalEngine extends GremlinPipeline implements GremlinFluentPipeline{

	public JTraversalEngine(Graph graph, Object starts, Class<?> elementClass, boolean isParallel) {
		super(graph, starts, elementClass, isParallel);
		
	}

	@Override
	public GremlinFluentPipeline V() {
		checkInputElementClass(Graph.class);
		stream = stream.flatMap(g->((Graph)g).getVertices().stream());
		elementClass = Vertex.class;

		//Step update
		Class[] args= {};
		Step step = new Step(this.getClass().getName(), "V", args);
		stepList.add(step);

		return this;
	}

	@Override
	public GremlinFluentPipeline V(String key, Object value) {
		checkInputElementClass(Graph.class);
		stream = stream.flatMap(g->((Graph)g).getVertices(key, value).stream());
		elementClass = Vertex.class;

		Class[] args = {String.class, Object.class};
		Step step = new Step(this.getClass().getName(), "V", args, key, value);
		stepList.add(step);
		return this;
	}

	@Override
	public GremlinFluentPipeline E() {
		checkInputElementClass(Graph.class);
		stream = stream.flatMap(g -> ((Graph) g).getEdges().stream());
		elementClass = Edge.class;

		Class[] args = {};
		Step step = new Step(this.getClass().getName(), "E", args);
		stepList.add(step);
		return this;
	}

	@Override
	public GremlinFluentPipeline E(String key, Object value) {
		checkInputElementClass(Graph.class);
		stream = stream.flatMap(g -> ((Graph) g).getEdges(key, value).stream());
		elementClass = Edge.class;

		Class[] args = {String.class, Object.class};
		Step step = new Step(this.getClass().getName(), "E", args, key, value);
		stepList.add(step);
		return this;
	}

	@Override
	public GremlinFluentPipeline id() {
		checkInputElementClass(Vertex.class, Edge.class);
		stream = stream.map(e -> ((Element) e).getId());
		elementClass = String.class;

		Class[] args = {};
		Step step = new Step(this.getClass().getName(), "id", args);
		stepList.add(step);
		return this;
	}

	@Override
	public GremlinFluentPipeline element(Class<? extends Element> elementClass) {
		// TODO Auto-generated method stub
		// 우리는 규칙을 만들었다 vertex -> id , edge -> id1|label|id2
		if (!(elementClass.equals(Vertex.class) || elementClass.equals(Edge.class))) {
			throw new IllegalArgumentException("The argument should be one of Vertex.class or Edge.class");
		}
		if (elementClass.equals(Vertex.class)) {
			stream = stream.map(e -> {
				Vertex v = g.getVertex((String) e);
				return v;
			}).filter(e -> e != null);
			this.elementClass = Vertex.class;
		}
		else{
			stream = stream.map(e -> g.getEdge((String) e)).filter(e -> e != null);
			this.elementClass = Edge.class;
		}
		//Step update
		Class[] args = {Class.class};
		Step step = new Step(this.getClass().getName(), "element", args, elementClass);
		stepList.add(step);
		return this;
	}

	@Override
	public GremlinFluentPipeline outE(String... labels) {
		checkInputElementClass(Vertex.class);
		if (isParallel)
			stream = stream.flatMap(v -> ((Vertex) v).getEdges(Direction.OUT, labels).parallelStream());
		else
			stream = stream.flatMap(v -> ((Vertex) v).getEdges(Direction.OUT, labels).stream());
		elementClass = Edge.class;


		Class[] args = {String.class};
		Step step = new Step(this.getClass().getName(), "outE", args, labels);
		stepList.add(step);
		return this;
	}

	@Override
	public GremlinFluentPipeline inE(String... labels) {
		checkInputElementClass(Vertex.class);
		if (isParallel)
			stream = stream.flatMap(v -> ((Vertex) v).getEdges(Direction.IN, labels).parallelStream());
		else
			stream = stream.flatMap(v -> ((Vertex) v).getEdges(Direction.IN, labels).stream());

		elementClass = Edge.class;
		return this;
	}

	@Override
	public GremlinFluentPipeline outV() {
		checkInputElementClass(Edge.class);
		stream = stream.map(e -> ((Edge) e).getVertex(Direction.OUT));
		elementClass = Vertex.class;
		return this;
	}

	@Override
	public GremlinFluentPipeline inV() {
		checkInputElementClass(Edge.class);
		stream = stream.map(e -> ((Edge) e).getVertex(Direction.IN));
		elementClass = Vertex.class;
		return this;
	}

	@Override
	public GremlinFluentPipeline out(String... labels) {
		checkInputElementClass(Vertex.class);
		stream = stream.flatMap(v -> ((Vertex) v).getEdges(Direction.OUT, labels).stream());
		elementClass = Vertex.class;
		return this;
	}

	@Override
	public GremlinFluentPipeline in(String... labels) {
		checkInputElementClass(Vertex.class);
		stream = stream.flatMap(v -> ((Vertex) v).getEdges(Direction.IN, labels).stream());
		elementClass = Vertex.class;
		return this;
	}

	@Override
	public GremlinFluentPipeline gather() {
		// TODO Auto-generated method stub
		List intermediate = stream.toList();
		if (isParallel) {
			stream = List.of(intermediate).parallelStream();
		}
		else
			stream = List.of(intermediate).stream();
		collectionClass = elementClass;
		elementClass = List.class;
		return this;
	}

	@Override
	public GremlinFluentPipeline scatter() {
		// TODO Auto-generated method stub
		if (elementClass.equals(List.class) || elementClass.equals(Collection.class) ||
				elementClass.equals(Set.class)) {
			stream = stream.flatMap(list ->{
				if (isParallel) {
					return ((Collection) list).parallelStream();
				} else return ((Collection) list).stream();

			});
			elementClass = collectionClass;
			collectionClass = null;

		}
		return this;
	}

	@Override
	public <I, C> GremlinFluentPipeline transform(Function<I, C> function, Class<?> elementClass,
			Class<?> collectionClass, boolean setUnboxing) {
		if (setUnboxing) {
			stream = stream.flatMap(entry->{
				if (isParallel) {
					return ((Collection<C>)function.apply((I)entry)).parallelStream();
				}
				else
					return ((Collection<C>)function.apply((I)entry)).stream();
			});
		}
		else
			stream = stream.map(entry-> (Collection<C>)function.apply((I)entry));
		this.elementClass = elementClass;
		this.collectionClass = collectionClass;
		return this;
	}

	@Override
	public <I> GremlinFluentPipeline dedup() {
		stream = stream.distinct();
		return this;
	}

	@Override
	public <I> GremlinFluentPipeline random(Double lowerBound) {
		Random r = new Random();
		stream = stream.filter(e->{
			double dr = r.nextDouble();
			return dr > lowerBound;
		});
		return this;
	}

	@Override
	public GremlinFluentPipeline has(String key) {
		checkInputElementClass(Vertex.class , Edge.class);
		stream = stream.filter(e->{
			//property
			return ((Element)e).getPropertyKeys().contains(key);
			});
		return this;
	}

	@Override
	public GremlinFluentPipeline has(String key, Object value) {
		checkInputElementClass(Vertex.class , Edge.class);
		stream = stream.filter(e->{
			Object object =  ((Element)e).getProperty(key);
			return object != null && object.equals(value);
		});
		return this;
	}

	@Override
	public GremlinFluentPipeline has(String key, NC compareToken, Object value) {
		checkInputElementClass(Vertex.class , Edge.class);
		stream = stream.filter(e->{
			Object val = ((Element) e).getProperty(key);
			if (val == null) {
				return false;
			}
			try {
				if (val instanceof Comparable) {
					//eq : equals, lt: less than, gt: greater than, lte: lt ||eq, ne: not equal
					int compare = ((Comparable) val).compareTo(value);
					if (compare == 0 && compareToken.equals(NC.$eq) || compareToken.equals(NC.$gte) || compareToken.equals(NC.$lte))
						return true;
					else if (compare < 0 && compareToken.equals(NC.$lt) || compareToken.equals(NC.$lte) || compareToken.equals(NC.$ne)) {
						return true;
					} else if (compare > 0 && compareToken.equals(NC.$gt) || compareToken.equals(NC.$gte) || compareToken.equals(NC.$ne)) {
						return true;
					}
				}
				return false;
			} catch (Exception exception) {
				return false;
			}
		});
		return this;
	}

	@Override
	public <E> GremlinFluentPipeline filter(Predicate<E> predicate) {
		stream = stream.filter(e-> predicate.test((E)e));
		return this;
	}

	@Override
	public GremlinFluentPipeline sort(Comparator comparator) {
		stream = stream.sorted(comparator);
		return this;
	}

	@Override
	public GremlinFluentPipeline limit(Long maxSize) {
		stream = stream.limit(maxSize);

		return this;
	}

	@Override
	public <E> GremlinFluentPipeline sideEffect(Collection<E> collection) {
		stream = stream.map(e->{
			collection.add((E)e);
			return e;
		});

		return this;
	}

	@Override
	public <E> GremlinFluentPipeline sideEffect(Function<E, E> function) {
		stream = stream.map(e->{
			function.apply((E) e);
			return e;
		});

		return this;
	}

	@Override
	public <I, C1, C2> GremlinFluentPipeline ifThenElse(Predicate<I> ifPredicate, Function<I, C1> thenFunction,
			Function<I, C2> elseFunction, boolean setThenUnboxing, boolean setElseUnboxing) {
			//AtmoicBoolean: boolean의 wrapping class, 동시성에서 안전성 보장
		AtomicBoolean isThen = new AtomicBoolean(true);
		stream = stream.map(e->{
			if(ifPredicate.test((I)e)){
				return (C1)thenFunction.apply((I) e);
			}
			else
				isThen.set(false);
			return (C2) elseFunction.apply((I) e);
		});
		if ((isThen.get() == true && setThenUnboxing) || (isThen.get() == false && setElseUnboxing)) {
			if (isParallel) {
				stream = stream.flatMap(e -> ((Collection) e).parallelStream());
			} else stream = stream.flatMap(e -> ((Collection) e).stream());
		}
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
		Map<Object, ?> groupedEntries = stream.collect(Collectors.groupingBy(i -> classifier.apply((I) i)));
		return (Map<T, List<I>>) groupedEntries;

	}

	@Override
	public <I, T> Map<T, Long> groupCount(Function<I, T> classifier) {
		Map<T, Long> groupedEntries = stream.collect(Collectors.groupingBy(e -> classifier.apply((I) e), Collectors.counting()));

		return groupedEntries;
	}

	@Override
	public Optional<?> reduce(BinaryOperator reducer) {
		Optional<?> reduced = stream.reduce(reducer);
		return reduced;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <I> List<I> toList() {
		return (List<I>) stream.collect(Collectors.toList());
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	private void checkInputElementClass(Class... correctClasses) {
		boolean isMatched = false;
		for (Class correct : correctClasses) {
			if (elementClass == correct || correct.isAssignableFrom(elementClass)) {
				isMatched = true;
				break;
			}
		}
		if (isMatched == false) {
			throw new UnsupportedOperationException(
					"Current stream element class " + elementClass + " should be one of " + Arrays.toString(correctClasses));
		}
	}

	public ArrayList<Step> getStepList(){
		return stepList;
	}

}
