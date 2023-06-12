package org.dksu.teststand

import io.r2dbc.spi.ConnectionFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer


@Configuration
class Config {

    @Bean
    fun initializer(connectionFactory: ConnectionFactory): ConnectionFactoryInitializer {
        val initializer = ConnectionFactoryInitializer()
        initializer.setConnectionFactory(connectionFactory)
        return initializer
    }
}

@SpringBootApplication
class ReactiveTestStandApplication

fun main(args: Array<String>) {
    runApplication<ReactiveTestStandApplication>(*args)
}
