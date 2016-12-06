/**
 * This class is generated by jOOQ
 */
package com.pokemon.db.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


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
public class PokemonP implements Serializable {

	private static final long serialVersionUID = 1087423383;

	String  name;
	Integer pokedexNum;
	Integer evolvesFrom;
	String  type1;
	String  type2;
	String  move1;
	String  move2;
	String  move3;
	String  move4;

	public PokemonP() {}

	public PokemonP(PokemonP value) {
		this.name = value.name;
		this.pokedexNum = value.pokedexNum;
		this.evolvesFrom = value.evolvesFrom;
		this.type1 = value.type1;
		this.type2 = value.type2;
		this.move1 = value.move1;
		this.move2 = value.move2;
		this.move3 = value.move3;
		this.move4 = value.move4;
	}

	public PokemonP(
			String  name,
			Integer pokedexNum,
			Integer evolvesFrom,
			String  type1,
			String  type2,
			String  move1,
			String  move2,
			String  move3,
			String  move4
	) {
		this.name = name;
		this.pokedexNum = pokedexNum;
		this.evolvesFrom = evolvesFrom;
		this.type1 = type1;
		this.type2 = type2;
		this.move1 = move1;
		this.move2 = move2;
		this.move3 = move3;
		this.move4 = move4;
	}

	public String getName() {
		return this.name;
	}

	public Integer getPokedexNum() {
		return this.pokedexNum;
	}

	public Integer getEvolvesFrom() {
		return this.evolvesFrom;
	}

	public String getType1() {
		return this.type1;
	}

	public String getType2() {
		return this.type2;
	}

	public String getMove1() {
		return this.move1;
	}

	public String getMove2() {
		return this.move2;
	}

	public String getMove3() {
		return this.move3;
	}

	public String getMove4() {
		return this.move4;
	}

	public void setPokedexNum(Integer pokedexNum) { this.pokedexNum = pokedexNum; }
	public void setName(String name) { this.name = name; }
	public void setEvolvesFrom(Integer evolvesFrom) { this.evolvesFrom = evolvesFrom; }
	public void setType1(String type1) { this.type1 = type1; }
	public void setType2(String type2) { this.type2 = type2; }
	public void setMove1(String move1) { this.move1 = move1; }
	public void setMove2(String move2) { this.move2 = move2; }
	public void setMove3(String move3) { this.move3 = move3; }
	public void setMove4(String move4) { this.move4 = move4; }

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Pokemon (");

		sb.append(name);
		sb.append(", ").append(pokedexNum);
		sb.append(", ").append(evolvesFrom);
		sb.append(", ").append(type1);
		sb.append(", ").append(type2);
		sb.append(", ").append(move1);
		sb.append(", ").append(move2);
		sb.append(", ").append(move3);
		sb.append(", ").append(move4);

		sb.append(")");
		return sb.toString();
	}
}