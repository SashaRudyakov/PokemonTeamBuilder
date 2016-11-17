/**
 * This class is generated by jOOQ
 */
package com.pokemon.db.tables;


import com.pokemon.db.Keys;
import com.pokemon.db.Public;
import com.pokemon.db.tables.records.StrongAgainstRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class StrongAgainst extends TableImpl<StrongAgainstRecord> {

	private static final long serialVersionUID = 632299988;

	/**
	 * The reference instance of <code>public.strong_against</code>
	 */
	public static final StrongAgainst STRONG_AGAINST = new StrongAgainst();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<StrongAgainstRecord> getRecordType() {
		return StrongAgainstRecord.class;
	}

	/**
	 * The column <code>public.strong_against.weak_type</code>.
	 */
	public final TableField<StrongAgainstRecord, String> WEAK_TYPE = createField("weak_type", org.jooq.impl.SQLDataType.VARCHAR.length(45).nullable(false), this, "");

	/**
	 * The column <code>public.strong_against.strong_type</code>.
	 */
	public final TableField<StrongAgainstRecord, String> STRONG_TYPE = createField("strong_type", org.jooq.impl.SQLDataType.VARCHAR.length(45).nullable(false), this, "");

	/**
	 * Create a <code>public.strong_against</code> table reference
	 */
	public StrongAgainst() {
		this("strong_against", null);
	}

	/**
	 * Create an aliased <code>public.strong_against</code> table reference
	 */
	public StrongAgainst(String alias) {
		this(alias, STRONG_AGAINST);
	}

	private StrongAgainst(String alias, Table<StrongAgainstRecord> aliased) {
		this(alias, aliased, null);
	}

	private StrongAgainst(String alias, Table<StrongAgainstRecord> aliased, Field<?>[] parameters) {
		super(alias, Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<StrongAgainstRecord> getPrimaryKey() {
		return Keys.STRONG_AGAINST_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<StrongAgainstRecord>> getKeys() {
		return Arrays.<UniqueKey<StrongAgainstRecord>>asList(Keys.STRONG_AGAINST_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<StrongAgainstRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<StrongAgainstRecord, ?>>asList(Keys.STRONG_AGAINST__STRONG_AGAINST_WEAK_TYPE_FKEY, Keys.STRONG_AGAINST__STRONG_AGAINST_STRONG_TYPE_FKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StrongAgainst as(String alias) {
		return new StrongAgainst(alias, this);
	}

	/**
	 * Rename this table
	 */
	public StrongAgainst rename(String name) {
		return new StrongAgainst(name, null);
	}
}
