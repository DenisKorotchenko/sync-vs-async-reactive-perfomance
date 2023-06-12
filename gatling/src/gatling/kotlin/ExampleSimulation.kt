import io.gatling.javaapi.core.CoreDsl.exec
import io.gatling.javaapi.core.CoreDsl.rampUsersPerSec
import io.gatling.javaapi.core.CoreDsl.scenario
import io.gatling.javaapi.core.PopulationBuilder
import io.gatling.javaapi.core.Simulation
import io.gatling.javaapi.http.HttpDsl.http
import java.time.Duration

class ExampleSimulation : Simulation() {

    private val httpReactive =
        http.baseUrl("http://reactive:8080/")
            .acceptHeader("*/*")
    private val httpSync =
        http.baseUrl("http://sync:8080/test-stand/")
            .acceptHeader("*/*")

    private val syncWarmup1 = scenario("Sync GET WarmUp").exec(
        exec(
            http("Sync GET Warmup")
                .get("data/getRandom")
                .requestTimeout(Duration.ofSeconds(3))
        )
    )
    private val reactWarmup1 = scenario("React GET WarmUp").exec(
        exec(
            http("React GET Warmup")
                .get("data/getRandom")
                .requestTimeout(Duration.ofSeconds(3))
        )
    )

    private val syncScens1 = mutableListOf<PopulationBuilder>()
    private val reactScens1 = mutableListOf<PopulationBuilder>()

    init {
        val rpss = listOf(135.0, 85.0, 80.0, 70.0, 60.0)
        for (rps in rpss) {
            syncScens1.add(
                scenario("Sync GET ${rps.toLong()} RPS")
                    .exec(
                        exec(
                            http("Sync GET ${rps.toLong()} RPS")
                                .get("data/getRandom")
                                .requestTimeout(Duration.ofSeconds(3))
                        )
                    )
                    .injectOpen(rampUsersPerSec(rps).to(rps + 0.1).during(60 * 5))
            )
            reactScens1.add(
                scenario("React GET ${rps.toLong()} RPS")
                    .exec(
                        exec(
                            http("React GET ${rps.toLong()} RPS")
                                .get("data/getRandom")
                                .requestTimeout(Duration.ofSeconds(3))
                        )
                    )
                    .injectOpen(rampUsersPerSec(rps).to(rps + 0.1).during(60 * 5))
            )
        }
    }

    init {
        setUp(
            syncWarmup1.injectOpen(rampUsersPerSec(10.9).to(60.0).during(60 * 5))
                .andThen(syncScens1[0].protocols(httpSync))
                .andThen(syncScens1[1].protocols(httpSync))
                .andThen(syncScens1[2].protocols(httpSync))
                .andThen(syncScens1[3].protocols(httpSync))
                .andThen(syncScens1[4].protocols(httpSync))
                .protocols(httpSync),

            reactWarmup1.injectOpen(rampUsersPerSec(10.9).to(60.0).during(60 * 5))
                .andThen(reactScens1[0].protocols(httpReactive))
                .andThen(reactScens1[1].protocols(httpReactive))
                .andThen(reactScens1[2].protocols(httpReactive))
                .andThen(reactScens1[3].protocols(httpReactive))
                .andThen(reactScens1[4].protocols(httpReactive))
                .protocols(httpReactive)
        )
    }
}
