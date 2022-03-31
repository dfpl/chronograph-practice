package org.dfpl.chronograph.crud.memory.jgraph;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.dfpl.chronograph.common.TemporalRelation;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Event;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Time;
import com.tinkerpop.blueprints.Vertex;

public class JVertex implements Vertex {

    private Graph g;
    private String id;
    private HashMap<String, Object> properties;

    JVertex(Graph g, String id) {
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
        if (orderByStart == true) {

        } else {

        }
    }

    @Override
    public Collection<Edge> getEdges(Direction direction, String... labels) { // Vertex에서 연관 된 Edges들을 뽑아오자
        // TODO Auto-generated method stub
        if (direction.equals(Direction.OUT)) {
            Set<Edge> outEdges = g.getEdges().parallelStream().filter(e -> e.getVertex(Direction.IN).getId().equals(this.getId())).collect(Collectors.toSet());
            System.out.println("outEdges = " + outEdges);
//              g.getEdges().parallelStream().
//                    filter(e -> e.getVertex(Direction.IN).equals(this)).peek(System.out::println).filter(e -> {
//                        if (labels.length == 0)
//                            return true;
//                        for (String label : labels) {
//                            if (e.getLabel().equals(label))
//                                return true;
//                        }
//                        return false;
//                    })
//                    .collect(Collectors.toSet());
//            return null;

        } else if (direction.equals(Direction.IN)) {
            return g.getEdges().parallelStream().
                    filter(e -> e.getVertex(Direction.OUT).getId().equals(this.id)).filter(e -> {
                        if (labels.length == 0)
                            return true;
                        for (String label : labels) {
                            if (e.getLabel().equals(label))
                                return true;
                        }
                        return false;
                    })
                    .collect(Collectors.toSet());
        }
        return new HashSet<Edge>();
    }

    @Override
    public Collection<Vertex> getVertices(Direction direction, String... labels) {
        if (direction.equals(Direction.OUT)) {
            return g.getEdges().parallelStream().
                    filter(e -> e.getVertex(Direction.IN).equals(this)).filter(e -> {
                        if (labels.length == 0)
                            return true;
                        for (String label : labels)
                            if (label.equals(e.getLabel())) return true;
                        return false;
                    }).map(e -> e.getVertex(direction.opposite())).collect(Collectors.toSet());
        } else if (direction.equals(Direction.IN)) {
            return g.getEdges().parallelStream().
                    filter(e -> e.getVertex(Direction.OUT).equals(this)).filter(e -> {
                        if (labels.length == 0)
                            return true;
                        for (String label : labels)
                            if (label.equals(e.getLabel())) return true;
                        return false;
                    }).map(e -> e.getVertex(direction.opposite())).collect(Collectors.toSet());
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
