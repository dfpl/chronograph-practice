package org.dfpl.chronograph.impl.juhyeok;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

import org.dfpl.chronograph.model.Direction;
import org.dfpl.chronograph.model.Edge;
import org.dfpl.chronograph.model.Graph;
import org.dfpl.chronograph.model.Vertex;

public class JuhyeokGraph implements Graph {
	final Map<String, Vertex> vertices = new HashMap<String, Vertex>();
	final Map<String, Edge> edges = new HashMap<String, Edge>(); // Key: InVertex|label|OutVertex <- Primary key?
	final Object lock = new Object();

	@Override
	public Vertex addVertex(String id) {
		Vertex newVertex = new JuhyeokVertex(this, id);
		vertices.put(id, newVertex);
		return newVertex;

	}

	@Override
	public Vertex getVertex(String id) {
		return vertices.get(id);
	}

	@Override
	public void removeVertex(Vertex vertex) {
		// 연결된 Edge가 삭제되는 것도 고려해야함.
		vertex.remove();
		this.vertices.remove(vertex.getId());
	}

	@Override
	public Collection<Vertex> getVertices() {
		return vertices.values();
	}

	@Override
	public Collection<Vertex> getVertices(String key, Object value) {
		return vertices.values().parallelStream().filter(v -> {
			if (v.getProperty(key) == null)
				return false;
			else if (v.getProperty(key).equals(value))
				return true;
			else
				return false;
		}).collect(Collectors.toSet());
	}

	@Override
	public Edge addEdge(Vertex outVertex, Vertex inVertex, String label) {
		String edgeId = outVertex.getId() + "|" + label + "|" + inVertex.getId();
		Edge newEdge = new JuhyeokEdge(this, outVertex, label, inVertex);
		outVertex.addEdge(label, inVertex);
		edges.put(edgeId, newEdge);
		return newEdge;
	}

	@Override
	public Edge getEdge(Vertex outVertex, Vertex inVertex, String label) {
		String edgeId = outVertex.getId() + "|" + label + "|" + inVertex.getId();
		return this.edges.get(edgeId);
	}

	@Override
	public void removeEdge(Edge edge) {
		edge.remove();
		
		String edgeId = edge.getVertex(Direction.OUT).getId() + "|" + edge.getLabel() + "|"
				+ edge.getVertex(Direction.IN).getId();
		edges.remove(edgeId);
	}

	@Override
	public Collection<Edge> getEdges() {
		HashSet<Edge> resultEdges = new HashSet<Edge>();
		this.edges.keySet().parallelStream().forEach(key -> {
			resultEdges.add(this.edges.get(key));
		});
		return resultEdges;
	}

	@Override
	public Collection<Edge> getEdges(String key, Object value) {
		HashSet<Edge> resultEdges = new HashSet<Edge>();
		this.edges.keySet().parallelStream().filter(k -> {
			return this.edges.get(k).getProperty(key).equals(value);
		}).forEach(kk -> {
			resultEdges.add(this.edges.get(kk));
		});
		return resultEdges;
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub

	}

}
