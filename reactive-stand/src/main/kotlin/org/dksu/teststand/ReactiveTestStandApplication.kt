package org.dksu.teststand

import io.r2dbc.spi.ConnectionFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.stereotype.Component
import java.time.Duration


@Configuration
class Config {

	@Bean
	fun initializer(connectionFactory: ConnectionFactory): ConnectionFactoryInitializer {
		val initializer = ConnectionFactoryInitializer()
		initializer.setConnectionFactory(connectionFactory)
//		val populator = CompositeDatabasePopulator()
//		populator.addPopulators(ResourceDatabasePopulator(ClassPathResource("./sql/schema.sql")))
//		initializer.setDatabasePopulator(populator)
		return initializer
	}
}


@Component
class NettyWebServerFactoryPortCustomizer : WebServerFactoryCustomizer<NettyReactiveWebServerFactory> {
	override fun customize(serverFactory: NettyReactiveWebServerFactory) {
		//serverFactory.setLifecycleTimeout(Duration.ofMillis(3000))
	}
}

@SpringBootApplication
class ReactiveTestStandApplication

fun main(args: Array<String>) {
	runApplication<ReactiveTestStandApplication>(*args)
}
