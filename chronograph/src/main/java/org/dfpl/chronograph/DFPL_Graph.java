package org.dfpl.chronograph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.dfpl.chronograph.model.Edge;
import org.dfpl.chronograph.model.Graph;
import org.dfpl.chronograph.model.Vertex;

public class DFPL_Graph implements Graph{

	Set<Vertex> v = new HashSet<Vertex>();
	
	@Override
	public Vertex addVertex(String id) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public Vertex getVertex(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeVertex(Vertex vertex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Vertex> getVertices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Vertex> getVertices(String key, Object value) {
		// TODO Auto-generated method stub
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
