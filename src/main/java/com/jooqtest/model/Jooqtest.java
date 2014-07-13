/**
 * This class is generated by jOOQ
 */
package com.jooqtest.model;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Jooqtest extends org.jooq.impl.SchemaImpl {

	private static final long serialVersionUID = 1543373967;

	/**
	 * The singleton instance of <code>jooqtest</code>
	 */
	public static final Jooqtest JOOQTEST = new Jooqtest();

	/**
	 * No further instances allowed
	 */
	private Jooqtest() {
		super("jooqtest");
	}

	@Override
	public final java.util.List<org.jooq.Table<?>> getTables() {
		java.util.List result = new java.util.ArrayList();
		result.addAll(getTables0());
		return result;
	}

	private final java.util.List<org.jooq.Table<?>> getTables0() {
		return java.util.Arrays.<org.jooq.Table<?>>asList(
			com.jooqtest.model.tables.DinnerFavorites.DINNER_FAVORITES,
			com.jooqtest.model.tables.DinnerGuests.DINNER_GUESTS,
			com.jooqtest.model.tables.Food.FOOD);
	}
}
