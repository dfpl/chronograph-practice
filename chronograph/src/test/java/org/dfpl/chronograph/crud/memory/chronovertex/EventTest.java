package org.dfpl.chronograph.crud.memory.chronovertex;

import static org.junit.Assert.*;

import java.util.NavigableSet;
import java.util.TreeSet;

import org.dfpl.chronograph.common.TemporalRelation;
import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.dfpl.chronograph.crud.memory.ChronoVertexEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Time;
import com.tinkerpop.blueprints.TimeInstant;
import com.tinkerpop.blueprints.TimePeriod;
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
	}

	@After
	public void tearDown() throws Exception {
		g.removeVertex(a);
	}

	@Test
	public void testGetEvent_WithTimeInstants() {
		time5 = new TimeInstant(5);
		event5 = a.addEvent(time5);

		time7 = new TimeInstant(7);
		event7 = a.addEvent(time7);

		time9 = new TimeInstant(9);
		event9 = a.addEvent(time9);
		
		assertNull(a.getEvent(time5, TemporalRelation.isBefore));
		assertEquals(event5, a.getEvent(time5, TemporalRelation.cotemporal));
		assertEquals(event7, a.getEvent(time5, TemporalRelation.isAfter));

		assertEquals(event5, a.getEvent(time7, TemporalRelation.isBefore));
		assertEquals(event7, a.getEvent(time7, TemporalRelation.cotemporal));
		assertEquals(event9, a.getEvent(time7, TemporalRelation.isAfter));

		assertEquals(event5, a.getEvent(time9, TemporalRelation.isBefore));
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
		TimePeriod time5to9 = new TimePeriod(5,9);
		TimePeriod time9to10 = new TimePeriod(9,10);
		TimePeriod time10to15 = new TimePeriod(10,15);
		
		ChronoVertexEvent event5to9 = a.addEvent(time5to9);
		ChronoVertexEvent event9to10 = a.addEvent(time9to10);
		ChronoVertexEvent event10to15 = a.addEvent(time10to15);
		
		assertNull(a.getEvent(time5to9, TemporalRelation.isBefore));
		assertEquals(event10to15, a.getEvent(time5to9, TemporalRelation.isAfter));
		assertEquals(event5to9, a.getEvent(time9to10, TemporalRelation.meets));
		assertEquals(event5to9, a.getEvent(time5to9, TemporalRelation.cotemporal));
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
	public void testGetEvents_WithTimeInstants() {
		time5 = new TimeInstant(5);
		event5 = a.addEvent(time5);

		time7 = new TimeInstant(7);
		event7 = a.addEvent(time7);

		time9 = new TimeInstant(9);
		event9 = a.addEvent(time9);
		
		NavigableSet<ChronoVertexEvent> validEvents = new TreeSet<>((ChronoVertexEvent e1, ChronoVertexEvent e2) -> {
			return e1.compareTo(e2);
		});
		validEvents.add(event7);
		validEvents.add(event9);
		
		NavigableSet<ChronoVertexEvent> retrievedEvents = a.getEvents(time5, TemporalRelation.isAfter);
		
		assertEquals(2, retrievedEvents.size());
		assertTrue(retrievedEvents.containsAll(validEvents));
	}
	
	@Test
	public void testGetEvents_WithTimePeriods() {		
		fail("Not implemented yet");
	}

	@Test
	public void testGetEvents_WithTimePeriodAndTimeInstant() {
		fail("Not implemented yet");
	}

	@Test
	public void testGetEvents_WithTimeInstantAndTimePeriod() {
		fail("Not implemented yet");
	}

	@Test
	public void testSetOrderByStart() {
		time5 = new TimeInstant(5);
		event5 = a.addEvent(time5);

		time7 = new TimeInstant(7);
		event7 = a.addEvent(time7);

		time9 = new TimeInstant(9);
		event9 = a.addEvent(time9);
		
		// Default
		assertEquals(event5, a.getEvent(time7, TemporalRelation.isBefore));
		assertEquals(event7, a.getEvent(time5, TemporalRelation.isAfter));

		// Set orderByStart to true when it is already true
		a.setOrderByStart(true);
		assertEquals(event7, a.getEvent(time5, TemporalRelation.isAfter));

		// Set orderByStart from true to false
		a.setOrderByStart(false);
		assertEquals(event9, a.getEvent(time5, TemporalRelation.isAfter));

		// Set orderByStart to false when it's already false
		a.setOrderByStart(false);
		assertEquals(event9, a.getEvent(time5, TemporalRelation.isAfter));

		// Set orderByStart from false to true
		a.setOrderByStart(true);
		assertEquals(event7, a.getEvent(time5, TemporalRelation.isAfter));
	}

	@Test
	public void testRemoveEvents_WithTimeInstants() {
		time5 = new TimeInstant(5);
		event5 = a.addEvent(time5);

		time7 = new TimeInstant(7);
		event7 = a.addEvent(time7);

		time9 = new TimeInstant(9);
		event9 = a.addEvent(time9);
		
		// Check before remove
		assertNull(a.getEvent(time5, TemporalRelation.isBefore));
		assertEquals(event5, a.getEvent(time5, TemporalRelation.cotemporal));
		assertEquals(event7, a.getEvent(time5, TemporalRelation.isAfter));

		// Remove events after time 5
		a.removeEvents(time5, TemporalRelation.isAfter);
		assertNull(a.getEvent(time5, TemporalRelation.isAfter));

		// Remove events at time 5
		a.removeEvents(time5, TemporalRelation.cotemporal);
		assertNull(a.getEvent(time5, TemporalRelation.cotemporal));
	}
}
