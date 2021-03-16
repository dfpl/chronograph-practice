package org.dfpl.chronograph.impl.jaehyun;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.dfpl.chronograph.model.Direction;
import org.dfpl.chronograph.model.Edge;
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
public class JaehyunVertex implements Vertex {

	String id;
	HashMap<String , Edge> InEdges; // <label ,Edge> direction is IN 
	HashMap<String , Edge> OutEdges; // <label ,Edge> direction is OUT	
	
	JaehyunVertex(){
		InEdges = new HashMap<String , Edge>();
		OutEdges = new HashMap<String , Edge>();
			
	}
	JaehyunVertex(String id){
		this.id = id;
		
	}
	@Override
	public Collection<Edge> getEdges(Direction direction, String... labels) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Vertex> getVertices(Direction direction, String... labels) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Edge addEdge(String label, Vertex inVertex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getProperty(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getPropertyKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProperty(String key, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> T removeProperty(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
