package org.dfpl.chronograph.crud.memory.jgraph;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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

	public HashMap<String, HashSet<Edge>> getOutEdges() {
		return outEdges;
	}

	public void setOutEdges(HashMap<String, HashSet<Edge>> outEdges) {
		this.outEdges = outEdges;
	}

	public HashMap<String, HashSet<Edge>> getInEdges() {
		return inEdges;
	}

	public void setInEdges(HashMap<String, HashSet<Edge>> inEdges) {
		this.inEdges = inEdges;
	}

	private HashMap<String, HashSet<Edge>> outEdges;
	private HashMap<String, HashSet<Edge>> inEdges;
	
	public JGraph() {
		vertices = new HashMap<>();
		edges = new HashMap<>();
		outEdges = new HashMap<>();
		inEdges = new HashMap<>();

		
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
	public void removeVertex(Vertex vertex) { 
		synchronized(vertices) {
			synchronized(edges) {
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
				outEdges.remove(vertex.getId());
				inEdges.remove(vertex.getId());
			}
		}		
	}

	@Override
	public Collection<Vertex> getVertices() {
		return vertices.values();
	}

	@Override
	public Collection<Vertex> getVertices(String key, Object value) { 
		// key value : property
		return vertices.values().stream().filter(new Predicate<Vertex>() {
			@Override
			public boolean test(Vertex t) {
				if(t.getProperty(key).equals(value))
					return true;
				return false;
			}
		}).collect(Collectors.toSet());
		
	}
	
	@Override
	public Edge addEdge(Vertex outVertex, Vertex inVertex, String label) {
		String eId = outVertex.toString() + "|"+ label + "|" + inVertex.toString();
		Edge returnEdge;

		if(edges.containsKey(eId)) {
			returnEdge = edges.get(eId);
		}
		else {
			Edge newEdge = new JEdge(this, outVertex, label, inVertex);
			edges.put(eId, newEdge);
			returnEdge = newEdge;
		}
		//outEdge
		if (outEdges.containsKey(outVertex.getId())) {
			HashSet<Edge> outEdgeSet = outEdges.get(outVertex.getId());
			outEdgeSet.add(returnEdge);
		}
		else{
			HashSet<Edge> outEdgeSet = new HashSet<>();
			outEdgeSet.add(returnEdge);
			outEdges.put(outVertex.getId(), outEdgeSet);
		}
		//inEdge
		if (inEdges.containsKey(inVertex.getId())) {
			HashSet<Edge> inEdgeSet = inEdges.get(inVertex.getId());
			inEdgeSet.add(returnEdge);
		}
		else{
			HashSet<Edge> inEdgeSet = new HashSet<>();
			inEdgeSet.add(returnEdge);
			inEdges.put(inVertex.getId(), inEdgeSet);
		}
		return returnEdge;

	}
	@Override
	public Edge getEdge(Vertex outVertex, Vertex inVertex, String label) {
		String eId = outVertex.toString() + "|"+ label + "|" + inVertex.toString();
		if(edges.containsKey(eId)) {
			return edges.get(eId);
		}
		return null;
	}

	@Override
	public Edge getEdge(String id) {
		return edges.get(id);
	}

	@Override
	public void removeEdge(Edge edge) {
		String eid= edge.getId();
		edges.remove(eid);
		outEdges.values().forEach(set -> set.remove(eid));
		inEdges.values().forEach(set -> set.remove(eid));

	}

	@Override
	public Collection<Edge> getEdges() {
		return edges.values();
	}
	
	@Override
	public Collection<Edge> getEdges(String key, Object value) {
		return edges.values().parallelStream().filter(e->{
			if(e.getProperty(key).equals(value))
				return true;
			return false;
		}).collect(Collectors.toSet());
	}

	@Override
	public void shutdown() {
		
	}
}
