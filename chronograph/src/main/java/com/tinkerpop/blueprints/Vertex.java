package com.tinkerpop.blueprints;

import java.util.Collection;

/**
 * A vertex maintains pointers to both a set of incoming and outgoing edges. The
 * outgoing edges are those edges for which the vertex is the tail. The incoming
 * edges are those edges for which the vertex is the head. Diagrammatically,
 * ---inEdges---&gt; vertex ---outEdges---&gt;.
 *
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 * @author Matthias Brocheler (http://matthiasb.com)
 * @author Jaewook Byun, Ph.D., Assistant Professor, Department of Software,
 *         Sejong University (slightly modify interface)
 */
public interface Vertex extends Element {

	/**
	 * Return the edges incident to the vertex according to the provided direction
	 * and edge labels.
	 *
	 * @param direction the direction of the edges to retrieve
	 * @param labels    the labels of the edges to retrieve
	 * @return an iterable of incident edges
	 */
	public Collection<Edge> getEdges(Direction direction, String... labels);

	/**
	 * Return the vertices adjacent to the vertex according to the provided
	 * direction and edge labels. This method does not remove duplicate vertices
	 * (i.e. those vertices that are connected by more than one edge).
	 *
	 * @param direction the direction of the edges of the adjacent vertices
	 * @param labels    the labels of the edges of the adjacent vertices
	 * @return an iterable of adjacent vertices
	 */
	public Collection<Vertex> getVertices(Direction direction, String... labels);

	/**
	 * Add a new outgoing edge from this vertex to the parameter vertex with
	 * provided edge label.
	 *
	 * @param label    the label of the edge
	 * @param inVertex the vertex to connect to with an incoming edge
	 * @return the newly created edge
	 */
	public Edge addEdge(String label, Vertex inVertex);

	/**
	 * Remove the element from the graph.
	 */
	public void remove();
}
