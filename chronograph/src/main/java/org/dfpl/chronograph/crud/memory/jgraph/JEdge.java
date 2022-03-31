package org.dfpl.chronograph.crud.memory.jgraph;

import java.util.HashMap;
import java.util.NavigableSet;
import java.util.Set;

import org.dfpl.chronograph.common.TemporalRelation;
import org.dfpl.chronograph.crud.memory.ChronoEdgeEvent;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Event;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Time;
import com.tinkerpop.blueprints.Vertex;

public class JEdge implements Edge{

	private Graph g;
	private String id;
	private Vertex out;
	private String label;
	private Vertex in;
	private HashMap<String, Object> properties;
	
	public JEdge(Graph g , Vertex out, String label, Vertex in) {
		this.g = g;
		this.id = out.toString()+"|"+label+"|"+in.toString();
		
		this.out = out;
		this.in = in;
		this.label = label;
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
		return (T)properties.get(key);
	}

	@Override
	public Set<String> getPropertyKeys() {
		// TODO Auto-generated method stub
		return properties.keySet();
	}

	@Override
	public void setProperty(String key, Object value) {
		// TODO Auto-generated method stub
		properties.put(key, value);
	}

	@Override
	public <T> T removeProperty(String key) {
		// TODO Auto-generated method stub
		return (T) properties.remove(key);
				
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
		
	}

	@Override
	public Vertex getVertex(Direction direction) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if(direction.equals(Direction.OUT))
			return out;
		else
			return in;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return label;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		g.removeEdge(this);
		
	}

	@Override
	public String toString() {
		return "JEdge{" +
				"out=" + out +
				", label='" + label + '\'' +
				", in=" + in +
				'}';
	}
}
