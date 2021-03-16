package org.dfpl.chronograph.impl.jaehyun;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.dfpl.chronograph.model.Edge;
import org.dfpl.chronograph.model.Graph;
import org.dfpl.chronograph.model.Vertex;

/**
 * Copyright (C) 2021- DFPL
 *
 *
 * @author Jaewook Byun, Ph.D, Assistant Professor, Department of Software,
 *         Sejong University, Associate Director of Auto-ID Labs Korea,
 *         jwbyun@sejong.ac.kr, bjw0829@gmail.com
 * 
 * @author Jaehyun 쓰세요
 */
public class JaehyunGraph implements Graph {

	HashMap<String, Vertex> vertices;
	HashSet<Edge> edges;

	JaehyunGraph() {
		vertices = new HashMap<String, Vertex>();
		edges = new HashSet<Edge>();

	}

	@Override
	public Vertex addVertex(String id) {
		JaehyunVertex vertex = new JaehyunVertex(id);
		vertices.put(id, vertex);

		return vertex;
	}

	@Override
	public Vertex getVertex(String id) {
		return vertices.get(id);
	}

	@Override
	public void removeVertex(Vertex vertex) { // 지울 때 연결된 엣지들도 지워야할까 ?
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<Vertex> getVertices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Vertex> getVertices(String key, Object value) {
		return vertices.values().parallelStream().filter(v -> {
			Object val = v.getProperty(key);
			if (val == null)
				return false;
			if (val.equals(value))
				return true;
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
