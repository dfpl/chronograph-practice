package org.dfpl.chronograph.common;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 * @author Jaewook Byun, Ph.D., Assistant Professor, Department of Software,
 *         Sejong University (slightly modify interface)
 */
public class Tokens {

	public static final String VERSION = "1.0";

	public static enum TimeType {
		/**
		 * Start time first Short Duration first
		 */
		type1,
		/**
		 * Start time first Long Duration first
		 */
		type2,
		/**
		 * Finish time first Short Duration first
		 */
		type3,
		/**
		 * Finish time first Long Duration first
		 */
		type4
	}

	/**
	 * Comparators for numerical values compatible with MongoDB Query Language
	 * 
	 */
	public static enum NC {
		/**
		 * Greater than
		 */
		$gt,
		/**
		 * Less than
		 */
		$lt,
		/**
		 * Equal to
		 */
		$eq,
		/**
		 * Greater than or equal to
		 */
		$gte,
		/**
		 * Less than or equal to
		 */
		$lte,
		/**
		 * Not equal to
		 */
		$ne
	}

	public static enum Conjunction {
		$and, $or
	}

	/**
	 * Full Comparator
	 * 
	 * @author jack
	 *
	 */
	public static enum FC {
		/**
		 * Greater than
		 */
		$gt,
		/**
		 * Less than
		 */
		$lt,
		/**
		 * Equal to
		 */
		$eq,
		/**
		 * Greater than or equal to
		 */
		$gte,
		/**
		 * Less than or equal to
		 */
		$lte,
		/**
		 * Not equal to
		 */
		$ne,
		/**
		 * In collection
		 */
		$in,
		/**
		 * Not in collection
		 */
		$nin, $exists, $max, $min
	}

	public static enum Order {
		DECR, INCR
	}
}