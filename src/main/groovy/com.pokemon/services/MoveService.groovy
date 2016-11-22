package com.pokemon.services

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.pokemon.db.tables.Move
import com.pokemon.db.tables.pojos.Move
import org.jooq.Record

@CompileStatic
@Service
@Slf4j
class MoveService  {

    private final DSLContext jooq;

    @Autowired
    MoveService(DSLContext dslc) {
        jooq = dslc;
    }

    // retrieve all people from the database
    com.pokemon.db.tables.pojos.Move getMove(String moveName) {
        Record result = jooq.select().from(com.pokemon.db.tables.Move.MOVE).where(
                com.pokemon.db.tables.Move.MOVE.NAME.equal(moveName)).fetchOne()
        result.into(com.pokemon.db.tables.pojos.Move)
    }
}