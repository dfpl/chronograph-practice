package org.dfpl.chronograph.impl.juhyeok;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

public class JuhyeokEdge implements Edge {
	private String label;
	private Map<String, Object> properties;
	private Graph g;
	private Vertex in;
	private Vertex out;

	public JuhyeokEdge(Graph g, Vertex outV, String label, Vertex inV) {
		this.g = g;
		this.in = inV;
		this.label = label;
		this.out = outV;
		this.properties = new HashMap<String, Object>();
	}

	@Override
	public Vertex getVertex(Direction direction) throws IllegalArgumentException {
		if (direction.equals(Direction.IN))
			return in;
		else
			return out;
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getProperty(String key) {
		return (T) this.properties.get(key);
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
		g.removeEdge(this);
	}

	public Vertex getIn() {
		return this.in;
	}

	public Vertex getOut() {
		return this.out;
	}

	@Override
	public String toString() {
		return this.label;
	}
}
