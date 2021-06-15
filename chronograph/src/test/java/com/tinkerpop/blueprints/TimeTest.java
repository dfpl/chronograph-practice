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

		assertEquals(containsResult, false);
		assertEquals(cotemporalResult, true);
		assertEquals(duringResult, false);
		assertEquals(finishesResult, false);
		assertEquals(isAfterResult, false);
		assertEquals(isBeforeResult, false);
		assertEquals(isFinishedByResult, false);
		assertEquals(isMetByResult, false);
		assertEquals(isOverlappedByResult, false);
		assertEquals(isStartedByResult, false);
		assertEquals(meetsResult, false);
		assertEquals(overlapsWithResult, false);
		assertEquals(startsResult, false);
	}

	@Test
	public void testCheckTemporalRelations_TimeInstantAndTimePeriod() {
		TimeInstant first = new TimeInstant((long) 5);
		TimePeriod second = new TimePeriod((long) 5, (long) 7);

		checkResults(first, second);

		assertEquals(containsResult, false);
		assertEquals(cotemporalResult, false);
		assertEquals(duringResult, false);
		assertEquals(finishesResult, false);
		assertEquals(isAfterResult, false);
		assertEquals(isBeforeResult, false);
		assertEquals(isFinishedByResult, false);
		assertEquals(isMetByResult, false);
		assertEquals(isOverlappedByResult, false);
		assertEquals(isStartedByResult, false);
		assertEquals(meetsResult, true);
		assertEquals(overlapsWithResult, false);
		assertEquals(startsResult, true);
	}

	@Test
	public void testCheckTemporalRelation_TimePeriodAndTimeInstant() {
		TimePeriod first = new TimePeriod(5, 10);
		TimeInstant second = new TimeInstant(5);

		checkResults(first, second);

		assertEquals(containsResult, false);
		assertEquals(cotemporalResult, false);
		assertEquals(duringResult, false);
		assertEquals(finishesResult, false);
		assertEquals(isAfterResult, false);
		assertEquals(isBeforeResult, false);
		assertEquals(isFinishedByResult, false);
		assertEquals(isMetByResult, true);
		assertEquals(isOverlappedByResult, false);
		assertEquals(isStartedByResult, true);
		assertEquals(meetsResult, false);
		assertEquals(overlapsWithResult, false);
		assertEquals(startsResult, false);
	}

	@Test
	public void testCheckTemporalRelation_TimePeriods() {
		TimePeriod first = new TimePeriod(5, 10);
		TimePeriod second = new TimePeriod(6, 10);

		checkResults(first, second);

		assertEquals(containsResult, false);
		assertEquals(cotemporalResult, false);
		assertEquals(duringResult, false);
		assertEquals(finishesResult, false);
		assertEquals(isAfterResult, false);
		assertEquals(isBeforeResult, false);
		assertEquals(isFinishedByResult, true);
		assertEquals(isMetByResult, false);
		assertEquals(isOverlappedByResult, false);
		assertEquals(isStartedByResult, false);
		assertEquals(meetsResult, false);
		assertEquals(overlapsWithResult, false);
		assertEquals(startsResult, false);
	}

}
