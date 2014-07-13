/**
 * This class is generated by jOOQ
 */
package com.jooqtest.model.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DinnerGuestsRecord extends org.jooq.impl.UpdatableRecordImpl<com.jooqtest.model.tables.records.DinnerGuestsRecord> implements org.jooq.Record2<java.lang.Long, java.lang.String> {

	private static final long serialVersionUID = 1022986379;

	/**
	 * Setter for <code>jooqtest.dinner_guests.id</code>.
	 */
	public void setId(java.lang.Long value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>jooqtest.dinner_guests.id</code>.
	 */
	public java.lang.Long getId() {
		return (java.lang.Long) getValue(0);
	}

	/**
	 * Setter for <code>jooqtest.dinner_guests.name</code>.
	 */
	public void setName(java.lang.String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>jooqtest.dinner_guests.name</code>.
	 */
	public java.lang.String getName() {
		return (java.lang.String) getValue(1);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.Long> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record2 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row2<java.lang.Long, java.lang.String> fieldsRow() {
		return (org.jooq.Row2) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row2<java.lang.Long, java.lang.String> valuesRow() {
		return (org.jooq.Row2) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field1() {
		return com.jooqtest.model.tables.DinnerGuests.DINNER_GUESTS.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return com.jooqtest.model.tables.DinnerGuests.DINNER_GUESTS.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DinnerGuestsRecord value1(java.lang.Long value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DinnerGuestsRecord value2(java.lang.String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DinnerGuestsRecord values(java.lang.Long value1, java.lang.String value2) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached DinnerGuestsRecord
	 */
	public DinnerGuestsRecord() {
		super(com.jooqtest.model.tables.DinnerGuests.DINNER_GUESTS);
	}

	/**
	 * Create a detached, initialised DinnerGuestsRecord
	 */
	public DinnerGuestsRecord(java.lang.Long id, java.lang.String name) {
		super(com.jooqtest.model.tables.DinnerGuests.DINNER_GUESTS);

		setValue(0, id);
		setValue(1, name);
	}
}
