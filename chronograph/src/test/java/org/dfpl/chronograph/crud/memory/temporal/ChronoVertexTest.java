package org.dfpl.chronograph.crud.memory.temporal;

import org.dfpl.chronograph.common.TemporalRelation;

//import static org.junit.Assert.*;

import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.dfpl.chronograph.crud.memory.ChronoVertexEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tinkerpop.blueprints.Event;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Time;
import com.tinkerpop.blueprints.TimeInstant;
import com.tinkerpop.blueprints.Vertex;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ChronoVertexTest {

	Graph g = new ChronoGraph();
	Vertex a;

	@Before
	public void setUp() throws Exception {
		g = new ChronoGraph();
		a = g.addVertex("A");

	}

	@Test
	public void testAddEventWithTimeInstant() {
		Time time = new TimeInstant(5);
		ChronoVertexEvent rawEvent = new ChronoVertexEvent(a, time);

		ChronoVertexEvent event = a.addEvent(time);

		assert (event.getTime().equals(rawEvent.getTime()));
		assert (event.getElement().equals(rawEvent.getElement()));
	}

	@Test
	public void testGetEventWithTimeInstants() {
		Time time5 = new TimeInstant(5);
		ChronoVertexEvent event5 = a.addEvent(time5);

		Time time7 = new TimeInstant(7);
		ChronoVertexEvent event7 = a.addEvent(time7);

		Time time9 = new TimeInstant(9);
		ChronoVertexEvent event9 = a.addEvent(time9);

		assert (a.getEvent(time5, TemporalRelation.isBefore) == null);
		assert (a.getEvent(time5, TemporalRelation.cotemporal).equals(event5));
		assert (a.getEvent(time5, TemporalRelation.isAfter).equals(event7));

		assert (a.getEvent(time7, TemporalRelation.isBefore).equals(event5));
		assert (a.getEvent(time7, TemporalRelation.cotemporal).equals(event7));
		assert (a.getEvent(time7, TemporalRelation.isAfter).equals(event9));

		assert (a.getEvent(time9, TemporalRelation.isBefore).equals(event5));
		assert (a.getEvent(time9, TemporalRelation.isAfter) == null);
		assert (a.getEvent(time9, TemporalRelation.during) == null);
		assert (a.getEvent(time9, TemporalRelation.finishes) == null);
		assert (a.getEvent(time9, TemporalRelation.isFinishedBy) == null);
		assert (a.getEvent(time9, TemporalRelation.isMetBy) == null);
		assert (a.getEvent(time9, TemporalRelation.isOverlappedBy) == null);
		assert (a.getEvent(time9, TemporalRelation.isStartedBy) == null);
		assert (a.getEvent(time9, TemporalRelation.isStartedBy) == null);
		assert (a.getEvent(time9, TemporalRelation.meets) == null);
		assert (a.getEvent(time9, TemporalRelation.overlapsWith) == null);
		assert (a.getEvent(time9, TemporalRelation.starts) == null);
	}

	@Test
	public void testGetEventWithTimePeriods() {
		fail("Not implemented yet");
	}

	@Test
	public void testGetEventWithTimePeriodAndTimeInstant() {
		fail("Not implemented yet");
	}

	@Test
	public void testSetOrderByStart() {
		Time time5 = new TimeInstant(5);
		ChronoVertexEvent event5 = a.addEvent(time5);

		Time time7 = new TimeInstant(7);
		ChronoVertexEvent event7 = a.addEvent(time7);

		Time time9 = new TimeInstant(9);
		ChronoVertexEvent event9 = a.addEvent(time9);

		// Default
		assert (a.getEvent(time5, TemporalRelation.isAfter).equals(event7));

		// Set orderByStart to true when it is already true
		a.setOrderByStart(true);
		assert (a.getEvent(time5, TemporalRelation.isAfter).equals(event7));

		// Set orderByStart from true to false
		a.setOrderByStart(false);
		assert (a.getEvent(time5, TemporalRelation.isAfter).equals(event9));

		// Set orderByStart to false when it's already false
		a.setOrderByStart(false);
		assert (a.getEvent(time5, TemporalRelation.isAfter).equals(event9));

		// Set orderByStart from false to true
		a.setOrderByStart(true);
		assert (a.getEvent(time5, TemporalRelation.isAfter).equals(event7));
	}

	@After
	public void tearDown() throws Exception {
		g.removeVertex(a);
	}
}
