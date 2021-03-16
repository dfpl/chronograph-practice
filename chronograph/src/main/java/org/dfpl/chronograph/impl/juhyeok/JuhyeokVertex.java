package org.dfpl.chronograph.impl.juhyeok;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.dfpl.chronograph.model.Direction;
import org.dfpl.chronograph.model.Edge;
import org.dfpl.chronograph.model.Vertex;

public class JuhyeokVertex implements Vertex {
	private String id;
	private Map<String, Object> properties;
	
	// Map<Direction, Set<Edge>>
	private Map<String, Set<Edge>> incidentEdges;

	public JuhyeokVertex(String id) {
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

	// OUT_Vertex --> IN_Vertex
	@SuppressWarnings("unlikely-arg-type")
	@Override
	public synchronized Edge addEdge(String label, Vertex inVertex) {
		Edge newEdge = new JuhyeokEdge();
		incidentEdges.get(Direction.OUT).add(newEdge);
		((JuhyeokVertex) inVertex).getIncidentEdges().get(Direction.IN).add(newEdge);
		return newEdge;
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized <T> T getProperty(String key) {
		if (this.properties.containsKey(key)) {
			return (T) this.properties.get(key);
		} else {
			return null;
		}
	}

	@Override
	public synchronized Set<String> getPropertyKeys() {
		return this.properties.keySet();
	}

	@Override
	public synchronized void setProperty(String key, Object value) {
		this.properties.put(key, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized <T> T removeProperty(String key) {
		if (this.properties.containsKey(key)) {
			Object removedValue = this.properties.get(key);
			this.properties.remove(key);
			return (T) removedValue;
		} else {
			return null;
		}

	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getId() {
		return this.id;
	}

	public Map<String, Set<Edge>> getIncidentEdges() {
		return this.incidentEdges;
	}

}
