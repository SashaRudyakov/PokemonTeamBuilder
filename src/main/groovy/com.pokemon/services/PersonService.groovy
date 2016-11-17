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
class PersonService  {

    private final DSLContext jooq;

    @Autowired
    PersonService(DSLContext dslc) {
        jooq = dslc;
    }

    // retrieve all people from the database
    List getPeople() {
        def result2 = jooq.select().from(com.pokemon.db.tables.Pokemon.POKEMON)
//
        result2.fetchInto(PokemonP)
    }

    // retrieve all people from the database
    List getTypes() {
        def result = jooq.select().from(com.pokemon.db.tables.Type.TYPE)
//
        result.fetchInto(com.pokemon.db.tables.pojos.Type)
    }

    // retrieve a person from the database based on id
    PokemonP getPerson(int id) {
        Record result = jooq.select().from(Pokemon.POKEMON).where(Pokemon.POKEMON.POKEDEX_NUM.equal(id)).fetchOne()
        result.into(PokemonP)
    }

    // update the given person from the database based on first name (MUST BE UNIQUE) NAME CANNOT BE CHANGED
    void updatePerson(PokemonP p) {
        def updateQuery = jooq.update(Pokemon.POKEMON).set(Pokemon.POKEMON.POKEDEX_NUM, p.getPokedexNum())
        updateQuery.set(Pokemon.POKEMON.NAME, p.getName())
        updateQuery.set(Pokemon.POKEMON.EVOLVES_TO, p.getEvolvesTo())
        updateQuery.set(Pokemon.POKEMON.TYPE1, p.getType1())
        updateQuery.set(Pokemon.POKEMON.TYPE2, p.getType2())
        updateQuery.set(Pokemon.POKEMON.MOVE1, p.getMove1())
        updateQuery.set(Pokemon.POKEMON.MOVE2, p.getMove2())
        updateQuery.set(Pokemon.POKEMON.MOVE3, p.getMove3())
        updateQuery.set(Pokemon.POKEMON.MOVE4, p.getMove4())
        updateQuery.set(Pokemon.POKEMON.SPRITE, p.getSprite())
        updateQuery.where(Pokemon.POKEMON.POKEDEX_NUM.equal(p.getPokedexNum())).execute()
    }

    // create a new person
    void createPerson(PokemonP p) {
        jooq.insertInto(Pokemon.POKEMON,
                Pokemon.POKEMON.POKEDEX_NUM,
                Pokemon.POKEMON.NAME,
                Pokemon.POKEMON.EVOLVES_TO,
                Pokemon.POKEMON.TYPE1,
                Pokemon.POKEMON.TYPE2,
                Pokemon.POKEMON.MOVE1,
                Pokemon.POKEMON.MOVE2,
                Pokemon.POKEMON.MOVE3,
                Pokemon.POKEMON.MOVE4,
                Pokemon.POKEMON.SPRITE
        ).values(
                p.getPokedexNum(),
                p.getName(),
                p.getEvolvesTo(),
                p.getType1(),
                p.getType2(),
                p.getMove1(),
                p.getMove2(),
                p.getMove3(),
                p.getMove4(),
                p.getSprite()
        ).execute()
    }

    void createType(com.pokemon.db.tables.pojos.Type t) {
        jooq.insertInto(com.pokemon.db.tables.Type.TYPE,
                com.pokemon.db.tables.Type.TYPE.NAME
        ).values(
                t.getName()
        ).execute()
    }
//
    // delete a person
    void deletePerson(int id) {
        jooq.delete(com.pokemon.db.tables.Pokemon.POKEMON).where(
                com.pokemon.db.tables.Pokemon.POKEMON.POKEDEX_NUM.equal(id)).execute()
    }

}