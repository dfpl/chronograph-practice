package org.dfpl.chronograph.impl.hgraph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

import org.dfpl.chronograph.model.Direction;
import org.dfpl.chronograph.model.Edge;
import org.dfpl.chronograph.model.Graph;
import org.dfpl.chronograph.model.Vertex;

public class HGraph implements Graph {

	HashMap<String, Vertex> vertices;
	HashMap<String, Edge> edges;

	public HGraph() {
		this.vertices = new HashMap<String, Vertex>();
		this.edges = new HashMap<String, Edge>();
	}

	@Override
	public Vertex addVertex(String id) {
		// TODO: Add check for valid ID
		if (this.vertices.containsKey(id))
			return this.vertices.get(id);

		HVertex vertex = new HVertex(id);
		this.vertices.put(id, vertex);
		return vertex;
	}

	@Override
	public Vertex getVertex(String id) {
		return this.vertices.get(id);
	}

	@Override
	public void removeVertex(Vertex vertex) {
		this.vertices.remove(vertex.getId());

		// Remove edges connected to the target vertex
		Iterator<Map.Entry<String, Edge>> edges = this.edges.entrySet().iterator();
		while (edges.hasNext()) {
			Edge cEdge = edges.next().getValue();
			if (cEdge.getVertex(Direction.OUT).getId() == vertex.getId()) {
				cEdge.remove();
			} else if (cEdge.getVertex(Direction.IN).getId() == vertex.getId()) {
				cEdge.remove();
			}
		}
	}

	@Override
	public Collection<Vertex> getVertices() {
		return vertices.values();
	}

	@Override
	public Collection<Vertex> getVertices(String key, Object value) {
		return this.vertices.values().stream().filter(p -> p.getProperty(key) == value).collect(Collectors.toSet());
	}

	@Override
	public Edge addEdge(Vertex outVertex, Vertex inVertex, String label) {
		if (this.edges.containsKey(label))
			return this.edges.get(label);

		String edgeId = outVertex.getId() + '|' + label + '|' + inVertex.getId();
		Edge edge = new HEdge(outVertex, inVertex, label);
		this.edges.put(edgeId, edge);
		return edge;
	}

	@Override
	public Edge getEdge(Vertex outVertex, Vertex inVertex, String label) {
		String targetId = outVertex.getId() + '|' + label + '|' + inVertex.getId();
		return this.edges.get(targetId);
	}

	@Override
	public void removeEdge(Edge edge) {
		

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
