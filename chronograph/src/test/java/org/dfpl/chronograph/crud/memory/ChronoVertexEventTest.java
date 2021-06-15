package org.dfpl.chronograph.crud.memory;

import static org.junit.Assert.*;

import java.util.NavigableSet;

import org.dfpl.chronograph.common.TemporalRelation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Time;
import com.tinkerpop.blueprints.TimeInstant;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.VertexEvent;

public class ChronoVertexEventTest {

	Graph g;
	Vertex a;
	Vertex b;
	Vertex c;
	Edge abLikes;
	Edge abLoves;
	Edge acLikes;
	Edge ccLikes;
	Time time5;
	Time time7;
	Time time9;

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

		time5 = new TimeInstant(5);
		time7 = new TimeInstant(7);
		time9 = new TimeInstant(9);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetVertexEvents() {
		ChronoVertexEvent aTime5 = a.addEvent(time5);

		ChronoVertexEvent bTime7 = b.addEvent(time7);
		b.addEvent(time9);

		c.addEvent(time7);

		String[] labels = { "loves" };
		NavigableSet<VertexEvent> vEvents = aTime5.getVertexEvents(Direction.OUT, TemporalRelation.isAfter, labels);

		assertEquals(1, vEvents.size());
		assertTrue(vEvents.contains(bTime7));
	}

	@Test
	public void testGetVertexEvents_DirectionIn() {
		a.addEvent(time5);

		b.addEvent(time7);
		b.addEvent(time9);

		ChronoVertexEvent cTime9 = c.addEvent(time7);

		String[] labels = { "likes" };
		NavigableSet<VertexEvent> vEvents = cTime9.getVertexEvents(Direction.IN, TemporalRelation.cotemporal, labels);

		assertEquals(1, vEvents.size());
		assertTrue(vEvents.contains(cTime9));
	}
}
