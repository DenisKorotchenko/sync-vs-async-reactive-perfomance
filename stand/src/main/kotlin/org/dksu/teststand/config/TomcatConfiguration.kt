package org.dksu.teststand.config

import org.apache.catalina.Executor
import org.apache.catalina.connector.Request
import org.apache.catalina.connector.Response
import org.apache.catalina.core.StandardThreadExecutor
import org.apache.catalina.valves.SemaphoreValve
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
//        factory.addContextValves(
//            object : SemaphoreValve(){
//                override fun permitDenied(request: Request?, response: Response?) {
//                    println("!!!!")
//                    super.permitDenied(request, response)
//                    throw RuntimeException()
//                }
//            }.apply {
//                concurrency = 1
//            }
//        )
        factory.addConnectorCustomizers(TomcatConnectorCustomizer { connector ->
            val executor = MyThreadExecutor(
            ).apply {
                maxQueueSize = 1
                name = "myTomcatThreadPool"
                namePrefix = "my-"
                maxThreads = 5
                minSpareThreads = 5
            }
            connector.service.addExecutor(executor)

            connector.protocolHandler.executor = executor
        })
    }
}