package org.dfpl.chronograph.crud.memory.chronovertex;

import static org.junit.Assert.*;

import java.util.NavigableSet;

import org.dfpl.chronograph.common.TemporalRelation;
import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.dfpl.chronograph.crud.memory.ChronoVertexEvent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tinkerpop.blueprints.Event;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Time;
import com.tinkerpop.blueprints.TimeInstant;
import com.tinkerpop.blueprints.Vertex;

public class AwareEventTest {
    Graph g = new ChronoGraph();

    Vertex a;
    Vertex b;
    Vertex c;

    Time time5;
    Time time7;
    Time time9;

    ChronoVertexEvent event5;
    ChronoVertexEvent event7;
    ChronoVertexEvent event9;

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
        g.addEdge(c, b, "likes");
        g.addEdge(a, c, "likes");
        g.addEdge(c, c, "likes");

        time5 = new TimeInstant(5);
        time7 = new TimeInstant(7);
        time9 = new TimeInstant(9);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetEvents() {
        event5 = a.addEvent(time5);

        b.addEvent(time5);
        b.addEvent(time7);

        b.addEvent(time9);

        assertEquals(0, a.getEvents(time5, TemporalRelation.isAfter, false, false).size());

        NavigableSet<Event> cotemporalAtTime5 = a.getEvents(time5, TemporalRelation.cotemporal, false, false);
        assertEquals(1, cotemporalAtTime5.size());
        assertEquals(event5, cotemporalAtTime5.first());
    }

    @Test
    public void testGetEvents_TrueAwareInEvents() {
        event5 = a.addEvent(time5);

        event7 = c.addEvent(time7);
        b.addEvent(time9);

        NavigableSet<Event> isBeforeEvents = b.getEvents(time9, TemporalRelation.isBefore, false, true);
        assertEquals(2, isBeforeEvents.size());
        assertTrue(isBeforeEvents.contains(event5));
        assertTrue(isBeforeEvents.contains(event7));
    }

    @Test
    public void testGetEvents_TrueAwareOutEvents() {
        a.addEvent(time5);

        event5 = b.addEvent(time5);
        event7 = b.addEvent(time7);
        event9 = b.addEvent(time9);

        assertEquals(2, a.getEvents(time5, TemporalRelation.cotemporal, true, false).size());

        NavigableSet<Event> isAfterEvents = a.getEvents(time5, TemporalRelation.isAfter, true, false);
        assertEquals(2, isAfterEvents.size());
        assertTrue(isAfterEvents.contains(event7));
        assertTrue(isAfterEvents.contains(event9));
    }
}
