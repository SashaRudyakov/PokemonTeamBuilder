package com.pokemon.services

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.pokemon.db.tables.Type
import com.pokemon.db.tables.StrongAgainst
import com.pokemon.db.tables.pojos.Type
import com.pokemon.db.tables.pojos.StrongAgainst
import org.springframework.stereotype.Service
import org.jooq.Record

@CompileStatic
@Service
@Slf4j
class TypeService  {

    private final DSLContext jooq;

    @Autowired
    TypeService(DSLContext dslc) {
        jooq = dslc;
    }

    // retrieve all types
    List getTypes() {
        def result2 = jooq.select().from(com.pokemon.db.tables.Type.TYPE)
        result2.fetchInto(com.pokemon.db.tables.pojos.Type)
    }

    // retrieve strong types
    List getStrongAgainst(String name) {
        def result = jooq.select()
                .from(com.pokemon.db.tables.Type.TYPE.join(com.pokemon.db.tables.StrongAgainst.STRONG_AGAINST)
                .on(com.pokemon.db.tables.Type.TYPE.NAME.equal(com.pokemon.db.tables.StrongAgainst.STRONG_AGAINST.WEAK_TYPE)))
                .where(com.pokemon.db.tables.StrongAgainst.STRONG_AGAINST.STRONG_TYPE.equal(name))
        result.fetchInto(com.pokemon.db.tables.pojos.Type)

    }

    // retrieve weak types
    List getWeakAgainst(String name) {
        def result = jooq.select()
                .from(com.pokemon.db.tables.Type.TYPE.join(com.pokemon.db.tables.StrongAgainst.STRONG_AGAINST)
                .on(com.pokemon.db.tables.Type.TYPE.NAME.equal(com.pokemon.db.tables.StrongAgainst.STRONG_AGAINST.STRONG_TYPE)))
                .where(com.pokemon.db.tables.StrongAgainst.STRONG_AGAINST.WEAK_TYPE.equal(name))
        result.fetchInto(com.pokemon.db.tables.pojos.Type)

    }


}