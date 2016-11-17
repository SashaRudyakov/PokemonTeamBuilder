package com.pokemon.config

import groovy.transform.CompileStatic
import org.jooq.*
//import org.jooq.conf.Settings
//import org.jooq.conf.SettingsTools
import org.jooq.impl.*
//import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
//import org.springframework.transaction.PlatformTransactionManager

import javax.sql.DataSource

/**
 * @author Matthew Little
 */
@Configuration
@CompileStatic
class BeanConfiguration {


    @Bean
    ConnectionProvider connectionProvider(DataSource dataSource) {
        new DataSourceConnectionProvider(dataSource)
    }

    @Bean
    DSLContext jooq(org.jooq.Configuration config) {
        new DefaultDSLContext(config)
    }

    @Bean
    ExceptionTranslator exceptionTranslator() {
        new ExceptionTranslator()
    }

    @Bean
    ExecuteListenerProvider executeListenerProvider(ExceptionTranslator exceptionTranslator) {
        new DefaultExecuteListenerProvider(exceptionTranslator)
    }

    @Bean
    TransactionProvider transactionProvider() {
        new SpringTransactionProvider()
    }

    @Bean
    org.jooq.Configuration jooqConfig(ConnectionProvider connectionProvider,
                                      TransactionProvider transactionProvider,
                                      ExecuteListenerProvider executeListenerProvider) {
        new DefaultConfiguration()
                .derive(connectionProvider)
                .derive(transactionProvider)
                .derive(executeListenerProvider)
                .derive(SQLDialect.POSTGRES_9_4)
    }
}
