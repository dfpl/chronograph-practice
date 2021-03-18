package org.dfpl.chronograph.impl.hgraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.dfpl.chronograph.model.Direction;
import org.dfpl.chronograph.model.Edge;
import org.dfpl.chronograph.model.Graph;
import org.dfpl.chronograph.model.Vertex;

public class HGraph implements Graph {

	HashMap<String, Vertex> vertices;

	@Override
	public Vertex addVertex(String id) {
		// TODO: Add check for valid ID
		if (vertices.containsKey(id))
			return vertices.get(id);
		return new HVertex(id);
	}

	@Override
	public Vertex getVertex(String id) {
		if (vertices.containsKey(id))
			return vertices.get(id);
		return null;
	}

	@Override
	public void removeVertex(Vertex vertex) {
		vertices.remove(vertex.getId());
		// TODO: Remove edges connected to the given vertex
		for(Edge e: vertex.getEdges(Direction.BOTH, "")) {
			e.remove();
		}
	}

	@Override
	public Collection<Vertex> getVertices() {
		return vertices.values();
	}

	@Override
	public Collection<Vertex> getVertices(String key, Object value) {
		// TODO: Filter a hashmap based on key or value
		
		return null;
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
