package org.dfpl.chronograph.traversal.traversalengine.Jtraversalengine;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.gremlin.GremlinFluentPipeline;
import com.tinkerpop.gremlin.GremlinPipeline;
import org.dfpl.chronograph.common.Step;
import org.dfpl.chronograph.crud.memory.jgraph.Email;
import org.dfpl.chronograph.crud.memory.jgraph.JGraph;
import org.dfpl.chronograph.traversal.JTraversalEngine;
import org.junit.Before;
import org.junit.Test;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class JTraversalEngineTest {

	JGraph g;
	Vertex a, b,c;
	Edge edge1,edge2,edge3,edge4;
	JTraversalEngine jTraversalEngine;
	GremlinPipeline gremlinPipeline;

	Email email;
	long beforeTime, afterTime;
	public long calculate(long beforeTime , long afterTime) {
		return afterTime - beforeTime;
	}
	@Before
	public void init() throws IOException {
		email = new Email();
		g = (JGraph)email.getG();
		gremlinPipeline = new GremlinPipeline(g,g,JGraph.class,false);
		JGraph jGraph = new JGraph();
		Vertex v1 = jGraph.addVertex("1");
		Vertex v2 = jGraph.addVertex("2");
		Vertex v3 = jGraph.addVertex("3");
		Vertex v4 = jGraph.addVertex("4");
		edge1 = jGraph.addEdge(v1, v2, "");
		edge2 = jGraph.addEdge(v1, v3, "");
		edge3 = jGraph.addEdge(v1, v4, "");
		edge4 = jGraph.addEdge(v3, v4, "");
		v1.setProperty("hi","value");
		v2.setProperty("key","value");
		v3.setProperty("key","value");
		v4.setProperty("key", "value");

		jTraversalEngine = new JTraversalEngine(jGraph,jGraph,JGraph.class,false);



	}
	@Test
	public void constructTest() {
		JTraversalEngine jEngine = new JTraversalEngine(g,g,JGraph.class,false);
		System.out.println(jEngine.V());
	}
	@Test
	public void edgeTest(){
		System.out.println(jTraversalEngine.E().toList());
	}

	@Test
	public void idTest() {

		System.out.println(jTraversalEngine.E().id().toList());
	}
	@Test
	public void outVTest() {
		System.out.println(jTraversalEngine.E().outV().toList());

	}
	@Test
	public void outTest(){
		beforeTime = System.currentTimeMillis();
		jTraversalEngine.V().outE("").outV().toList();
		afterTime = System.currentTimeMillis();
		System.out.println( calculate(beforeTime, afterTime));
	}
	@Test
	public void outEdgeTest() {
		beforeTime = System.currentTimeMillis();

		afterTime = System.currentTimeMillis();

	}
	@Test
	public void vTest(){
		System.out.println(jTraversalEngine.V().toList());
	}
	@Test
	public void elementTest(){
	//	System.out.println(jTraversalEngine.V().id().element(Vertex.class).toList());
		System.out.println(jTraversalEngine.E().id().element(Edge.class).toList());
	}
	@Test
	public void gatherTest(){
		List<List<Vertex>> vertices = jTraversalEngine.V().gather().toList();
		vertices.get(0).forEach(System.out::println);
	}
	@Test
	public void scatterTest(){
		System.out.println(jTraversalEngine.V().scatter().toList());
	}
	@Test
	public void transformTestUnboxing(){
		List<Edge> edges=jTraversalEngine.V().transform((Function<Vertex, Collection<Edge>>)
				t->t.getEdges(Direction.OUT,"1"),Edge.class,null,true).toList();
		System.out.println(edges);
	}
	@Test
	public void transformTestWithoutUnboxing(){
		List<Set<Edge>> edges=jTraversalEngine.V().transform((Function<Vertex, Collection<Edge>>)
				t->t.getEdges(Direction.OUT,""),Collection.class,Edge.class,false).toList();
		System.out.println(edges);
		edges.get(0).forEach(System.out::println);
	}
	@Test
	public void randomTest(){
		System.out.println(jTraversalEngine.V().random(0.5).toList());
	}
	@Test
	public void hasTest1(){
		List<Vertex> vertices = jTraversalEngine.V().gather().toList();
		vertices.forEach(v->{
		});
	}
	@Test
	public void stepListTest(){
		jTraversalEngine.V().toList();
		ArrayList<Step> stepList = jTraversalEngine.getStepList();
		stepList.forEach(System.out::println);

	}
}
