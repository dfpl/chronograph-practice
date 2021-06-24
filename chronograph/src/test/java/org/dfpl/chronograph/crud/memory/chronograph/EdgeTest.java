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

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * The in-memory implementation of temporal graph database.
 *
 * @author Jaewook Byun, Ph.D., Assistant Professor, Department of Software,
 * Sejong University (slightly modify interface)
 */
public class EdgeTest {
    Graph g;
    Vertex a;
    Vertex b;
    Vertex c;
    Edge abLikes;
    Edge abLoves;
    Edge acLikes;
    Edge ccLikes;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        g = new ChronoGraph();
        a = g.addVertex("A");
        b = g.addVertex("B");
        c = g.addVertex("C");

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCreateEdges() {
        assertEquals(g.getEdges().size(), 0);

        abLikes = g.addEdge(a, b, "likes");
        abLoves = g.addEdge(a, b, "loves");
        acLikes = g.addEdge(a, c, "likes");
        ccLikes = g.addEdge(c, c, "likes");

        assertEquals(4, g.getEdges().size());

        assertNotNull(g.getEdge("A|likes|B"));
        assertNotNull(g.getEdge("A|loves|B"));
        assertNotNull(g.getEdge("A|likes|C"));
        assertNotNull(g.getEdge("C|likes|C"));

        assertEquals("[A|likes|B, A|likes|C, A|loves|B, C|likes|C]", g.getEdges().stream().map(Element::getId).sorted().collect(Collectors.toList()).toString());
    }

    @Test
    public void testRemoveEdge() {
        g.addEdge(a, b, "likes");
        g.addEdge(a, b, "loves");
        Edge ac = g.addEdge(a, c, "likes");
        g.addEdge(c, c, "likes");

        g.removeEdge(ac);

        assertEquals("[A, B, C]", g.getVertices().stream().map(Element::getId).sorted().collect(Collectors.toList()).toString());
        assertEquals("[A|likes|B, A|loves|B, C|likes|C]", g.getEdges().stream().map(Element::getId).sorted().collect(Collectors.toList()).toString());
    }
}
