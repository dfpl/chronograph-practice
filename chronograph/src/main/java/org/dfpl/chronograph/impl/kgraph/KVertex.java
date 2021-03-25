package org.dfpl.chronograph.impl.kgraph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

public class KVertex implements Vertex {

	private HashMap<String, Object> properties;
	@SuppressWarnings("unused")
	private HashMap<String, Set<Edge>> incidentEdges;
	String id;
	HashMap<String, Edge> InEdges; // <label ,Edge> direction is IN
	HashMap<String, Edge> OutEdges; // <label ,Edge> direction is OUT

	public KVertex(String id) {
		this.id = id;
		this.properties = new HashMap<String, Object>();
		this.incidentEdges = new HashMap<String, Set<Edge>>();
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

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getProperty(String key) {
		// TODO Auto-generated method stub
		if (this.properties.containsKey(key)) {
			return (T) this.properties.get(key);
		} else {
			return null;
		}
	}

	@Override
	public Set<String> getPropertyKeys() {
		// TODO Auto-generated method stub
		return this.properties.keySet();
	}

	@Override
	public void setProperty(String key, Object value) {
		// TODO Auto-generated method stub
		this.properties.put(key, value);

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
		return this.id;
	}

}
