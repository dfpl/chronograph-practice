package org.dfpl.chronograph.impl.juhyeok;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * Copyright (C) 2021- DFPL
 *
 *
 * @author Jaewook Byun, Ph.D, Assistant Professor, Department of Software,
 *         Sejong University, Associate Director of Auto-ID Labs Korea,
 *         jwbyun@sejong.ac.kr, bjw0829@gmail.com
 * 
 * @author Juhyeok Lee, Bachelor Student, Department of Software, Sejong
 *         University, zero5.two4@gmail.com
 */

public class JuhyeokVertex implements Vertex {
	private String id;
	private HashMap<String, Object> properties;
	private Graph g;

	public JuhyeokVertex(Graph g, String id) {
		this.g = g;
		this.id = id;
		this.properties = new HashMap<String, Object>();
	}

	@Override
	public Collection<Edge> getEdges(Direction direction, String... labels) {
		return g.getEdges().parallelStream().filter(e -> {
			if (e.getVertex(direction).equals(this))
				return true;
			return false;
		}).filter(ee -> {
			for (String label : labels)
				if (label.equals(ee.getLabel()))
					return true;
			return false;
		}).collect(Collectors.toSet());
	}

	@Override
	public Collection<Vertex> getVertices(Direction direction, String... labels) {
		return g.getEdges().parallelStream().filter(e -> {
			if (e.getVertex(direction.opposite()).equals(this))
				return true;
			return false;
		}).filter(ee -> {
			for (String label : labels)
				if (label.equals(ee.getLabel()))
					return true;
			return false;
		}).map(eee -> eee.getVertex(direction)).collect(Collectors.toSet());
	}

	@Override
	public Edge addEdge(String label, Vertex inVertex) {
		Edge newEdge = new JuhyeokEdge(g, this, label, inVertex);
		g.addEdge(this, inVertex, label);
		return newEdge;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getProperty(String key) {
		return (T)this.properties.get(key);
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
		return (T)this.properties.remove(key);
	}

	@Override
	public void remove() {
		g.removeVertex(this);
	}

	@Override
	public String toString() {
		return id;
	}
	
	@Override
	public String getId() {
		return this.id;
	}

}
