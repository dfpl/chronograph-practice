package org.dfpl.chronograph;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.dfpl.chronograph.model.Edge;
import org.dfpl.chronograph.model.Graph;
import org.dfpl.chronograph.model.Vertex;

public class DFPL_Graph implements Graph{

	HashMap<String , Vertex> Vertices;
	HashSet<Edge> Edges;
	
	DFPL_Graph(){
		Vertices = new HashMap<String , Vertex>();
		Edges = new HashSet<Edge>();
		
	}
	@Override
	public Vertex addVertex(String id) {
		// TODO Auto-generated method stub
		DFPL_Vertex vertex = new DFPL_Vertex(id);
		Vertices.put(id, vertex);
		
		return vertex;
	}

	@Override
	public Vertex getVertex(String id) {
		// TODO Auto-generated method stub
		return Vertices.get(id);
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
	public Collection<Vertex> getVertices(String key, Object value) { // Stream 사용해서 코딩해보기
		
		// TODO Auto-generated method stub
		return Vertices.entrySet().stream().filter(t->t.getKey() == id);
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
