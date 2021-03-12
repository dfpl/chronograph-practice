package org.dfpl.chronograph.model;

import java.util.Set;

/**
 * An Edge links two vertices. Along with its key/value properties, an edge has
 * both a directionality and a label. The directionality determines which vertex
 * is the tail vertex (out vertex) and which vertex is the head vertex (in
 * vertex). The edge label determines the type of relationship that exists
 * between the two vertices. Diagrammatically, outVertex ---label---&gt;
 * inVertex.
 *
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 * @author Jaewook Byun, Ph.D., Assistant Professor, Department of Software,
 *         Sejong University (slightly modify interface)
 */
public interface Edge {

	/**
	 * Return the tail/out or head/in vertex.
	 *
	 * @param direction whether to return the tail/out or head/in vertex
	 * @return the tail/out or head/in vertex
	 * @throws IllegalArgumentException is thrown if a direction of both is provided
	 */
	public Vertex getVertex(Direction direction) throws IllegalArgumentException;

	/**
	 * Return the label associated with the edge.
	 *
	 * @return the edge label
	 */
	public String getLabel();

	/**
	 * Return the object value associated with the provided string key. If no value
	 * exists for that key, return null.
	 *
	 * @param key the key of the key/value property
	 * @return the object value related to the string key
	 */
	public <T> T getProperty(String key);

	/**
	 * Return all the keys associated with the element.
	 *
	 * @return the set of all string keys associated with the element
	 */
	public Set<String> getPropertyKeys();

	/**
	 * Assign a key/value property to the element. If a value already exists for
	 * this key, then the previous key/value is overwritten.
	 *
	 * @param key   the string key of the property
	 * @param value the object value o the property
	 */
	public void setProperty(String key, Object value);

	/**
	 * Un-assigns a key/value property from the element. The object value of the
	 * removed property is returned.
	 *
	 * @param key the key of the property to remove from the element
	 * @return the object value associated with that key prior to removal
	 */
	public <T> T removeProperty(String key);

	/**
	 * Remove the element from the graph.
	 */
	public void remove();
}
