package org.dfpl.chronograph.crud.memory.temporal;

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

public class ChronoVertex {

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

	@After
	public void tearDown() throws Exception {
		g.removeVertex(a);
	}
}
