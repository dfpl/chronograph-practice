package org.dfpl.chronograph.crud.memory.jgraph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class JGraph implements Graph{

	private HashMap<String,Vertex> vertices;
	private HashMap<String,Edge> edges;
	
	public JGraph() {
		vertices = new HashMap<>();
		edges = new HashMap<>();
		
	}
	//TODO: solve vertices and edges when edges are deleted
	@Override
	public Vertex addVertex(String id) {
		// TODO Auto-generated method stub
		if(vertices.containsKey(id))
			return vertices.get(id);
		else {
			Vertex newVertex = new JVertex(this,id);
			vertices.put(id, new JVertex(this, id));
			return newVertex;
		}
	}

	@Override
	public Vertex getVertex(String id) {
		// TODO Auto-generated method stub
		return vertices.get(id);
		
	}

	@Override
	public void removeVertex(Vertex vertex) { //Vertex 지우고 Edges 까지 지워야함 
		// TODO Auto-generated method stub
		vertices.remove(vertex.getId());
		Iterator<Entry<String, Edge>> edgeIterator = edges.entrySet().iterator();
		while(edgeIterator.hasNext()) {
			Entry<String, Edge> edgeEntry = edgeIterator.next();
			Edge thisEdge = edgeEntry.getValue();
			if(thisEdge.getVertex(Direction.OUT).equals(vertex)) {
				edgeIterator.remove();
			}
			if(thisEdge.getVertex(Direction.IN).equals(vertex)) {
				edgeIterator.remove();
			}
		}
		
	}

	@Override
	public Collection<Vertex> getVertices() {
		// TODO Auto-generated method stub
		return vertices.values();
	}

	@Override
	public Collection<Vertex> getVertices(String key, Object value) { // key value : property
		// TODO Auto-generated method stub
//		return vertices.values().parallelStream().
//				filter(v->(v.getProperty(key).equals(value))
//				.collect(Collectors.toSet())); // 이해 안됨 
		return vertices.values().stream().filter(new Predicate<Vertex>() {
			@Override
			public boolean test(Vertex t) {
				// TODO Auto-generated method stub
				if(t.getProperty(key).equals(value))
					return true;
				return false;
			}
		}).collect(Collectors.toSet());
		
	}
	
	@Override
	public Edge addEdge(Vertex outVertex, Vertex inVertex, String label) {
		// TODO Auto-generated method stub
		String eId = outVertex.toString() + "|"+ label + "|" + inVertex.toString();
		if(edges.containsKey(eId)) {
			return edges.get(eId);
		}
		else {
			Edge newEdge = new JEdge(this, outVertex,label,inVertex);
			edges.put(eId, newEdge);
			return newEdge;
		}
	}
	@Override
	public Edge getEdge(Vertex outVertex, Vertex inVertex, String label) {
		String eId = outVertex.toString() + "|"+ label + "|" + inVertex.toString();
		if(edges.containsKey(eId)) {
			return edges.get(eId);
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Edge getEdge(String id) {
		// TODO Auto-generated method stub
		return edges.get(id);
	}

	@Override
	public void removeEdge(Edge edge) {
		// TODO Auto-generated method stub
		String eid= edge.getId();
		edges.remove(eid);
	}

	@Override
	public Collection<Edge> getEdges() {
		// TODO Auto-generated method stub
		return edges.values();
	}
	//이메일 데이터에서 한명 거쳐서 가야하는 문제, 인덱스가 없을 때 느린것을 확인하고, 그것을 효율적으로 유지할 수 있는 방법을 만든다.
	
	@Override
	public Collection<Edge> getEdges(String key, Object value) {
		// TODO Auto-generated method stub
		return edges.values().parallelStream().filter(e->{
			if(e.getProperty(key).equals(value))
				return true;
			return false;
		}).collect(Collectors.toSet());
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

}
