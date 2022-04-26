package org.dfpl.chronograph.crud.memory.jgraph;

import java.util.*;
import java.util.stream.Collectors;

import org.dfpl.chronograph.common.TemporalRelation;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Event;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Time;
import com.tinkerpop.blueprints.Vertex;

public class JVertex implements Vertex {

    private JGraph g;
    private String id;
    private HashMap<String, Object> properties;

    JVertex(JGraph g, String id) {
        this.g = g;
        this.id = id;
        properties = new HashMap<>();
    }

    @Override
    public String getId() {
        return id;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getProperty(String key) {
        return (T) this.properties;
    }

    @Override
    public Set<String> getPropertyKeys() {
        return properties.keySet();
    }

    @Override
    public void setProperty(String key, Object value) {
        this.properties.put(key, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T removeProperty(String key) {
        this.properties.remove(key);
        return (T) this.properties;
    }

    @Override
    public <T extends Event> T addEvent(Time time) {
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
        if (orderByStart == true) {

        } else {

        }
    }

    @Override
    public Collection<Edge> getEdges(Direction direction, String... labels) {
        // TODO Auto-generated method stub
        if (direction.equals(Direction.OUT)) {
            HashMap<String, HashSet<Edge>> outEdges = g.getOutEdges();
            if(outEdges.containsKey(this.id)){
                return outEdges.get(this.id).parallelStream().filter(e -> {
                    if (labels.length == 0)
                        return true;
                    for (String label : labels) {
                        if (e.getLabel().equals(label)) {
                            return true;
                        }
                    }
                    return false;
                }).collect(Collectors.toSet());

            } else return new HashSet<>();
            //Set<Edge> outEdges = g.getEdges().parallelStream().filter(e -> e.getVertex(Direction.OUT).getId().equals(this.getId())).collect(Collectors.toSet());
            //System.out.println("outEdges = " + outEdges);
//        	return      g.getEdges().parallelStream().filter(e -> {
//                    	boolean b1= e.getVertex(Direction.OUT).getId().equals(this.id.toString());
//        				boolean b2= false;
//                    			for(String label: labels) {
//        					if(label.equals(e.getLabel()))
//        						b2 = true;
//        				}
//                    			if( b1 && b2)
//                          			return true;
//                    			else
//                    				return false;
//                    })
//                    .collect(Collectors.toSet());

        } else{
            HashMap<String, HashSet<Edge>> inEdges = g.getInEdges();
            if (inEdges.containsKey(this.id)) {
                return inEdges.get(this.id).parallelStream().filter(e->{
                    if(labels.length == 0)
                        return true;
                    for (String label :
                            labels) {
                        if (e.getLabel().equals(label)) {
                            return true;
                        }
                    }
                    return false;
                }).collect(Collectors.toSet());
            }
            else
                return new HashSet<Edge>();
//            return g.getEdges().parallelStream().
//                    filter(e -> e.getVertex(Direction.IN).getId().equals(this.id)).filter(e -> {
//                        if (labels.length == 0)
//                            return true;
//                        for (String label : labels) {
//                            if (e.getLabel().equals(label))
//                                return true;
//                        }
//                        return false;
//                    })
//                    .collect(Collectors.toSet());
        }
    }
    @Override
    public Collection<Vertex> getVertices(Direction direction, String... labels) {
        if (direction.equals(Direction.OUT)) {
            HashMap<String, HashSet<Edge>> outEdges = g.getOutEdges();
            if(outEdges.containsKey(this.id))
            return outEdges.get(this.id).parallelStream().filter(e -> {
                        if (labels.length == 0)
                            return true;
                        for (String label : labels)
                            if (label.equals(e.getLabel())) return true;
                        return false;
                    }).map(e -> e.getVertex(direction.opposite())).collect(Collectors.toSet());
            else    return new HashSet<Vertex>();
        }
        else{
            HashMap<String, HashSet<Edge>> inEdges = g.getInEdges();
            if (inEdges.containsKey(this.id))
                return inEdges.get(this.id).parallelStream().filter(e -> {
                        if (labels.length == 0)
                            return true;
                        for (String label : labels)
                            if (label.equals(e.getLabel())) return true;
                        return false;
                    }).map(e -> e.getVertex(direction.opposite())).collect(Collectors.toSet());
            else    return new HashSet<Vertex>();
        }
    }

    @Override
    public Edge addEdge(String label, Vertex inVertex) {
        return this.g.addEdge(this, inVertex, label);

    }

    @Override
    public void remove() {
        g.removeVertex(this);
    }

    @Override
    public <T extends Event> NavigableSet<T> getEvents(Time time, TemporalRelation tr, boolean awareOutEvents,
                                                       boolean awareInEvents) {
        return null;
    }

    @Override
    public String toString() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JVertex jVertex = (JVertex) o;
        return id.equals(jVertex.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
