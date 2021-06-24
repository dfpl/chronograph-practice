package org.dfpl.chronograph.crud.memory.chronograph;

import static org.junit.Assert.*;

import java.util.stream.Collectors;

import com.tinkerpop.blueprints.Element;
import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * The in-memory implementation of temporal graph database.
 *
 * @author Jaewook Byun, Ph.D., Assistant Professor, Department of Software,
 * Sejong University (slightly modify interface)
 */
public class VertexTest {

    Graph g;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        g = new ChronoGraph();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAddVertex() {
        g.addVertex("A");

        // -----------------------------------------------------//
        assertEquals(1, g.getVertices().size());
        assertEquals("A", g.getVertex("A").getId());

        g.addVertex("B");
        g.addVertex("C");

        assertEquals(3, g.getVertices().size());
        assertEquals("A", g.getVertex("A").getId());
        assertEquals("B", g.getVertex("B").getId());
        assertEquals("C", g.getVertex("C").getId());
    }

    @Test
    public void testGetVertices() {
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");

        // -----------------------------------------------------//
        assertEquals("[A, B, C]", g.getVertices().stream().map(Element::getId).sorted().collect(Collectors.toList()).toString());
    }

    @Test
    public void testRemoveVertex() {
        Vertex a = g.addVertex("A");
        Vertex b = g.addVertex("B");
        Vertex c = g.addVertex("C");
        g.addEdge(a, b, "likes");
        g.addEdge(a, b, "loves");
        g.addEdge(a, c, "likes");
        g.addEdge(c, c, "likes");

        g.removeVertex(a);

        assertEquals("[B, C]", g.getVertices().stream().map(Element::getId).sorted().collect(Collectors.toList()).toString());

        assertEquals("[C|likes|C]", g.getEdges().stream().map(Element::getId).sorted().collect(Collectors.toList()).toString());
    }
}
