package org.dfpl.chronograph.algorithm.general.centrality.betweenness;

import java.util.List;

import com.tinkerpop.blueprints.Vertex;

public class Path {
    Vertex start;
    Vertex end;
    int size;
    List<Vertex> path;

    public Path(List<Vertex> path){
        this.start = path.get(0);
        this.size = path.size();
        this.end = path.get(size-1);

        this.path = path;
    }

    public List<Vertex> setPath(List<Vertex> path){
        this.path = path;
        this.size = path.size();
        return path;
    }

    public int getSize(){
        return this.size;
    }

    public List<Vertex> getPath(){
        return this.path;
    }

    public Vertex getStart(){
        return start;
    }

    public Vertex getEnd(){
        return end;
    }
}
