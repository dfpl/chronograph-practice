package org.dfpl.chronograph.impl.hgraph;

import java.util.Collection;
import java.util.Set;

import org.dfpl.chronograph.model.Direction;
import org.dfpl.chronograph.model.Edge;
import org.dfpl.chronograph.model.Vertex;

public class HVertex implements Vertex{
	
	private String id;
	
	public HVertex(String id) {
		this.id = id;
	}
	
	@Override
	public Collection<Edge> getEdges(Direction direction, String... labels) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Vertex> getVertices(Direction direction, String... labels) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Edge addEdge(String label, Vertex inVertex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getProperty(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getPropertyKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProperty(String key, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> T removeProperty(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
