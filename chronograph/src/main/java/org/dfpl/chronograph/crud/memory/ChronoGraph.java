package org.dfpl.chronograph.crud.memory;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * The in-memory implementation of temporal graph database.
 *
 * @author Jaewook Byun, Ph.D., Assistant Professor, Department of Software,
 *         Sejong University (slightly modify interface)
 */
public class ChronoGraph implements Graph {

	// data abstraction
	private HashMap<String, Vertex> vertices;
	private HashMap<String, Edge> edges;

	// index <vertexID, HashSet<JEdge>>
	private HashMap<String, HashSet<Edge>> outEdges;
	private HashMap<String, HashSet<Edge>> inEdges;

	public ChronoGraph() {
		vertices = new HashMap<String, Vertex>();
		edges = new HashMap<String, Edge>();
		outEdges = new HashMap<String, HashSet<Edge>>();
		inEdges = new HashMap<String, HashSet<Edge>>();
	}

	/**
	 * Create a new vertex, add it to the graph, and return the newly created
	 * vertex. The provided object identifier is a recommendation for the identifier
	 * to use. It is not required that the implementation use this identifier.
	 *
	 * @param id the recommended object identifier
	 * @return the newly created vertex
	 */
	@Override
	public Vertex addVertex(String id) {
		if (vertices.containsKey(id)) {
			return vertices.get(id);
		} else {
			ChronoVertex newVertex = new ChronoVertex(this, id);
			vertices.put(id, newVertex);
			return newVertex;
		}
	}

	/**
	 * Return the vertex referenced by the provided object identifier. If no vertex
	 * is referenced by that identifier, then return null.
	 *
	 * @param id the identifier of the vertex to retrieved from the graph
	 * @return the vertex referenced by the provided identifier or null when no such
	 *         vertex exists
	 */
	@Override
	public Vertex getVertex(String id) {
		return vertices.get(id);
	}

	/**
	 * Return an iterable to all the vertices in the graph. If this is not possible
	 * for the implementation, then an UnsupportedOperationException can be thrown.
	 *
	 * @return an iterable reference to all vertices in the graph
	 */
	@Override
	public Collection<Vertex> getVertices() {
		return vertices.values();
	}

	/**
	 * Return an iterable to all the vertices in the graph that have a particular
	 * key/value property. If this is not possible for the implementation, then an
	 * UnsupportedOperationException can be thrown. The graph implementation should
	 * use indexing structures to make this efficient else a full vertex-filter scan
	 * is required.
	 *
	 * @param key   the key of vertex
	 * @param value the value of the vertex
	 * @return an iterable of vertices with provided key and value
	 */
	@Override
	public Collection<Vertex> getVertices(String key, Object value) {
		return vertices.values().parallelStream().filter(v -> {
			Object val = v.getProperty(key);
			if (val == null)
				return false;
			else if (val.equals(value))
				return true;
			else
				return false;
		}).collect(Collectors.toSet());
	}

	/**
	 * Add an edge to the graph. The added edges requires a recommended identifier,
	 * a tail vertex, an head vertex, and a label. Like adding a vertex, the
	 * provided object identifier may be ignored by the implementation.
	 *
	 * @param outVertex the vertex on the tail of the edge
	 * @param inVertex  the vertex on the head of the edge
	 * @param label     the label associated with the edge
	 * @return the newly created edge
	 * 
	 */
	@Override
	public Edge addEdge(Vertex outVertex, Vertex inVertex, String label) {
		String edgeId = outVertex.toString() + "|" + label + "|" + inVertex.toString();
		// whole edges
		ChronoEdge edgeToReturn = null;
		if (edges.containsKey(edgeId)) {
			edgeToReturn = (ChronoEdge) edges.get(edgeId);
		} else {
			ChronoEdge newEdge = new ChronoEdge(this, outVertex, label, inVertex);
			edges.put(edgeId, newEdge);
			edgeToReturn = newEdge;
		}
		// outEdges
		if (outEdges.containsKey(outVertex.getId())) {
			HashSet<Edge> outEdgeSet = outEdges.get(outVertex.getId());
			outEdgeSet.add(edgeToReturn);
		} else {
			HashSet<Edge> outEdgeSet = new HashSet<Edge>();
			outEdgeSet.add(edgeToReturn);
			outEdges.put(outVertex.getId(), outEdgeSet);
		}
		// inEdges
		if (inEdges.containsKey(inVertex.getId())) {
			HashSet<Edge> inEdgeSet = inEdges.get(inVertex.getId());
			inEdgeSet.add(edgeToReturn);
		} else {
			HashSet<Edge> inEdgeSet = new HashSet<Edge>();
			inEdgeSet.add(edgeToReturn);
			inEdges.put(inVertex.getId(), inEdgeSet);
		}

		return edgeToReturn;
	}

