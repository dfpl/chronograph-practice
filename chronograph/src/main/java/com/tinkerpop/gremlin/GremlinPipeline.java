package com.tinkerpop.gremlin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Stream;

import org.dfpl.chronograph.common.Step;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 * @author Jaewook Byun, Ph.D., Assistant Professor, Department of Software,
 *         Sejong University (slightly modify interface)
 */
public class GremlinPipeline {

	protected Graph g;
	protected Stream<?> stream;
	protected Class<?> elementClass;
	protected Class<?> collectionClass;
	protected boolean isParallel;
	protected ArrayList<Step> stepList;
	protected HashMap<String, Integer> stepIndex;

	/**
	 * Initialize TraversalEngine
	 * 
	 * @param g
	 * @param starts       of single Element (i.e., Graph, Vertex, Edge,
	 *                     VertexEvent, EdgeEvent) or Collection<Vertex> or
	 *                     Collection<Edge>
	 * @param elementClass either of Graph.class, Vertex.class, Edge.class
	 * 
	 * @param isParallel   if true, steps are executed in parallel
	 */
	public GremlinPipeline(Graph graph, Object starts, Class<?> elementClass, boolean isParallel) {
		if (starts instanceof Graph || starts instanceof Vertex || starts instanceof Edge) {
			stream = Stream.of(starts);
		} else if (starts instanceof Collection) {
			stream = ((Collection<?>) starts).stream();
		} else {
			throw new IllegalArgumentException();
		}

		if (isParallel)
			stream.parallel();

		stepList = new ArrayList<Step>();
		stepIndex = new HashMap<String, Integer>();

		this.elementClass = elementClass;
		this.collectionClass = null;
	}

	public boolean isParallel() {
		return isParallel;
	}

	public void setParallel(boolean isParallel) {
		this.isParallel = isParallel;
	}

}
