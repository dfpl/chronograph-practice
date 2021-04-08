package org.dfpl.chronograph.traversal.memory.jgremlin;

import java.util.List;
import java.util.stream.Collectors;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.common.Tokens.NC;
import com.tinkerpop.gremlin.GremlinFluentPipeline;
import com.tinkerpop.gremlin.GremlinPipeline;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class JTraversalEngine<S, E> extends GremlinPipeline<S, E> implements GremlinFluentPipeline<S, E> {

	public JTraversalEngine(Graph graph, Object starts, Class elementClass) {
		super(graph, starts, elementClass);
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
