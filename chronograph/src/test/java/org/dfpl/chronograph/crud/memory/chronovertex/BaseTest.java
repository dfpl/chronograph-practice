package org.dfpl.chronograph.crud.memory.chronovertex;

import static org.junit.Assert.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import java.util.stream.Collectors;

import com.tinkerpop.blueprints.Element;
import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * The in-memory implementation of temporal graph database.
 *
 * @author Jaewook Byun, Ph.D., Assistant Professor, Department of Software,
 * Sejong University (slightly modify interface)
 */
public class BaseTest {
    Graph g;
    Vertex a;
    Vertex b;
    Vertex c;

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

        g.addEdge(a, b, "likes");
        g.addEdge(a, b, "loves");
        g.addEdge(a, c, "likes");
        g.addEdge(c, c, "likes");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetId() {
        assertEquals("A", a.getId());
    }

    @Test
    public void testGetEdges_WithDirectionIn() {
        assertEquals("[A|likes|B]", b.getEdges(Direction.IN, "likes").stream().map(Element::getId).sorted().collect(Collectors.toList())
            .toString());
    }

    @Test
    public void testGetEdges_WithDirectionOut() {
        assertEquals("[A|likes|B, A|likes|C]", a.getEdges(Direction.OUT, "likes").stream().map(Element::getId).sorted().collect(Collectors.toList())
            .toString());
        assertEquals("[A|likes|B, A|likes|C, A|loves|B]", a.getEdges(Direction.OUT, "likes", "loves").stream().map(Element::getId).sorted()
            .collect(Collectors.toList()).toString());
    }

    @Test
    public void testGetVertices() {
        assertThat(a.getVertices(Direction.OUT, (String[]) null), containsInAnyOrder(b, c));

        assertEquals("[B, C]", a.getVertices(Direction.OUT, "likes", "loves").stream().map(Element::getId).sorted()
            .collect(Collectors.toList()).toString());
    }

    @Test
    public void testRemoveVertex() {
        a.remove();

        assertEquals("[B, C]", g.getVertices().stream().map(Element::getId).sorted().collect(Collectors.toList()).toString());

        assertEquals("[C|likes|C]", g.getEdges().stream().map(Element::getId).sorted().collect(Collectors.toList()).toString());
    }

    @Test
    public void testAddAndRemoveProperty() {
        assertEquals(0, a.getPropertyKeys().size());

        // Add properties
        a.setProperty("name", "J. B.");
        a.setProperty("title", "Prof.");

        // Assert added properties
        assertEquals("J. B.", a.getProperty("name").toString());
        assertEquals("Prof.", a.getProperty("title").toString());
        assertEquals(2, a.getPropertyKeys().size());

        // Assert removal of properties
        a.removeProperty("name");
        assertNull(a.getProperty("name"));
        assertEquals(1, a.getPropertyKeys().size());
    }

}
