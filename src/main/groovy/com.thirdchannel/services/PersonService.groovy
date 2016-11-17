package com.thirdchannel.services

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.thirdchannel.db.tables.People2
import com.thirdchannel.model.Person
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
    List<Person> getPeople() {
        def result2 = jooq.select().from(People2.PEOPLE2)

        result2.fetchInto(Person)
    }

    // retrieve a person from the database based on id
    Person getPerson(int id) {
        Record result = jooq.select().from(People2.PEOPLE2).where(People2.PEOPLE2.ID.equal(id)).fetchOne()
        result.into(Person)
    }

    // update the given person from the database based on first name (MUST BE UNIQUE) NAME CANNOT BE CHANGED
    void updatePerson(Person p) {
        def updateQuery = jooq.update(People2.PEOPLE2).set(People2.PEOPLE2.FIRST_NAME, p.firstName)
        updateQuery.set(People2.PEOPLE2.LAST_NAME, p.lastName)
        updateQuery.set(People2.PEOPLE2.AGE, p.age)
        updateQuery.where(People2.PEOPLE2.ID.equal(p.id)).execute()
    }

    // create a new person
    void createPerson(Person p) {
        jooq.insertInto(People2.PEOPLE2,
                People2.PEOPLE2.FIRST_NAME,
                People2.PEOPLE2.LAST_NAME,
                People2.PEOPLE2.AGE
        ).values(
                p.firstName,
                p.lastName,
                p.age
        ).execute()
    }

    // delete a person
    void deletePerson(int id) {
        jooq.delete(People2.PEOPLE2).where(People2.PEOPLE2.ID.equal(id)).execute()
    }

}