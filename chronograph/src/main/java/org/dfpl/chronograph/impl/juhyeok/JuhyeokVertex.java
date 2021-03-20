package org.dfpl.chronograph.impl.juhyeok;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.dfpl.chronograph.model.Direction;
import org.dfpl.chronograph.model.Edge;
import org.dfpl.chronograph.model.Graph;
import org.dfpl.chronograph.model.Vertex;

public class JuhyeokVertex implements Vertex {
	private String id;
	private HashMap<String, Object> properties;
	private Graph g;
	private HashMap<String, HashMap<String, Set<Edge>>> incidentEdges;

	public JuhyeokVertex(Graph g, String id) {
		this.g = g;
		this.id = id;
		this.properties = new HashMap<String, Object>();
		this.incidentEdges = new HashMap<String, HashMap<String, Set<Edge>>>();
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public Collection<Edge> getEdges(Direction direction, String... labels) {
		Set<String> labelSet = new HashSet<String>();
		for (int i = 0; i < labels.length; i++)
			labelSet.add(labels[i]);

		Set<Edge> resultEdges = new HashSet<Edge>();
		// if labels' length is 0, return all incidentEdges
		if (labelSet.isEmpty()) {
			this.incidentEdges.get(direction).keySet().parallelStream().forEach(key -> {
				resultEdges.addAll(this.incidentEdges.get(direction).get(key));
			});
		} else {
			labelSet.parallelStream().forEach(key -> {
				resultEdges.addAll(this.incidentEdges.get(direction).get(key));
			});
		}
		return resultEdges;
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public Collection<Vertex> getVertices(Direction direction, String... labels) {
		Set<String> labelSet = new HashSet<String>();
		for (int i = 0; i < labels.length; i++)
			labelSet.add(labels[i]);

		HashSet<Vertex> resultVertices = new HashSet<Vertex>();
		if (labelSet.isEmpty()) {
			this.incidentEdges.get(direction).keySet().parallelStream().forEach(key -> {
				this.incidentEdges.get(direction).get(key).parallelStream()
						.forEach(e -> resultVertices.add(e.getVertex(direction)));
			});
		} else {
			labelSet.parallelStream().forEach(key -> {
				this.incidentEdges.get(direction).get(key).parallelStream().forEach(e -> {
					resultVertices.add(e.getVertex(direction));
				});
			});
		}
		return resultVertices;
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public Edge addEdge(String label, Vertex inVertex) {
		Edge newEdge = new JuhyeokEdge(this.g, this, label, inVertex);
		
		HashMap<String, Set<Edge>> edgeMap = ((JuhyeokVertex) inVertex).getIncidentEdges().get(Direction.IN);
		if(edgeMap.containsKey(label)) {
			edgeMap.get(label).add(newEdge);
		}else {
			edgeMap.put(label, new HashSet<Edge>());
			edgeMap.get(label).add(newEdge);
		}
		return newEdge;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> T getProperty(String key) {
		if (this.properties.containsKey(key)) {
			return (T) this.properties.get(key);
		} else {
			return null;
		}
	}

	@Override
	public Set<String> getPropertyKeys() {
		return this.properties.keySet();
	}

	@Override
	public void setProperty(String key, Object value) {
		this.properties.put(key, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T removeProperty(String key) {
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
		this.incidentEdges.keySet().parallelStream().forEach(k -> {
			this.incidentEdges.get(k).values().parallelStream().forEach(e -> {
				((JuhyeokVertex) ((JuhyeokEdge) e).getIn()).removeEdge((Edge) e);
				((JuhyeokVertex) ((JuhyeokEdge) e).getOut()).removeEdge((Edge) e);
			});
		});
	}

	@Override
	public String getId() {
		return this.id;
	}

	public HashMap<String, HashMap<String, Set<Edge>>> getIncidentEdges() {
		return incidentEdges;
	}

	@SuppressWarnings("unlikely-arg-type")
	public void removeEdge(Edge edge) {
		if (edge.getVertex(Direction.IN).equals(this)) {
			this.incidentEdges.get(Direction.IN).get(edge.getLabel()).remove(edge);
		}else {
			this.incidentEdges.get(Direction.OUT).get(edge.getLabel()).remove(edge);
		}
	}

}
