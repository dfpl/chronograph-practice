package org.dfpl.chronograph.crud.memory.chronoedge;

import static org.junit.Assert.*;

import java.util.stream.Collectors;

import com.tinkerpop.blueprints.*;
import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BaseTest {

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

        abLikes = g.addEdge(a, b, "likes");
        abLoves = g.addEdge(a, b, "loves");
        acLikes = g.addEdge(a, c, "likes");
        ccLikes = g.addEdge(c, c, "likes");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testRemove() {
        acLikes.remove();

        assertEquals("[A, B, C]", g.getVertices().stream().map(Element::getId).sorted().collect(Collectors.toList()).toString());
        assertEquals("[A|likes|B, A|loves|B, C|likes|C]", g.getEdges().stream().map(Element::getId).sorted().collect(Collectors.toList()).toString());
    }

    @Test
    public void testGetLabel() {
        assertEquals("likes", acLikes.getLabel());
    }

    @Test
    public void testGetId() {
        assertEquals("A|likes|C", acLikes.getId());
    }

    @Test
    public void testAddAndRemoveProperty() {
        // Set properties
        abLikes.setProperty("name", "J. B.");
        abLikes.setProperty("title", "Prof.");

        // Check all property keys
        assertEquals("[name, title]", abLikes.getPropertyKeys().stream().sorted().collect(Collectors.toList()).toString());

        // Check get property
        assertEquals(abLikes.getProperty("name"), "J. B.");

        // Check removal of property
        abLikes.removeProperty("name");
        assertNull(abLikes.getProperty("name"));
    }

    @Test
    public void testGetVertex() {
        assertEquals("A", acLikes.getVertex(Direction.OUT).toString());
        assertEquals("C", acLikes.getVertex(Direction.IN).toString());
    }
}
