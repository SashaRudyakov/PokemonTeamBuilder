package com.pokemon.services

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.pokemon.db.tables.Trainer
import com.pokemon.db.tables.Trains
import com.pokemon.db.tables.Pokemon
import com.pokemon.db.tables.pojos.Trainer
import com.pokemon.db.tables.pojos.Trains
import com.pokemon.db.tables.pojos.PokemonP
import org.springframework.stereotype.Service
import org.jooq.Record

@CompileStatic
@Service
@Slf4j
class TrainerService  {

    private final DSLContext jooq;

    @Autowired
    TrainerService(DSLContext dslc) {
        jooq = dslc;
    }

    // retrieve all trainers
    List getTrainers() {
        def result2 = jooq.select().from(com.pokemon.db.tables.Trainer.TRAINER)
        result2.fetchInto(com.pokemon.db.tables.pojos.Trainer)
    }

    // create a new trainer
    void createTrainer(com.pokemon.db.tables.pojos.Trainer p) {
        jooq.insertInto(com.pokemon.db.tables.Trainer.TRAINER,
                com.pokemon.db.tables.Trainer.TRAINER.NAME,
                com.pokemon.db.tables.Trainer.TRAINER.AGE,
                com.pokemon.db.tables.Trainer.TRAINER.GENDER,
                com.pokemon.db.tables.Trainer.TRAINER.USERNAME
        ).values(
                p.getName(),
                p.getAge(),
                p.getGender(),
                p.getUsername()
        ).execute()
    }

    // delete a trainer
    void deleteTrainer(Integer id) {
        jooq.delete(com.pokemon.db.tables.Trainer.TRAINER).where(
                com.pokemon.db.tables.Trainer.TRAINER.TID.equal(id)).execute()
    }

    // retrieve a trainer by id
    com.pokemon.db.tables.pojos.Trainer getTrainer(int id) {
        Record result = jooq.select().from(com.pokemon.db.tables.Trainer.TRAINER)
                .where(com.pokemon.db.tables.Trainer.TRAINER.TID.equal(id)).fetchOne()
        result.into(com.pokemon.db.tables.pojos.Trainer)
    }

    // update trainer
    void updateTrainer(com.pokemon.db.tables.pojos.Trainer p) {
        def updateQuery = jooq.update(com.pokemon.db.tables.Trainer.TRAINER).set(com.pokemon.db.tables.Trainer.TRAINER.TID, p.getTid())
        updateQuery.set(com.pokemon.db.tables.Trainer.TRAINER.NAME, p.getName())
        updateQuery.set(com.pokemon.db.tables.Trainer.TRAINER.GENDER, p.getGender())
        updateQuery.set(com.pokemon.db.tables.Trainer.TRAINER.AGE, p.getAge())
        updateQuery.set(com.pokemon.db.tables.Trainer.TRAINER.USERNAME, p.getUsername())
        updateQuery.where(com.pokemon.db.tables.Trainer.TRAINER.TID.equal(p.getTid())).execute()
    }

    // retrieve trainer team
    List getTeam(int id) {
        def result = jooq.select()
                .from(com.pokemon.db.tables.Trains.TRAINS
                .join(com.pokemon.db.tables.Pokemon.POKEMON)
                .on(com.pokemon.db.tables.Trains.TRAINS.POKEDEX_NUM.equal(com.pokemon.db.tables.Pokemon.POKEMON.POKEDEX_NUM))
        ).where(com.pokemon.db.tables.Trains.TRAINS.TID.equal(id))

        result.fetchInto(PokemonP);
    }

    // drop pokemon from team
    void dropPokemon(int pid, int tid) {
        jooq.delete(com.pokemon.db.tables.Trains.TRAINS)
                .where(com.pokemon.db.tables.Trains.TRAINS.POKEDEX_NUM.equal(pid))
                .and(com.pokemon.db.tables.Trains.TRAINS.TID.equal(tid))
                .execute()
    }

    // add pokemon to team
    void addPokemon(com.pokemon.db.tables.pojos.Trains trains) {
        jooq.insertInto(com.pokemon.db.tables.Trains.TRAINS,
                com.pokemon.db.tables.Trains.TRAINS.POKEDEX_NUM,
                com.pokemon.db.tables.Trains.TRAINS.TID
        ).values(
                trains.getPokedexNum(),
                trains.getTid()
        ).execute()
    }
}