	/**
	 * Return the edge referenced by the provided object identifier. If no edge is
	 * referenced by that identifier, then return null.
	 *
	 * @param id the identifier of the edge to retrieved from the graph
	 * @return the edge referenced by the provided identifier or null when no such
	 *         edge exists
	 */
	@Override
	public Edge getEdge(Vertex outVertex, Vertex inVertex, String label) {
		String edgeId = outVertex.toString() + "|" + label + "|" + inVertex.toString();
		return edges.get(edgeId);
	}
	
	/**
	 * Return the edge referenced by the provided object identifier. If no edge is
	 * referenced by that identifier, then return null.
	 *
	 * @param id the identifier of the edge to retrieved from the graph
	 * @return the edge referenced by the provided identifier or null when no such
	 *         edge exists
	 */
	@Override
	public Edge getEdge(String id) {
		return edges.get(id);
	}

	/**
	 * Return an iterable to all the edges in the graph. If this is not possible for
	 * the implementation, then an UnsupportedOperationException can be thrown.
	 *
	 * @return an iterable reference to all edges in the graph
	 */
	@Override
	public Collection<Edge> getEdges() {
		return edges.values();
	}

	/**
	 * Return an iterable to all the edges in the graph that have a particular
	 * key/value property. If this is not possible for the implementation, then an
	 * UnsupportedOperationException can be thrown. The graph implementation should
	 * use indexing structures to make this efficient else a full edge-filter scan
	 * is required.
	 *
	 * @param key   the key of the edge
	 * @param value the value of the edge
	 * @return an iterable of edges with provided key and value
	 */
	@Override
	public Collection<Edge> getEdges(String key, Object value) {
		return edges.values().parallelStream().filter(v -> {
			Object val = v.getProperty(key);
			if (val == null)
				return false;
			else if (val.equals(value))
				return true;
			else
				return false;
		}).collect(Collectors.toSet());
	}

	/**
	 * Remove the provided vertex from the graph. Upon removing the vertex, all the
	 * edges by which the vertex is connected must be removed as well.
	 *
	 * @param vertex the vertex to remove from the graph
	 */
	@Override
	public void removeVertex(Vertex vertex) {
		this.vertices.remove(vertex.getId());
		// whole edges
		Iterator<Entry<String, Edge>> eIter = edges.entrySet().iterator();
		while (eIter.hasNext()) {
			Entry<String, Edge> entry = eIter.next();
			Edge cEdge = entry.getValue();
			if (cEdge.getVertex(Direction.OUT).equals(vertex)) {
				eIter.remove();
			}
			if (cEdge.getVertex(Direction.IN).equals(vertex)) {
				eIter.remove();
			}
		}
		// indexes
		outEdges.remove(vertex.getId());
		inEdges.remove(vertex.getId());
	}

	@Override
	public void removeEdge(Edge edge) {
		this.edges.remove(edge.toString());
		this.outEdges.values().forEach(set -> set.remove(edge));
		this.inEdges.values().forEach(set -> set.remove(edge));
	}

	@Override
	public void shutdown() {
		// Do Nothing
	}

	HashMap<String, HashSet<Edge>> getOutEdges() {
		return outEdges;
	}

	void setOutEdges(HashMap<String, HashSet<Edge>> outEdges) {
		this.outEdges = outEdges;
	}

	HashMap<String, HashSet<Edge>> getInEdges() {
		return inEdges;
	}

	void setInEdges(HashMap<String, HashSet<Edge>> inEdges) {
		this.inEdges = inEdges;
	}
}
