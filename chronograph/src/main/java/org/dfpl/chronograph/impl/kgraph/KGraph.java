package org.dfpl.chronograph.impl.kgraph;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

//그래프를 어떻게 조작할 것인지 미리 설정(노드 추가/삭제 방법 등), model package에 각 함수 상세설명
public class KGraph implements Graph{
	
	HashMap<String, Vertex> vertices;
	HashSet<Edge> edges;
	
	public KGraph() {
		vertices = new HashMap<String, Vertex>();
		edges = new HashSet<Edge>();
	}
	
	
	@Override
	public Vertex addVertex(String id) {
		Vertex newVertex = new KVertex(id);
		vertices.put(id, newVertex);
		return newVertex;
	
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
		return vertices.values();
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
