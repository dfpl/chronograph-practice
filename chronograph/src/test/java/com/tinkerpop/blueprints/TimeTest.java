package com.tinkerpop.blueprints;

import static org.junit.Assert.*;

import org.dfpl.chronograph.common.TemporalRelation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TimeTest {

    boolean isBeforeResult;
    boolean isAfterResult;
    boolean meetsResult;
    boolean isMetByResult;
    boolean overlapsWithResult;
    boolean isOverlappedByResult;
    boolean startsResult;
    boolean isStartedByResult;
    boolean duringResult;
    boolean containsResult;
    boolean finishesResult;
    boolean isFinishedByResult;
    boolean cotemporalResult;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    public void checkResults(Time first, Time second) {
        containsResult = first.checkTemporalRelation(second, TemporalRelation.contains);
        cotemporalResult = first.checkTemporalRelation(second, TemporalRelation.cotemporal);
        duringResult = first.checkTemporalRelation(second, TemporalRelation.during);
        finishesResult = first.checkTemporalRelation(second, TemporalRelation.finishes);
        isAfterResult = first.checkTemporalRelation(second, TemporalRelation.isAfter);
        isBeforeResult = first.checkTemporalRelation(second, TemporalRelation.isBefore);
        isFinishedByResult = first.checkTemporalRelation(second, TemporalRelation.isFinishedBy);
        isMetByResult = first.checkTemporalRelation(second, TemporalRelation.isMetBy);
        isOverlappedByResult = first.checkTemporalRelation(second, TemporalRelation.isOverlappedBy);
        isStartedByResult = first.checkTemporalRelation(second, TemporalRelation.isStartedBy);
        meetsResult = first.checkTemporalRelation(second, TemporalRelation.meets);
        overlapsWithResult = first.checkTemporalRelation(second, TemporalRelation.overlapsWith);
        startsResult = first.checkTemporalRelation(second, TemporalRelation.starts);
    }

    @Test
    public void testCheckTemporalRelations_TimeInstants() {
        long t = 5;
        TimeInstant first = new TimeInstant(t);
        TimeInstant second = new TimeInstant(t);

        checkResults(first, second);

        assertFalse(containsResult);
        assertTrue(cotemporalResult);
        assertFalse(duringResult);
        assertFalse(finishesResult);
        assertFalse(isAfterResult);
        assertFalse(isBeforeResult);
        assertFalse(isFinishedByResult);
        assertFalse(isMetByResult);
        assertFalse(isOverlappedByResult);
        assertFalse(isStartedByResult);
        assertFalse(meetsResult);
        assertFalse(overlapsWithResult);
        assertFalse(startsResult);
    }

    @Test
    public void testCheckTemporalRelations_TimeInstantAndTimePeriod() {
        TimeInstant first = new TimeInstant((long) 5);
        TimePeriod second = new TimePeriod((long) 5, (long) 7);

        checkResults(first, second);

        assertFalse(containsResult);
        assertFalse(cotemporalResult);
        assertFalse(duringResult);
        assertFalse(finishesResult);
        assertFalse(isAfterResult);
        assertFalse(isBeforeResult);
        assertFalse(isFinishedByResult);
        assertFalse(isMetByResult);
        assertFalse(isOverlappedByResult);
        assertFalse(isStartedByResult);
        assertTrue(meetsResult);
        assertFalse(overlapsWithResult);
        assertTrue(startsResult);
    }

    @Test
    public void testCheckTemporalRelation_TimePeriodAndTimeInstant() {
        TimePeriod first = new TimePeriod(5, 10);
        TimeInstant second = new TimeInstant(5);

        checkResults(first, second);

        assertFalse(containsResult);
        assertFalse(cotemporalResult);
        assertFalse(duringResult);
        assertFalse(finishesResult);
        assertFalse(isAfterResult);
        assertFalse(isBeforeResult);
        assertFalse(isFinishedByResult);
        assertTrue(isMetByResult);
        assertFalse(isOverlappedByResult);
        assertTrue(isStartedByResult);
        assertFalse(meetsResult);
        assertFalse(overlapsWithResult);
        assertFalse(startsResult);
    }

    @Test
    public void testCheckTemporalRelation_TimePeriods() {
        TimePeriod first = new TimePeriod(5, 10);
        TimePeriod second = new TimePeriod(6, 10);

        checkResults(first, second);

        assertFalse(containsResult);
        assertFalse(cotemporalResult);
        assertFalse(duringResult);
        assertFalse(finishesResult);
        assertFalse(isAfterResult);
        assertFalse(isBeforeResult);
        assertTrue(isFinishedByResult);
        assertFalse(isMetByResult);
        assertFalse(isOverlappedByResult);
        assertFalse(isStartedByResult);
        assertFalse(meetsResult);
        assertFalse(overlapsWithResult);
        assertFalse(startsResult);
    }

}
