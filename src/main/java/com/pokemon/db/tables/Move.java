/**
 * This class is generated by jOOQ
 */
package com.pokemon.db.tables;


import com.pokemon.db.Keys;
import com.pokemon.db.Public;
import com.pokemon.db.tables.records.MoveRecord;

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
public class Move extends TableImpl<MoveRecord> {

	private static final long serialVersionUID = -396545525;

	/**
	 * The reference instance of <code>public.move</code>
	 */
	public static final Move MOVE = new Move();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<MoveRecord> getRecordType() {
		return MoveRecord.class;
	}

	/**
	 * The column <code>public.move.name</code>.
	 */
	public final TableField<MoveRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(45).nullable(false), this, "");

	/**
	 * The column <code>public.move.strength</code>.
	 */
	public final TableField<MoveRecord, Integer> STRENGTH = createField("strength", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>public.move.effect</code>.
	 */
	public final TableField<MoveRecord, String> EFFECT = createField("effect", org.jooq.impl.SQLDataType.VARCHAR.length(45), this, "");

	/**
	 * The column <code>public.move.description</code>.
	 */
	public final TableField<MoveRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

	/**
	 * The column <code>public.move.type</code>.
	 */
	public final TableField<MoveRecord, String> TYPE = createField("type", org.jooq.impl.SQLDataType.VARCHAR.length(45).nullable(false), this, "");

	/**
	 * Create a <code>public.move</code> table reference
	 */
	public Move() {
		this("move", null);
	}

	/**
	 * Create an aliased <code>public.move</code> table reference
	 */
	public Move(String alias) {
		this(alias, MOVE);
	}

	private Move(String alias, Table<MoveRecord> aliased) {
		this(alias, aliased, null);
	}

	private Move(String alias, Table<MoveRecord> aliased, Field<?>[] parameters) {
		super(alias, Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<MoveRecord> getPrimaryKey() {
		return Keys.MOVE_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<MoveRecord>> getKeys() {
		return Arrays.<UniqueKey<MoveRecord>>asList(Keys.MOVE_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<MoveRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<MoveRecord, ?>>asList(Keys.MOVE__MOVE_TYPE_FKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Move as(String alias) {
		return new Move(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Move rename(String name) {
		return new Move(name, null);
	}
}
