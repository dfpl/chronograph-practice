package org.dfpl.chronograph.impl.hgraph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class HGraph implements Graph {

	HashMap<String, Vertex> vertices;
	HashMap<String, Edge> edges;

	public HGraph() {
		this.vertices = new HashMap<String, Vertex>();
		this.edges = new HashMap<String, Edge>();
	}

	@Override
	public Vertex addVertex(String id) {
		if (this.vertices.containsKey(id))
			return this.vertices.get(id);

		Vertex vertex = new HVertex(this, id);
		this.vertices.put(id, vertex);
		return vertex;
	}

	@Override
	public Vertex getVertex(String id) {
		return this.vertices.get(id);
	}

	@Override
	public void removeVertex(Vertex vertex) {
		// Remove edges connected to the target vertex
		Iterator<Entry<String, Edge>> edges = this.edges.entrySet().iterator();
		while (edges.hasNext()) {
			Edge cEdge = edges.next().getValue();

			Direction[] directions = { Direction.IN, Direction.OUT };

			for (Direction direction : directions) {
				if (cEdge.getVertex(direction).getId() == vertex.getId()) {
					// Remove edge from opposite vertex's edge index
					HVertex oppositeVertex = (HVertex) cEdge.getVertex(direction.opposite());
					oppositeVertex.removeEdgeIndex(cEdge, direction.opposite());

					edges.remove();
				}
			}
		}
		// Remove vertex from vertex list
		this.vertices.remove(vertex.getId());
	}

	@Override
	public Collection<Vertex> getVertices() {
		return vertices.values();
	}

	@Override
	public Collection<Vertex> getVertices(String key, Object value) {
		return this.vertices.values().parallelStream().filter(p -> p.getProperty(key) == value)
				.collect(Collectors.toSet());
	}

	@Override
	public Edge addEdge(Vertex outVertex, Vertex inVertex, String label) {
		if (this.edges.containsKey(label))
			return this.edges.get(label);

		Edge edge = new HEdge(this, outVertex, inVertex, label);
		this.edges.put(edge.toString(), edge);
		outVertex.addEdge(label, inVertex);
		return edge;
	}

	@Override
	public Edge getEdge(Vertex outVertex, Vertex inVertex, String label) {
		for (Edge edge : this.edges.values()) {
			if (edge.getVertex(Direction.OUT).equals(outVertex) && edge.getVertex(Direction.IN).equals(inVertex)
					&& edge.getLabel().equals(label)) {
				return edge;
			}
		}
		return null;
	}

	@Override
	public void removeEdge(Edge edge) {
		// Remove edge from vertex's index
		Direction[] directions = { Direction.OUT, Direction.IN };
		for (Direction direction : directions) {
			HVertex vertex = (HVertex) edge.getVertex(direction);
			vertex.removeEdgeIndex(edge, direction);
		}

		// Remove edge from edge list
		this.edges.remove(edge.toString());
	}

	@Override
	public Collection<Edge> getEdges() {
		return this.edges.values();
	}

	@Override
	public Collection<Edge> getEdges(String key, Object value) {
		return this.edges.values().stream().filter(e -> e.getProperty(key).equals(value)).collect(Collectors.toSet());
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
	}
}
