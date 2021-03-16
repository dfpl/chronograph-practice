package org.dfpl.chronograph.impl.juhyeok;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.dfpl.chronograph.model.Edge;
import org.dfpl.chronograph.model.Graph;
import org.dfpl.chronograph.model.Vertex;

public class JuhyeokGraph implements Graph{
	final Map<String, Vertex> vertices = new HashMap<String, Vertex>();
	final Map<String, Edge> edges = new HashMap<String, Edge>();
	
	@Override
	public synchronized Vertex addVertex(String id) {
		Vertex newVertex = new JuhyeokVertex(id);
		vertices.put(id, newVertex);
		return newVertex;
	}

	@Override
	public synchronized Vertex getVertex(String id) {
		return vertices.get(id);
	}

	@Override
	public void removeVertex(Vertex vertex) {
		// 연결된 Edge가 삭제되는 것도 고려해야함.
		
	}

	@Override
	public Collection<Vertex> getVertices() {
		return vertices.values();
	}

	@Override
	public synchronized Collection<Vertex> getVertices(String key, Object value) {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Edge getEdge(Vertex outVertex, Vertex inVertex, String label) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeEdge(Edge edge) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Edge> getEdges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Edge> getEdges(String key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

}
