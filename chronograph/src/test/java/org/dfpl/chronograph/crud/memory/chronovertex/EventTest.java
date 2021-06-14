package org.dfpl.chronograph.crud.memory.chronovertex;

import static org.junit.Assert.*;

import org.dfpl.chronograph.common.TemporalRelation;
import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.dfpl.chronograph.crud.memory.ChronoVertexEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Time;
import com.tinkerpop.blueprints.TimeInstant;
import com.tinkerpop.blueprints.Vertex;

public class EventTest {
	Graph g = new ChronoGraph();
	Vertex a;
	Time time5;
	Time time7;
	Time time9;

	ChronoVertexEvent event5;
	ChronoVertexEvent event7;
	ChronoVertexEvent event9;

	@Before
	public void setUp() throws Exception {
		g = new ChronoGraph();
		a = g.addVertex("A");

		time5 = new TimeInstant(5);
		event5 = a.addEvent(time5);

		time7 = new TimeInstant(7);
		event7 = a.addEvent(time7);

		time9 = new TimeInstant(9);
		event9 = a.addEvent(time9);

	}

	@After
	public void tearDown() throws Exception {
		g.removeVertex(a);
	}

	@Test
	public void testGetEvent_WithTimeInstants() {

		assertNull(a.getEvent(time5, TemporalRelation.isBefore));
		assertEquals(a.getEvent(time5, TemporalRelation.cotemporal), event5);
		assertEquals(a.getEvent(time5, TemporalRelation.isAfter), event7);

		assertEquals(a.getEvent(time7, TemporalRelation.isBefore), event5);
		assertEquals(a.getEvent(time7, TemporalRelation.cotemporal), event7);
		assertEquals(a.getEvent(time7, TemporalRelation.isAfter), event9);

		assertEquals(a.getEvent(time9, TemporalRelation.isBefore), event5);
		assertNull(a.getEvent(time9, TemporalRelation.isAfter));
		assertNull(a.getEvent(time9, TemporalRelation.during));
		assertNull(a.getEvent(time9, TemporalRelation.finishes));
		assertNull(a.getEvent(time9, TemporalRelation.isFinishedBy));
		assertNull(a.getEvent(time9, TemporalRelation.isMetBy));
		assertNull(a.getEvent(time9, TemporalRelation.isOverlappedBy));
		assertNull(a.getEvent(time9, TemporalRelation.isStartedBy));
		assertNull(a.getEvent(time9, TemporalRelation.isStartedBy));
		assertNull(a.getEvent(time9, TemporalRelation.meets));
		assertNull(a.getEvent(time9, TemporalRelation.overlapsWith));
		assertNull(a.getEvent(time9, TemporalRelation.starts));
	}

	@Test
	public void testGetEvent_WithTimePeriods() {
		// s
		fail("Not implemented yet");
	}

	@Test
	public void testGetEvent_WithTimePeriodAndTimeInstant() {
		fail("Not implemented yet");
	}

	@Test
	public void testGetEvent_WithTimeInstantAndTimePeriod() {
		fail("Not implemented yet");
	}

	@Test
	public void testSetOrderByStart() {
		// Default
		assertEquals(a.getEvent(time7, TemporalRelation.isBefore), event5);
		assertEquals(a.getEvent(time5, TemporalRelation.isAfter), event7);

		// Set orderByStart to true when it is already true
		a.setOrderByStart(true);
		assertEquals(a.getEvent(time5, TemporalRelation.isAfter), event7);

		// Set orderByStart from true to false
		a.setOrderByStart(false);
		assertEquals(a.getEvent(time5, TemporalRelation.isAfter), event9);

		// Set orderByStart to false when it's already false
		a.setOrderByStart(false);
		assertEquals(a.getEvent(time5, TemporalRelation.isAfter), event9);

		// Set orderByStart from false to true
		a.setOrderByStart(true);
		assertEquals(a.getEvent(time5, TemporalRelation.isAfter), event7);
	}

	@Test
	public void testRemoveEvents_WithTimeInstants() {
		// Check before remove
		assertNull(a.getEvent(time5, TemporalRelation.isBefore));
		assertEquals(a.getEvent(time5, TemporalRelation.cotemporal), event5);
		assertEquals(a.getEvent(time5, TemporalRelation.isAfter), event7);

		// Remove events after time 5
		a.removeEvents(time5, TemporalRelation.isAfter);
		assertNull(a.getEvent(time5, TemporalRelation.isAfter));

		// Remove events at time 5
		a.removeEvents(time5, TemporalRelation.cotemporal);
		assertNull(a.getEvent(time5, TemporalRelation.cotemporal));
	}
}
