package com.pokemon.config
import static org.springframework.transaction.TransactionDefinition.PROPAGATION_NESTED

import org.jooq.Transaction
import org.jooq.TransactionContext
import org.jooq.TransactionProvider
import org.jooq.exception.DataAccessException
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.DefaultTransactionDefinition

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

/**
 * Based on org.jooq.example.spring.SpringTransaction and org.jooq.example.spring.SpringTransactionProvider
 * from https://github.com/jOOQ/jOOQ/tree/master/jOOQ-examples/jOOQ-spring-boot-example.
 *
 * @author Lukas Eder
 * @author Burt Beckwith
 */
@CompileStatic
@Slf4j
class SpringTransactionProvider implements TransactionProvider {

    private static final TransactionDefinition NESTED = new DefaultTransactionDefinition(PROPAGATION_NESTED) as TransactionDefinition

    PlatformTransactionManager transactionManager

    void begin(TransactionContext ctx) {
        log.info 'Begin transaction'
        ctx.transaction new SpringTransaction(transactionManager.getTransaction(NESTED))
    }

    void commit(TransactionContext ctx) throws DataAccessException {
        log.info 'Commit transaction'
        transactionManager.commit(((SpringTransaction) ctx.transaction()).tx)
    }

    void rollback(TransactionContext ctx) throws DataAccessException {
        log.info 'Rollback transaction'
        transactionManager.rollback(((SpringTransaction) ctx.transaction()).tx)
    }

    static class SpringTransaction implements Transaction {
        final TransactionStatus tx

        SpringTransaction(TransactionStatus status) {
            tx = status
        }
    }
}