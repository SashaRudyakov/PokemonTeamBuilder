package com.pokemon.services

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.pokemon.db.tables.Pokemon
import com.pokemon.db.tables.Type
import com.pokemon.db.tables.pojos.PokemonP
import com.pokemon.db.tables.pojos.Type
import org.springframework.stereotype.Service
import org.jooq.Record

@CompileStatic
@Service
@Slf4j
class PokemonService  {

    private final DSLContext jooq;

    @Autowired
    PokemonService(DSLContext dslc) {
        jooq = dslc;
    }

    // retrieve all people from the database
    List getAllPokemon() {
        def result2 = jooq.select().from(com.pokemon.db.tables.Pokemon.POKEMON)
        result2.fetchInto(PokemonP)
    }

    // retrieve a person from the database based on id
    PokemonP getPokemon(int id) {
        Record result = jooq.select().from(Pokemon.POKEMON).where(Pokemon.POKEMON.POKEDEX_NUM.equal(id)).fetchOne()
        result.into(PokemonP)
    }

    // update the given person from the database based on first name (MUST BE UNIQUE) NAME CANNOT BE CHANGED
    void updatePokemon(PokemonP p) {
        def updateQuery = jooq.update(Pokemon.POKEMON).set(Pokemon.POKEMON.POKEDEX_NUM, p.getPokedexNum())
        updateQuery.set(Pokemon.POKEMON.NAME, p.getName())
        updateQuery.set(Pokemon.POKEMON.EVOLVES_FROM, p.getEvolvesFrom())
        updateQuery.set(Pokemon.POKEMON.TYPE1, p.getType1())
        updateQuery.set(Pokemon.POKEMON.TYPE2, p.getType2())
        updateQuery.set(Pokemon.POKEMON.MOVE1, p.getMove1())
        updateQuery.set(Pokemon.POKEMON.MOVE2, p.getMove2())
        updateQuery.set(Pokemon.POKEMON.MOVE3, p.getMove3())
        updateQuery.set(Pokemon.POKEMON.MOVE4, p.getMove4())
        updateQuery.where(Pokemon.POKEMON.POKEDEX_NUM.equal(p.getPokedexNum())).execute()
    }
}