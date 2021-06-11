package org.dfpl.chronograph.crud.memory.temporal;

import static org.junit.Assert.*;

import org.dfpl.chronograph.common.TemporalRelation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tinkerpop.blueprints.TimeInstant;
import com.tinkerpop.blueprints.TimePeriod;

public class TimeInstantTest {

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

	public void checkResults(TimeInstant first, TimeInstant second) {
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
	public void testWithEqualTimeInstants() {
		long t = 5;
		TimeInstant first = new TimeInstant(t);
		TimeInstant second = new TimeInstant(t);

		checkResults(first, second);

		assert (containsResult == false);
		assert (cotemporalResult == true);
		assert (duringResult == false);
		assert (finishesResult == false);
		assert (isAfterResult == false);
		assert (isBeforeResult == false);
		assert (isFinishedByResult == false);
		assert (isMetByResult == false);
		assert (isOverlappedByResult == false);
		assert (isStartedByResult == false);
		assert (meetsResult == false);
		assert (overlapsWithResult == false);
		assert (startsResult == false);
	}

	@Test
	public void testWithUnequalInstants() {
		TimeInstant first = new TimeInstant((long) 5);
		TimeInstant second = new TimeInstant((long) 7);

		checkResults(first, second);

		assert (containsResult == false);
		assert (cotemporalResult == false);
		assert (duringResult == false);
		assert (finishesResult == false);
		assert (isAfterResult == false);
		assert (isBeforeResult == true);
		assert (isFinishedByResult == false);
		assert (isMetByResult == false);
		assert (isOverlappedByResult == false);
		assert (isStartedByResult == false);
		assert (meetsResult == false);
		assert (overlapsWithResult == false);
		assert (startsResult == false);

		checkResults(second, first);

		assert (containsResult == false);
		assert (cotemporalResult == false);
		assert (duringResult == false);
		assert (finishesResult == false);
		assert (isAfterResult == true);
		assert (isBeforeResult == false);
		assert (isFinishedByResult == false);
		assert (isMetByResult == false);
		assert (isOverlappedByResult == false);
		assert (isStartedByResult == false);
		assert (meetsResult == false);
		assert (overlapsWithResult == false);
		assert (startsResult == false);
	}

	@Test
	public void testWithTimePeriod() {
		TimeInstant first = new TimeInstant((long) 5);
		TimePeriod second = new TimePeriod((long) 5, (long) 7);

		checkResults(first, second);

		assert (containsResult == false);
		assert (cotemporalResult == false);
		assert (duringResult == false);
		assert (finishesResult == false);
		assert (isAfterResult == false);
		assert (isBeforeResult == false);
		assert (isFinishedByResult == false);
		assert (isMetByResult == false);
		assert (isOverlappedByResult == false);
		assert (isStartedByResult == false);
		assert (meetsResult == true);
		assert (overlapsWithResult == false);
		assert (startsResult == true);
	}

}
