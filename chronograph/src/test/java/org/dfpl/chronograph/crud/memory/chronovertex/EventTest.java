package org.dfpl.chronograph.crud.memory.chronovertex;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

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
    TemporalRelation tr;

    Vertex a;
    Time time;

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
    public void testCreateForUnallowedEvents() {
        time = new TimePeriod(5, 8);

        a.addEvent(time);

        assertNull(a.addEvent(new TimeInstant((5))));
        assertNull(a.addEvent(new TimeInstant((6))));
        assertNull(a.addEvent(new TimeInstant((8))));

        assertNull(a.addEvent( new TimePeriod(5, 8)));
        assertNull(a.addEvent( new TimePeriod(5, 6)));
        assertNull(a.addEvent( new TimePeriod(6, 7)));
        assertNull(a.addEvent( new TimePeriod(7, 8)));
    }

    @Test
    public void testCreateForMerge() {
        time = new TimePeriod(5, 9);

        a.addEvent(new TimeInstant((5)));
        a.addEvent(new TimeInstant((6)));

        assertNotNull(a.addEvent(time));
        assertNull(a.getEvent(new TimeInstant((5)), TemporalRelation.cotemporal));
        assertNull(a.getEvent(new TimeInstant((6)), TemporalRelation.cotemporal));
    }

    @Test
    public void testCreateForExtend() {
        time = new TimePeriod(5, 9);
        a.addEvent(time);

        // Overlap
        a.addEvent(new TimePeriod(8, 10));
        assertNull(a.getEvent(time, TemporalRelation.cotemporal));
        assertNotNull(a.getEvent(new TimePeriod(5, 10), TemporalRelation.cotemporal));

        // Meet
        time = new TimePeriod(10, 11);
        a.addEvent(time);
        assertNull(a.getEvent(time, TemporalRelation.cotemporal));
        assertNotNull(a.getEvent(new TimePeriod(5, 11), TemporalRelation.cotemporal));

        // Overlap
        time = new TimePeriod(4, 6);
        a.addEvent(time);
        assertNull(a.getEvent(time, TemporalRelation.cotemporal));
        assertNotNull(a.getEvent(new TimePeriod(4, 11), TemporalRelation.cotemporal));

        time = new TimePeriod(3, 4);
        a.addEvent(time);
        assertNull(a.getEvent(time, TemporalRelation.cotemporal));
        assertNotNull(a.getEvent(new TimePeriod(3, 11), TemporalRelation.cotemporal));
    }

    @Test
    public void testCreateAndDeleteForCotemporalGivenTimeInstants() {
        tr = TemporalRelation.cotemporal;

        // No event exists
        time = new TimeInstant(5);
        assertNull(a.getEvent(time, tr));
        assertEquals(0, a.getEvents(time, tr).size());

        // One cotemporal event exists
        ChronoVertexEvent cotemporalEvent = a.addEvent(time);
        assertEquals(cotemporalEvent, a.getEvent(time, tr));
        assertEquals(1, a.getEvents(time, tr).size());

        // Remove event
        a.removeEvents(time, tr);
        assertNull(a.getEvent(time, tr));
        assertEquals(0, a.getEvents(time, tr).size());
    }

    @Test
    public void testCreateAndDeleteForIsAfterGivenTimeInstants() {
        tr = TemporalRelation.isAfter;
        time = new TimeInstant(5);

        // No event after time 5 exists
        assertNull(a.getEvent(time, tr));

        // Two events after time 5 exists
        Time afterTime1 = new TimeInstant(7);
        ChronoVertexEvent afterEvent1 = a.addEvent(afterTime1);

        Time afterTime2 = new TimeInstant(9);
        ChronoVertexEvent afterEvent2 = a.addEvent(afterTime2);

        assertEquals(afterEvent1, a.getEvent(time, tr));
        assertEquals(2, a.getEvents(time, tr).size());
        List<ChronoVertexEvent> expectedEvents = new LinkedList<>(List.of(afterEvent1, afterEvent2));
        assertTrue(a.getEvents(time, tr).containsAll(expectedEvents));
        assertEquals(2, a.getEvents(time, tr).size());

        // Remove events after time 5
        a.removeEvents(time, tr);
        assertEquals(0, a.getEvents(time, tr).size());
    }

    @Test
    public void testCreateAndDeleteForIsBeforeGivenTimeInstants() {
        tr = TemporalRelation.isBefore;
        time = new TimeInstant(10);

        // No event exists
        assertNull(a.getEvent(time, tr));

        // Two isBefore events exist
        Time beforeTime1 = new TimeInstant(7);
        ChronoVertexEvent beforeEvent1 = a.addEvent(beforeTime1);

        Time beforeTime2 = new TimeInstant(9);
        ChronoVertexEvent beforeEvent2 = a.addEvent(beforeTime2);

        assertEquals(beforeEvent1, a.getEvent(time, tr));

        List<ChronoVertexEvent> expectedEvents = new LinkedList<>(List.of(beforeEvent1, beforeEvent2));
        assertTrue(a.getEvents(time, tr).containsAll(expectedEvents));
        assertEquals(2, a.getEvents(time, tr).size());

        a.removeEvents(time, tr);
        assertEquals(0, a.getEvents(time, tr).size());
    }

    @Test
    public void testCreateAndDeleteForIsMetByGivenTimeInstants() {
        tr = TemporalRelation.isMetBy;
        time = new TimeInstant(5);

        // One cotemporal event exists
        a.addEvent(time);
        assertNull(a.getEvent(time, tr));
        assertEquals(0, a.getEvents(time, tr).size());

        // No event is removed
        a.removeEvents(time, tr);
        assertNull(a.getEvent(time, tr));
    }

    @Test
    public void testCreateAndDeleteForIsOverlappedByGivenTimeInstants() {
        tr = TemporalRelation.isOverlappedBy;
        time = new TimeInstant(5);

        // One cotemporal event exists
        a.addEvent(time);
        assertNull(a.getEvent(time, tr));
        assertEquals(0, a.getEvents(time, tr).size());

        // No event is removed
        a.removeEvents(time, tr);
        assertNull(a.getEvent(time, tr));
    }

    @Test
    public void testCreateAndDeleteForMeetsGivenTimeInstants() {
        tr = TemporalRelation.meets;
        time = new TimeInstant(5);

        // One cotemporal event exists
        a.addEvent(time);
        assertNull(a.getEvent(time, tr));
        assertEquals(0, a.getEvents(time, tr).size());

        // No event is removed
        a.removeEvents(time, tr);
        assertNull(a.getEvent(time, tr));
    }

    @Test
    public void testCreateAndDeleteForOverlapsWithGivenTimeInstants() {
        tr = TemporalRelation.overlapsWith;
        time = new TimeInstant(5);

        // One cotemporal event exists
        a.addEvent(time);
        assertNull(a.getEvent(time, tr));
        assertEquals(0, a.getEvents(time, tr).size());

        // No event is removed
        a.removeEvents(time, tr);
        assertNull(a.getEvent(time, tr));
    }

    @Test
    public void testCreateAndDeleteForStartsGivenTimeInstants() {
        tr = TemporalRelation.starts;
        time = new TimeInstant(5);

        // One cotemporal event exists
        a.addEvent(time);
        assertNull(a.getEvent(time, tr));
        assertEquals(0, a.getEvents(time, tr).size());

        // No event is removed
        a.removeEvents(time, tr);
        assertNull(a.getEvent(time, tr));
    }

    @Test
    public void testCreateAndDeleteForIsStartedByGivenTimeInstants() {
        tr = TemporalRelation.isStartedBy;
        time = new TimeInstant(5);

        // One cotemporal event exists
        a.addEvent(time);
        assertNull(a.getEvent(time, tr));
        assertEquals(0, a.getEvents(time, tr).size());

        // No event is removed
        a.removeEvents(time, tr);
        assertNull(a.getEvent(time, tr));
    }

    @Test
    public void testCreateAndDeleteForDuringGivenTimeInstants() {
        tr = TemporalRelation.during;
        time = new TimeInstant(5);

        // One cotemporal event exists
        a.addEvent(time);
        assertNull(a.getEvent(time, tr));
        assertEquals(0, a.getEvents(time, tr).size());

        // No event is removed
        a.removeEvents(time, tr);
        assertNull(a.getEvent(time, tr));
    }

    @Test
    public void testCreateAndDeleteForContainsGivenTimeInstants() {
        tr = TemporalRelation.contains;
        time = new TimeInstant(5);

        // One cotemporal event exists
        a.addEvent(time);
        assertNull(a.getEvent(time, tr));
        assertEquals(0, a.getEvents(time, tr).size());

        // No event is removed
        a.removeEvents(time, tr);
        assertNull(a.getEvent(time, tr));
    }

    @Test
    public void testCreateAndDeleteForFinishesGivenTimeInstants() {
        tr = TemporalRelation.finishes;
        time = new TimeInstant(5);

        // One cotemporal event exists
        a.addEvent(time);
        assertNull(a.getEvent(time, tr));
        assertEquals(0, a.getEvents(time, tr).size());

        // No event is removed
        a.removeEvents(time, tr);
        assertNull(a.getEvent(time, tr));
    }

    @Test
    public void testCreateAndDeleteForIsFinishedByGivenTimeInstants() {
        tr = TemporalRelation.isFinishedBy;
        time = new TimeInstant(5);

        // One cotemporal event exists
        a.addEvent(time);
        assertNull(a.getEvent(time, tr));
        assertEquals(0, a.getEvents(time, tr).size());

        // No event is removed
        a.removeEvents(time, tr);
        assertNull(a.getEvent(time, tr));
    }

    @Test
    public void testCreateAndDeleteForIsBeforeGivenTimePeriod() {
        tr = TemporalRelation.isBefore;
        time = new TimePeriod(9, 10);

        // No event exists
        assertNull(a.getEvent(time, tr));
        assertEquals(0, a.getEvents(time, tr).size());

        // Two valid events exist
        TimePeriod validTime1 = new TimePeriod(1, 2);
        ChronoVertexEvent validEvent1 = a.addEvent(validTime1);

        TimePeriod validTime2 = new TimePeriod(3, 4);
        ChronoVertexEvent validEvent2 = a.addEvent(validTime2);

        List<ChronoVertexEvent> validEvents = new LinkedList<>(List.of(validEvent1, validEvent2));
        assertEquals(validEvent1, a.getEvent(time, tr));
        assertEquals(2, a.getEvents(time, tr).size());
        assertTrue(a.getEvents(time, tr).containsAll(validEvents));

        // Remove two valid events
        a.removeEvents(time, tr);
        assertNull(a.getEvent(time, tr));
    }

    @Test
    public void testCreateAndDeleteForIsAfterGivenTimePeriod() {
        tr = TemporalRelation.isAfter;
        time = new TimePeriod(9, 10);

        // Only one event exists
        a.addEvent(time);
        assertNull(a.getEvent(time, tr));
        assertEquals(0, a.getEvents(time, tr).size());

        // Two valid events exist
        TimePeriod validTime1 = new TimePeriod(11, 12);
        ChronoVertexEvent validEvent1 = a.addEvent(validTime1);

        TimePeriod validTime2 = new TimePeriod(13, 14);
        ChronoVertexEvent validEvent2 = a.addEvent(validTime2);

        List<ChronoVertexEvent> validEvents = new LinkedList<>(List.of(validEvent1, validEvent2));
        assertEquals(validEvent1, a.getEvent(time, tr));
        assertEquals(2, a.getEvents(time, tr).size());
        assertTrue(a.getEvents(time, tr).containsAll(validEvents));

        // Remove two valid events
        a.removeEvents(time, tr);
        assertNull(a.getEvent(time, tr));
    }

    @Test
    public void testCreateAndDeleteForMeetsGivenTimePeriod() {
        tr = TemporalRelation.meets;
        time = new TimePeriod(15, 16);

        // No event exists
        assertNull(a.getEvent(time, tr));
        assertEquals(0, a.getEvents(time, tr).size());

        // One meets event exists
        TimePeriod validTime1 = new TimePeriod(9, 15);
        ChronoVertexEvent validEvent1 = a.addEvent(validTime1);

        List<ChronoVertexEvent> validEvents = new LinkedList<>(List.of(validEvent1));
        assertEquals(validEvent1, a.getEvent(time, tr));
        assertEquals(1, a.getEvents(time, tr).size());
        assertTrue(a.getEvents(time, tr).containsAll(validEvents));

        // Remove valid events
        a.removeEvents(time, tr);
        assertNull(a.getEvent(time, tr));
    }

    @Test
    public void testCreateAndDeleteForIsMetByGivenTimePeriod() {
        tr = TemporalRelation.isMetBy;
        time = new TimePeriod(9, 15);

        // No event exists
        assertNull(a.getEvent(time, tr));
        assertEquals(0, a.getEvents(time, tr).size());

        // One valid event exists
        TimePeriod validTime1 = new TimePeriod(15, 16);
        ChronoVertexEvent validEvent1 = a.addEvent(validTime1);

        List<ChronoVertexEvent> validEvents = new LinkedList<>(List.of(validEvent1));
        assertEquals(validEvent1, a.getEvent(time, tr));
        assertEquals(1, a.getEvents(time, tr).size());
        assertTrue(a.getEvents(time, tr).containsAll(validEvents));

        // Remove valid event
        a.removeEvents(time, tr);
        assertNull(a.getEvent(time, tr));
    }

    @Test
    public void testCreateAndDeleteForOverlapsWithGivenTimePeriod() {
        tr = TemporalRelation.overlapsWith;
        time = new TimePeriod(9, 15);

        // No event exists
        assertNull(a.getEvent(time, tr));
        assertEquals(0, a.getEvents(time, tr).size());

        // One valid event exists
        TimePeriod validTime1 = new TimePeriod(8, 10);
        ChronoVertexEvent validEvent1 = a.addEvent(validTime1);

        List<ChronoVertexEvent> validEvents = new LinkedList<>(List.of(validEvent1));
        assertEquals(validEvent1, a.getEvent(time, tr));
        assertEquals(1, a.getEvents(time, tr).size());
        assertTrue(a.getEvents(time, tr).containsAll(validEvents));

        // Remove valid event
        a.removeEvents(time, tr);
        assertNull(a.getEvent(time, tr));
    }

    @Test
    public void testCreateAndDeleteForisOvelappedByGivenTimePeriod() {
        tr = TemporalRelation.isOverlappedBy;
        time = new TimePeriod(8, 10);

        // No event exists
        assertNull(a.getEvent(time, tr));
        assertEquals(0, a.getEvents(time, tr).size());

        // One valid event exists
        TimePeriod validTime1 = new TimePeriod(9, 14);
        ChronoVertexEvent validEvent1 = a.addEvent(validTime1);

        List<ChronoVertexEvent> validEvents = new LinkedList<>(List.of(validEvent1));
        assertEquals(validEvent1, a.getEvent(time, tr));
        assertEquals(1, a.getEvents(time, tr).size());
        assertTrue(a.getEvents(time, tr).containsAll(validEvents));

        // Remove valid events
        a.removeEvents(time, tr);
        assertNull(a.getEvent(time, tr));
    }

    @Test
    public void testCreateAndDeleteForStartsGivenTimePeriod() {
        tr = TemporalRelation.starts;
        time = new TimePeriod(8, 15);

        // No event exists
        assertNull(a.getEvent(time, tr));
        assertEquals(0, a.getEvents(time, tr).size());

        // One valid event exists
        TimePeriod validTime1 = new TimePeriod(8, 10);
        ChronoVertexEvent validEvent1 = a.addEvent(validTime1);

        List<ChronoVertexEvent> validEvents = new LinkedList<>(List.of(validEvent1));
        assertEquals(validEvent1, a.getEvent(time, tr));
        assertEquals(1, a.getEvents(time, tr).size());
        assertTrue(a.getEvents(time, tr).containsAll(validEvents));

        // Remove valid events
        a.removeEvents(time, tr);
        assertNull(a.getEvent(time, tr));
    }

    @Test
    public void testCreateAndDeleteForIsStartedByGivenTimePeriod() {
        tr = TemporalRelation.isStartedBy;
        time = new TimePeriod(8, 10);

        // No event exists
        assertNull(a.getEvent(time, tr));
        assertEquals(0, a.getEvents(time, tr).size());

        // One valid event exists
        TimePeriod validTime1 = new TimePeriod(8, 15);
        ChronoVertexEvent validEvent1 = a.addEvent(validTime1);

        List<ChronoVertexEvent> validEvents = new LinkedList<>(List.of(validEvent1));
        assertEquals(validEvent1, a.getEvent(time, tr));
        assertEquals(1, a.getEvents(time, tr).size());
        assertTrue(a.getEvents(time, tr).containsAll(validEvents));

        // Remove valid events
        a.removeEvents(time, tr);
        assertNull(a.getEvent(time, tr));
    }

    @Test
    public void testCreateAndDeleteForDuringGivenTimePeriod() {
        tr = TemporalRelation.during;
        time = new TimePeriod(5, 10);

        // No event exists
        assertNull(a.getEvent(time, tr));
        assertEquals(0, a.getEvents(time, tr).size());

        // Add an invalid event
        TimePeriod validTime1 = new TimePeriod(6, 9);
        ChronoVertexEvent validEvent1 = a.addEvent(validTime1);

        assertEquals(validEvent1, a.getEvent(time, tr));
        assertEquals(1, a.getEvents(time, tr).size());

        // Remove valid events
        a.removeEvents(time, tr);
        assertNull(a.getEvent(time, tr));
    }

    @Test
    public void testCreateAndDeleteForContainsGivenTimePeriod() {
        tr = TemporalRelation.contains;
        time = new TimePeriod(4, 6);

        // No event exists
        assertNull(a.getEvent(time, tr));
        assertEquals(0, a.getEvents(time, tr).size());

        // A valid event exists
        TimePeriod validTime1 = new TimePeriod(3, 7);
        ChronoVertexEvent validEvent1 = a.addEvent(validTime1);

        List<ChronoVertexEvent> validEvents = new LinkedList<>(List.of(validEvent1));
        assertEquals(validEvent1, a.getEvent(time, tr));
        assertTrue(a.getEvents(time, tr).containsAll(validEvents));

        // Remove valid events
        a.removeEvents(time, tr);
        assertNull(a.getEvent(time, tr));
    }

    @Test
    public void testCreateAndDeleteForFinishesGivenTimePeriod() {
        tr = TemporalRelation.finishes;
        time = new TimePeriod(4, 6);

        // No event exists
        assertNull(a.getEvent(time, tr));
        assertEquals(0, a.getEvents(time, tr).size());

        // One valid event exists
        TimePeriod validTime1 = new TimePeriod(5, 6);
        ChronoVertexEvent validEvent1 = a.addEvent(validTime1);

        List<ChronoVertexEvent> validEvents = new LinkedList<>(List.of(validEvent1));
        assertEquals(validEvent1, a.getEvent(time, tr));
        assertEquals(1, a.getEvents(time, tr).size());
        assertTrue(a.getEvents(time, tr).containsAll(validEvents));

        // Remove valid events
        a.removeEvents(time, tr);
        assertNull(a.getEvent(time, tr));
    }

    @Test
    public void testCreateAndDeleteForIsFinishedByGivenTimePeriod() {
        tr = TemporalRelation.isFinishedBy;
        time = new TimePeriod(5, 6);

        // No event exists
        assertNull(a.getEvent(time, tr));
        assertEquals(0, a.getEvents(time, tr).size());

        // One valid event exists
        TimePeriod validTime1 = new TimePeriod(4, 6);
        ChronoVertexEvent validEvent1 = a.addEvent(validTime1);

        List<ChronoVertexEvent> validEvents = new LinkedList<>(List.of(validEvent1));
        assertEquals(validEvent1, a.getEvent(time, tr));
        assertEquals(1, a.getEvents(time, tr).size());
        assertTrue(a.getEvents(time, tr).containsAll(validEvents));

        // Remove valid events
        a.removeEvents(time, tr);
        assertNull(a.getEvent(time, tr));
    }

    @Test
    public void testCreateAndDeleteForCotemporalGivenTimePeriod() {
        tr = TemporalRelation.cotemporal;
        time = new TimePeriod(5, 6);

        // No event exists
        assertNull(a.getEvent(time, tr));
        assertEquals(0, a.getEvents(time, tr).size());

        // One valid event exists
        TimePeriod validTime1 = new TimePeriod(5, 6);
        ChronoVertexEvent validEvent1 = a.addEvent(validTime1);

        List<ChronoVertexEvent> validEvents = new LinkedList<>(List.of(validEvent1));
        assertEquals(validEvent1, a.getEvent(time, tr));
        assertEquals(1, a.getEvents(time, tr).size());
        assertTrue(a.getEvents(time, tr).containsAll(validEvents));

        // Remove valid events
        a.removeEvents(time, tr);
        assertNull(a.getEvent(time, tr));
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
}
