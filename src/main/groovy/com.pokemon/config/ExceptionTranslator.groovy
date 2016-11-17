package com.pokemon.config
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

import org.jooq.ExecuteContext
import org.jooq.SQLDialect
import org.jooq.impl.DefaultExecuteListener
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator
import org.springframework.jdbc.support.SQLExceptionTranslator
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

/**
 * Based on org.jooq.example.spring.ExceptionTranslator from https://github.com/jOOQ/jOOQ/tree/master/jOOQ-examples/jOOQ-spring-boot-example.
 *
 * @author Petri Kainulainen
 * @author Adam Zell
 * @author Lukas Eder
 * @author Burt Beckwith
 */
@CompileStatic
@Slf4j
class ExceptionTranslator extends DefaultExecuteListener {

    private static final long serialVersionUID = 1

    private final SQLExceptionTranslator noDialectExceptionTranslator = new SQLStateSQLExceptionTranslator()
    private final ConcurrentMap<String, SQLErrorCodeSQLExceptionTranslator> translatorsByDialect =
            new ConcurrentHashMap<String, SQLErrorCodeSQLExceptionTranslator>()

    private final Map<String, String> jooqNamesToSpringNames = [
            DERBY:    'Derby',
            H2:       'H2',
            HSQLDB:   'HSQL',
            MARIADB:  'MySQL',
            MYSQL:    'MySQL',
            POSTGRES: 'PostgreSQL']

    void exception(ExecuteContext context) {
        if (context.sqlException()) {
            context.exception getTranslator(context.configuration().dialect()).translate('jOOQ', context.sql(), context.sqlException())
        }
        else if (context.exception()) {
            context.exception context.exception()
        }
    }

    protected SQLExceptionTranslator getTranslator(SQLDialect dialect) {
        if (!dialect) {
            return noDialectExceptionTranslator
        }

        String name = dialect.name()
        SQLErrorCodeSQLExceptionTranslator translator = translatorsByDialect.get(name)
        if (!translator) {
            // not atomic, but they're not expensive to create
            translator = new SQLErrorCodeSQLExceptionTranslator(jooqNamesToSpringNames[name])
            translatorsByDialect[name] = translator
        }

        translator
    }
}