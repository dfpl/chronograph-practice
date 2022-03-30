package org.dfpl.chronograph.crud.memory.jgraph;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NavigableSet;
import java.util.Set;

import org.dfpl.chronograph.common.TemporalRelation;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Event;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Time;
import com.tinkerpop.blueprints.Vertex;

public class JVertex implements Vertex{

	private Graph g;
	private String id;
	private HashMap<String, Object> properties;
	
	JVertex(Graph g , String id){
		this.g = g;
		this.id = id;
		properties = new HashMap<>();
	}
	
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getProperty(String key) {
		// TODO Auto-generated method stub
		return (T) this.properties;
	}

	@Override
	public Set<String> getPropertyKeys() {
		// TODO Auto-generated method stub
		return properties.keySet();
	}

	@Override
	public void setProperty(String key, Object value) {
		// TODO Auto-generated method stub
		this.properties.put(key, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T removeProperty(String key) {
		// TODO Auto-generated method stub
		this.properties.remove(key);
		return (T) this.properties;
	}
	@Override
	public <T extends Event> T addEvent(Time time) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public <T extends Event> NavigableSet<T> getEvents(Time time, TemporalRelation... temporalRelations) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Event> T getEvent(Time time, TemporalRelation tr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeEvents(Time time, TemporalRelation tr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOrderByStart(boolean orderByStart) {
		// TODO Auto-generated method stub
		if(orderByStart == true) {
			
		}
		else {
			
		}
	}

	@Override
	public Collection<Edge> getEdges(Direction direction, String... labels) { // Vertex에서 연관 된 Edges들을 뽑아오자
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public Collection<Vertex> getVertices(Direction direction, String... labels) {
		// TODO Auto-generated method stub // 이 Vertex와 연동 된 모든 Vertices들을 return
		if(direction.equals(Direction.OUT)) {
			
		}
		else if(direction.equals(Direction.IN)) {
			
		}
			
		return null;
	}

	@Override
	public Edge addEdge(String label, Vertex inVertex) {
		// TODO Auto-generated method stub
		return this.g.addEdge(this, inVertex, label);
	}

	@Override
	public void remove() {  
		// TODO Auto-generated method stub
		g.removeVertex(this);	
		
	}

	@Override
	public <T extends Event> NavigableSet<T> getEvents(Time time, TemporalRelation tr, boolean awareOutEvents,
			boolean awareInEvents) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.id;
	}
}
