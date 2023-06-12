package org.dksu.teststand.config

import org.apache.catalina.core.StandardThreadExecutor
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.stereotype.Component


//
//@Component
//class TomcatCustomizer : TomcatServletWebServerFactory() {
//    override fun postProcessContext(context: Context) {
//        val engine: Engine = context.getParent().getParent() as Engine
//        val service: Service = engine.getService()
//        val server: Server = service.getServer()
//        val connector: Connector = service.findConnectors().get(0)
//    }
//}


@Component
class MyTomcatWebServerCustomizer : WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
    override fun customize(factory: TomcatServletWebServerFactory) {
        factory.addConnectorCustomizers(TomcatConnectorCustomizer { connector ->
            val executor = StandardThreadExecutor(
            ).apply {
                maxQueueSize = 10
                name = "myTomcatThreadPool"
                namePrefix = "my-"
                maxThreads = 3
                minSpareThreads = 3
            }
            connector.service.addExecutor(executor)

            connector.protocolHandler.executor = executor
        })
    }
}